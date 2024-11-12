package ch.bbw.csr.murder_matrix.items;


/**
 * The enum Clues type.
 */
public enum CluesType implements BaseItem {
    /**
     * The Broken watch.
     */
    BROKEN_WATCH("Broken watch", 0.0f),
    /**
     * The Crumpled note.
     */
    CRUMPLED_NOTE("Crumpled note", 0.0f),
    /**
     * The Muddy footprint.
     */
    MUDDY_FOOTPRINT("Muddy footprint", 0.0f),
    /**
     * The Strange key.
     */
    STRANGE_KEY("Strange key", 0.0f),
    /**
     * The Torn piece of fabric.
     */
    TORN_PIECE_OF_FABRIC("Torn piece of Fabric", 0.0f),
    /**
     * The Advanced blood stain.
     */
    ADVANCED_BLOOD_STAIN("A blood stain", 0.0f);

    private final String key;
    private final float weight;

    CluesType(String key, float weight) {
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
