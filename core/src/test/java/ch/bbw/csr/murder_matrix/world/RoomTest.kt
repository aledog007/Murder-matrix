package ch.bbw.csr.murder_matrix.world

import ch.bbw.csr.murder_matrix.items.Item
import ch.bbw.csr.murder_matrix.items.ItemType
import ch.bbw.csr.murder_matrix.players.NPC
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class RoomTest {
    private var room: Room? = null

    @BeforeEach
    fun setUp() {
        room = Room("Living Room", "A cozy living room with a fireplace.")
    }

    @Test
    fun testAddAndGetExit() {
        val kitchen = Room("Kitchen", "A modern kitchen.")
        room!!.addExit("north", kitchen)

        Assertions.assertEquals(kitchen, room!!.getExit("north"))
        Assertions.assertEquals(kitchen, room!!.getExit(Direction.NORTH))
    }

    @Test
    fun testAddAndRemoveNPC() {
        val npc = NPC("John", "A friendly neighbor")
        room!!.addNPC(npc)

        Assertions.assertTrue(room!!.npcsInRoom.contains(npc))

        room!!.removeNPC(npc)
        Assertions.assertFalse(room!!.npcsInRoom.contains(npc))
    }

    @Test
    fun testGetNPC() {
        val john = NPC("John", "A friendly neighbor")
        val jane = NPC("Jane", "A mysterious lady")
        room!!.addNPC(john)
        room!!.addNPC(jane)

        Assertions.assertEquals(john, room!!.getNPC("John"))
        Assertions.assertEquals(jane, room!!.getNPC("Jane"))
        Assertions.assertNull(room!!.getNPC("Bob"))
    }

    @Test
    fun testAddAndRemoveItem() {
        val item = Item(ItemType.KEY)
        room!!.addItem(item)

        Assertions.assertTrue(room!!.hasItem(item))

        room!!.removeItem(item)
        Assertions.assertFalse(room!!.hasItem(item))
    }

    @Test
    fun testExamineItem() {
        val key = Item(ItemType.KEY)
        room!!.addItem(key)

        val description = room!!.examineItem(key)
        Assertions.assertNotNull(description)
        Assertions.assertTrue(description!!.contains("Key"))
    }

    @Test
    fun testGetExits() {
        val kitchen = Room("Kitchen", "A modern kitchen.")
        val bedroom = Room("Bedroom", "A cozy bedroom.")
        room!!.addExit("north", kitchen)
        room!!.addExit("east", bedroom)

        val exits = room!!.getExits()
        Assertions.assertEquals(2, exits.size)
        Assertions.assertTrue(exits.contains("north"))
        Assertions.assertTrue(exits.contains("east"))
    }

    @Test
    fun testGetItems() {
        val key = Item(ItemType.KEY)
        val book = Item(ItemType.SWORD)
        room!!.addItem(key)
        room!!.addItem(book)

        val items = room!!.getItems()
        Assertions.assertEquals(2, items.size)
        Assertions.assertTrue(items.contains("Key"))
        Assertions.assertTrue(items.contains("Sword"))
    }

    @Test
    fun testToString() {
        val kitchen = Room("Kitchen", "A modern kitchen.")
        room!!.addExit("north", kitchen)

        val john = NPC("John", "A friendly neighbor")
        room!!.addNPC(john)

        val key = Item(ItemType.KEY)
        room!!.addItem(key)

        val description = room.toString()
        Assertions.assertTrue(description.contains("Living Room"))
        Assertions.assertTrue(description.contains("A cozy living room with a fireplace."))
        Assertions.assertTrue(description.contains("John"))
        Assertions.assertTrue(description.contains("Key"))
        Assertions.assertTrue(description.contains("Exits: north"))
    }
}
