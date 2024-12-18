package elice.webshopping.domain.order;

public enum OrderStatus {
    PENDING,
    ORDERED,
    SHIPPING,
    DELIVERED,
    CANCELED;

    public boolean canBeCanceled() {
        return this == PENDING || this == ORDERED;
    }
}
