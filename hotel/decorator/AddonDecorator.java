package decorator;

import strategy.Booking;

import java.math.BigDecimal;
import java.util.Objects;

public abstract class AddonDecorator implements BookingPricer {
    protected final BookingPricer delegate;

    protected AddonDecorator(BookingPricer delegate) {
        this.delegate = Objects.requireNonNull(delegate);
    }

    @Override
    public BigDecimal quote(Booking booking) {
        return delegate.quote(booking).add(addOnCost(booking));
    }

    protected abstract BigDecimal addOnCost(Booking booking);
    protected abstract String addOnName();

    @Override
    public String description() {
        return delegate.description() + " + " + addOnName();
    }
}
