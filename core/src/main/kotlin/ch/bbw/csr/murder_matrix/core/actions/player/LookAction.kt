package ch.bbw.csr.murder_matrix.core.actions.player

import ch.bbw.csr.murder_matrix.core.actions.CommandAction
import ch.bbw.csr.murder_matrix.core.actions.utils.LookUtility
import ch.bbw.csr.murder_matrix.players.Player

class LookAction(private val player: Player) : CommandAction {
    private val lookUtility = LookUtility(player)
    override fun execute(): String {
        return lookUtility.describeCurrentRoom()
    }
}
