package am.application;

import java.util.ConcurrentModificationException;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Warehouse {
    private final Map<MaterialType, Material> stocks = new ConcurrentHashMap<>();
    private final ExecutorService executor = Executors.newCachedThreadPool();

    public CompletableFuture<Void> modifyStock(MaterialType type, int quantity) {
        return CompletableFuture.supplyAsync(() -> {
            stocks.computeIfAbsent(type, Material::new).adjustQuantity(quantity);
            return null;
        }, executor);
    }

    public void displayStock() {
        stocks.forEach((type, material) -> {
            System.out.println(type.getName() + " has " + material.getQuantity() + " units");
        });

    }
}

