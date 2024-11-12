package ch.bbw.csr.murder_matrix.core

import ch.bbw.csr.murder_matrix.ai.AzureAIClient
import ch.bbw.csr.murder_matrix.players.Player
import ch.bbw.csr.murder_matrix.world.World

/**
 * @author Cédric Skwar <cdrc></cdrc>@5y5.one>
 */
class CommandParser(private val gameScreen: GameScreen?, internal val world: World, internal val player: Player) {
    internal var npcOrItemName: String? = null
    fun parseCommand(command: String?): String? {
        val directionResolver = AzureAIClient()
        directionResolver.setSystemPrompt(
            String.format(
                """
            You are the interpreter for a text-based adventure game. Your task is to output the player's movement or status based on their input. The output should be concise and follow the format specified in the rules below.

            If the user asks for help (e.g., "Help" or "Show me available commands"), respond with "staying, help".

            Rules (in order of precedence):
            1. Inventory check: If the user asks to see their inventory, return "inventory".
            2. Room check: If the user asks to see the rooms next to them, return "looking".
            4. Valid movement: If the user moves in a valid direction (not blocked by a wall or locked door), return only the direction (e.g., "west", "east", "north", or "south").
            5. Key usage on locked door: If the user uses a key to move through a locked door and the inventory includes at least 1 key, return the direction followed by "unlocked" (e.g., "down, unlocked").
            6. Wall collision: If the user attempts to move into a wall, return "staying, wall".
            7. Locked door without key: If the user attempts to move through a locked door without using a key, return "staying, locked".
            8. Key usage without locked door: If the user mentions using a key on a direction where no locked door is present (e.g., a wall or an open path), return only the direction without "unlocked" (e.g., "left").
            9. Staying in room: If the user attempts to stay in the room or perform an action involving no movement, return "staying, same".
            10. Raw direction input: If the user inputs any direction directly ("north", "south", "west", or "east"), return only that direction. This rule is applied after all other rules for staying have been evaluated.
            11. Directions given in ("up", "down", "left", "right") resolve to their corresponding direction in a compass, e.g "up" is "north".
            12. If the user specifies a talking action copy the name from the command and return it like this: `talking, NPCNAME`.
            13. If the user specifies guessing action copy the name from the command and return it like this: `guessing, NPCNAME`.
            14. If the user asks to go to the previous room or go back, return (going, back).
            15. If the user asks to pickup an item, return (take, ITEMNAME).
            16. If the user asks to drop an item, return (drop, ITEMNAME).
            17. If the user asks what the current day is, return (day)
            18. If the user asks for map, return (map)

            General guidelines:
            - Do not include any extra information beyond the specified direction or status.
            - Always respond in lowercase.
            - ALWAYS fixup the NPCNAMEs if the user misspelled them.
            - ALWAYS fixup the ITEMNAMEs
            - Use only the following outputs: "north", "south", "west", "east", "staying", "inventory", "looking", or combinations as specified in the rules (e.g., "south, unlocked", "staying, wall").

            Available information:
            Rooms next to the player: %s
            The player's inventory: %s
            The rooms item content: %s
            All NPCs in the world: %s

            """.trimIndent(),
                player.currentRoom.getExits(),
                player.getInventory(),
                player.currentRoom.items.joinToString(separator = ", "),
                world.NPCs.joinToString { it!!.name })
        )
        return directionResolver.getResponse(command)
    }


    fun executeCommand(command: String?): String {
        val cleanedCommand = command?.trim().orEmpty()
        npcOrItemName = cleanedCommand.split(",").getOrNull(1)?.filterNot { it.isWhitespace() }

        return Command.fromCommandText(cleanedCommand)?.actionProvider?.invoke(this)?.execute() ?: "Unknown command"
    }

    fun commander(command: String?): String {
        val parsedCommand = parseCommand(command)
        return executeCommand(parsedCommand)
    }

    internal fun extractNpcOrItemName(): String {
        return npcOrItemName?.let { return it }.toString()
    }
}
