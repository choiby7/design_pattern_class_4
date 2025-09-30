package decorator;

import java.math.BigDecimal;
import strategy.Booking;

public interface BookingPricer {
    BigDecimal quote(Booking booking);
    String description(); // 패키지 설명 문자열
}
