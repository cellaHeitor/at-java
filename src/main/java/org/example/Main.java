package org.example;

import org.example.model.Tarefa;
import io.javalin.Javalin;
import io.javalin.http.Context;

import java.time.Instant;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {

    private static List<Tarefa> tarefas = new ArrayList<>();
    private static AtomicInteger contadorId = new AtomicInteger(1);

    public static void main(String[] args) {
        Javalin app = Javalin.create().start(7000);

        app.get("/hello", ctx -> ctx.result("Hello, Javalin!"));

        app.get("/status", ctx -> {
            ctx.json(Map.of(
                    "status", "ok",
                    "timestamp", Instant.now().toString()
            ));
        });

        app.post("/echo", ctx -> {
            Map<String, Object> json = ctx.bodyAsClass(Map.class);
            ctx.json(json);
        });

        app.get("/saudacao/{nome}", ctx -> {
            String nome = ctx.pathParam("nome");
            ctx.json(Map.of("mensagem", "Olá, " + nome + "!"));
        });

        app.post("/tarefas", ctx -> {
            Tarefa nova = ctx.bodyAsClass(Tarefa.class);
            nova.id = contadorId.getAndIncrement();
            tarefas.add(nova);
            ctx.status(201).json(nova);
        });

        app.get("/tarefas", ctx -> ctx.json(tarefas));

        app.get("/tarefas/{id}", ctx -> {
            int id = Integer.parseInt(ctx.pathParam("id"));
            Optional<Tarefa> encontrada = tarefas.stream()
                    .filter(t -> t.id == id)
                    .findFirst();

            if (encontrada.isPresent()) {
                ctx.json(encontrada.get());
            } else {
                ctx.status(404).json(Map.of("erro", "Tarefa não encontrada"));
            }
        });
    }
}
