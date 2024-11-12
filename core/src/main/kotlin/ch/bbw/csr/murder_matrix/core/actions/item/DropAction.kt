package ch.bbw.csr.murder_matrix.core.actions.item

import ch.bbw.csr.murder_matrix.core.actions.CommandAction
import ch.bbw.csr.murder_matrix.players.Player

class DropAction(private val player: Player, private val itemName: String) : CommandAction {
    override fun execute(): String {
        val item = player.getInventoryInventory().getItem(itemName)
        return if (item == null) {
            "You don't have the $itemName in your inventory."
        } else {
            player.removeFromInventory(item)
            player.currentRoom.addItem(item)
            "You dropped your $itemName in the current room."
        }
    }
}
