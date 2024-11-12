package ch.bbw.csr.murder_matrix.core

import ch.bbw.csr.murder_matrix.core.Command.entries
import ch.bbw.csr.murder_matrix.core.actions.CommandAction
import ch.bbw.csr.murder_matrix.core.actions.item.DropAction
import ch.bbw.csr.murder_matrix.core.actions.item.ShowInventoryAction
import ch.bbw.csr.murder_matrix.core.actions.item.TakeAction
import ch.bbw.csr.murder_matrix.core.actions.player.*
import ch.bbw.csr.murder_matrix.core.actions.util.CurrentDayRetrievalAction
import ch.bbw.csr.murder_matrix.core.actions.util.ShowHelpAction
import ch.bbw.csr.murder_matrix.world.Direction

enum class Command(
    val keyword: String,
    val description: String,
    val actionProvider: (CommandParser) -> CommandAction
) {
    HELP("help", "Show this help message", { ShowHelpAction() }),
    INVENTORY("inventory", "Check your inventory", { parser -> ShowInventoryAction(parser.player) }),
    LOOK("look", "Examine your surroundings", { parser -> LookAction(parser.player) }),
    NORTH("north", "Move north", { parser -> GoAction(parser.player, Direction.NORTH) }),
    SOUTH("south", "Move south", { parser -> GoAction(parser.player, Direction.SOUTH) }),
    EAST("east", "Move east", { parser -> GoAction(parser.player, Direction.EAST) }),
    WEST("west", "Move west", { parser -> GoAction(parser.player, Direction.WEST) }),
    BACK("back", "Return to the previous location", { parser ->
        GlobalConstants.lastDirection?.let { lastDir ->
            GoAction(parser.player, lastDir)
        } ?: throw IllegalStateException("No previous direction available")
    }),
    TALK("talk", "Talk to someone in the room", { parser ->
        val npcName = parser.extractNpcOrItemName()
        TalkAction(parser.player, npcName)
    }),
    GUESS("guess", "Guess who the murderer is", { parser ->
        val npcName = parser.extractNpcOrItemName()
        GuessAction(parser.world, npcName)
    }),
    MAP("map", "Display the map", { parser ->
        MapAction(parser.player, parser.world)
    }),
    DROP("drop", "Drop an item from your inventory", { parser ->
        DropAction(parser.player, parser.extractNpcOrItemName())
    }),
    TAKE("take", "Pick up an item", { parser ->
        TakeAction(parser.player, parser.extractNpcOrItemName())
    }),
    CURRENT_DAY("day", "Show the current day", { parser ->
        CurrentDayRetrievalAction(parser.world)
    });

    companion object {
        private val keywordMap = entries.associateBy { it.keyword.lowercase() }

        fun fromCommandText(text: String): Command? {
            return keywordMap.entries.firstOrNull { text.lowercase().contains(it.key) }?.value
        }
    }
}
