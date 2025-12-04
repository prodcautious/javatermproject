import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Eventually add saving and loading to this, but for now, just create a new inventory on load
        HashMap<Item, Integer> inventory = new HashMap<>();

        // For now, add a few random items
        addItemToHashMap(inventory, "Apple", "Fruit", 0.69, 10);
        addItemToHashMap(inventory, "Banana", "Fruit", 0.49, 15);
        addItemToHashMap(inventory, "Strawberries", "Fruit", 2.99, 8);
        addItemToHashMap(inventory, "Blueberries", "Fruit", 3.49, 5);
        addItemToHashMap(inventory, "Orange", "Fruit", 0.89, 12);
        addItemToHashMap(inventory, "Bread Loaf", "Bakery", 2.49, 20);
        addItemToHashMap(inventory, "Bagel", "Bakery", 1.29, 25);
        addItemToHashMap(inventory, "Croissant", "Bakery", 1.79, 18);
        addItemToHashMap(inventory, "Muffin", "Bakery", 1.49, 22);
        addItemToHashMap(inventory, "Donut", "Bakery", 0.99, 30);

        Scanner scanner = new Scanner(System.in);
        System.out.println("Hello User! Welcome to Inventory Manager\n");

        boolean running = true;
        while(running) {
            System.out.println("Available Commands:");
            System.out.println("=================================================");
            System.out.println("[1] List items alphabetically (Given a category)");
            System.out.println("[2] List items by price (Given a category)");
            System.out.println("[3] Add item to inventory");
            System.out.println("[4] Remove item from inventory");
            System.out.println("[5] Quit");
            System.out.println("=================================================");
            System.out.print("Enter command: ");

            int command = scanner.nextInt();
            scanner.nextLine();

            if(command == 1) {
                System.out.println("\nPlease select a category:");
                System.out.println(getCategories(inventory));
                System.out.print("Enter Category: ");
                String category = scanner.nextLine();
                System.out.println("\n" + category + " listed alphabetically:");
                System.out.println(sortItemsAlphabetically(inventory, category));
            }
            else if(command == 2) {
                System.out.println("\nPlease select a category:");
                System.out.println(getCategories(inventory));
                System.out.print("Enter Category: ");
                String category = scanner.nextLine();
                System.out.println("\n" + category + " listed by price:");
                System.out.println(sortItemsByPrice(inventory, category));
            }
            else if(command == 3) {
                System.out.print("\nEnter item name: ");
                String name = scanner.nextLine();
                System.out.print("Enter item category: ");
                String category = scanner.nextLine();
                System.out.print("Enter item price: ");
                double price = scanner.nextDouble();
                System.out.print("Enter stock quantity: ");
                int stock = scanner.nextInt();
                scanner.nextLine();
                addItemToHashMap(inventory, name, category, price, stock);
                System.out.println();
            }
            else if(command == 4) {
                System.out.print("\nEnter item name: ");
                String name = scanner.nextLine();
                System.out.print("Enter item category: ");
                String category = scanner.nextLine();
                removeItemFromHashMap(inventory, name, category);
                System.out.println();
            }
            else if(command == 5) {
                System.out.println("\nGoodbye!");
                running = false;
            }
            else {
                System.out.println("\nInvalid command. Please try again.\n");
            }
        }
        
        scanner.close();
    }

    public static void addItemToHashMap(HashMap<Item, Integer> map, String name, String category, double price, int stock) {
        Item newItem = new Item(name, category, price, stock);

        // If item already exists
        if (map.containsKey(newItem)) {
            Item existingItem = getItemFromMap(map, newItem);
            map.put(existingItem, map.get(existingItem) + stock);
            System.out.println("Added stock to existing item: " + name + " | +" + stock + " units");
        }
        // If item doesn't already exist
        else {
            map.put(newItem, stock);
            System.out.println("Added new item: " + name + " | $" + price + " | " + stock + " units");
        }
    }

    public static void removeItemFromHashMap(HashMap<Item, Integer> map, String name, String category) {
        Item searchItem = new Item(name, category, 0, 0);

        // If item exists
        if (map.containsKey(searchItem)) {
            Item itemToRemove = getItemFromMap(map, searchItem);
            map.remove(itemToRemove);
            System.out.println("Removed item: " + name + " from inventory");
        }
        // Item doesn't exist
        else {
            System.out.println("Item not found in inventory");
        }
    }

    private static Item getItemFromMap(HashMap<Item, Integer> map, Item searchItem) {
        for (Item item : map.keySet()) {
            if (item.equals(searchItem)) {
                return item;
            }
        }
        return null;
    }

    public static String getCategories(HashMap<Item, Integer> map) {
        List<String> categories = new ArrayList<>();
        
        for (Item item : map.keySet()) {
            String category = item.getCategory();
            if (!categories.contains(category)) {
                categories.add(category);
            }
        }
        
        categories.sort(String.CASE_INSENSITIVE_ORDER);
        
        String result = "";
        for (String category : categories) {
            result += "- " + category + "\n";
        }
        
        return result;
    }

    public static String sortItemsAlphabetically(HashMap<Item, Integer> map, String category) {
        List<Item> items = new ArrayList<>();
        
        for(Item item : map.keySet()) {
            if (item.getCategory().equalsIgnoreCase(category)) {
                items.add(item);
            }
        }

        if (items.isEmpty()) {
            return "No items found in category: " + category;
        }

        items.sort(Comparator.comparing(Item::getName, String.CASE_INSENSITIVE_ORDER));

        String result = "";
        for (Item item : items) {
            result += String.format("%-20s $%.2f (Stock: %d)\n", 
                item.getName(), item.getPrice(), map.get(item));
        }
        
        return result;
    }

    public static String sortItemsByPrice(HashMap<Item, Integer> map, String category) {
        List<Item> items = new ArrayList<>();
        
        for(Item item : map.keySet()) {
            if (item.getCategory().equalsIgnoreCase(category)) {
                items.add(item);
            }
        }

        if (items.isEmpty()) {
            return "No items found in category: " + category;
        }

        items.sort(Comparator.comparing(Item::getPrice));

        String result = "";
        for (Item item : items) {
            result += String.format("%-20s $%.2f (Stock: %d)\n", 
                item.getName(), item.getPrice(), map.get(item));
        }
        
        return result;
    }
}