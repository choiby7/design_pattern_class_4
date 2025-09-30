package strategy;

import java.math.BigDecimal;

public interface PricingStrategy {
    BigDecimal quote(Booking booking);
    default String name() { return getClass().getSimpleName(); }
}
