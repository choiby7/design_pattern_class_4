package strategy;

import java.math.BigDecimal;
import java.util.Objects;

public final class BookingService {
    private PricingStrategy strategy;

    public BookingService(PricingStrategy strategy) {
        this.strategy = Objects.requireNonNull(strategy);
    }

    public void setStrategy(PricingStrategy strategy) {
        this.strategy = Objects.requireNonNull(strategy);
    }

    public BigDecimal quote(Booking booking) {
        return strategy.quote(booking);
    }

    public String currentPlanName() { return strategy.name(); }
}
