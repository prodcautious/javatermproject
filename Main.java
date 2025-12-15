/*

ADT's used
- ArrayList
- LinkedList
- Queue
- Hashmap

 */


import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;
import java.util.InputMismatchException;

public class Main {
    // Main Method
    public static void main(String[] args) {
        // Hashmap to store Item data
        HashMap<Item, Integer> inventory = new HashMap<>();

        // Queue of Items that are to be restocked
        Queue<Item> restockQueue = new LinkedList<>();
        initializeInventory(inventory);

        // Input scanner
        Scanner scanner = new Scanner(System.in);
        System.out.println("Hello User! Welcome to Inventory Manager\n");

        // Main loop - uses processcommand to determine which method to run based on user's input
        runInventoryManager(scanner, inventory, restockQueue);
        scanner.close();
    }

    // Initialization
    private static void initializeInventory(HashMap<Item, Integer> inventory) {
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
    }

    // Main Loop
    private static void runInventoryManager(Scanner scanner, HashMap<Item, Integer> inventory, Queue<Item> restockQueue) {
        boolean running = true;
        while (running) {
            displayMenu();
            
            try {
                int command = scanner.nextInt();
                scanner.nextLine();
                
                if (command < 1 || command > 8) {
                    System.out.println("\nInvalid command. Please enter a number between 1 and 8.\n");
                    continue;
                }
                
                running = processCommand(scanner, inventory, restockQueue, command);
            } catch (InputMismatchException e) {
                System.out.println("\nInvalid input. Please enter a number.\n");
                scanner.nextLine();
            }
        }
    }

    private static void displayMenu() {
        System.out.println("Available Commands:");
        System.out.println("=================================================");
        System.out.println("[1] List items alphabetically (Given a category)");
        System.out.println("[2] List items by price (Given a category)");
        System.out.println("[3] Add item to inventory");
        System.out.println("[4] Remove item amount");
        System.out.println("[5] Edit existing item");
        System.out.println("[6] View restock queue");
        System.out.println("[7] Restock next item");
        System.out.println("[8] Quit");
        System.out.println("=================================================");
        System.out.print("Enter command: ");
    }

    private static boolean processCommand(Scanner scanner, HashMap<Item, Integer> inventory, Queue<Item> restockQueue, int command) {
        switch (command) {
            case 1:
                handleListAlphabetically(scanner, inventory);
                break;
            case 2:
                handleListByPrice(scanner, inventory);
                break;
            case 3:
                handleAddItem(scanner, inventory);
                break;
            case 4:
                handleRemoveItem(scanner, inventory, restockQueue);
                break;
            case 5:
                handleEditItem(scanner, inventory);
                break;
            case 6:
                printRestockQueue(restockQueue);
                break;
            case 7:
                restockNextItem(restockQueue, inventory);
                break;
            case 8:
                System.out.println("\nGoodbye!");
                return false;
        }
        return true;
    }

    // Handler Methods
    private static void handleListAlphabetically(Scanner scanner, HashMap<Item, Integer> inventory) {
        // Ask user for category
        System.out.println("\nPlease select a category:");
        System.out.println(getCategories(inventory));
        System.out.print("Enter Category: ");
        String category = scanner.nextLine();
        
        // Check if category exists
        if (category.trim().isEmpty()) {
            System.out.println("Category cannot be empty.\n");
            return;
        }
        
        // Print items sorted alphabetically
        System.out.println("\n" + category + " listed alphabetically:");
        System.out.println(sortItemsAlphabetically(inventory, category));
    }

    private static void handleListByPrice(Scanner scanner, HashMap<Item, Integer> inventory) {
        // Ask user for category
        System.out.println("\nPlease select a category:");
        System.out.println(getCategories(inventory));
        System.out.print("Enter Category: ");
        String category = scanner.nextLine();
        
        // Check if category exists        
        if (category.trim().isEmpty()) {
            System.out.println("Category cannot be empty.\n");
            return;
        }

        // Print items sorted by price  
        System.out.println("\n" + category + " listed by price:");
        System.out.println(sortItemsByPrice(inventory, category));
    }

    private static void handleAddItem(Scanner scanner, HashMap<Item, Integer> inventory) {
        // Ask for name input + check if it exists
        System.out.print("\nEnter item name: ");
        String name = scanner.nextLine();
        if (name.trim().isEmpty()) {
            System.out.println("Item name cannot be empty.\n");
            return;
        }
        
        // Ask for category input + check if it exists
        System.out.print("Enter item category: ");
        String category = scanner.nextLine();
        if (category.trim().isEmpty()) {
            System.out.println("Category cannot be empty.\n");
            return;
        }

        double price = -1;
        int stock = -1;
        
        try {
            // Enter item price and make sure it is positive
            System.out.print("Enter item price: ");
            price = scanner.nextDouble();
            if (price < 0) {
                System.out.println("Price cannot be negative.\n");
                scanner.nextLine();
                return;
            }

            // Enter item quantity and make sure it is positive      
            System.out.print("Enter stock quantity: ");
            stock = scanner.nextInt();         
            if (stock < 0) {
                System.out.println("Stock cannot be negative.\n");
                scanner.nextLine();
                return;
            }
            
            // Add item to Hashmap
            scanner.nextLine();
            addItemToHashMap(inventory, name, category, price, stock);
            System.out.println();

        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Price and stock must be numbers.\n");
            scanner.nextLine();
        }
    }

