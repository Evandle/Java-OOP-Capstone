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

    public static boolean registerUser(String username, String password, String role) {
        String query = "INSERT INTO USERS (username, password, role) VALUES (?, ?, ?)";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            if (conn == null) {
                System.out.println("Cannot connect to database.");
                return false;
            }

            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.setString(3, role);

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

    // Optional: quick test
    public static void main(String[] args) {
        boolean success = registerUser("testuser", "password123", "CUSTOMER");
        System.out.println("Register success: " + success);
    }
}