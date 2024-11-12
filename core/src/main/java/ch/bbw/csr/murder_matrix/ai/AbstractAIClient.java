package ch.bbw.csr.murder_matrix.ai;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Abstract ai client.
 */
public abstract class AbstractAIClient {
    /**
     * The Chat messages.
     */
    protected final List<ChatMessage> chatMessages = new ArrayList<>();
    /**
     * The System prompt set.
     */
    protected boolean systemPromptSet = false;

    /**
     * Sets system prompt.
     *
     * @param systemPrompt the system prompt
     */
    public abstract void setSystemPrompt(final String systemPrompt);

    /**
     * Gets response.
     *
     * @param content the content
     * @return the response
     */
    public abstract String getResponse(final String content);

    /**
     * Add user chat message.
     *
     * @param content the content
     */
    protected void addUserChatMessage(final String content) {
        chatMessages.add(new ChatMessage("user", content));
    }

    /**
     * Add system chat message.
     *
     * @param content the content
     */
    protected void addSystemChatMessage(final String content) {
        chatMessages.add(new ChatMessage("system", content));
    }

    /**
     * Add assistant chat message.
     *
     * @param content the content
     */
    protected void addAssistantChatMessage(final String content) {
        chatMessages.add(new ChatMessage("assistant", content));
    }

    /**
     * The type Chat message.
     */
    protected static class ChatMessage {
        private final String role;
        private final String content;

        /**
         * Instantiates a new Chat message.
         *
         * @param role    the role
         * @param content the content
         */
        public ChatMessage(String role, String content) {
            this.role = role;
            this.content = content;
        }

        /**
         * Gets role.
         *
         * @return the role
         */
        public String getRole() {
            return role;
        }

        /**
         * Gets content.
         *
         * @return the content
         */
        public String getContent() {
            return content;
        }
    }
}
