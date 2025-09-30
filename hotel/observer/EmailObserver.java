package observer;

public final class EmailObserver implements RoomAvailabilityObserver {
    private final String emailAddress;

    public EmailObserver(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    @Override
    public void onEvent(RoomAvailabilityEvent e) {
        String subject = "[호텔] 예약 현황 업데이트";
        String body = switch (e.type()) {
            case BECAME_UNAVAILABLE -> 
                "방 %s (%s) 이 예약 되었습니다. 잔여: %d".formatted(
                    e.room().id(), e.room().type(), e.availableCountByType());
            case BECAME_AVAILABLE -> 
                "방 %s (%s) 예약 가능합니다. 잔여: %d".formatted(
                    e.room().id(), e.room().type(), e.availableCountByType());
            case LOW_STOCK -> 
                "매진 임박 %s: 오직 %d 남았습니다!".formatted(
                    e.room().type(), e.availableCountByType());
        };

        System.out.printf("[Email to %s] Subject: %s%n%s%n", emailAddress, subject, body);
    }
}
