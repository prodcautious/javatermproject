import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import java.util.HashMap;

public class MainTest {
private HashMap<Item, Integer> inventory;

    @BeforeEach
    void setUp() {
        inventory = new HashMap<>();
    }

    @Test
    void testAddNewItem() {
        Main.addItemToHashMap(inventory, "Apple", "Fruit", 0.69, 10);
        assertEquals(1, inventory.size());
    }

    @Test
    void testAddExistingItem() {
        Main.addItemToHashMap(inventory, "Apple", "Fruit", 0.69, 10);
        Main.addItemToHashMap(inventory, "Apple", "Fruit", 0.69, 5);
        assertEquals(1, inventory.size());
    }

    @Test
    void testRemoveExistingItem() {
        Main.addItemToHashMap(inventory, "Apple", "Fruit", 0.69, 10);
        Main.removeItemFromHashMap(inventory, "Apple", "Fruit");
        assertEquals(0, inventory.size());
    }

    @Test
    void testRemoveNonExistentItem() {
        Main.removeItemFromHashMap(inventory, "Banana", "Fruit");
        assertEquals(0, inventory.size());
    }

    @Test
    void testGetCategories() {
        Main.addItemToHashMap(inventory, "Apple", "Fruit", 0.69, 10);
        Main.addItemToHashMap(inventory, "Bread", "Bakery", 2.49, 5);
        String categories = Main.getCategories(inventory);
        assertTrue(categories.contains("Fruit"));
        assertTrue(categories.contains("Bakery"));
    }

    @Test
    void testSortItemsAlphabetically() {
        Main.addItemToHashMap(inventory, "Orange", "Fruit", 0.89, 10);
        Main.addItemToHashMap(inventory, "Apple", "Fruit", 0.69, 10);
        String result = Main.sortItemsAlphabetically(inventory, "Fruit");
        assertTrue(result.indexOf("Apple") < result.indexOf("Orange"));
    }

    @Test
    void testSortItemsByPrice() {
        Main.addItemToHashMap(inventory, "Orange", "Fruit", 0.89, 10);
        Main.addItemToHashMap(inventory, "Apple", "Fruit", 0.69, 10);
        String result = Main.sortItemsByPrice(inventory, "Fruit");
        assertTrue(result.indexOf("Apple") < result.indexOf("Orange"));
    }

    @Test
    void testCaseInsensitiveCategory() {
        Main.addItemToHashMap(inventory, "Apple", "Fruit", 0.69, 10);
        String result = Main.sortItemsAlphabetically(inventory, "fruit");
        assertTrue(result.contains("Apple"));
    }

    @Test
    void testNonExistentCategory() {
        Main.addItemToHashMap(inventory, "Apple", "Fruit", 0.69, 10);
        String result = Main.sortItemsAlphabetically(inventory, "Meat");
        assertTrue(result.contains("No items found"));
    }

    @Test
    void testItemEquality() {
        Item item1 = new Item("Apple", "Fruit", 0.69, 10);
        Item item2 = new Item("Apple", "Fruit", 0.99, 5);
        assertEquals(item1, item2);
    }
}