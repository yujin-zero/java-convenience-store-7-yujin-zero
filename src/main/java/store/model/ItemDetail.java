package store.model;

public class ItemDetail {
    private int quantity;
    private final int price;

    public ItemDetail(int quantity, int price) {
        this.quantity = quantity;
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getPrice() {
        return price;
    }

    public void addQuantity(int quantity) {
        this.quantity += quantity;
    }
}

