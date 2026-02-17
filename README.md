# Grocery Shopping System (OOP Capstone)

A Java-based application that streamlines the grocery shopping experience using Object-Oriented Programming principles. This system allows users to browse categories, manage a shopping cart within a budget, and generate receipts.

## How to Run

### Prerequisites
Before running the system, ensure you have the following installed:
* **JDK** (Version 17 or higher recommended)
* **IDE** (IntelliJ IDEA, Eclipse, or VS Code)
* **SQLite** (For database management)

### Installation

1.  **Clone the Repository**
    ```bash
    git clone https://github.com/Evandle/Java-OOP-Capstone.git
    cd Java-OOP-Capstone
    ```

2.  **Open in IDE**
    * Open your IDE.
    * Select **File > Open** and choose the project folder.
    * Allow the IDE to index files and download any dependencies (if using Maven/Gradle).

3.  **Database Setup**
    * The project uses **SQLite**.
    * Ensure the `capstone.db` file is in the root directory.
    * *Optional:* If the database is missing/broken, run `OOP-Campstone/src/capstone/DatabaseSetup.java` first to initialize the tables.

### Running the Application

1.  Navigate to the main source folder: `OOP-Campstone/src`.
2.  Locate **`Main.java`**.
3.  Right-click the file and select **Run 'Main'**.

### Default Login Credentials
Use these credentials to test the system:
* **Admin:** `admin` / `admin123`
---

## Key Features to Test
* ✅ **Browse Categories:** Explore the 7 main sections (Frozen Goods, Drinks, Snacks, etc.).
* ✅ **Checkout System:** Process a transaction and view the generated receipt.
