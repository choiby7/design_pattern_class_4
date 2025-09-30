package strategy;

import java.time.LocalDate;
import java.util.Objects;

public final class DateRange {
    public final LocalDate checkIn;   // 포함
    public final LocalDate checkOut;  // 미포함 (통상적 예약 모델)

    public DateRange(LocalDate in, LocalDate out) {
        if (in == null || out == null || !out.isAfter(in))
            throw new IllegalArgumentException("잘못된 날짜 범위");
        this.checkIn = in;
        this.checkOut = out;
    }

    public boolean overlaps(DateRange other) {
        // [in, out) 와 [in2, out2) 겹침?
        return !(this.checkOut.compareTo(other.checkIn) <= 0
              || this.checkIn.compareTo(other.checkOut) >= 0);
    }

    public int nights() {
        return (int) java.time.temporal.ChronoUnit.DAYS.between(checkIn, checkOut);
    }

    @Override public String toString() { return checkIn + " ~ " + checkOut; }
    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DateRange)) return false;
        DateRange that = (DateRange) o;
        return checkIn.equals(that.checkIn) && checkOut.equals(that.checkOut);
    }
    @Override public int hashCode() { return Objects.hash(checkIn, checkOut); }
}
