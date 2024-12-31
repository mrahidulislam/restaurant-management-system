import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        RestaurantManagementSystem rms = new RestaurantManagementSystem();
        Scanner scanner = new Scanner(System.in);

        // Adding sample data
        rms.addMenuItem(1, "Chicken Biriyani", 200.00);
        rms.addMenuItem(2, "Coca-Cola", 30.00);
        rms.addMenuItem(3, "Beef Khichuri", 150.00);

        rms.addMenuItem(4, "Beef Polao", 170.00);
        rms.addMenuItem(5, "Mineral Water", 20.00);
        rms.addTable(1);
        rms.addTable(2);

        boolean adminRegistered = false;

        while (true) {
            System.out.println("\nRestaurant Management System");
            System.out.println("1. Register Admin");
            System.out.println("2. Admin Panel");
            System.out.println("3. Customer Panel");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            if (choice == 1) {
                System.out.print("Enter new username: ");
                String username = scanner.next();
                System.out.print("Enter new password: ");
                String password = scanner.next();
                adminRegistered = rms.registerAdmin(username, password);
            } else if (choice == 2) {
                if (!adminRegistered) {
                    System.out.println("Admin must register first!");
                    continue;
                }
                System.out.print("Enter Username: ");
                String username = scanner.next();
                System.out.print("Enter Password: ");
                String password = scanner.next();

                if (rms.adminLogin(username, password)) {
                    while (true) {
                        System.out.println("\nAdmin Panel");
                        System.out.println("1. Add Menu Item");
                        System.out.println("2. View Sales");
                        System.out.println("3. Add Table");
                        System.out.println("4. View Active Orders");
                        System.out.println("5. Logout");
                        System.out.print("Enter your choice: ");
                        int adminChoice = scanner.nextInt();

                        if (adminChoice == 1) {
                            System.out.print("Enter Item ID: ");
                            int id = scanner.nextInt();
                            scanner.nextLine(); // Consume newline
                            System.out.print("Enter Item Name: ");
                            String name = scanner.nextLine();
                            System.out.print("Enter Item Price: ");
                            double price = scanner.nextDouble();
                            rms.addMenuItem(id, name, price);
                            System.out.println("Item added successfully.");
                        } else if (adminChoice == 2) {
                            // View sales
                            System.out.println("Daily Sales: " + rms.getDailySales() + " Taka");
                        } else if (adminChoice == 3) {
                            System.out.print("Enter Table ID: ");
                            int tableId = scanner.nextInt();
                            rms.addTable(tableId);
                            System.out.println("Table added successfully.");
                        } else if (adminChoice == 4) {
                            rms.getOrders();
                        } else if (adminChoice == 5) {
                            break; // Logout
                        }
                    }
                }
            } else if (choice == 3) {
                while (true) {
                    System.out.println("\nCustomer Panel");
                    System.out.println("1. View Menu");
                    System.out.println("2. Reserve Table and Order");
                    System.out.println("3. Cancel Order");
                    System.out.println("4. Back to Main Menu");
                    System.out.print("Enter your choice: ");
                    int customerChoice = scanner.nextInt();

                    if (customerChoice == 1) {
                        rms.viewMenu();
                    } else if (customerChoice == 2) {
                        rms.viewTables();
                        System.out.print("Enter a table ID to reserve: ");
                        int tableId = scanner.nextInt();
                        if (rms.assignTable(tableId)) {
                            System.out.println("Table reserved successfully.");
                            System.out.println("Enter item IDs (comma-separated): ");
                            scanner.nextLine(); // Consume newline
                            String[] itemIds = scanner.nextLine().split(",");
                            Order order = new Order();
                            for (String itemId : itemIds) {
                                for (MenuItem menuItem : rms.getMenu()) {
                                    if (menuItem.getId() == Integer.parseInt(itemId)) {
                                        order.addItem(menuItem);
                                    }
                                }
                            }
                            rms.createOrder(order);
                            System.out.println("Order placed! Your Order ID: " + order.getOrderId());
                        } else {
                            System.out.println("No available table.");
                        }
                    } else if (customerChoice == 3) {
                        System.out.print("Enter your Order ID to cancel: ");
                        scanner.nextLine(); // Consume leftover newline
                        String orderId = scanner.nextLine();
                        if (!rms.cancelOrder(orderId)) {
                            System.out.println("Invalid Order ID. Cancellation failed.");
                        }
                    } else if (customerChoice == 4) {
                        break; // Back to main menu
                    }
                }
            } else if (choice == 4) {
                System.out.println("Exiting... Thank you!");
                break;
            }
        }
    }
}