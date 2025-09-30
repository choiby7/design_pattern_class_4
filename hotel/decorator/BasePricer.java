package decorator;

import strategy.Booking;
import strategy.PricingStrategy;
import java.math.BigDecimal;
import java.util.Objects;

public final class BasePricer implements BookingPricer {
    private final PricingStrategy strategy;

    public BasePricer(PricingStrategy strategy) {
        this.strategy = Objects.requireNonNull(strategy);
    }

    @Override
    public BigDecimal quote(Booking booking) {
        return strategy.quote(booking); // 기본 객실요금(전략 패턴 결과)
    }

    @Override
    public String description() {
        return "Base(" + strategy.name() + ")";
    }
}
