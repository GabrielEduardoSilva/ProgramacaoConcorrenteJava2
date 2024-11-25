package ServerB;

import shared.Article;
import shared.DataLoader;
import shared.SearchAlgorithm;
import shared.SearchResult;

import java.io.*;
import java.net.*;
import java.util.List;

public class ServerB {
    private static final int PORT = 6000;

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(PORT);
        System.out.println("Servidor B aguardando conexões na porta " + PORT);

        while (true) {
            Socket serverASocket = serverSocket.accept();
            new Thread(() -> handleServerA(serverASocket)).start();
        }
    }

    private static void handleServerA(Socket serverASocket) {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(serverASocket.getInputStream()));
             PrintWriter out = new PrintWriter(serverASocket.getOutputStream(), true)) {

            String query = in.readLine();
            System.out.println("Consulta recebida do Servidor A: " + query);

            List<Article> dataset = DataLoader.loadDataset("data/data_B.json");

            List<Article> results = SearchAlgorithm.search(dataset, query);

            SearchResult searchResult = new SearchResult(results);
            out.println(searchResult.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}