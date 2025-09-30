package observer;

public final class EmailObserver implements RoomAvailabilityObserver {
    private final String emailAddress;

    public EmailObserver(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    @Override
    public void onEvent(RoomAvailabilityEvent e) {
        String subject = "[Hotel] Room Availability Update";
        String body = switch (e.type()) {
            case BECAME_UNAVAILABLE -> 
                "Room %s (%s) has been reserved. Remaining: %d".formatted(
                    e.room().id(), e.room().type(), e.availableCountByType());
            case BECAME_AVAILABLE -> 
                "Room %s (%s) is now available again. Remaining: %d".formatted(
                    e.room().id(), e.room().type(), e.availableCountByType());
            case LOW_STOCK -> 
                "Low stock alert for %s: only %d left!".formatted(
                    e.room().type(), e.availableCountByType());
        };

        System.out.printf("[Email to %s] Subject: %s%n%s%n", emailAddress, subject, body);
    }
}
