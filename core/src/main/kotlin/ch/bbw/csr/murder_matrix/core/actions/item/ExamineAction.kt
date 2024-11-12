package ch.bbw.csr.murder_matrix.core.actions.item

import ch.bbw.csr.murder_matrix.core.actions.CommandAction
import ch.bbw.csr.murder_matrix.items.BaseItem
import ch.bbw.csr.murder_matrix.items.Item
import ch.bbw.csr.murder_matrix.players.Player

class ExamineAction(private val player: Player, private val target: BaseItem) : CommandAction {
    override fun execute(): String {
        // Check if it's an item in the room
        var itemDescription = player.currentRoom.examineItem(Item(target))
        if (itemDescription != null) {
            return itemDescription
        }

        // Check if it's an item in the inventory
        itemDescription = player.examineItem(Item(target))
        if (itemDescription != null) {
            return itemDescription
        }

        // Check if it's an NPC
        //NPC npc = player.getCurrentRoom().getNPC(target);
        //if (npc != null) {
        //    return npc.getDescription();
        //}
        return "You don't see anything special about that."
    }
}
