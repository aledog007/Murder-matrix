package ch.bbw.csr.murder_matrix.players

import ch.bbw.csr.murder_matrix.players.npc.NPCPersonality
import ch.bbw.csr.murder_matrix.players.npc.NPCSettings
import java.util.*

class NPC(@JvmField val name: String, val description: String, val settings: NPCSettings = NPCSettings()) {
    @JvmField
    var isMurderer: Boolean = settings.isMurderer
    private val dialogue: MutableList<String> = ArrayList()
    var personality: NPCPersonality = settings.personality
    var movementFrequency: Float = settings.movementFrequency
    var preferredRooms: List<String> = settings.preferredRooms

    init {
        initializeDialogue()
    }

    private fun initializeDialogue() {
        dialogue.clear()

        dialogue.add("Hello there! Lovely weather we're having, isn't it?")
        dialogue.add("I'm afraid I haven't seen anything suspicious.")
        dialogue.add("Have you tried the hors d'oeuvres? They're delightful!")
        dialogue.add("Oh my, a murder you say? How dreadful!")

        when (personality) {
            NPCPersonality.SHY -> dialogue.add("I prefer staying in quieter rooms.")
            NPCPersonality.CURIOUS -> dialogue.add("I love exploring every nook and cranny.")
            NPCPersonality.SOCIAL -> dialogue.add("Is there a gathering happening here?")
            else -> {}
        }
    }

    fun talk(): String {
        return if (isMurderer) {
            val nervousDialogues = listOf(
                "Hello there! Lovely weather we're having, isn't it? *looks nervous*",
                "I didn't see anything unusual, honestly.",
                "Murder? I... I wouldn't know anything about that!",
                "Oh dear, this is all very unsettling..."
            )
            nervousDialogues[Random().nextInt(nervousDialogues.size)]
        } else {
            dialogue[Random().nextInt(dialogue.size)]
        }
    }

    fun prefersRoom(room: String): Boolean {
        return preferredRooms.contains(room)
    }
}
