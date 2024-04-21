package am.application;

public enum MaterialType {
    IRON("Iron", "For construction", "/icon/path.png", 100),
    COPPER("Copper", "For electical circuits", "/icon/path1.png", 80),
    BOLT("Bolt", "Construction", "/icon/path2.png", 130),
    NEW("New", "Other", "/icon/path4.png", 10);

    private final String name;
    private final String description;
    private final String icon;
    private final int maxCapacity;

    MaterialType(String name, String description, String icon, int maxCapacity) {
        this.name = name;
        this.description = description;
        this.icon = icon;
        this.maxCapacity = maxCapacity;
    }

    public String getName() {
        return name;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }
}
