package store.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.StringTokenizer;
import store.model.Promotion;

public class PromotionLoader {

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
