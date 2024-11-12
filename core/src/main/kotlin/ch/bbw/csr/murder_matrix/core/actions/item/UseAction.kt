package ch.bbw.csr.murder_matrix.core.actions.item

import ch.bbw.csr.murder_matrix.core.actions.CommandAction
import ch.bbw.csr.murder_matrix.items.Item
import ch.bbw.csr.murder_matrix.players.Player

class UseAction(private val player: Player, private val item: Item) : CommandAction {
    override fun execute(): String {
        return if (player.hasItem(item)) {
            player.removeFromInventory(item)
            "You use the $item."
        } else {
            "You don't have that item."
        }
    }

}
