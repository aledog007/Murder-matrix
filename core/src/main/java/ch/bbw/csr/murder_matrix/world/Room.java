package ch.bbw.csr.murder_matrix.world;

import ch.bbw.csr.murder_matrix.items.Item;
import ch.bbw.csr.murder_matrix.players.NPC;

import java.util.*;

/**
 * The type Room.
 */
public class Room {
    private final String name;
    private final String description;
    private final Map<Direction, Room> exits;
    private final List<NPC> npcsInRoom;
    private final List<Item> items;
    private String onEnter;
    private String onExit;

    /**
     * Instantiates a new Room.
     *
     * @param name        the name
     * @param description the description
     */
    public Room(String name, String description) {
        this.name = name;
        this.description = description;
        this.exits = new HashMap<>();
        this.npcsInRoom = new ArrayList<>();
        this.items = new ArrayList<>();
        this.onEnter = "";
        this.onExit = "";
    }

    /**
     * Add exit.
     *
     * @param direction the direction
     * @param room      the room
     */
    public void addExit(String direction, Room room) {
        exits.put(Direction.fromString(direction), room);
    }

    /**
     * Gets exit.
     *
     * @param direction the direction
     * @return the exit
     */
    public Room getExit(String direction) {
        return getExit(Direction.fromString(direction));
    }

    /**
     * Gets exit.
     *
     * @param direction the direction
     * @return the exit
     */
    public Room getExit(Direction direction) {
        return exits.get(direction);
    }

    /**
     * Add npc.
     *
     * @param npc the npc
     */
    public void addNPC(NPC npc) {
        npcsInRoom.add(npc);
    }

    /**
     * Remove npc.
     *
     * @param npc the npc
     */
    public void removeNPC(NPC npc) {
        npcsInRoom.remove(npc);
    }

    /**
     * Gets npc.
     *
     * @param name the name
     * @return the npc
     */
    public NPC getNPC(String name) {
        for (NPC npc : npcsInRoom) {
            if (npc.name.equalsIgnoreCase(name)) {
                return npc;
            }
        }
        return null;
    }

    /**
     * Gets item.
     *
     * @param name the name
     * @return the item
     */
    public Item getItem(String name) {
        for (Item item : items) {
            if (Objects.requireNonNull(item.getItemName()).equalsIgnoreCase(name)) {
                return item;
            }
        }
        return null;
    }

    /**
     * Add item.
     *
     * @param item the item
     */
    public void addItem(Item item) {
        items.add(item);
    }

    /**
     * Remove item.
     *
     * @param item the item
     */
    public void removeItem(Item item) {
        items.remove(item);
    }

    /**
     * Has item boolean.
     *
     * @param item the item
     * @return the boolean
     */
    public boolean hasItem(Item item) {
        return items.contains(item);
    }

    /**
     * Examine item string.
     *
     * @param item the item
     * @return the string
     */
    public String examineItem(Item item) {
        if (hasItem(item)) {
            // You could have more detailed descriptions for each item
            return "You see " + item.type.getKey() + ". It looks interesting.";
        }
        return null;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets description.
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Gets exits.
     *
     * @return the exits
     */
    public List<String> getExits() {
        List<String> roomNames = new ArrayList<>();
        for (Direction room : exits.keySet()) {
            roomNames.add(room.toString().toLowerCase());
        }
        return roomNames;
    }

    /**
     * Gets npcs in room.
     *
     * @return the npcs in room
     */
    public List<NPC> getNpcsInRoom() {
        return new ArrayList<>(npcsInRoom);
    }

    /**
     * Gets items.
     *
     * @return the items
     */
    public List<String> getItems() {
        List<String> names = new ArrayList<>();
        for (Item item : items) {
            names.add(item.getItemName());
        }
        return names;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("You are in the ").append(name).append(".\n");
        sb.append(description).append("\n");

        if (!npcsInRoom.isEmpty()) {
            sb.append("You see the following people here:\n");
            for (NPC npc : npcsInRoom) {
                sb.append("- ").append(npc.name).append("\n");
            }
        }

        if (!items.isEmpty()) {
            sb.append("You see the following items:\n");
            for (Item item : items) {
                sb.append("- ").append(item.getItemName()).append("\n");
            }
        }

        sb.append("Exits: ").append(String.join(", ", getExits())).append("\n");

        return sb.toString();
    }

    /**
     * Sets on enter.
     *
     * @param onEnter the on enter
     */
    public void setOnEnter(String onEnter) {
        this.onEnter = onEnter;
    }

    /**
     * Sets on exit.
     *
     * @param onExit the on exit
     */
    public void setOnExit(String onExit) {
        this.onExit = onExit;
    }


    /**
     * Trigger on enter string.
     *
     * @return the string
     */
    public String triggerOnEnter() {
        return this.onEnter;
    }

    /**
     * Trigger on exit string.
     *
     * @return the string
     */
    public String triggerOnExit() {
        return this.onExit;
    }
}
