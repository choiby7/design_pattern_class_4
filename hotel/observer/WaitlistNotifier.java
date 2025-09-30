package observer;

import strategy.RoomType;

import java.util.*;

public final class WaitlistNotifier implements RoomAvailabilityObserver {
    private final Map<RoomType, Deque<String>> waitlist = new EnumMap<>(RoomType.class);

    public void enqueue(RoomType type, String customerName) {
        waitlist.computeIfAbsent(type, t -> new ArrayDeque<>()).addLast(customerName);
    }

    @Override
    public void onEvent(RoomAvailabilityEvent e) {
        if (e.type() == RoomAvailabilityEvent.Type.BECAME_AVAILABLE) {
            var type = e.room().type();
            var q = waitlist.getOrDefault(type, new ArrayDeque<>());
            if (!q.isEmpty()) {
                String next = q.pollFirst();
                System.out.printf("[WAITLIST] Notify %s: %s 방이 다시 예약 가능! (남은 %d개)%n",
                        next, type, e.availableCountByType());
            }
        }
    }
}
