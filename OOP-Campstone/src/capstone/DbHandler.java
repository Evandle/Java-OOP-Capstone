package capstone;
import javax.management.Query;
import java.sql.*;

public class DbHandler {
    private static final String CONNECTION_STRING = "jdbc:sqlite:capstone.db";

    public static Connection connect() {
        try {
            Connection conn = DriverManager.getConnection(CONNECTION_STRING);
            System.out.println("Connected to database successfully at: " + CONNECTION_STRING);
            return conn;
        } catch(SQLException e) {
            System.out.println("Database connection failed: " + e.getMessage());
            return null;
        }
    }

    public static User loginUser(String username, String password) {
        String query = "SELECT * FROM USERS WHERE USERNAME = ? AND PASSWORD = ?";

        try(Connection conn = connect();
            PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, username);
            pstmt.setString(2, password);

            ResultSet rs = pstmt.executeQuery();

            if(rs.next())  {
                String role = rs.getString("role");
                String dbUser = rs.getString("username");

                User user;
                if("ADMIN".equalsIgnoreCase(role)) {
                    user = new Admin(dbUser, password);
                } else {
                    user = new Customer(dbUser, password, rs.getString("address"));
                }

                user.setId(rs.getInt("user_id"));
                return user;
            }
        } catch (SQLException e) {
            System.out.println("Login failed: " + e.getMessage());
        }
        return null;
    }

    public static boolean registerUser(String username, String password, String role, String address) {
        String query = "INSERT INTO USERS (username, password, role, address) VALUES (?, ?, ?, ?)";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.setString(3, role);
            pstmt.setString(4, address);

            int rows = pstmt.executeUpdate();
            if(rows > 0) {
                System.out.println("User registered successfully: " + username);
                return true;
            } else {
                System.out.println("No rows affected while inserting user.");
            }

        } catch (SQLException e) {
            if(e.getMessage().contains("UNIQUE constraint failed")) {
                System.out.println("Username is already in use: " + username);
            } else {
                System.out.println("Error registering user: " + e.getMessage());
            }
        }
        return false;
    }

    public static boolean deleteUser(String username) {
        String query = "DELETE FROM USERS WHERE USERNAME = ?";

        try(Connection conn = connect();
            PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, username);
            int rows = pstmt.executeUpdate();

            if(rows > 0) {
                System.out.println("Deleted user: " + username);
                return true;
            } else {
                System.out.println("No user found with username: " + username);
            }

        } catch (SQLException e) {
            System.out.println("Error deleting user: " + e.getMessage());
        }
        return false;
    }

    public static boolean addItem(Item item) {
        String query = "INSERT INTO items (name, price, quantity_in_stock, category_id) VALUES (?, ?, ?, ?)";

        try(Connection conn = connect();
        PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, item.getItemName());
            pstmt.setDouble(2, item.getPrice());
            pstmt.setInt(3, item.getStock());
            pstmt.setInt(4, item.getCategoryId());

            pstmt.executeUpdate();
            System.out.println("Item added successfully: " + item.getItemName());
            return true;

        } catch (SQLException e) {
            if(e.getMessage().contains("UNIQUE constraint failed")) {
                System.out.println("Item name is already in use: " + item.getItemName());
            } else {
                System.out.println("Error adding Item: " + e.getMessage());
            }
        }
        return false;
    }

    public static boolean deleteItem(int itemId) {
        String query = "DELETE FROM items WHERE item_id = ?";

        try(Connection conn = connect();
        PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, itemId);
            int rowsAffected = pstmt.executeUpdate();

            if(rowsAffected > 0) {
                System.out.println("Item deleted successfully: " + itemId);
                return true;
            } else {
                System.out.println("Error: Item ID" + itemId + " not found.");
                return false;
            }
        }catch (SQLException e) {
            if(e.getMessage().contains("UNIQUE constraint failed")) {
                System.out.println("Item name is already in use: " + itemId);
            } else  {
                System.out.println("Error deleting Item: " + e.getMessage());
            }
            return  false;
        }
    }

    public static boolean updateStock(int itemId, int quantity) {
        String query =  "UPDATE items SET quantity_in_stock = quantity_in_stock + ? WHERE item_id = ?";

        try(Connection conn = connect();
        PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, quantity);
            pstmt.setInt(2, itemId);

            int rowsAffected = pstmt.executeUpdate();
            if(rowsAffected > 0) {
                System.out.println("Item added successfully: " + itemId);
                return true;
            } else  {
                System.out.println("Error: Item ID" + itemId + " not found.");
                return false;
            }

        }catch (SQLException e) {
            System.out.println("Item ID is already in use: " + itemId);
            return false;
        }
    }

    public static boolean setStock(int itemId, int quantity) {
        String query = "UPDATE items SET quantity_in_stock = ? WHERE item_id = ?";

        try(Connection conn = connect();
        PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, quantity);
            pstmt.setInt(2, itemId);

            int rowsAffected = pstmt.executeUpdate();
            if(rowsAffected > 0) {
                System.out.println("Item added successfully: " + itemId);
                return true;
            } else {
                System.out.println("Error: Item ID" + itemId + " not found.");
                return false;
            }

        }catch (SQLException e) {
            System.out.println("Item ID is already in use: " + itemId);
            return false;
        }
    }

    public static java.util.ArrayList<String> getCategoryNames() {
        java.util.ArrayList<String> list = new java.util.ArrayList<>();
        String query = "SELECT name FROM categories";
        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                list.add(rs.getString("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static java.util.ArrayList<Item> getItems() {
        java.util.ArrayList<Item> items = new java.util.ArrayList<>();
        String query = "SELECT * FROM items";
        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                int id = rs.getInt("item_id");
                String name = rs.getString("name");
                double price = rs.getDouble("price");
                int stock = rs.getInt("quantity_in_stock");
                int categoryId = rs.getInt("category_id");

                Item item = new Item(name, price, stock, categoryId);

                item.setId(id);

                items.add(item);
            }

        } catch (SQLException e) {
            System.out.println("Error fetching items: " + e.getMessage());
            e.printStackTrace();
        }
        return items;
    }

    public static java.util.ArrayList<User> getUsers() {
        java.util.ArrayList<User> users = new java.util.ArrayList<>();
        String query = "SELECT * FROM users";
        try (Connection conn = connect();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                int id =  rs.getInt("user_id");
                String username = rs.getString("username");
                String password = rs.getString("password");
                String role = rs.getString("role");

                User user = null;
                if("ADMIN".equals(role)) {
                    user = new Admin(id, username, password);
                }
                else if("CUSTOMER".equals(role)) {
                    String address = rs.getString("address");
                    user = new Customer(id, username, password, address);
                }
                else if("EMPLOYEE".equals(role)) {
                    user = new Employee(id, username, password);
                }
                if(user != null) {
                    users.add(user);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error fetching users: " + e.getMessage());
            e.printStackTrace();
        }
        return users;
    }

    private static boolean isItemInCart(int userId, int itemId) {
        String query = "SELECT 1 FROM cart_items WHERE user_id = ? AND item_id = ?";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, userId);
            pstmt.setInt(2, itemId);

            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next();
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean addToCart(int userId, int itemId, int quantity) {
        String query;

        if (isItemInCart(userId, itemId)) {
            query = "UPDATE cart_items SET quantity = quantity + ? WHERE user_id = ? AND item_id = ?";
        } else {
            query = "INSERT INTO cart_items (quantity, user_id, item_id) VALUES (?, ?, ?)";
        }

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, quantity);
            pstmt.setInt(2, userId);
            pstmt.setInt(3, itemId);

            int rows = pstmt.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            System.out.println("Error adding to cart: " + e.getMessage());
            return false;
        }
    }

    public static void deductStock(java.util.List<CartItem> cartItems) {
        String query = "UPDATE items SET quantity_in_stock = quantity_in_stock - ? WHERE item_id = ?";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            conn.setAutoCommit(false);

            for (CartItem c : cartItems) {

                pstmt.setInt(1, c.getQuantity());

                pstmt.setInt(2, c.getItem().getId());

                pstmt.addBatch();
            }

            pstmt.executeBatch();
            conn.commit();
            System.out.println("Stock updated successfully.");

        } catch (SQLException e) {
            System.out.println("Error updating stock: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static java.util.ArrayList<CartItem> getUserCart(int userId) {
        java.util.ArrayList<CartItem> cart = new java.util.ArrayList<>();

        String query = "SELECT c.item_id, i.name, i.price, c.quantity " +
                "FROM cart_items c " +
                "JOIN items i ON c.item_id = i.item_id " +
                "WHERE c.user_id = ?";

        try (java.sql.Connection conn = connect();
             java.sql.PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, userId);
            java.sql.ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                cart.add(new CartItem(
                        rs.getInt("item_id"),
                        rs.getString("name"),
                        rs.getDouble("price"),
                        rs.getInt("quantity")
                ));
            }
        } catch (java.sql.SQLException e) {
            System.out.println("Error fetching cart: " + e.getMessage());
            e.printStackTrace();
        }
        return cart;
    }

    public static void clearUserCart(int userId) {
        String query = "DELETE FROM cart_items WHERE user_id = ?";

        try (java.sql.Connection conn = connect();
             java.sql.PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, userId);
            pstmt.executeUpdate();
            System.out.println("Cart cleared for User ID: " + userId);

        } catch (java.sql.SQLException e) {
            System.out.println("Error clearing cart: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void removeFromCart(int userId, int itemId) {
        String query = "DELETE FROM cart_items WHERE user_id = ? AND item_id = ?";

        try (java.sql.Connection conn = connect();
             java.sql.PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, userId);
            pstmt.setInt(2, itemId);
            pstmt.executeUpdate();

        } catch (java.sql.SQLException e) {
            System.out.println("Error removing from cart: " + e.getMessage());
            e.printStackTrace();
        }
    }
}