    private static void handleRemoveItem(Scanner scanner, HashMap<Item, Integer> inventory, Queue<Item> restockQueue) {
        // Enter item name and make sure it's not empty
        System.out.print("\nEnter item name: ");
        String name = scanner.nextLine();
        if (name.trim().isEmpty()) {
            System.out.println("Item name cannot be empty.\n");
            return;
        }

        // Enter item name and make sure it's not empty        
        System.out.print("Enter item category: ");
        String category = scanner.nextLine();
        if (category.trim().isEmpty()) {
            System.out.println("Category cannot be empty.\n");
            return;
        }
        
        try {
            // Enter price and make sure it's positive
            System.out.print("Enter amount to remove: ");
            int amount = scanner.nextInt();
            scanner.nextLine();
            if (amount < 0) {
                System.out.println("Amount cannot be negative.\n");
                return;
            }
            
            // Remove item from Hashmap
            removeItemFromHashMap(inventory, restockQueue, name, category, amount);
            System.out.println();

        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Amount must be a number.\n");
            scanner.nextLine();
        }
    }

    private static void handleEditItem(Scanner scanner, HashMap<Item, Integer> inventory) {
        // Enter name and make sure it is not an empty string
        System.out.print("\nEnter item name to edit: ");
        String name = scanner.nextLine();
        if (name.trim().isEmpty()) {
            System.out.println("Item name cannot be empty.\n");
            return;
        }
        // Enter category and make sure it is not an empty string        
        System.out.print("Enter item category: ");
        String category = scanner.nextLine();
        if (category.trim().isEmpty()) {
            System.out.println("Category cannot be empty.\n");
            return;
        }

        Item searchItem = new Item(name, category, 0, 0);
        Item existingItem = getItemFromMap(inventory, searchItem);

        // Item doesn't exist
        if (existingItem == null) {
            System.out.println("Item not found in inventory\n");
            return;
        }

        System.out.println("\nCurrent item details:");
        System.out.println("Name: " + existingItem.getName());
        System.out.println("Category: " + existingItem.getCategory());
        System.out.println("Price: $" + existingItem.getPrice());
        System.out.println("Stock: " + inventory.get(existingItem));

        System.out.println("\nWhat would you like to edit?");
        System.out.println("[1] Name");
        System.out.println("[2] Category");
        System.out.println("[3] Price");
        System.out.println("[4] Stock");
        System.out.print("Enter choice: ");
        
        try {
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Enter new name: ");
                    String newName = scanner.nextLine();
                    if (newName.trim().isEmpty()) {
                        System.out.println("Name cannot be empty.");
                        break;
                    }
                    existingItem.setName(newName);
                    System.out.println("Name updated successfully!");
                    break;
                case 2:
                    System.out.print("Enter new category: ");
                    String newCategory = scanner.nextLine();
                    if (newCategory.trim().isEmpty()) {
                        System.out.println("Category cannot be empty.");
                        break;
                    }
                    existingItem.setCategory(newCategory);
                    System.out.println("Category updated successfully!");
                    break;
                case 3:
                    System.out.print("Enter new price: ");
                    double newPrice = scanner.nextDouble();
                    scanner.nextLine();
                    if (newPrice < 0) {
                        System.out.println("Price cannot be negative.");
                        break;
                    }
                    existingItem.setPrice(newPrice);
                    System.out.println("Price updated successfully!");
                    break;
                case 4:
                    System.out.print("Enter new stock quantity: ");
                    int newStock = scanner.nextInt();
                    scanner.nextLine();
                    if (newStock < 0) {
                        System.out.println("Stock cannot be negative.");
                        break;
                    }
                    inventory.put(existingItem, newStock);
                    System.out.println("Stock updated successfully!");
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter a number.");
            scanner.nextLine();
        }
        System.out.println();
    }

    // Inventory Operations
    public static void addItemToHashMap(HashMap<Item, Integer> map, String name, String category, double price, int stock) {
        // Creates a new item out of passed attributes
        Item newItem = new Item(name, category, price, stock);

        // If the item already exists in the map, just add the stock.
        if (map.containsKey(newItem)) {
            Item existingItem = getItemFromMap(map, newItem);
            map.put(existingItem, map.get(existingItem) + stock);
            System.out.println("Added stock to existing item: " + name + " | +" + stock + " units");

        // Add new item to hashmap
        } else {
            map.put(newItem, stock);
            System.out.println("Added new item: " + name + " | $" + price + " | " + stock + " units");
        }
    }

