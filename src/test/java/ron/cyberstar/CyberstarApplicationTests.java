package ron.cyberstar;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.random.RandomGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CyberstarApplicationTests {

  @Test
  void testSubscribe() throws Exception {
    RandomGenerator generator = RandomGenerator.getDefault();
    int n = generator.nextInt(99);
    ExecutorService executor = Executors.newFixedThreadPool(n);
    for (int i = 1; i < n; i++) {
      int finalI = i;
      executor.execute(() -> {
        HttpClient client = HttpClient.newBuilder().build();
        HttpRequest request = null;
        try {
          request = HttpRequest.newBuilder().uri(new URI(
                  "http://localhost:8080/cyberstar/Ron099/subscribe?currentUserId="+finalI))
              .POST(HttpRequest.BodyPublishers.ofString("{currentUserId: " + finalI+"}")).build();
          HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
        } catch (URISyntaxException | IOException | InterruptedException e) {
          throw new RuntimeException(e);
        }
      });
    }

    HttpClient client = HttpClient.newBuilder().build();
    HttpRequest request = null;

    request = HttpRequest.newBuilder().uri(new URI(
            "http://localhost:8080/cyberstar/Ron099/info"))
        .GET().build();
    HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
  }

}
