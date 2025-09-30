package strategy;

import java.math.BigDecimal;

public final class Demo {
    public static void main(String[] args) {
        Booking deluxe2N = new Booking(RoomType.DELUXE, 2, 1, 1);
        Booking suite4N  = new Booking(RoomType.SUITE, 4, 2, 2);

        BookingService svc = new BookingService(new StandardStrategy());
        print(svc, deluxe2N);  // 기본가

        svc.setStrategy(new PeakSeasonStrategy(new BigDecimal("1.35"))); // 성수기 35% 가산
        print(svc, deluxe2N);

        svc.setStrategy(new OffPeakStrategy(new BigDecimal("0.95")));    // 비성수기 5% 할인
        print(svc, deluxe2N);

        svc.setStrategy(new LongStayStrategy(3, new BigDecimal("0.10"))); // 3박↑ 10% 추가 할인
        print(svc, suite4N);
    }

    private static void print(BookingService svc, Booking b) {
        System.out.printf("[%s] %s %d박 -> %,d원%n",
            svc.currentPlanName(), b.roomType(), b.nights(), svc.quote(b).longValue());
    }
}
