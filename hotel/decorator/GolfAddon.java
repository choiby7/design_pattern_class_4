package decorator;

import strategy.Booking;

import java.math.BigDecimal;
import java.math.RoundingMode;

/** 골프장 : 투숙 기간 정액(Flat per stay) */
public final class GolfAddon extends AddonDecorator {
    private final BigDecimal flatPerStay;

    public GolfAddon(BookingPricer delegate, BigDecimal flatPerStay) {
        super(delegate);
        this.flatPerStay = flatPerStay;
    }

    @Override
    protected BigDecimal addOnCost(Booking b) {
        return flatPerStay.setScale(0, RoundingMode.HALF_UP);
    }

    @Override
    protected String addOnName() { return "Golf"; }
}
