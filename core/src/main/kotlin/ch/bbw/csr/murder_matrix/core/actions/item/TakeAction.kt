package ch.bbw.csr.murder_matrix.core.actions.item

import ch.bbw.csr.murder_matrix.core.actions.CommandAction
import ch.bbw.csr.murder_matrix.players.Player

class TakeAction(private val player: Player, private val itemName: String) : CommandAction {
    override fun execute(): String {
        val inventory = player.getInventoryInventory()
        val item = player.currentRoom.getItem(itemName) ?: return "You don't see that here."

        return if (item.weight + inventory.getWeight() <= inventory.getMaxWeight()) {
            player.addToInventory(item)
            player.currentRoom.removeItem(item)
            "You take the ${item.itemName}."
        } else {
            "Item would put you over max weight limit"
        }
    }
}
