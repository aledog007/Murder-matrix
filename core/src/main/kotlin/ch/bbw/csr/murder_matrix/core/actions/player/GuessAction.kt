package ch.bbw.csr.murder_matrix.core.actions.player

import ch.bbw.csr.murder_matrix.core.actions.CommandAction
import ch.bbw.csr.murder_matrix.world.World

class GuessAction(private val world: World, private val npcName: String) : CommandAction {
    override fun execute(): String {
        val murderers = world.NPCs.filter { it!!.isMurderer }

        return if (murderers.any { it!!.name == npcName }) {
            "You guessed correctly! The murderer is $npcName. SPECIAL-PLAYER-WON"
        } else {
            "Wrong guess! The murderer isn't $npcName. You got arrested for incorrect accusations. SPECIAL-PLAYER-LOSS"
        }
    }
}
