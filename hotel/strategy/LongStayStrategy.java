package strategy;

import java.math.BigDecimal;
import java.math.RoundingMode;

/** 장기숙박 특가: threshold 박 이상이면 총액에서 extraDiscount(비율) 추가 할인 */
public final class LongStayStrategy implements PricingStrategy {
    private final int threshold;
    private final BigDecimal extraDiscount; // 0.10 = 10% 할인

    public LongStayStrategy(int threshold, BigDecimal extraDiscount) {
        if (threshold <= 0) throw new IllegalArgumentException("threshold must be > 0");
        this.threshold = threshold;
        this.extraDiscount = extraDiscount;
    }

    @Override
    public BigDecimal quote(Booking b) {
        BigDecimal base = b.roomType().baseRate()
                .multiply(BigDecimal.valueOf(b.nights()));
        if (b.nights() >= threshold) {
            base = base.multiply(BigDecimal.ONE.subtract(extraDiscount));
        }
        return base.setScale(0, RoundingMode.HALF_UP);
    }
}
