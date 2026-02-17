# Java-OOP-Capstone/Grocery System
## [Class Diagram](https://docs.google.com/document/d/1B-mUNfXHVFjU-MUtspN7D6kqCawGlG5pk-BDTuVrhCA/edit?usp=sharing)

This system simulates a Grocery Shopping Experience where customers can browse and purchase products. Users must log in to make purchases; otherwise, an exception is thrown.
Customers can view available items, their prices, and stock levels, and create a shopping list to checkout, which displays selected items and the total amount.
Stock is managed via a CSV file, and out-of-stock items are disabled for selection. New users must sign up with a username, password (minimum 8 characters, including an uppercase letter and a number), and delivery address; invalid passwords trigger an exception.
Employees have dedicated access to manage stock, including adding/removing products and updating prices or quantities. A single admin account can manage users and handle stock.

## How to run

Prerequisites
    Java Development Kit (JDK) 17 or higher.
    IDE: IntelliJ IDEA, Eclipse, or VS Code (with Java Extensions).
    Git (to clone the repository).
    
1. Clone the Repo
   `git clone https://github.com/your-username/grocery-shopping-system.git
   cd grocery-shopping-system`
   
3. Open in IDE
    - Open your IDE and select File > Open.
    - Navigate to the cloned folder and select it.
    - Let the IDE build the project and resolve dependencies.
      
4. Database Setup (SQLite)
   The project uses SQLite. The database file (grocery.db) should be located in the root directory.
   Note: If the database is missing/not working, run the DatabaseSetup.java file first to initialize the tables (Frozen Goods, Necessities, etc.).
   
5. Running the Application
    Navigate to OOP-Campstone/src/.
    Right-click Main.java and select Run.
    The login window should appear.

        Default Admin Login: admin / admin

Key Features to Test

    ✅ Browse Categories: Check the 7 main categories (Frozen, Drinks, Snacks, etc.).

    ✅ Add to Cart: Select items and watch the total budget update.

    ✅ Checkout: Process a mock transaction and view the receipt.

## Project To-Do List

- [X] Add Categories in the database
- [X] Add Customer GUI
- [X] Add Customer GUI Functionality
- [X] Add Admin Tools GUI
- [x] Add Admin Tools GUI Functionality
- [X] Maybe add a receipt system
- [ ] Add more stuff idk...








