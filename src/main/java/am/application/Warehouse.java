package am.application;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

public class Warehouse {
    private static final Logger logger = Logger.getLogger(Warehouse.class.getName());
    private final Map<MaterialType, Material> stocks = new ConcurrentHashMap<>();
    private final ExecutorService executor = Executors.newCachedThreadPool();

    public CompletableFuture<Void> modifyStock(MaterialType type, int quantity) {
        return CompletableFuture.supplyAsync(() -> {
            stocks.computeIfAbsent(type, Material::new).adjustQuantity(quantity);
            return null;
        }, executor);
    }

    public void addMaterial(MaterialType type, int quantity) throws InventoryException {
        stocks.computeIfAbsent(type, Material::new).adjustQuantity(quantity);
        logger.info("Added " + quantity + " units of " + type.getName() + " to warehouse.");
    }

    public void displayStock() {
        stocks.forEach((type, material) -> {
            System.out.println(type.getName() + " has " + material.getQuantity() + " units");
        });

    }

    public int getMaterialQuantity(MaterialType materialType) {
        return stocks.get(materialType).getQuantity();
    }

    public void removeMaterialAsync(MaterialType materialType, int quantity) {
        Material material = stocks.get(materialType);

        if (material == null) {
            logger.warning("Attempt to remove material failed: Material not found.");
            throw new InventoryException("Material not found.");
        }
        material.adjustQuantity(-quantity);
        logger.info("Removed " + quantity + " units of " + materialType.getName() + " from warehouse.");
    }

    public void transferMaterial(Warehouse destination, MaterialType type, int quantity) throws InventoryException {
        Material sourceMaterial = stocks.get(type);
        if (sourceMaterial == null || sourceMaterial.getQuantity() < quantity) {
            logger.warning("Attempt to transfer material failed: Not enough material for transfer.");
            throw new InventoryException("Not enough material for transfer.");
        }
        this.removeMaterial(type, quantity);
        destination.addMaterial(type, quantity);
        logger.info("Transferred " + quantity + " units of " + type.getName() + " to another warehouse.");
    }

    void removeMaterial(MaterialType type, int quantity) {
        stocks.get(type).adjustQuantity(-quantity);
        logger.info("Removed " + quantity + " units of " + type.getName() + " from warehouse.");
    }
}

