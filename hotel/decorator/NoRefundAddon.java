package decorator;

import strategy.Booking;

import java.math.BigDecimal;
import java.math.RoundingMode;

/** 조식: 1박당 1인 가격 × (성인 + (아동 포함 여부)) × 박수 */
public final class NoRefundAddon extends AddonDecorator {

    public NoRefundAddon(BookingPricer delegate) {
        super(delegate);
    }

    @Override
    protected BigDecimal addOnCost(Booking b) {
        return delegate.quote(b)
                .multiply(BigDecimal.valueOf(-0.1))
                .setScale(0, RoundingMode.HALF_UP);
    }

    @Override
    protected String addOnName() { return "NoRefund"; }
}
