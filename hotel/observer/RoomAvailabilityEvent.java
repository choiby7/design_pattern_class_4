package observer;

import java.time.Instant;

public final class RoomAvailabilityEvent {
    public enum Type { BECAME_UNAVAILABLE, BECAME_AVAILABLE, LOW_STOCK }

    private final Type type;
    private final Room room;
    private final long availableCountByType; // 해당 타입의 남은 가용 객실 수
    private final Instant timestamp;

    private RoomAvailabilityEvent(Type type, Room room, long count) {
        this.type = type;
        this.room = room;
        this.availableCountByType = count;
        this.timestamp = Instant.now();
    }

    public static RoomAvailabilityEvent ofUnavailable(Room room, long count) {
        return new RoomAvailabilityEvent(Type.BECAME_UNAVAILABLE, room, count);
    }

    public static RoomAvailabilityEvent ofAvailable(Room room, long count) {
        return new RoomAvailabilityEvent(Type.BECAME_AVAILABLE, room, count);
    }

    public static RoomAvailabilityEvent ofLowStock(Room room, long count) {
        return new RoomAvailabilityEvent(Type.LOW_STOCK, room, count);
    }

    public Type type() { return type; }
    public Room room() { return room; }
    public long availableCountByType() { return availableCountByType; }
    public Instant timestamp() { return timestamp; }
}
