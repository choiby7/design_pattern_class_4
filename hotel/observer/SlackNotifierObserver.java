package observer;

public final class SlackNotifierObserver implements RoomAvailabilityObserver {
    private final String channel;

    public SlackNotifierObserver(String channel) {
        this.channel = channel;
    }

    @Override
    public void onEvent(RoomAvailabilityEvent e) {
        String msg = switch (e.type()) {
            case BECAME_UNAVAILABLE -> 
                "[Slack:%s] Room %s (%s) 예약됨, 남은 %d개".formatted(
                    channel, e.room().id(), e.room().type(), e.availableCountByType());
            case BECAME_AVAILABLE -> 
                "[Slack:%s] Room %s (%s) 다시 사용 가능!, 남은 %d개".formatted(
                    channel, e.room().id(), e.room().type(), e.availableCountByType());
            case LOW_STOCK -> 
                "[Slack:%s] ⚠ Low stock for %s, only %d left!".formatted(
                    channel, e.room().type(), e.availableCountByType());
        };
        System.out.println(msg);
    }
}
