package am.application;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MaterialTest {
    private Material iron;
    private Material copper;
    private Material bolt;
    private Material newMaterial;

    @BeforeEach
    public void setUp() {
        iron = new Material(MaterialType.IRON);
        copper = new Material(MaterialType.COPPER);
        bolt = new Material(MaterialType.BOLT);
        newMaterial = new Material(MaterialType.NEW);
    }

    @Test
    public void testInitialQuantityShouldBeZero() {
        assertEquals(0, iron.getQuantity());
        assertEquals(0, copper.getQuantity());
        assertEquals(0, bolt.getQuantity());
        assertEquals(0, newMaterial.getQuantity());
    }

    @Test
    public void testAdjustQuantityWithinCapacity() {
        assertDoesNotThrow(() -> iron.adjustQuantity(50));
        assertEquals(50, iron.getQuantity());

        assertDoesNotThrow(() -> copper.adjustQuantity(80));
        assertEquals(80, copper.getQuantity());
    }

    @Test
    public void testAdjustQuantityExceedsCapacity() {
        InventoryException exception = assertThrows(InventoryException.class, () -> iron.adjustQuantity(101));
        assertEquals("Operation outide limits for Iron", exception.getMessage());

        exception = assertThrows(InventoryException.class, () -> bolt.adjustQuantity(131));
        assertEquals("Operation outide limits for Bolt", exception.getMessage());
    }

    @Test
    public void testAdjustQuantityBelowZero() {
        assertDoesNotThrow(() -> newMaterial.adjustQuantity(5));
        InventoryException exception = assertThrows(InventoryException.class, () -> newMaterial.adjustQuantity(-11));
        assertEquals("Operation outide limits for New", exception.getMessage());
    }

    @Test
    public void testMultipleAdjustments() {
        assertDoesNotThrow(() -> bolt.adjustQuantity(100));
        assertDoesNotThrow(() -> bolt.adjustQuantity(-20));
        assertEquals(80, bolt.getQuantity());
    }
}
