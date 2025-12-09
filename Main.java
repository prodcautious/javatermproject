import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        HashMap<Item, Integer> inventory = new HashMap<>();
        Queue<Item> restockQueue = new LinkedList<>();

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
        boolean running = true;

        while (running) {
            System.out.println("\nAvailable Commands:");
            System.out.println("[1] List items alphabetically");
            System.out.println("[2] List items by price");
            System.out.println("[3] Add item");
            System.out.println("[4] Remove item amount");
            System.out.println("[5] View restock queue");
            System.out.println("[6] Restock next item");
            System.out.println("[7] Quit");
            System.out.print("Choice: ");

            int command = scanner.nextInt();
            scanner.nextLine();

            if (command == 1) {
                System.out.println(getCategories(inventory));
                System.out.print("Category: ");
                String category = scanner.nextLine();
                System.out.println(sortItemsAlphabetically(inventory, category));
            }

            else if (command == 2) {
                System.out.println(getCategories(inventory));
                System.out.print("Category: ");
                String category = scanner.nextLine();
                System.out.println(sortItemsByPrice(inventory, category));
            }

            else if (command == 3) {
                System.out.print("Name: ");
                String name = scanner.nextLine();
                System.out.print("Category: ");
                String category = scanner.nextLine();
                System.out.print("Price: ");
                double price = scanner.nextDouble();
                System.out.print("Stock: ");
                int stock = scanner.nextInt();
                scanner.nextLine();
                addItemToHashMap(inventory, name, category, price, stock);
            }

            else if (command == 4) {
                System.out.print("Name: ");
                String name = scanner.nextLine();
                System.out.print("Category: ");
                String category = scanner.nextLine();
                System.out.print("Amount to remove: ");
                int amount = scanner.nextInt();
                scanner.nextLine();
                removeItemFromHashMap(inventory, restockQueue, name, category, amount);
            }

            else if (command == 5) {
                printRestockQueue(restockQueue);
            }

            else if (command == 6) {
                restockNextItem(restockQueue, inventory);
            }

            else if (command == 7) {
                running = false;
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

    public static void removeItemFromHashMap(HashMap<Item, Integer> map,
                                             Queue<Item> restockQueue,
                                             String name,
                                             String category,
                                             int amount) {
        Item searchItem = new Item(name, category, 0, 0);

        if (map.containsKey(searchItem)) {
            Item item = getItemFromMap(map, searchItem);
            int newQty = map.get(item) - amount;
            map.put(item, newQty);

            if (newQty <= 0) {
                map.put(item, 0);
                if (!restockQueue.contains(item)) {
                    restockQueue.add(item);
                }
                System.out.println(name + " is out of stock and added to restock queue");
            }
        } else {
            System.out.println("Item not found");
        }
    }

    public static void printRestockQueue(Queue<Item> queue) {
        if (queue.isEmpty()) {
            System.out.println("Restock queue is empty");
            return;
        }
        System.out.println("Restock Queue:");
        for (Item item : queue) {
            System.out.println("- " + item.getName());
        }
    }

    public static void restockNextItem(Queue<Item> queue,
                                       HashMap<Item, Integer> inventory) {
        if (queue.isEmpty()) {
            System.out.println("Nothing to restock");
            return;
        }
        Item item = queue.poll();
        inventory.put(item, 10);
        System.out.println("Restocked " + item.getName() + " with 10 units");
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