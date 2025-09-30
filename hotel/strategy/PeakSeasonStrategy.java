package strategy;

import java.math.BigDecimal;
import java.math.RoundingMode;

public final class PeakSeasonStrategy implements PricingStrategy {
    private final BigDecimal peakMultiplier;

    public PeakSeasonStrategy(BigDecimal peakMultiplier) {
        this.peakMultiplier = peakMultiplier; // 예: 1.35 (35% 가산)
    }

    @Override
    public BigDecimal quote(Booking b) {
        return b.roomType().baseRate()
                .multiply(peakMultiplier)
                .multiply(BigDecimal.valueOf(b.nights()))
                .setScale(0, RoundingMode.HALF_UP);
    }
}
