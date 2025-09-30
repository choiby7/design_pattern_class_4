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
                "[Slack:%s] 방 %s (%s) 예약됨, 남은 %d개".formatted(
                    channel, e.room().id(), e.room().type(), e.availableCountByType());
            case BECAME_AVAILABLE -> 
                "[Slack:%s] 방 %s (%s) 다시 사용 가능!, 남은 %d개".formatted(
                    channel, e.room().id(), e.room().type(), e.availableCountByType());
            case LOW_STOCK -> 
                "[Slack:%s] 매진 임박 %s, 오직 %d 가능!".formatted(
                    channel, e.room().type(), e.availableCountByType());
        };
        System.out.println(msg);
    }
}
