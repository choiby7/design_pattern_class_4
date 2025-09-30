package strategy;

import java.math.BigDecimal;
import java.math.RoundingMode;

public final class OffPeakStrategy implements PricingStrategy {
    private final BigDecimal offMultiplier;

    public OffPeakStrategy(BigDecimal offMultiplier) {
        this.offMultiplier = offMultiplier; // 예: 0.95 (5% 할인)
    }

    @Override
    public BigDecimal quote(Booking b) {
        return b.roomType().baseRate()
                .multiply(offMultiplier)
                .multiply(BigDecimal.valueOf(b.nights()))
                .setScale(0, RoundingMode.HALF_UP);
    }
}
