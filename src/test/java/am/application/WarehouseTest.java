package am.application;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class WarehouseTest {
    private Warehouse warehouse1;
    private Warehouse warehouse2;
    private Logger mockLogger;

    @BeforeEach
    public void setUp() {
        warehouse1 = new Warehouse();
        warehouse2 = new Warehouse();
    }

    @Test
    public void testAddMaterialAsync() throws InterruptedException {
        warehouse1.addMaterial(MaterialType.IRON, 50);
        assertEquals(50, warehouse1.getMaterialQuantity(MaterialType.IRON));
    }

    @Test
    public void testRemoveMaterialAsync() throws InterruptedException {
        warehouse1.addMaterial(MaterialType.IRON, 100);
        warehouse1.removeMaterial(MaterialType.IRON, 50);
        assertEquals(50, warehouse1.getMaterialQuantity(MaterialType.IRON));
    }

    @Test
    public void testTransferMaterial() throws InventoryException {
        warehouse1.addMaterial(MaterialType.IRON, 100);
        warehouse1.transferMaterial(warehouse2, MaterialType.IRON, 50);
        assertEquals(50, warehouse1.getMaterialQuantity(MaterialType.IRON));
        assertEquals(50, warehouse2.getMaterialQuantity(MaterialType.IRON));
    }

    @Test
    public void testTransferInsufficientMaterial() {
        assertThrows(InventoryException.class, () -> warehouse1.transferMaterial(warehouse2, MaterialType.IRON, 50));
    }

    @Test
    public void testTransferNonExistentMaterial() {
        warehouse1.addMaterial(MaterialType.IRON, 50);
        assertThrows(InventoryException.class, () -> warehouse1.transferMaterial(warehouse2, MaterialType.COPPER, 20));
    }

    @Test
    public void testAddExcessiveMaterial() {
        assertThrows(InventoryException.class, () -> warehouse1.addMaterial(MaterialType.IRON, 200));
    }

    @Test
    public void testRemoveMaterialNotPresent() {
        assertThrows(NullPointerException.class, () -> warehouse1.removeMaterial(MaterialType.IRON, 10));
    }

}
