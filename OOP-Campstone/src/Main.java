import capstone.Admin;
import capstone.DbHandler;
import capstone.Employee;

public class Main {
    public static void main(String[] args) {
        DbHandler.connect();

        Admin myAdmin = new Admin();

        Employee newGuy = new Employee("john", "password123");

        myAdmin.addEmployee(newGuy);

        myAdmin.removeUser(newGuy);
    }

}
