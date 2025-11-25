package capstone;

public class Admin extends Employee {
    public Admin() {
        //Sample dummy admin
        super("admin", "12345");
    }

    public Admin(String username, String password) {
        super(username, password);
    }

    public void addEmployee(Employee employee) {
        boolean success = DbHandler.registerUser(employee.getUsername(), employee.getPassword(), "EMPLOYEE", null);
        if(success)
            System.out.println(employee.getUsername() + " added to database.");
    }

    public void addCustomer(Customer customer) {
        boolean success = DbHandler.registerUser(customer.getUsername(), customer.getPassword(), "CUSTOMER", customer.getAddress());
        if(success)
            System.out.println(customer.getUsername() + " added to database.");
    }

    public void removeUser(User user) {
        boolean success = DbHandler.deleteUser(user.getUsername());
        if(success)
            System.out.println(user.getUsername() + " removed from database.");
    }
}
