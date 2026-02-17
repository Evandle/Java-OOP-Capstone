import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.SQLException;

public class DatabaseSetup {
    private static final String DB_URL = "jdbc:sqlite:capstone.db";

    public static void main(String[] args) {
        resetDatabase();
    }

    public static void resetDatabase() {
        System.out.println("Beginning database reset...");

        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {

            // 1. Enable Foreign Keys (SQLite specific)
            stmt.execute("PRAGMA foreign_keys = ON;");

            // 2. DROP TABLES (Order matters due to foreign keys)
            System.out.println("Dropping existing tables...");
            stmt.execute("DROP TABLE IF EXISTS items;");
            stmt.execute("DROP TABLE IF EXISTS users;");
            stmt.execute("DROP TABLE IF EXISTS categories;");

            // 3. CREATE TABLES
            System.out.println("Creating new tables...");

            // Categories
            stmt.execute("CREATE TABLE categories (" +
                    "category_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "name TEXT NOT NULL);");

            // Users
            stmt.execute("CREATE TABLE users (" +
                    "user_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "username TEXT UNIQUE NOT NULL, " +
                    "password TEXT NOT NULL, " +
                    "role TEXT DEFAULT 'CUSTOMER', " +
                    "address TEXT);");

            // Items
            stmt.execute("CREATE TABLE items (" +
                    "item_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "name TEXT NOT NULL, " +
                    "price DECIMAL(10, 2) NOT NULL, " +
                    "quantity_in_stock INTEGER DEFAULT 0, " +
                    "category_id INTEGER, " +
                    "FOREIGN KEY (category_id) REFERENCES categories(category_id));");

            // 4. SEED DATA
            System.out.println("Seeding data...");

            // A. Categories (Fixed IDs 1-7)
            stmt.execute("INSERT INTO categories (category_id, name) VALUES " +
                    "(1, 'Frozen Goods'), " +
                    "(2, 'General Necessities'), " +
                    "(3, 'Drinks'), " +
                    "(4, 'Snacks'), " +
                    "(5, 'Canned Goods'), " +
                    "(6, 'Washing Necessities'), " +
                    "(7, 'Bath');");

            stmt.execute("INSERT INTO users (username, password, role, address) VALUES " +
                    "('admin', 'admin', 'ADMIN', 'My House, Place');");

            // C. Items (Bulk Insert for Demo)
            conn.setAutoCommit(false);

            // Category 1: Frozen
            stmt.addBatch("INSERT INTO items (name, price, quantity_in_stock, category_id) VALUES ('Purefoods Tender Juicy Hotdog (1kg)', 215.00, 50, 1)");
            stmt.addBatch("INSERT INTO items (name, price, quantity_in_stock, category_id) VALUES ('Pampangas Best Tocino (450g)', 145.00, 40, 1)");
            stmt.addBatch("INSERT INTO items (name, price, quantity_in_stock, category_id) VALUES ('Magnolia Chicken Whole (1.2kg)', 230.00, 30, 1)");
            stmt.addBatch("INSERT INTO items (name, price, quantity_in_stock, category_id) VALUES ('Selecta Ice Cream Double Dutch (1.5L)', 250.00, 25, 1)");

            // Category 2: General
            stmt.addBatch("INSERT INTO items (name, price, quantity_in_stock, category_id) VALUES ('Jasmine Rice (5kg)', 320.00, 100, 2)");
            stmt.addBatch("INSERT INTO items (name, price, quantity_in_stock, category_id) VALUES ('Datu Puti Soy Sauce (1L)', 58.00, 120, 2)");
            stmt.addBatch("INSERT INTO items (name, price, quantity_in_stock, category_id) VALUES ('Cooking Oil (350ml)', 45.00, 150, 2)");
            stmt.addBatch("INSERT INTO items (name, price, quantity_in_stock, category_id) VALUES ('Maggi Magic Sarap (Pack)', 55.00, 200, 2)");
            stmt.addBatch("INSERT INTO items (name, price, quantity_in_stock, category_id) VALUES ('Tray of Eggs (30pcs)', 240.00, 40, 2)");

            // Category 3: Drinks
            stmt.addBatch("INSERT INTO items (name, price, quantity_in_stock, category_id) VALUES ('Coca-Cola (1.5L)', 75.00, 120, 3)");
            stmt.addBatch("INSERT INTO items (name, price, quantity_in_stock, category_id) VALUES ('C2 Apple Green Tea (500ml)', 28.00, 150, 3)");
            stmt.addBatch("INSERT INTO items (name, price, quantity_in_stock, category_id) VALUES ('Nescafe Classic Stick', 3.00, 1000, 3)");
            stmt.addBatch("INSERT INTO items (name, price, quantity_in_stock, category_id) VALUES ('San Miguel Pale Pilsen', 55.00, 200, 3)");
            stmt.addBatch("INSERT INTO items (name, price, quantity_in_stock, category_id) VALUES ('Summit Mineral Water (1L)', 25.00, 100, 3)");

            // Category 4: Snacks
            stmt.addBatch("INSERT INTO items (name, price, quantity_in_stock, category_id) VALUES ('Jack n Jill Chippy Red', 32.00, 150, 4)");
            stmt.addBatch("INSERT INTO items (name, price, quantity_in_stock, category_id) VALUES ('Piattos Cheese (85g)', 35.00, 140, 4)");
            stmt.addBatch("INSERT INTO items (name, price, quantity_in_stock, category_id) VALUES ('SkyFlakes Crackers (Pack)', 65.00, 200, 4)");
            stmt.addBatch("INSERT INTO items (name, price, quantity_in_stock, category_id) VALUES ('Lucky Me! Pancit Canton Kalamansi', 15.00, 500, 4)");
            stmt.addBatch("INSERT INTO items (name, price, quantity_in_stock, category_id) VALUES ('Dried Mangoes (100g)', 150.00, 60, 4)");

            // Category 5: Canned
            stmt.addBatch("INSERT INTO items (name, price, quantity_in_stock, category_id) VALUES ('Century Tuna Flakes in Oil', 38.00, 150, 5)");
            stmt.addBatch("INSERT INTO items (name, price, quantity_in_stock, category_id) VALUES ('Argentina Corned Beef (175g)', 45.00, 200, 5)");
            stmt.addBatch("INSERT INTO items (name, price, quantity_in_stock, category_id) VALUES ('555 Sardines Tomato Sauce', 22.00, 300, 5)");
            stmt.addBatch("INSERT INTO items (name, price, quantity_in_stock, category_id) VALUES ('Spam Luncheon Meat', 245.00, 60, 5)");

            // Category 6: Washing
            stmt.addBatch("INSERT INTO items (name, price, quantity_in_stock, category_id) VALUES ('Surf Powder Cherry Blossom (1kg)', 180.00, 60, 6)");
            stmt.addBatch("INSERT INTO items (name, price, quantity_in_stock, category_id) VALUES ('Joy Dishwashing Liquid (200ml)', 55.00, 120, 6)");
            stmt.addBatch("INSERT INTO items (name, price, quantity_in_stock, category_id) VALUES ('Downy Sachet', 6.00, 500, 6)");
            stmt.addBatch("INSERT INTO items (name, price, quantity_in_stock, category_id) VALUES ('Zonrox Bleach (1L)', 48.00, 80, 6)");

            // Category 7: Bath
            stmt.addBatch("INSERT INTO items (name, price, quantity_in_stock, category_id) VALUES ('Safeguard White Soap', 45.00, 150, 7)");
            stmt.addBatch("INSERT INTO items (name, price, quantity_in_stock, category_id) VALUES ('Palmolive Shampoo Sachet', 7.00, 500, 7)");
            stmt.addBatch("INSERT INTO items (name, price, quantity_in_stock, category_id) VALUES ('Colgate Toothpaste (145ml)', 120.00, 80, 7)");
            stmt.addBatch("INSERT INTO items (name, price, quantity_in_stock, category_id) VALUES ('Green Cross Alcohol (500ml)', 75.00, 120, 7)");

            stmt.executeBatch();
            conn.commit(); // Commit transaction

            System.out.println("Database reset complete!");
            System.out.println("Admin User Created -> Username: admin | Password: admin");
            System.out.println("Sample items populated.");

        } catch (SQLException e) {
            System.err.println("Database reset failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
}