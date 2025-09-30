package observer;

public final class ConsoleAvailabilityObserver implements RoomAvailabilityObserver {
    @Override
    public void onEvent(RoomAvailabilityEvent e) {
        switch (e.type()) {
            case BECAME_UNAVAILABLE -> System.out.printf(
                "[AVAIL][UNAVAILABLE] room=%s, type=%s, left=%d%n",
                e.room().id(), e.room().type(), e.availableCountByType()
            );
            case BECAME_AVAILABLE -> System.out.printf(
                "[AVAIL][AVAILABLE] room=%s, type=%s, left=%d%n",
                e.room().id(), e.room().type(), e.availableCountByType()
            );
            case LOW_STOCK -> System.out.printf(
                "[AVAIL][LOW_STOCK] type=%s, left=%d (triggered by room=%s)%n",
                e.room().type(), e.availableCountByType(), e.room().id()
            );
        }
    }
}
