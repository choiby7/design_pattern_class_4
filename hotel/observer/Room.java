package observer;

import strategy.RoomType;

import java.util.Objects;

public final class Room {
    private final String id;
    private final RoomType type;
    private RoomStatus status;

    public Room(String id, RoomType type) {
        this.id = Objects.requireNonNull(id);
        this.type = Objects.requireNonNull(type);
        this.status = RoomStatus.AVAILABLE;
    }

    public String id() { return id; }
    public RoomType type() { return type; }
    public RoomStatus status() { return status; }

    public void occupy() { this.status = RoomStatus.OCCUPIED; }
    public void release() { this.status = RoomStatus.AVAILABLE; }

    @Override public String toString() {
        return "Room{id='%s', type=%s, status=%s}".formatted(id, type, status);
    }
}
