package observer;

import strategy.RoomType;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public final class RoomInventory {
    private final Map<String, Room> rooms = new ConcurrentHashMap<>();
    private final List<RoomAvailabilityObserver> observers = new ArrayList<>();
    private final int lowStockThreshold; // 타입별 가용 수가 이 값 이하로 떨어지면 LOW_STOCK 알림

    public RoomInventory(int lowStockThreshold) {
        this.lowStockThreshold = Math.max(lowStockThreshold, 0);
    }

    public void addObserver(RoomAvailabilityObserver o) { observers.add(Objects.requireNonNull(o)); }
    public void removeObserver(RoomAvailabilityObserver o) { observers.remove(o); }

    private void notifyAll(RoomAvailabilityEvent e) {
        for (RoomAvailabilityObserver o : List.copyOf(observers)) {
            o.onEvent(e);
        }
    }

    public void addRoom(Room room) { rooms.put(room.id(), room); }

    public Optional<Room> findAvailable(RoomType type) {
        return rooms.values().stream()
                .filter(r -> r.type() == type && r.status() == RoomStatus.AVAILABLE)
                .findFirst();
    }

    public long countAvailable(RoomType type) {
        return rooms.values().stream()
                .filter(r -> r.type() == type && r.status() == RoomStatus.AVAILABLE)
                .count();
    }

    /** 성공 시 true. 방이 존재하고 현재 AVAILABLE이면 OCCUPIED로 전환하고 이벤트 발행 */
    public boolean reserve(String roomId) {
        Room r = rooms.get(roomId);
        if (r == null || r.status() != RoomStatus.AVAILABLE) return false;

        r.occupy();
        long left = countAvailable(r.type());
        notifyAll(RoomAvailabilityEvent.ofUnavailable(r, left));
        if (left <= lowStockThreshold) {
            notifyAll(RoomAvailabilityEvent.ofLowStock(r, left));
        }
        return true;
    }

    /** 성공 시 true. 방이 존재하고 현재 OCCUPIED면 AVAILABLE로 전환하고 이벤트 발행 */
    public boolean cancel(String roomId) {
        Room r = rooms.get(roomId);
        if (r == null || r.status() != RoomStatus.OCCUPIED) return false;

        r.release();
        long left = countAvailable(r.type());
        notifyAll(RoomAvailabilityEvent.ofAvailable(r, left));
        return true;
    }

    public Collection<Room> allRooms() { return Collections.unmodifiableCollection(rooms.values()); }
}
