package store.model;

public class Product {
    private String name;
    private int price;
    private int quantity;
    private String promotion;

    public Product(String name, int price, int quantity, String promotion) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.promotion = promotion;
    }

    public void reduceQuantity(int orderQuantity) {
        quantity -= orderQuantity;
    }

    public int getPrice() {
        return price;
    }
}
