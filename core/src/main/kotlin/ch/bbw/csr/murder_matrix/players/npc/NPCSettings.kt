package ch.bbw.csr.murder_matrix.players.npc

data class NPCSettings(
    val isMurderer: Boolean = false,
    val movementFrequency: Float = 0.1f,
    val personality: NPCPersonality = NPCPersonality.DEFAULT,
    val preferredRooms: List<String> = listOf("Library", "Study")
)
