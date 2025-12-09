public class Item {
    private String name;
    private String category;
    private double price;
    private int stock;

    public Item(String name, String category, double price, int stock)
    {
        this.name = name;
        this.category = category;
        this.price = price;
        this.stock = stock;
    }

    // Getters
    public String getName()
    {
        return name;
    }

    public String getCategory()
    {
        return category;
    }

    public double getPrice()
    {
        return price;
    }

    public int getStock()
    {
        return stock;
    }

    // Setters
    public void setName(String newName)
    {
        name = newName;
    }

    public void setCategory(String newCategory)
    {
        category = newCategory;
    }

    public void setPrice(int newAmount)
    {
        price = newAmount;
    }

    public void setStock(int newAmount)
    {
        stock = newAmount;
    }

    @Override
    public boolean equals(Object object)
    {
        // If object is the same
        if(this == object)
        {
            return true;
        }

        if(object == null)
        {
            return false;
        }

        // Checks if objects are in the same class
        if(getClass() != object.getClass())
        {
            return false;
        }
    
        Item other = (Item) object;

        // Compare name & category
        return this.name.equals(other.name) && this.category.equals(other.category);
    }

    @Override public int hashCode() {
        return name.hashCode() + category.hashCode();
    }

    @Override
    public String toString() {
        return name + " ($" + price + ")";
    }
}
