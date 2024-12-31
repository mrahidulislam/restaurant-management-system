public class Table {
    private int id;
    private boolean isOccupied;

    // Constructor
    public Table(int id) {
        this.id = id;
        this.isOccupied = false;  // Initially, tables are not occupied
    }

    // Getter for ID
    public int getId() {
        return id;
    }

    // Getter for isOccupied
    public boolean isOccupied() {
        return isOccupied;
    }

    // Setter for isOccupied
    public void setOccupied(boolean isOccupied) {
        this.isOccupied = isOccupied;
    }

    // Method to check if the table is available
    public boolean isAvailable() {
        return !isOccupied;  // If the table is not occupied, it's available
    }

    @Override
    public String toString() {
        return "Table ID: " + id + (isAvailable() ? " (Available)" : " (Occupied)");
    }
}
