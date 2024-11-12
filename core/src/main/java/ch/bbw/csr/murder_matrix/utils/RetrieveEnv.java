package ch.bbw.csr.murder_matrix.utils;

import io.github.cdimascio.dotenv.Dotenv;

/**
 * The type Retrieve env.
 */
public class RetrieveEnv {

    private static Dotenv getEnv() {
        return Dotenv.configure().ignoreIfMissing().ignoreIfMalformed().load();
    }

    /**
     * Gets env.
     *
     * @param key the key
     * @return the env
     */
    public static String getEnv(String key) {
        return getEnv().get(key);
    }
}
