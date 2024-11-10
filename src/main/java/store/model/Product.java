package store.model;

public class Product {
    private String name;
    private int price;
    private int quantity;
    private Promotion promotion;

    public Product(String name, int price, int quantity, Promotion promotion) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.promotion = promotion;
    }

    public void reduceQuantity(int orderQuantity) {
        quantity -= orderQuantity;
    }

    public int calculateAddPromotionItem(int quantity) {
        int remainCount = quantity % (promotion.getBuy() + promotion.getGet());
        int addCount = (promotion.getBuy() + promotion.getGet()) - remainCount;
        if (remainCount > 0 && remainCount >= promotion.getBuy() && addCount <= this.quantity) {
            return addCount;
        }
        return 0;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public Promotion getPromotion() {
        return promotion;
    }

    public int getQuantity() {
        return quantity;
    }
}
