package ch.bbw.csr.murder_matrix.core.actions.item

import ch.bbw.csr.murder_matrix.core.actions.CommandAction
import ch.bbw.csr.murder_matrix.players.Player

class ShowInventoryAction(private val player: Player) : CommandAction {
    override fun execute(): String {
        val items: List<String> = player.getInventory()
        if (items.isEmpty()) {
            return "Your inventory is empty."
        } else {
            val sb = StringBuilder("You are carrying:\n")
            for (item in items) {
                sb.append("- ").append(item).append("\n")
            }
            return sb.toString()
        }
    }
}
