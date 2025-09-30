// com/example/hotel/strategy/Booking.java
package strategy;

public final class Booking {
    private final RoomType roomType;
    private final int nights;
    private final int adults;
    private final int children;

    public Booking(RoomType roomType, int nights, int adults, int children) {
        if (nights <= 0) throw new IllegalArgumentException("nights must be > 0");
        if (adults < 0 || children < 0) throw new IllegalArgumentException("people must be >= 0");
        this.roomType = roomType;
        this.nights = nights;
        this.adults = adults;
        this.children = children;
    }

    public RoomType roomType() { return roomType; }
    public int nights() { return nights; }
    public int adults() { return adults; }
    public int children() { return children; }
}
