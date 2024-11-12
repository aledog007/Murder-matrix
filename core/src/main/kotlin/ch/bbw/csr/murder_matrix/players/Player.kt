package ch.bbw.csr.murder_matrix.players

import ch.bbw.csr.murder_matrix.items.Item
import ch.bbw.csr.murder_matrix.world.Direction
import ch.bbw.csr.murder_matrix.world.Room

class Player(@JvmField var currentRoom: Room) {
    private val inventory = Inventory()

    fun move(direction: Direction?): Boolean {
        val nextRoom = currentRoom.getExit(direction)
        if (nextRoom != null) {
            currentRoom = nextRoom
            return true
        }
        return false
    }

    fun addToInventory(item: Item) {
        inventory.addItem(item)
    }

    fun removeFromInventory(item: Item): Boolean {
        return inventory.removeItem(item)
    }

    fun hasItem(item: Item): Boolean {
        return inventory.containsItem(item)
    }

    fun examineItem(item: Item): String? {
        if (hasItem(item)) {
            return "You examine the " + item.type + " closely. It seems important."
        }
        return null
    }

    fun getInventory(): List<String> {
        return inventory.itemNames
    }

    fun getInventoryInventory(): Inventory = inventory
}
