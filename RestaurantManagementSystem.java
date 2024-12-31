import java.util.ArrayList;
import java.util.HashMap;

public class RestaurantManagementSystem {
    private HashMap<String, String> adminCredentials = new HashMap<>();
    private ArrayList<MenuItem> menu = new ArrayList<>();
    private ArrayList<Table> tables = new ArrayList<>();
    private ArrayList<Order> orders = new ArrayList<>();
    private double dailySales = 0;

    // Register Admin
    public boolean registerAdmin(String username, String password) {
        if (adminCredentials.containsKey(username)) {
            System.out.println("Admin already registered.");
            return false;
        }
        adminCredentials.put(username, password);
        System.out.println("Admin registered successfully.");
        return true;
    }

    // Admin Login
    public boolean adminLogin(String username, String password) {
        if (adminCredentials.containsKey(username) && adminCredentials.get(username).equals(password)) {
            System.out.println("Login successful.");
            return true;
        }
        System.out.println("Invalid credentials.");
        return false;
    }

    // Add Menu Item
    public void addMenuItem(int id, String name, double price) {
        menu.add(new MenuItem(id, name, price));
    }

    // View Menu
    public void viewMenu() {
        System.out.println("\nMenu:");
        for (MenuItem item : menu) {
            System.out.println(item);
        }
    }

    // Get Menu
    public ArrayList<MenuItem> getMenu() {
        return menu;
    }

    // Add Table
    public void addTable(int id) {
        tables.add(new Table(id));
    }

    // View Tables
    public void viewTables() {
        System.out.println("\nAvailable Tables:");
        for (Table table : tables) {
            if (!table.isOccupied()) {
                System.out.println(table);
            }
        }
    }

    // Assign Table
    public boolean assignTable(int tableId) {
        for (Table table : tables) {
            if (table.getId() == tableId && !table.isOccupied()) {
                table.setOccupied(true);
                return true;
            }
        }
        return false;
    }

    // Create Order
    public void createOrder(Order order) {
        orders.add(order);
        dailySales += order.calculateTotal();
    }

    // Cancel Order
    public boolean cancelOrder(String orderId) {
        for (Order order : orders) {
            if (order.getOrderId().equals(orderId.trim())) {
                dailySales -= order.calculateTotal();
                orders.remove(order);
                System.out.println("Order canceled successfully.");
                return true;
            }
        }
        System.out.println("Order not found.");
        return false;
    }

    // View Orders
    public void viewOrders() {
        System.out.println("\nActive Orders:");
        for (Order order : orders) {
            System.out.println(order);
        }
    }

    // View Sales
    public void viewSales() {
        System.out.println("Daily Sales: " + dailySales + " Taka");
    }

    // Getter for Daily Sales
    public double getDailySales() {
        return dailySales;
    }

    // Getter for Orders
    public ArrayList<Order> getOrders() {
        return orders;
    }

    // Getter for Tables (This is what you were missing)
    public ArrayList<Table> getTables() {
        return tables;
    }
}