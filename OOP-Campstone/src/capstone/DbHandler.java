package capstone;
import java.sql.*;

public class DbHandler {
    private static final String CONNECTION_STRING = "jdbc:sqlite:capstone.db";

    public static Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(CONNECTION_STRING);
            System.out.println("Connected to database successfully");
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
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
            throw new RuntimeException(e);
        }

        return null;
    }

    public static boolean registerUser(String username, String password, String role) {
        String query = "INSERT INTO users (username, password, role) VALUES (?, ?, ?)";

        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.setString(3, role);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            if(e.getMessage().contains("UNIQUE constraint failed")) {
                System.out.println("Username is already in use.");
            } else {
                System.out.println(e.getMessage());
            }
            return false;
        }
    }

    public static boolean deleteUser(String username) {
        String query = "DELETE FROM USERS WHERE USERNAME = ?";

        try(Connection conn = connect();
            PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, username);
            pstmt.executeUpdate();

            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
}

