package ch.bbw.csr.murder_matrix.core.actions.util

import ch.bbw.csr.murder_matrix.core.actions.CommandAction
import ch.bbw.csr.murder_matrix.world.World

class CurrentDayRetrievalAction(private val world: World) : CommandAction {
    override fun execute(): String {
        return "The current day is: ${world.currentDay}"
    }

}
