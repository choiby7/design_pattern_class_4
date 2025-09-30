package decorator;

import strategy.Booking;

import java.math.BigDecimal;
import java.math.RoundingMode;

/** 조식: 1박당 1인 가격 × (성인 + (아동 포함 여부)) × 박수 */
public final class BreakfastAddon extends AddonDecorator {
    private final BigDecimal pricePerPersonPerNight;
    private final boolean chargeChildren;

    public BreakfastAddon(BookingPricer delegate, BigDecimal pricePerPersonPerNight, boolean chargeChildren) {
        super(delegate);
        this.pricePerPersonPerNight = pricePerPersonPerNight;
        this.chargeChildren = chargeChildren;
    }

    @Override
    protected BigDecimal addOnCost(Booking b) {
        int people = b.adults() + (chargeChildren ? b.children() : 0);
        return pricePerPersonPerNight
                .multiply(BigDecimal.valueOf(people))
                .multiply(BigDecimal.valueOf(b.nights()))
                .setScale(0, RoundingMode.HALF_UP);
    }

    @Override
    protected String addOnName() { return "Breakfast"; }
}
