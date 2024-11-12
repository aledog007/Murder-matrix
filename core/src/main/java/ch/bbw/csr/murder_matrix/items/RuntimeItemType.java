package ch.bbw.csr.murder_matrix.items;

import java.util.HashMap;
import java.util.Map;

/**
 * The type Runtime item type.
 */
public class RuntimeItemType implements BaseItem {
    private static final Map<String, RuntimeItemType> RUNTIME_ITEMS = new HashMap<>();

    private final String key;
    private final float weight;

    // Private constructor for controlled creation
    private RuntimeItemType(String key, float weight) {
        this.key = key;
        this.weight = weight;
    }

    /**
     * Gets or create.
     *
     * @param key    the key
     * @param weight the weight
     * @return the or create
     */
    public static RuntimeItemType getOrCreate(String key, float weight) {
        return RUNTIME_ITEMS.computeIfAbsent(key, k -> new RuntimeItemType(k, weight));
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public float getWeight() {
        return weight;
    }
}
