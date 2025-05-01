import java.util.logging.Logger;
import java.util.logging.Level;

public class DiceSum extends DieRoll {
    private static final Logger logger = Logger.getLogger(DiceSum.class.getName());

    private DieRoll r1;
    private DieRoll r2;

    // Constructor có thêm kiểm tra null (validation)
    public DiceSum(DieRoll r1, DieRoll r2) {
        super(0, 0, 0); // Giả định DieRoll có constructor này

        if (r1 == null || r2 == null) {
            logger.severe("One or both DieRoll objects are null.");
            throw new IllegalArgumentException("DieRoll parameters cannot be null.");
        }

        this.r1 = r1;
        this.r2 = r2;

        logger.info("DiceSum initialized with two DieRoll objects.");
    }

    // Ghi đè phương thức makeRoll
    @Override
    public RollResult makeRoll() {
        logger.info("makeRoll() called in DiceSum.");

        RollResult result1 = r1.makeRoll();
        logger.fine("First roll result: " + result1);

        RollResult result2 = r2.makeRoll();
        logger.fine("Second roll result: " + result2);

        RollResult combined = result1.andThen(result2);
        logger.info("Combined roll result: " + combined);

        return combined;
    }

    // ✅ Helper method: Tính tổng điểm từ makeRoll()
    public int getTotalRollValue() {
        RollResult result = makeRoll();
        return result.getTotal(); // Giả định RollResult có phương thức getTotal()
    }

    // ✅ Thông báo cải tiến rõ ràng hơn cho người dùng
    @Override
    public String toString() {
        return "DiceSum Result: [" + r1.toString() + "] + [" + r2.toString() + "] = Total: " + getTotalRollValue();
    }
}