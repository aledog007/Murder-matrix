package ch.bbw.csr.murder_matrix.world

import ch.bbw.csr.murder_matrix.items.CluesType
import ch.bbw.csr.murder_matrix.items.Item
import ch.bbw.csr.murder_matrix.items.ItemType
import ch.bbw.csr.murder_matrix.players.NPC
import ch.bbw.csr.murder_matrix.players.npc.NPCPersonality
import ch.bbw.csr.murder_matrix.players.npc.NPCSettings
import java.util.*

/**
 * The type World.
 */
class World {
    private val rooms: MutableList<Room> = ArrayList<Room>()

    /**
     * Gets np cs.
     *
     * @return the np cs
     */
    val NPCs: MutableList<NPC?> = ArrayList<NPC?>()
    private val random: Random = Random()
    private var murderer: NPC? = null
    /**
     * Gets current day.
     *
     * @return the current day
     */
    /**
     * Sets current day.
     *
     * @param currentDay the current day
     */
    @JvmField
    var currentDay: Int = 1

    /**
     * Instantiates a new World.
     */
    init {
        initializeRooms()
        initializeNPCs()
        selectMurderer()
        distributeClues()
    }

    private fun initializeRooms() {
        val hall = Room("Grand Hall", "A spacious hall with ornate decorations.")
        val kitchen = Room("Kitchen", "A bustling kitchen filled with the aroma of spices.")
        kitchen.setOnEnter("You smell a strong aroma of spices as you enter the kitchen.")
        kitchen.setOnExit("You leave the kitchen, still sensing the aroma lingering behind.")
        val library = Room("Library", "Walls lined with books and a cozy fireplace.")
        val diningRoom = Room("Dining Room", "A grand table set for an elaborate feast.")
        val study = Room("Study", "A quiet room with a large desk and leather chairs.")
        val conservatory = Room("Conservatory", "A glass-walled room filled with exotic plants.")
        val ballroom = Room("Ballroom", "A vast room with a polished dance floor.")
        val billiardRoom = Room("Billiard Room", "A room with a large billiard table and hunting trophies.")

        hall.addExit("north", kitchen)
        hall.addExit("east", library)
        hall.addExit("south", diningRoom)
        hall.addExit("west", ballroom)
        kitchen.addExit("south", hall)
        kitchen.addExit("east", study)
        library.addExit("west", hall)
        library.addExit("north", study)
        diningRoom.addExit("north", hall)
        diningRoom.addExit("east", conservatory)
        study.addExit("west", kitchen)
        study.addExit("south", library)
        conservatory.addExit("west", diningRoom)
        conservatory.addExit("north", billiardRoom)
        ballroom.addExit("east", hall)
        ballroom.addExit("north", billiardRoom)
        billiardRoom.addExit("south", ballroom)
        billiardRoom.addExit("east", study)
        billiardRoom.addExit("south", conservatory)

        rooms.addAll(
            listOf(
                hall,
                kitchen,
                library,
                diningRoom,
                study,
                conservatory,
                ballroom,
                billiardRoom
            )
        )

        val itemTypes = ItemType.entries.toTypedArray()
        val maxItemsPerRoom = 3
        for (room in rooms) {
            val numberOfItems = random.nextInt(maxItemsPerRoom) + 1
            (1..numberOfItems).forEach { _ ->
                val randomItemType = itemTypes[random.nextInt(itemTypes.size)]
                room.addItem(Item(randomItemType))
            }
        }
    }

    private fun initializeNPCs() {
        val butlerSettings =
            NPCSettings(
                isMurderer = false,
                movementFrequency = 0.5f,
                personality = NPCPersonality.SOCIAL,
                preferredRooms = listOf("Dining Room", "Library")
            )
        val chefSettings = NPCSettings(
            isMurderer = false,
            personality = NPCPersonality.SHY,
            movementFrequency = 0.15f,
            preferredRooms = listOf("Dining Room", "Kitchen")
        )
        val librarianSettings =
            NPCSettings(
                isMurderer = false,
                movementFrequency = 0.1f,
                personality = NPCPersonality.SHY,
                preferredRooms = listOf("Library")
            )
        val maidSettings =
            NPCSettings(
                isMurderer = false,
                movementFrequency = 1f,
                personality = NPCPersonality.CURIOUS,
                preferredRooms = listOf("Library", "Kitchen")
            )
        val gardenerSettings = NPCSettings(
            isMurderer = false,
            personality = NPCPersonality.CURIOUS,
            movementFrequency = 0.76f,
            preferredRooms = listOf("Ballroom", "Billiard Room")
        )
        val butler = NPC("Butler", "A stern-looking man in a tailcoat.", butlerSettings)
        val chef = NPC("Chef", "A jolly woman with flour on her apron.", chefSettings)
        val librarian = NPC("Librarian", "An elderly man with spectacles.", librarianSettings)
        val maid = NPC("Maid", "A young woman in a neat uniform.", maidSettings)
        val gardener = NPC("Gardener", "A rugged man with dirt-stained hands.", gardenerSettings)
        NPCs.addAll(listOf(butler, chef, librarian, maid, gardener))

        for (npc in this.NPCs) {
            val randomRoom = rooms[random.nextInt(rooms.size)]
            randomRoom.addNPC(npc)
        }
    }

