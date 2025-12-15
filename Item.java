public class Item {
    private String name;
    private String category;
    private double price;
    private int stock;

    public Item(String name, String category, double price, int stock) {
        this.name = name;
        this.category = category;
        this.price = price;
        this.stock = stock;
    }

    // Getters
    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public double getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    // Override Methods
    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }

        if (object == null || getClass() != object.getClass()) {
            return false;
        }
    
        Item other = (Item) object;
        return this.name.equals(other.name) && this.category.equals(other.category);
    }

    @Override
    public int hashCode() {
        return name.hashCode() + category.hashCode();
    }

    @Override
    public String toString() {
        return name + " ($" + price + ")";
    }
}