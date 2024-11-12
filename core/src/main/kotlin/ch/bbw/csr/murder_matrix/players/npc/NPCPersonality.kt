package ch.bbw.csr.murder_matrix.players.npc

enum class NPCPersonality(val description: String) {
    DEFAULT("Nothing special, normal roaming behaviour"),
    SHY("Prefers to stay in one or two specific rooms"),
    CURIOUS("Moves often, exploring many rooms"),
    SOCIAL("Seeks out rooms with other NPCs")
}
