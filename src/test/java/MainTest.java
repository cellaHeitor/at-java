
import org.example.Main;
import org.example.model.Tarefa;
import org.junit.jupiter.api.*;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MainTest {

    @BeforeAll
    public static void startServer() {
        new Thread(() -> Main.main(null)).start();
        try {
            Thread.sleep(1000); // Espera o servidor subir
        } catch (InterruptedException ignored) {}
    }

    private String sendGet(String endpoint) throws IOException {
        HttpURLConnection conn = (HttpURLConnection) new URL("http://localhost:7000" + endpoint).openConnection();
        conn.setRequestMethod("GET");
        assertEquals(200, conn.getResponseCode());
        return new String(conn.getInputStream().readAllBytes());
    }

    private String sendPost(String endpoint, String json, int expectedStatus) throws IOException {
        HttpURLConnection conn = (HttpURLConnection) new URL("http://localhost:7000" + endpoint).openConnection();
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);
        conn.setRequestProperty("Content-Type", "application/json");

        try (OutputStream os = conn.getOutputStream()) {
            os.write(json.getBytes());
        }

        assertEquals(expectedStatus, conn.getResponseCode());
        return new String(conn.getInputStream().readAllBytes());
    }

    @Test
    @Order(1)
    public void testHelloEndpoint() throws IOException {
        String response = sendGet("/hello");
        assertEquals("Hello, Javalin!", response);
    }

    @Test
    @Order(2)
    public void testPostCriacaoTarefa() throws IOException {
        String json = """
            {
                "nome": "Tarefa Teste",
                "descricao": "Criada via teste"
            }
            """;
        String response = sendPost("/tarefas", json, 201);
        assertTrue(response.contains("\"nome\":\"Tarefa Teste\""));
    }

    @Test
    @Order(3)
    public void testBuscarTarefaPorId() throws IOException {
        String response = sendGet("/tarefas/1");
        assertTrue(response.contains("\"id\":1"));
        assertTrue(response.contains("Tarefa Teste"));
    }

    @Test
    @Order(4)
    public void testListarTodasTarefas() throws IOException {
        String response = sendGet("/tarefas");
        assertTrue(response.startsWith("["));
        assertTrue(response.contains("Tarefa Teste"));
    }
}
