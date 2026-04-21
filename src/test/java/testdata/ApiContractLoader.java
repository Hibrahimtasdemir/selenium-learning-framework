package testdata;

import io.restassured.path.json.JsonPath;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public final class ApiContractLoader {

  private ApiContractLoader() {}

  @SuppressWarnings("unchecked")
  public static Map<String, Object> loadJsonObject(String classpathResource) {
    String json = loadText(classpathResource);
    return JsonPath.from(json).getMap("$");
  }

  public static String loadText(String classpathResource) {
    try (InputStream inputStream =
        ApiContractLoader.class.getClassLoader().getResourceAsStream(classpathResource)) {
      if (inputStream == null) {
        throw new IllegalArgumentException("Resource not found on classpath: " + classpathResource);
      }
      return new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
    } catch (IOException e) {
      throw new RuntimeException("Could not read classpath resource: " + classpathResource, e);
    }
  }
}
