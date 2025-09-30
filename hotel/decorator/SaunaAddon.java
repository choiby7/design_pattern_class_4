package decorator;

import strategy.Booking;

import java.math.BigDecimal;
import java.math.RoundingMode;

/** 사우나: 성인 수 × 1박당 1인 요금 × 박수 (아동 무료 가정) */
public final class SaunaAddon extends AddonDecorator {
    private final BigDecimal pricePerAdultPerNight;

    public SaunaAddon(BookingPricer delegate, BigDecimal pricePerAdultPerNight) {
        super(delegate);
        this.pricePerAdultPerNight = pricePerAdultPerNight;
    }

    @Override
    protected BigDecimal addOnCost(Booking b) {
        return pricePerAdultPerNight
                .multiply(BigDecimal.valueOf(b.adults()))
                .multiply(BigDecimal.valueOf(b.nights()))
                .setScale(0, RoundingMode.HALF_UP);
    }

    @Override
    protected String addOnName() { return "Sauna"; }
}