    public static void removeItemFromHashMap(HashMap<Item, Integer> map, Queue<Item> restockQueue, String name, String category, int amount) {
        // Item to remove
        Item searchItem = new Item(name, category, 0, 0);

        // It item exists
        if (map.containsKey(searchItem)) {
            Item item = getItemFromMap(map, searchItem);
            int currentStock = map.get(item);
            int newQty = currentStock - amount;
            
            if (newQty < 0) {
                newQty = 0;
            }
            
            // If there is still existing stock
            map.put(item, newQty);
            System.out.println("Removed " + amount + " units from " + name + ". New stock: " + newQty);

            // If the item is out of stock, add it to the restockQueue
            if (newQty <= 0) {
                if (!restockQueue.contains(item)) {
                    restockQueue.add(item);
                    System.out.println(name + " is out of stock and added to restock queue");
                }
            }

        // Item not found
        } else {
            System.out.println("Item not found");
        }
    }

    public static void printRestockQueue(Queue<Item> queue) {
        // Empty check
        if (queue.isEmpty()) {
            System.out.println("\nRestock queue is empty\n");
            return;
        }
        // Iterates through items in the restock queue
        System.out.println("\nRestock Queue:");
        for (Item item : queue) {
            System.out.println("- " + item.getName());
        }
        System.out.println();
    }

    public static void restockNextItem(Queue<Item> queue, HashMap<Item, Integer> inventory) {
        // Empty check
        if (queue.isEmpty()) {
            System.out.println("\nNothing to restock\n");
            return;
        }
        // .poll() takes and removes the head of the queue
        Item item = queue.poll();

        // Add 10 stock to item (Could probably restock with a certain amount at some point)
        inventory.put(item, 10);
        System.out.println("\nRestocked " + item.getName() + " with 10 units\n");
    }

    private static Item getItemFromMap(HashMap<Item, Integer> map, Item searchItem) {
        // Item exists
        for (Item item : map.keySet()) {
            if (item.equals(searchItem)) {
                return item;
            }
        }
        // Item does not exist
        return null;
    }

    // Query Operations
    public static String getCategories(HashMap<Item, Integer> map) {
        List<String> categories = new ArrayList<>();
        
        for (Item item : map.keySet()) {
            String category = item.getCategory();
            if (!categories.contains(category)) {
                categories.add(category);
            }
        }
        
        categories.sort(String.CASE_INSENSITIVE_ORDER);
        
        StringBuilder result = new StringBuilder();
        for (String category : categories) {
            result.append("- ").append(category).append("\n");
        }
        
        return result.toString();
    }

    public static String sortItemsAlphabetically(HashMap<Item, Integer> map, String category) {
        List<Item> items = filterItemsByCategory(map, category);

        if (items.isEmpty()) {
            return "No items found in category: " + category;
        }

        items.sort(Comparator.comparing(Item::getName, String.CASE_INSENSITIVE_ORDER));
        return formatItemList(items, map);
    }

    public static String sortItemsByPrice(HashMap<Item, Integer> map, String category) {
        List<Item> items = filterItemsByCategory(map, category);

        if (items.isEmpty()) {
            return "No items found in category: " + category;
        }

        items.sort(Comparator.comparing(Item::getPrice));
        return formatItemList(items, map);
    }

    // Helpers
    private static List<Item> filterItemsByCategory(HashMap<Item, Integer> map, String category) {
        List<Item> items = new ArrayList<>();
        for (Item item : map.keySet()) {
            if (item.getCategory().equalsIgnoreCase(category)) {
                items.add(item);
            }
        }
        return items;
    }

    private static String formatItemList(List<Item> items, HashMap<Item, Integer> map) {
        StringBuilder result = new StringBuilder();
        for (Item item : items) {
            result.append(String.format("%-20s $%.2f (Stock: %d)\n", 
                item.getName(), item.getPrice(), map.get(item)));
        }
        return result.toString();
    }

    public static void printInventory(HashMap<Item, Integer> inventory) {
        System.out.println("\n=========== INVENTORY ===========");
    
        if (inventory.isEmpty()) {
            System.out.println("Inventory is empty.");
            return;
        }
    
        for (Item item : inventory.keySet()) {
            int qty = inventory.get(item);
            System.out.printf(
                "%-20s | %-10s | $%-6.2f | qty: %d%n",
                item.getName(),
                item.getCategory(),
                item.getPrice(),
                qty
            );
        }
    
        System.out.println("=================================\n");
    }
}