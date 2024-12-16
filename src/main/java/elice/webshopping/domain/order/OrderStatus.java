package elice.webshopping.domain.order;

public enum OrderStatus {
    PENDING,
    ORDERED,
    SHIPPING,
    DELIVERED,
    CANCELED;

    public boolean orderCanBeDeleted() {
        return this == PENDING || this == ORDERED;
    }
}
