Este projeto é uma API REST simples feita com Javalin e Java, 
com armazenamento em memória para gerenciamento de tarefas.

Funcionalidades
Endpoints básicos: /hello, /status, /echo, /saudacao/{nome}

CRUD básico para /tarefas (criar, listar, buscar por ID)

Testes automatizados com JUnit 5

Clientes Java para consumir os endpoints via HttpURLConnection

Pré-requisitos
Java 17+ instalado

Gradle instalado (ou usar o wrapper ./gradlew)

=================================================================

Como Buildar e Executar
1. Clone o repositório e navegue até a pasta do projeto:
   git clone <repo-url>
   cd <repo-folder>

2. Compile o projeto com Gradle:
   ./gradlew build

3. Execute a aplicação:
   ./gradlew run

A aplicação iniciará na porta 7000.

=================================================================

Executar os Testes
Execute os testes automatizados com:

./gradlew test

Relatório dos testes será gerado em:

build/reports/tests/test/index.html

=================================================================

Uso dos Clientes Java
O cliente Java ClienteHttp contém métodos que exemplificam chamadas HTTP para os endpoints:

postNovaTarefa() — cria uma nova tarefa (POST /tarefas)

getTodasTarefas() — lista todas as tarefas (GET /tarefas)

getTarefaPorId(int id) — busca tarefa por ID (GET /tarefas/{id})

getStatus() — consulta o status do sistema (GET /status)

Para executar o cliente (após iniciar o servidor):

./gradlew run --args='client'

Ou, se preferir, configure o mainClass para org.example.client.ClienteHttp no build.gradle.kts e rode:

./gradlew run

=================================================================

Testando manualmente com curl
Exemplos rápidos:

Hello: curl http://localhost:7000/hello

Listar tarefa: curl http://localhost:7000/tarefas

Buscar tarefa por ID: curl http://localhost:7000/tarefas/1
