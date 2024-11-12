package ch.bbw.csr.murder_matrix.core.actions.utils

import ch.bbw.csr.murder_matrix.players.Player

class LookUtility(private val player: Player) {

    fun describeCurrentRoom(): String = describeCurrentRoomDelegate(player)

    companion object {
        fun describeCurrentRoom(player: Player): String = describeCurrentRoomDelegate(player)
    }
}

private fun describeCurrentRoomDelegate(player: Player): String = buildString {
    val currentRoom = player.currentRoom
    appendLine("You are in ${currentRoom.name}.")
    appendLine(currentRoom.description)

    currentRoom.npcsInRoom.takeIf { it.isNotEmpty() }?.let {
        appendLine("You see the following people here:")
        it.forEach { npc -> appendLine("- ${npc.name}") }
    }

    currentRoom.getItems().takeIf { it.isNotEmpty() }?.let {
        appendLine("You see the following items:")
        it.forEach { item -> appendLine("- $item") }
    }

    appendLine("Exits: ${currentRoom.getExits().joinToString(", ")}")
}
