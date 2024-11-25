package shared;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import shared.Article;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class DataLoader {
    public static List<Article> loadDataset(String filePath) {
        List<Article> articles = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();

        try {
            JsonNode rootNode = mapper.readTree(new File(filePath));
            JsonNode titlesNode = rootNode.path("title");

            titlesNode.fields().forEachRemaining(entry -> {
                String title = entry.getValue().asText();
                articles.add(new Article(title));
            });

        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao carregar o dataset: " + filePath);
        }

        return articles;
    }
}