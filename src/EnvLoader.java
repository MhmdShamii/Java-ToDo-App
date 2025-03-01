import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class EnvLoader {
    private static final String ENV_FILE_PATH = "DataBase_Credentials.env";

    public static Map<String, String> loadEnv() {
        Map<String, String> envVars = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(ENV_FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains("=") && !line.startsWith("#")) { // Ignore comments
                    String[] parts = line.split("=", 2); // Split only at the first '='
                    envVars.put(parts[0].trim(), parts[1].trim());
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading .env file: " + e.getMessage());
        }
        return envVars;
    }
}
