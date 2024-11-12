package ch.bbw.csr.murder_matrix.ai;

import ch.bbw.csr.murder_matrix.utils.RetrieveEnv;
import com.azure.ai.openai.OpenAIClient;
import com.azure.ai.openai.OpenAIClientBuilder;
import com.azure.ai.openai.models.*;
import com.azure.core.credential.KeyCredential;

import java.util.List;
import java.util.stream.Collectors;

/**
 * The type Base azure ai client.
 */
public abstract class BaseAzureAiClient extends AbstractAIClient {
    /**
     * The Client.
     */
    protected final OpenAIClient client;

    /**
     * Instantiates a new Base azure ai client.
     *
     * @param apiKeyEnvName the api key env name
     * @param endpoint      the endpoint
     */
    public BaseAzureAiClient(String apiKeyEnvName, String endpoint) {
        final String apiKey = RetrieveEnv.getEnv(apiKeyEnvName);
        if (apiKey == null || apiKey.isEmpty()) {
            throw new RuntimeException("The `" + apiKeyEnvName + "` needs to be set.");
        }
        OpenAIClientBuilder builder = new OpenAIClientBuilder()
            .credential(new KeyCredential(apiKey));
        if (endpoint != null) {
            builder.endpoint(endpoint);
        }
        client = builder.buildClient();
    }

    @Override
    public void setSystemPrompt(final String systemPrompt) {
        this.addSystemChatMessage(systemPrompt);
        this.systemPromptSet = true;
    }

    @Override
    public String getResponse(final String content) {
        this.addUserChatMessage(content);
        if (!systemPromptSet) {
            throw new IllegalArgumentException("System prompt must be set");
        }

        List<ChatRequestMessage> azureChatMessages = chatMessages.stream()
            .map(this::convertToChatRequestMessage)
            .collect(Collectors.toList());

        ChatCompletions chatCompletions = client.getChatCompletions(getModelName(),
            new ChatCompletionsOptions(azureChatMessages));

        ChatResponseMessage message = chatCompletions.getChoices().get(0).getMessage();
        String response = message.getContent();
        addAssistantChatMessage(response);

        return response;
    }

    /**
     * Gets model name.
     *
     * @return the model name
     */
    protected abstract String getModelName();

    private ChatRequestMessage convertToChatRequestMessage(ChatMessage message) {
        return switch (message.getRole()) {
            case "user" -> new ChatRequestUserMessage(message.getContent());
            case "system" -> new ChatRequestSystemMessage(message.getContent());
            case "assistant" -> new ChatRequestAssistantMessage(message.getContent());
            default -> throw new IllegalArgumentException("Unknown role: " + message.getRole());
        };
    }
}
