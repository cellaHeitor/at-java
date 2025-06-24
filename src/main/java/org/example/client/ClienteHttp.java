package org.example.client;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class ClienteHttp {

    private static final String BASE_URL = "http://localhost:7000";

    public static void main(String[] args) throws IOException {
        postNovaTarefa();
        getTodasTarefas();
        getTarefaPorId(1);
        getStatus();
    }

    public static void postNovaTarefa() throws IOException {
        String json = """
            {
              "nome": "Ler livro",
              "descricao": "Capítulo sobre APIs REST"
            }
            """;

        HttpURLConnection conn = (HttpURLConnection) new URL(BASE_URL + "/tarefas").openConnection();
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);
        conn.setRequestProperty("Content-Type", "application/json");

        try (OutputStream os = conn.getOutputStream()) {
            os.write(json.getBytes(StandardCharsets.UTF_8));
        }

        int status = conn.getResponseCode();
        String resposta = new String(conn.getInputStream().readAllBytes());
        System.out.println("POST /tarefas -> Status: " + status);
        System.out.println("Resposta: " + resposta);
    }

    public static void getTodasTarefas() throws IOException {
        HttpURLConnection conn = (HttpURLConnection) new URL(BASE_URL + "/tarefas").openConnection();
        conn.setRequestMethod("GET");

        int status = conn.getResponseCode();
        String resposta = new String(conn.getInputStream().readAllBytes());
        System.out.println("\nGET /tarefas -> Status: " + status);
        System.out.println("Resposta: " + resposta);
    }

    public static void getTarefaPorId(int id) throws IOException {
        HttpURLConnection conn = (HttpURLConnection) new URL(BASE_URL + "/tarefas/" + id).openConnection();
        conn.setRequestMethod("GET");

        int status = conn.getResponseCode();
        InputStream respostaStream = status == 200
                ? conn.getInputStream()
                : conn.getErrorStream();

        String resposta = new String(respostaStream.readAllBytes());
        System.out.println("\nGET /tarefas/" + id + " -> Status: " + status);
        System.out.println("Resposta: " + resposta);
    }

    public static void getStatus() throws IOException {
        HttpURLConnection conn = (HttpURLConnection) new URL(BASE_URL + "/status").openConnection();
        conn.setRequestMethod("GET");

        int status = conn.getResponseCode();
        String resposta = new String(conn.getInputStream().readAllBytes());
        System.out.println("\nGET /status -> Status: " + status);
        System.out.println("Resposta: " + resposta);
    }
}
