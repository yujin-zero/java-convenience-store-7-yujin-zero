package store.model;

import camp.nextstep.edu.missionutils.DateTimes;
import java.time.LocalDateTime;
import store.constants.PromotionType;

public class Promotion {
    private PromotionType type;
    private String name;
    private int buy;
    private int get;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    public Promotion(String name, int buy, int get, LocalDateTime startDate, LocalDateTime endDate) {
        this.type = PromotionType.fromName(name);
        this.name = name;
        this.buy = buy;
        this.get = get;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public boolean isApplicable(int quantity) {
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

    public int calculateAppliedItemCount(int quantity) {
        int discountBundles = quantity / (buy + get);
        return discountBundles * (buy + get);
    }

    public String getName() {
        return name;
    }

    public int getBuy() {
        return buy;
    }

    public int getGet() {
        return get;
    }
}
