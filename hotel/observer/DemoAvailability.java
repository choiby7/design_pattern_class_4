package observer;

import strategy.RoomType;

public final class DemoAvailability {
    public static void main(String[] args) {
        // 1) 재고/옵저버 준비
        RoomInventory inventory = new RoomInventory(/*lowStockThreshold=*/1);
        inventory.addObserver(new ConsoleAvailabilityObserver());
        WaitlistNotifier waitlist = new WaitlistNotifier();
        inventory.addObserver(waitlist);

        inventory.addObserver(new SlackNotifierObserver("#hotel-alerts"));
        inventory.addObserver(new EmailObserver("admin@hotel.com"));

        // 대기열 등록(예: DELUXE가 비면 알림)
        waitlist.enqueue(RoomType.DELUXE, "Alice");
        waitlist.enqueue(RoomType.DELUXE, "Bob");

        // 2) 방 등록
        inventory.addRoom(new Room("D-101", RoomType.DELUXE));
        inventory.addRoom(new Room("D-102", RoomType.DELUXE));
        inventory.addRoom(new Room("S-201", RoomType.STANDARD));

        // 3) 예약: DELUXE 두 개 연속 예약 → LOW_STOCK 트리거
        inventory.reserve("D-101"); // UNAVAILABLE, left=1
        inventory.reserve("D-102"); // UNAVAILABLE, left=0 → LOW_STOCK

        // 4) 취소: 하나 취소되면 BECAME_AVAILABLE → 대기열 첫 손님 알림
        inventory.cancel("D-101");  // AVAILABLE, left=1 → Waitlist 'Alice' 알림

        // 5) 다시 예약: 남은 DELUXE 하나 또 예약 → LOW_STOCK
        inventory.reserve("D-101"); // UNAVAILABLE, left=0 → LOW_STOCK
        // 6) 또 취소: 다시 비면 Waitlist 'Bob' 알림
        inventory.cancel("D-101");  // AVAILABLE, left=1
    }
}
