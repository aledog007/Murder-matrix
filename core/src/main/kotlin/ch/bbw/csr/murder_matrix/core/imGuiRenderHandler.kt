package ch.bbw.csr.murder_matrix.core

import ch.bbw.csr.murder_matrix.items.Item
import ch.bbw.csr.murder_matrix.items.RuntimeItemType
import ch.bbw.csr.murder_matrix.players.NPC
import ch.bbw.csr.murder_matrix.players.Player
import ch.bbw.csr.murder_matrix.players.npc.NPCSettings
import ch.bbw.csr.murder_matrix.render.ImGuiRender
import ch.bbw.csr.murder_matrix.world.World
import com.badlogic.gdx.Gdx
import imgui.ImGui
import imgui.flag.ImGuiCond

var areWindowsHidden = false
var itemWeight = floatArrayOf(10f)

fun handleKeyPress() {
    areWindowsHidden = !areWindowsHidden
}

fun imGuiRenderHandler(
    additionalWindows: MutableMap<String, Boolean>,
    player: Player,
    world: World
): MutableMap<String, Boolean> {
    ImGuiRender.startImGui()

    if (!areWindowsHidden) {
        additionalWindows["debug"] = true
        ImGui.setNextWindowSize(500f, 400f, ImGuiCond.Once)
        ImGui.begin("Debug")

        ImGui.text("GDX Version: ${Gdx.app.version}")
        ImGui.text("Current Day: ${world.currentDay}")
        ImGui.text("Alive NPCs (includes Murders): ${world.NPCs.size}")

        if (ImGui.collapsingHeader("Debug tools")) {
            if (ImGui.button("Spawn an NPC")) {
                val npc = NPC("test-npc", "Used for testing purposes")
                player.currentRoom.addNPC(npc)
                world.NPCs.add(npc)
            }
            ImGui.sameLine()
            if (ImGui.button("Spawn a Murderer")) {
                val murderNPCSettings = NPCSettings(isMurderer = true)
                val murderNPC = NPC("test-murder", "Spawned for testing purposes", murderNPCSettings)
                world.NPCs.add(murderNPC)
                player.currentRoom.addNPC(murderNPC)
            }
            ImGui.separator()
            if (ImGui.button("Set Normal Screen")) {
                GlobalConstants.currentScreens = Screens.MAIN_GAME
            }
            ImGui.sameLine()
            if (ImGui.button("Set Win Screen")) {
                GlobalConstants.currentScreens = Screens.WIN
            }
            ImGui.sameLine()
            if (ImGui.button("Set Game Over Screen")) {
                GlobalConstants.currentScreens = Screens.GAME_OVER
            }
            ImGui.separator()
            ImGui.text("Specify item weight:")
            ImGui.sliderFloat("Item Weight", itemWeight, 0f, 50f)

            if (ImGui.button("Spawn an Item")) {
                player.currentRoom.addItem(
                    Item(
                        RuntimeItemType.getOrCreate(
                            "test-item-${itemWeight[0]}",
                            itemWeight[0]
                        )
                    )
                )
            }
            ImGui.separator()
            intArrayOf(world.currentDay)
            ImGui.text("Set the day:")
            syncSlider("Day", world.currentDay, 1, world.NPCs.size - 1) { newDay ->
                world.currentDay = newDay
            }
            if (ImGui.button("Advance Day")) {
                world.advanceDay()
            }

        }

        if (ImGui.collapsingHeader("Memory")) {
            ImGui.text(String.format("Java Heap Memory: %.2f MB", Gdx.app.javaHeap / 1048576f))
            ImGui.text(String.format("Native Heap Memory: %.2f MB", Gdx.app.nativeHeap / 1048576f))
        }

        if (ImGui.collapsingHeader("Additional Debug windows")) {
            if (ImGui.button(if (additionalWindows["metrics"] == true) "Hide ImGUI Metrics" else "Show ImGUI Metrics")) {
                additionalWindows["metrics"] = additionalWindows["metrics"] != true
            }
            if (ImGui.button(if (additionalWindows["styleEditor"] == true) "Hide Style Editor" else "Show Style Editor")) {
                additionalWindows["styleEditor"] = additionalWindows["styleEditor"] != true
            }
        }

        ImGui.end()

        if (additionalWindows["metrics"] == true) {
            ImGui.showMetricsWindow()
        }
        if (additionalWindows["styleEditor"] == true) {
            ImGui.showStyleEditor()
        }
    } else {
        additionalWindows["styleEditor"] = false
        additionalWindows["metrics"] = true
        additionalWindows["debug"] = true

    }

    ImGuiRender.endImGui()
    return additionalWindows
}

fun syncSlider(label: String, value: Int, min: Int, max: Int, onChange: (Int) -> Unit) {
    val sliderValue = intArrayOf(value)
    if (ImGui.sliderInt(label, sliderValue, min, max) && sliderValue[0] != value) {
        onChange(sliderValue[0])
    }
}
