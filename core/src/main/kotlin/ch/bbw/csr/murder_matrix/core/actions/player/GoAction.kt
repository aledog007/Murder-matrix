package ch.bbw.csr.murder_matrix.core.actions.player

import ch.bbw.csr.murder_matrix.core.GlobalConstants
import ch.bbw.csr.murder_matrix.core.actions.CommandAction
import ch.bbw.csr.murder_matrix.core.actions.utils.LookUtility
import ch.bbw.csr.murder_matrix.players.Player
import ch.bbw.csr.murder_matrix.world.Direction

class GoAction(private val player: Player, private val direction: Direction) : CommandAction {
    override fun execute(): String {
        val currentRoom = player.currentRoom

        return if (player.move(direction)) {
            val onExitMessage = currentRoom.triggerOnExit().takeIf { it.isNotBlank() } ?: ""
            val onEnterMessage = player.currentRoom.triggerOnEnter().takeIf { it.isNotBlank() } ?: ""

            GlobalConstants.lastDirection = Direction.Companion.invertDirection(direction)
            listOfNotNull(
                onExitMessage.takeIf { it.isNotEmpty() },
                "You go ${direction.toString().lowercase()}\n",
                onEnterMessage.takeIf { it.isNotEmpty() },
                LookUtility.describeCurrentRoom(player)
            ).joinToString(separator = "\n")
        } else {
            "You can't go that way."
        }
    }
}
