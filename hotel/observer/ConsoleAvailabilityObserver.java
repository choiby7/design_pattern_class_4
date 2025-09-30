package observer;

public final class ConsoleAvailabilityObserver implements RoomAvailabilityObserver {
    @Override
    public void onEvent(RoomAvailabilityEvent e) {
        switch (e.type()) {
            case BECAME_UNAVAILABLE -> System.out.printf(
                "[잔여 방 없음] 방=%s, 종류=%s, 잔여=%d%n",
                e.room().id(), e.room().type(), e.availableCountByType()
            );
            case BECAME_AVAILABLE -> System.out.printf(
                "[예약 가능] 방=%s, 종류=%s, 잔여=%d%n",
                e.room().id(), e.room().type(), e.availableCountByType()
            );
            case LOW_STOCK -> System.out.printf(
                "[예약 가능][매진 임박] 종류=%s, 잔여=%d (예약 가능 방=%s)%n",
                e.room().type(), e.availableCountByType(), e.room().id()
            );
        }
    }
}
