package ch.bbw.csr.murder_matrix.ai;

/**
 * The type Open router.
 */
public class OpenRouter extends BaseAzureAiClient {
    /**
     * Instantiates a new Open router.
     */
    public OpenRouter() {
        super("OPENROUTER_API_KEY", "https://openrouter.ai/api/v1/chat/completions");
    }

    @Override
    protected String getModelName() {
        return "gpt-4-0613";
    }
}
