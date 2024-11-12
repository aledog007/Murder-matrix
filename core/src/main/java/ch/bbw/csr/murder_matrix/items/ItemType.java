package ch.bbw.csr.murder_matrix.items;

/**
 * The enum Item type.
 */
public enum ItemType implements BaseItem {
    /**
     * Sword item type.
     */
    SWORD("Sword", 5.0f),
    /**
     * Gun item type.
     */
    GUN("Gun", 30.0f),
    /**
     * Key item type.
     */
    KEY("Key", 0.5f);

    private final String key;
    private final float weight;

    ItemType(String key, float weight) {
        this.key = key;
        this.weight = weight;
    }

    public String getKey() {
        return key;
    }

    public float getWeight() {
        return weight;
    }
}

