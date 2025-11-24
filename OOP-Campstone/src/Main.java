import capstone.Admin;
import capstone.DbHandler;
import capstone.Employee;

public class Main {
    public static void main(String[] args) {
        DbHandler.connect();

        Admin myAdmin = new Admin();

        Employee newGuy = new Employee("john", "password123");
        Employee newGuy2 = new Employee("bob", "password123");
        Employee newGuy3 = new Employee("carl", "password123");
        myAdmin.addEmployee(newGuy);
        myAdmin.addEmployee(newGuy2);
        myAdmin.addEmployee(newGuy3);
    }

}
