import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class RestaurantManagementSystemGUI {

    private JFrame frame;
    private JPanel mainPanel, adminPanel, customerPanel;
    private RestaurantManagementSystem rms;

    public RestaurantManagementSystemGUI() {
        rms = new RestaurantManagementSystem(); // Initialize the RMS object
        frame = new JFrame("Restaurant Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);

        mainPanel = new JPanel();
        adminPanel = new JPanel();
        customerPanel = new JPanel();

        // Set up the main panel
        mainPanel.setLayout(new GridLayout(4, 1));
        JButton btnRegisterAdmin = new JButton("Register Admin");
        JButton btnAdminLogin = new JButton("Admin Panel");
        JButton btnCustomerPanel = new JButton("Customer Panel");
        JButton btnExit = new JButton("Exit");

        mainPanel.add(btnRegisterAdmin);
        mainPanel.add(btnAdminLogin);
        mainPanel.add(btnCustomerPanel);
        mainPanel.add(btnExit);

        // Action Listeners
        btnRegisterAdmin.addActionListener(e -> showAdminRegisterPanel());
        btnAdminLogin.addActionListener(e -> showAdminLoginPanel());
        btnCustomerPanel.addActionListener(e -> showCustomerPanel());
        btnExit.addActionListener(e -> System.exit(0));

        // Set up the Admin Panel
        adminPanel.setLayout(new GridLayout(5, 1));
        JButton btnAddTable = new JButton("Add Table");  // New button to add a table
        JButton btnAddMenuItem = new JButton("Add Menu Item");
        JButton btnViewSales = new JButton("View Sales");
        JButton btnViewOrders = new JButton("View Orders");
        JButton btnLogoutAdmin = new JButton("Logout");

        adminPanel.add(btnAddTable); // Add the new button here
        adminPanel.add(btnAddMenuItem);
        adminPanel.add(btnViewSales);
        adminPanel.add(btnViewOrders);
        adminPanel.add(btnLogoutAdmin);

        // Action Listeners for Admin Panel
        btnAddTable.addActionListener(e -> addTable()); // Action listener for adding a table
        btnAddMenuItem.addActionListener(e -> addMenuItem());
        btnViewSales.addActionListener(e -> viewSales());
        btnViewOrders.addActionListener(e -> viewOrders());
        btnLogoutAdmin.addActionListener(e -> showMainMenu());

        // Set up the Customer Panel
        customerPanel.setLayout(new GridLayout(5, 1));
        JButton btnViewMenu = new JButton("View Menu");
        JButton btnPlaceOrder = new JButton("Place Order");
        JButton btnCancelOrder = new JButton("Cancel Order");
        JButton btnViewTables = new JButton("View Table Availability"); // New button
        JButton btnBackToMain = new JButton("Back to Main Menu");

        customerPanel.add(btnViewMenu);
        customerPanel.add(btnPlaceOrder);
        customerPanel.add(btnCancelOrder);
        customerPanel.add(btnViewTables); // Add new button
        customerPanel.add(btnBackToMain);

        // Action Listeners for Customer Panel
        btnViewMenu.addActionListener(e -> viewMenu());
        btnPlaceOrder.addActionListener(e -> placeOrder());
        btnCancelOrder.addActionListener(e -> cancelOrder());
        btnViewTables.addActionListener(e -> viewTableAvailability()); // Action listener for the new button
        btnBackToMain.addActionListener(e -> showMainMenu());

        // Display the main panel initially
        frame.setContentPane(mainPanel);
        frame.setVisible(true);
    }

    // Admin Register Panel (For demonstration purposes)
    private void showAdminRegisterPanel() {
        JPanel panel = new JPanel(new GridLayout(3, 2));
        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();

        panel.add(new JLabel("Enter Username:"));
        panel.add(usernameField);
        panel.add(new JLabel("Enter Password:"));
        panel.add(passwordField);

        JButton btnRegister = new JButton("Register");
        panel.add(btnRegister);

        btnRegister.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            if (rms.registerAdmin(username, password)) {
                JOptionPane.showMessageDialog(frame, "Admin Registered Successfully");
                showMainMenu();
            } else {
                JOptionPane.showMessageDialog(frame, "Admin already exists.");
            }
        });

        int option = JOptionPane.showConfirmDialog(frame, panel, "Admin Registration", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (option == JOptionPane.CANCEL_OPTION) {
            showMainMenu();
        }
    }

    // Admin Login Panel
    private void showAdminLoginPanel() {
        JPanel panel = new JPanel(new GridLayout(3, 2));
        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();

        panel.add(new JLabel("Enter Username:"));
        panel.add(usernameField);
        panel.add(new JLabel("Enter Password:"));
        panel.add(passwordField);

        JButton btnLogin = new JButton("Login");
        panel.add(btnLogin);

        btnLogin.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            if (rms.adminLogin(username, password)) {
                showAdminPanel();
            } else {
                JOptionPane.showMessageDialog(frame, "Invalid credentials.");
            }
        });

        int option = JOptionPane.showConfirmDialog(frame, panel, "Admin Login", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (option == JOptionPane.CANCEL_OPTION) {
            showMainMenu();
        }
    }

    // Customer Panel
    private void showCustomerPanel() {
        frame.setContentPane(customerPanel);
        frame.revalidate();
    }

    // Admin Panel
    private void showAdminPanel() {
        frame.setContentPane(adminPanel);
        frame.revalidate();
    }

    // Show main menu again
    private void showMainMenu() {
        frame.setContentPane(mainPanel);
        frame.revalidate();
    }

    // Functionality to add a menu item (Admin action)
    private void addMenuItem() {
        String itemName = JOptionPane.showInputDialog("Enter Item Name:");
        String itemPrice = JOptionPane.showInputDialog("Enter Item Price:");
        try {
            int id = rms.getMenu().size() + 1;
            double price = Double.parseDouble(itemPrice);
            rms.addMenuItem(id, itemName, price);
            JOptionPane.showMessageDialog(frame, "Menu item added successfully.");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frame, "Invalid price entered.");
        }
    }

    // View the menu (Customer action)
    private void viewMenu() {
        StringBuilder menuText = new StringBuilder("Menu:\n");
        for (MenuItem item : rms.getMenu()) {
            menuText.append(item).append("\n");
        }
        JOptionPane.showMessageDialog(frame, menuText.toString());
    }


    private void placeOrder() {
        String tableId = JOptionPane.showInputDialog("Enter Table ID:");
        try {
            int id = Integer.parseInt(tableId);
            if (rms.assignTable(id)) {
                JOptionPane.showMessageDialog(frame, "Table reserved successfully!");
                // Proceed with ordering items
                String itemIds = JOptionPane.showInputDialog("Enter Item IDs (comma-separated):");
                String[] itemIdArray = itemIds.split(",");

                Order order = new Order();  // Create a new order
                for (String itemId : itemIdArray) {
                    for (MenuItem item : rms.getMenu()) {
                        if (item.getId() == Integer.parseInt(itemId.trim())) {
                            order.addItem(item);  // Add item to the order
                        }
                    }
                }

                rms.createOrder(order);  // Add the order to the system
                JOptionPane.showMessageDialog(frame, "Order placed successfully! Your Order ID: " + order.getOrderId());
            } else {
                JOptionPane.showMessageDialog(frame, "Table not available.");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frame, "Invalid Table ID.");
        }
    }


    // Cancel an order (Customer action)
    private void cancelOrder() {
        String orderId = JOptionPane.showInputDialog("Enter Order ID to cancel:");
        if (!rms.cancelOrder(orderId)) {
            JOptionPane.showMessageDialog(frame, "Invalid Order ID. Cancellation failed.");
        }
    }

    // Check table availability
    private void viewTableAvailability() {
        StringBuilder tableStatus = new StringBuilder("Table Availability:\n");
        for (Table table : rms.getTables()) {
            tableStatus.append("Table ID: ").append(table.getId())
                    .append(" - ").append(table.isAvailable() ? "Available" : "Reserved").append("\n");
        }
        JOptionPane.showMessageDialog(frame, tableStatus.toString());
    }

    // View sales (Admin action)
    private void viewSales() {
        JOptionPane.showMessageDialog(frame, "Daily Sales: " + rms.getDailySales() + " Taka");
    }

    // View orders (Admin action)
    private void viewOrders() {
        StringBuilder orderText = new StringBuilder("Active Orders:\n");
        for (Order order : rms.getOrders()) {
            orderText.append(order).append("\n");
        }
        JOptionPane.showMessageDialog(frame, orderText.toString());
    }

    // Add a table (Admin action)
    private void addTable() {
        String tableIdStr = JOptionPane.showInputDialog("Enter Table ID:");
        try {
            int tableId = Integer.parseInt(tableIdStr);
            rms.addTable(tableId);
            JOptionPane.showMessageDialog(frame, "Table added successfully.");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frame, "Invalid Table ID. Please enter a valid number.");
        }
    }



    public static void main(String[] args) {
        new RestaurantManagementSystemGUI();
    }
}

