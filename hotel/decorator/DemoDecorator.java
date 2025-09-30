package decorator;

import java.math.BigDecimal;
import strategy.*;

/**
 * 데코레이터 체인을 "직접" 연결해서 사용하는 데모.
 * - 기본 요금은 Strategy로 계산 (Peak/OffPeak 등)
 * - 옵션은 Decorator로 감싸며 누적 가산
 */
public final class DemoDecorator {
    public static void main(String[] args) {
        // 1) 예시 Booking (성인/아동 포함)
        Booking deluxe3N = new Booking(RoomType.DELUXE, /*nights*/3, /*adults*/2, /*children*/1);

        // 2) 기본 요금 전략 선택 (예: 성수기 35% 가산)
        PricingStrategy peak = new PeakSeasonStrategy(new BigDecimal("1.35"));

        // 3) BasePricer 생성 (전략 결과를 사용)
        BookingPricer pricer = new BasePricer(peak);

        // 4) 옵션을 "직접" 체인으로 래핑
        //    - 조식(아동 포함, 1인 1박 18,000)
        pricer = new BreakfastAddon(pricer, new BigDecimal("18000"), true);
        //    - 수영장(투숙 기간 정액 30,000)
        pricer = new PoolAddon(pricer, new BigDecimal("30000"));
        //    - 사우나(성인만, 1인 1박 12,000)
        pricer = new SaunaAddon(pricer, new BigDecimal("12000"));

        // 5) 견적 출력
        var amount = pricer.quote(deluxe3N);
        System.out.printf("[%s] %s %d박 (adults=%d, children=%d) -> %,d원%n",
                pricer.description(),
                deluxe3N.roomType(), deluxe3N.nights(),
                deluxe3N.adults(), deluxe3N.children(),
                amount.longValue());

        // 6) 또 다른 조합: 비성수기 + 조식만 (체인 다시 구성)
        BookingPricer offpeakBreakfast =
                new BreakfastAddon(                    // 최상단 데코레이터
                    new BasePricer(                     // 기본요금 계산자
                        new OffPeakStrategy(new BigDecimal("0.95")) // 전략
                    ),
                    new BigDecimal("15000"),
                    true
                );

        var amount2 = offpeakBreakfast.quote(deluxe3N);
        System.out.printf("[%s] -> %,d원%n", offpeakBreakfast.description(), amount2.longValue());
    }
}
