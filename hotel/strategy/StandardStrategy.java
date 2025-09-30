package strategy;

import java.math.BigDecimal;
import java.math.RoundingMode;

public final class StandardStrategy implements PricingStrategy {
    @Override
    public BigDecimal quote(Booking b) {
        return b.roomType().baseRate()
                .multiply(BigDecimal.valueOf(b.nights()))
                .setScale(0, RoundingMode.HALF_UP);
    }
}
