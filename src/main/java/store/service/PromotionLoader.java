package store.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import store.model.Promotion;

public class PromotionLoader {

    public List<Promotion> loadPromotions(String filePath) {
        List<Promotion> promotions = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            parsePromotionsFromFile(br, promotions);
        } catch (IOException e) {
            throw new RuntimeException("파일을 읽는 도중 오류가 발생했습니다: " + filePath, e);
        }

        return promotions;
    }

    private void parsePromotionsFromFile(BufferedReader br, List<Promotion> promotions) throws IOException {
        String line = br.readLine();
        while ((line = br.readLine()) != null) {
            Promotion promotion = parsePromotionLine(line);
            promotions.add(promotion);
        }
    }

    private Promotion parsePromotionLine(String line) {
        StringTokenizer tokenizer = new StringTokenizer(line, ",");

        String name = tokenizer.nextToken().trim();
        int buy = Integer.parseInt(tokenizer.nextToken().trim());
        int get = Integer.parseInt(tokenizer.nextToken().trim());
        LocalDateTime startDate = LocalDate.parse(tokenizer.nextToken().trim()).atStartOfDay();
        LocalDateTime endDate = LocalDate.parse(tokenizer.nextToken().trim()).atStartOfDay();

        return new Promotion(name, buy, get, startDate, endDate);
    }
}
