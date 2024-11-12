package ch.bbw.csr.murder_matrix.ai;

/**
 * The type Azure ai client.
 */
public class AzureAIClient extends BaseAzureAiClient {
    /**
     * Instantiates a new Azure ai client.
     */
    public AzureAIClient() {
        super("OPENAI_API_KEY", null);
    }

    @Override
    protected String getModelName() {
        return "gpt-4-0613";
    }
}

