import java.util.ArrayList;
import java.util.UUID;

public class Order {
    private String orderId;
    private ArrayList<MenuItem> items;

    public Order() {
        this.orderId = UUID.randomUUID().toString();
        this.items = new ArrayList<>();
    }

    public String getOrderId() {
        return orderId;
    }

    public void addItem(MenuItem item) {
        items.add(item);
    }

    public double calculateTotal() {
        double total = 0;
        for (MenuItem item : items) {
            total += item.getPrice();
        }
        return total;
    }

    @Override
    public String toString() {
        return "Order ID: " + orderId + ", Items: " + items + ", Total: " + calculateTotal() + "Taka";
    }
}