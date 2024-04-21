package am.application;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Material {

    private final MaterialType type;
    private final AtomicInteger quantity = new AtomicInteger(0);
    private final ReadWriteLock rwLock = new ReentrantReadWriteLock();

    public Material(MaterialType type) {
        this.type = type;
    }

    public void adjustQuantity(int amount) {
        rwLock.writeLock().lock();
        try {
            int newQuantity = quantity.get()+amount;
            if (newQuantity > type.getMaxCapacity()||newQuantity <0) {
throw new InventoryException("Operation outide limits for " + type.getName());            }
            quantity.addAndGet(amount);
        } finally {
            rwLock.writeLock().unlock();
        }
    }
}