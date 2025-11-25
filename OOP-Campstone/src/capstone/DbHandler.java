package capstone;
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

                if("ADMIN".equalsIgnoreCase(role)) {
                    return new Admin(dbUser, password);
                } else {
                    return new Customer(dbUser, password, rs.getString("address"));
                }
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
        String query = "INSERT INTO items (name, price, quantity_in_stock) VALUES (?, ?, ?)";

        try(Connection conn = connect();
        PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, item.getItemName());
            pstmt.setString(2, item.getPrice() + "");
            pstmt.setString(3, item.getStock() + "");

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
}