package ch.bbw.csr.murder_matrix.core.actions.player

import ch.bbw.csr.murder_matrix.core.actions.CommandAction
import ch.bbw.csr.murder_matrix.players.Player

class TalkAction(private val player: Player, private val npcName: String) : CommandAction {
    override fun execute(): String {
        val npc = player.currentRoom.getNPC(npcName)
        return npc?.talk() ?: "There's no one here by that name."
    }
}
