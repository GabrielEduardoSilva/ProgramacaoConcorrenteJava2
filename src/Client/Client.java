package Client;

import java.io.*;
import java.net.*;

public class Client {
    private static final String SERVER_A_HOST = "localhost";
    private static final int SERVER_A_PORT = 5500;

    public static void main(String[] args) {
        try (Socket socket = new Socket(SERVER_A_HOST, SERVER_A_PORT);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             BufferedReader console = new BufferedReader(new InputStreamReader(System.in))) {

            System.out.println("Cliente conectado ao Servidor A.");
            System.out.print("Digite a substring para busca: ");
            String query = console.readLine();

            out.println(query);

            String response = in.readLine();
            System.out.println("Resultados da busca:");
            System.out.println(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}