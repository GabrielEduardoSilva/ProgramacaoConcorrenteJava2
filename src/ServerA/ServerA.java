package ServerA;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import shared.Article;
import shared.DataLoader;
import shared.SearchAlgorithm;
import shared.SearchResult;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

public class ServerA {
    private static final int PORT = 5500;

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(PORT);
        System.out.println("Servidor A aguardando conexões na porta " + PORT);

        while (true) {
            Socket clientSocket = serverSocket.accept();
            new Thread(() -> handleClient(clientSocket)).start();
        }
    }

    private static void handleClient(Socket clientSocket) {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

            String query = in.readLine();
            System.out.println("Consulta recebida do cliente: " + query);

            List<Article> dataset = DataLoader.loadDataset("data/data_A.json");
            List<Article> localResults = SearchAlgorithm.search(dataset, query);

            try (Socket serverBSocket = new Socket("localhost", 6000);
                 PrintWriter serverBOut = new PrintWriter(serverBSocket.getOutputStream(), true);
                 BufferedReader serverBIn = new BufferedReader(new InputStreamReader(serverBSocket.getInputStream()))) {

                serverBOut.println(query);
                String response = serverBIn.readLine();
                SearchResult serverBResults = parseResults(response);

                localResults.addAll(serverBResults.getArticles());
                out.println(new SearchResult(localResults).toString());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static SearchResult parseResults(String response) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode rootNode = mapper.readTree(response);
            JsonNode articlesNode = rootNode.path("articles");

            List<Article> articles = new ArrayList<>();
            for (JsonNode articleNode : articlesNode) {
                String title = articleNode.path("title").asText();
                articles.add(new Article(title));
            }

            return new SearchResult(articles);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao processar JSON da resposta: " + response);
        }
    }
}