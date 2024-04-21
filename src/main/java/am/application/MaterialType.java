package am.application;

public enum MaterialType {
    IRON("Iron", "For construction","/icon/path.png",100),
    COOPER("Copper", "For electical circuits","/icon/path1.png",100),
    BOLT("Bolt", "Construction","/icon/path2.png",100),
    NEW("New", "Other","/icon/path4.png",100);

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

    public String getDescription() {
        return description;
    }

    public String getIcon() {
        return icon;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }
}
