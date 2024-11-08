package store.model;

import camp.nextstep.edu.missionutils.DateTimes;
import java.time.LocalDateTime;

public class Promotion {
    private String name;
    private int buy;
    private int get;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    public Promotion(String name, int buy, int get, LocalDateTime startDate, LocalDateTime endDate) {
        this.name = name;
        this.buy = buy;
        this.get = get;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public boolean isApplicable(Product product, int quantity) {
        LocalDateTime currentDate = DateTimes.now();

        return (currentDate.isAfter(startDate) || currentDate.isEqual(startDate)) && (currentDate.isBefore(endDate)
                || currentDate.isEqual(endDate)) && quantity >= buy;
    }

    public int calculateDiscountAmount(int quantity, int price) {
        if (quantity >= buy) {
            int discountBundles = quantity / (buy + get);
            int discountAmount = discountBundles * price * get;
            return discountAmount;
        }
        return 0;
    }

    public int calculateFreeItems(int quantity) {
        if (quantity >= buy) {
            int freeItems = (quantity / (buy + get)) * get;
            return freeItems;
        }
        return 0;
    }

    public String getName() {
        return name;
    }
}
