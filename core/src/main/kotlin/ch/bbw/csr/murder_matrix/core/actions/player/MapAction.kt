package ch.bbw.csr.murder_matrix.core.actions.player

import ch.bbw.csr.murder_matrix.core.actions.CommandAction
import ch.bbw.csr.murder_matrix.players.Player
import ch.bbw.csr.murder_matrix.world.Room
import ch.bbw.csr.murder_matrix.world.World

class MapAction(private val player: Player, private val world: World) : CommandAction {

    private data class Position(val x: Int, val y: Int)

    override fun execute(): String {
        val roomPositions = calculateRoomPositions()

        val (minX, maxX, minY, maxY) = findMapDimensions(roomPositions.values)

        return buildGridMap(roomPositions, minX, maxX, minY, maxY)
    }

    private fun calculateRoomPositions(): Map<Room, Position> {
        val positions = mutableMapOf<Room, Position>()
        val visited = mutableSetOf<Room>()

        val startRoom = player.currentRoom
        positions[startRoom] = Position(0, 0)

        val queue = ArrayDeque<Room>()
        queue.add(startRoom)

        while (queue.isNotEmpty()) {
            val currentRoom = queue.removeFirst()
            if (currentRoom in visited) continue
            visited.add(currentRoom)

            val currentPos = positions[currentRoom]!!

            for (exit in currentRoom.getExits()) {
                val nextRoom = currentRoom.getExit(exit)
                if (nextRoom !in positions) {
                    // Calculate next room position based on direction
                    val nextPos = when (exit.lowercase()) {
                        "north" -> Position(currentPos.x, currentPos.y - 1)
                        "south" -> Position(currentPos.x, currentPos.y + 1)
                        "east" -> Position(currentPos.x + 1, currentPos.y)
                        "west" -> Position(currentPos.x - 1, currentPos.y)
                        else -> continue
                    }
                    positions[nextRoom] = nextPos
                    queue.add(nextRoom)
                }
            }
        }

        return positions
    }

    private fun findMapDimensions(positions: Collection<Position>): Array<Int> {
        val minX = positions.minOf { it.x }
        val maxX = positions.maxOf { it.x }
        val minY = positions.minOf { it.y }
        val maxY = positions.maxOf { it.y }
        return arrayOf(minX, maxX, minY, maxY)
    }

    private fun buildGridMap(
        roomPositions: Map<Room, Position>,
        minX: Int,
        maxX: Int,
        minY: Int,
        maxY: Int
    ): String {
        val mapBuilder = StringBuilder("Map Layout:\n")

        for (y in minY..maxY) {
            val roomRow = StringBuilder()
            val connectRow = StringBuilder()

            for (x in minX..maxX) {
                val roomAtPosition = roomPositions.entries.find { it.value == Position(x, y) }?.key

                if (roomAtPosition != null) {
                    // Add room representation
                    val roomSymbol = when {
                        roomAtPosition == player.currentRoom -> "[*${roomAtPosition.name.take(1)}*]"
                        else -> "[ ${roomAtPosition.name.take(1)} ]"
                    }
                    roomRow.append(roomSymbol)

                    // Add horizontal connection if there's a room to the east
                    val eastRoom = roomAtPosition.getExit("east")
                    connectRow.append(if (eastRoom != null) "----" else "    ")
                } else {
                    roomRow.append("     ")
                    connectRow.append("     ")
                }
            }
            mapBuilder.append(roomRow).append("\n")

            // Add vertical connections if this isn't the last row
            if (y < maxY) {
                for (x in minX..maxX) {
                    val roomAtPosition = roomPositions.entries.find { it.value == Position(x, y) }?.key
                    if (roomAtPosition != null && roomAtPosition.getExit("south") != null) {
                        mapBuilder.append("  |  ")
                    } else {
                        mapBuilder.append("     ")
                    }
                }
                mapBuilder.append("\n")
            }
        }

        mapBuilder.append("\nLegend:\n")
        mapBuilder.append("[*X*] - Your current location\n")
        mapBuilder.append("[ X ] - Room (first letter of room name)\n")
        mapBuilder.append("---- - East/West connection\n")
        mapBuilder.append("  |  - North/South connection\n")

        return mapBuilder.toString()
    }
}
