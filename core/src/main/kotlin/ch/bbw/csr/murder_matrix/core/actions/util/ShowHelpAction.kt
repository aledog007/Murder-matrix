package ch.bbw.csr.murder_matrix.core.actions.util

import ch.bbw.csr.murder_matrix.core.Command
import ch.bbw.csr.murder_matrix.core.actions.CommandAction

class ShowHelpAction : CommandAction {
    override fun execute(): String {
        val commandsHelp = Command.entries.joinToString(separator = "\n") { command ->
            "- ${command.keyword}: ${command.description}"
        }
        return "Available commands:\n$commandsHelp"
    }
}
