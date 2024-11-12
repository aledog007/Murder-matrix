package ch.bbw.csr.murder_matrix.players

import ch.bbw.csr.murder_matrix.items.Item

class Inventory(
    private val maxSize: Int = 50,
    private val maxWeight: Double = 30.0
) {
    private val items = mutableListOf<Item>()
    private var currentWeight = 0.0

    fun addItem(item: Item): Boolean {
        return if (items.size < maxSize && currentWeight + item.weight <= maxWeight) {
            items.add(item)
            currentWeight += item.weight
            true
        } else {
            false
        }
    }

    fun removeItem(item: Item): Boolean {
        return items.remove(item).also { removed ->
            if (removed) currentWeight -= item.weight
        }
    }

    val currentSize: Int
        get() = items.size

    fun containsItem(item: Item): Boolean = item in items

    fun getItems(): List<Item> = items.toList()

    val itemNames: List<String>
        get() = items.map { it.itemName!! }

    fun getWeight(): Double = currentWeight

    fun getMaxWeight(): Double = maxWeight

    fun getItem(name: String): Item? {
        for (item in items) {
            if (item.itemName?.lowercase() == name.lowercase()) return item
        }
        return null
    }
}
