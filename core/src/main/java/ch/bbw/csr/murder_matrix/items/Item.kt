package ch.bbw.csr.murder_matrix.items

class Item(@JvmField val type: BaseItem) {
    val weight: Double

    init {
        this.weight = type.getWeight().toDouble()
    }

    override fun toString(): String {
        return type.getKey() + " (Weight: " + weight + ")"
    }

    val itemName: String?
        get() = type.getKey()
}