    private fun selectMurderer() {
        val murdererIndex = random.nextInt(NPCs.size)
        murderer = NPCs[murdererIndex]
        murderer!!.isMurderer = true
    }

    private fun distributeClues() {
        val clues = mutableListOf<Item?>(
            Item(CluesType.TORN_PIECE_OF_FABRIC),
            Item(CluesType.MUDDY_FOOTPRINT),
            Item(CluesType.CRUMPLED_NOTE),
            Item(CluesType.BROKEN_WATCH),
            Item(CluesType.STRANGE_KEY)
        )

        for (clue in clues) {
            val randomRoom = rooms.get(random.nextInt(rooms.size))
            randomRoom.addItem(clue)
        }
    }

    /**
     * Update.
     *
     * @param delta the delta
     */
    fun update(delta: Float) {
        // Move NPCs randomly
        for (npc in this.NPCs) {
            if ((random.nextFloat() < 0.1f) && (delta % 5 == 0f)) {
                var currentRoom: Room? = null
                for (room in rooms) {
                    if (room.npcsInRoom.contains(npc)) {
                        currentRoom = room
                        break
                    }
                }
                if (currentRoom != null) {
                    val exits = currentRoom.getExits()
                    var nextRoom: Room? = null
                    when (npc!!.personality) {
                        NPCPersonality.SHY -> {
                            if (!npc.prefersRoom(currentRoom.name)) {
                                nextRoom = selectPreferredRoomOrRandom(currentRoom, exits, npc)
                            }
                        }

                        NPCPersonality.CURIOUS -> {
                            nextRoom = currentRoom.getExit(exits[random.nextInt(exits.size)])
                        }

                        NPCPersonality.SOCIAL -> {
                            nextRoom = rooms.stream()
                                .filter { room -> room.npcsInRoom.isNotEmpty() && !room.npcsInRoom.contains(npc) }
                                .findAny()
                                .orElse(currentRoom.getExit(exits[random.nextInt(exits.size)]))
                        }

                        else -> {
                            nextRoom = currentRoom.getExit(exits[random.nextInt(exits.size)])
                        }
                    }

                    if (nextRoom != null && nextRoom != currentRoom) {
                        currentRoom.removeNPC(npc)
                        nextRoom.addNPC(npc)
                    }
                }
            }
        }

        if (delta > 30) {
            advanceDay()
        }
    }

    fun advanceDay() {
        currentDay++
        // The murderer commits another crime
        if (murderer != null) {
            val murderRoom = rooms[random.nextInt(rooms.size)]
            val victim = murderRoom.npcsInRoom.stream()
                .filter { npc: NPC? -> !npc!!.isMurderer }
                .findAny()
                .orElse(null)
            if (victim != null) {
                murderRoom.removeNPC(victim)
                NPCs.remove(victim)
                murderRoom.addItem(Item(CluesType.ADVANCED_BLOOD_STAIN))
            } else {
                val exits = murderRoom.exits
                if (exits.isNotEmpty()) {
                    val randomExit = exits[random.nextInt(exits.size)]
                    val nextRoom = murderRoom.getExit(randomExit)
                    murderRoom.removeNPC(murderer)
                    nextRoom.addNPC(murderer)
                }
            }
        }
    }

    val startingRoom: Room?
        /**
         * Gets starting room.
         *
         * @return the starting room
         */
        get() = rooms[0]


    private fun selectPreferredRoomOrRandom(currentRoom: Room, exits: List<String>, npc: NPC): Room {
        val preferredExits = exits.filter { npc.prefersRoom(currentRoom.getExit(it).name) }

        val chosenExit = if (preferredExits.isNotEmpty()) {
            preferredExits.random()
        } else {
            exits.random()
        }

        return currentRoom.getExit(chosenExit)
    }

}
