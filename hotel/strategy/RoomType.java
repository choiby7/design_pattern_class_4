package strategy;

import java.math.BigDecimal;

public enum RoomType {
    STANDARD(new BigDecimal("90000")),
    DELUXE  (new BigDecimal("140000")),
    SUITE   (new BigDecimal("230000"));

    private final BigDecimal baseRate;
    RoomType(BigDecimal baseRate) { this.baseRate = baseRate; }
    public BigDecimal baseRate() { return baseRate; }
}
