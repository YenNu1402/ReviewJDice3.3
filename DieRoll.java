import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;
import java.util.logging.Level;
/**
 * Class DieRoll - đại diện cho một lần tung nhiều xúc xắc với số mặt và điểm thưởng xác định.
 * 
 * Các thay đổi được refactor gồm:
 * Thêm tên lớp DieRoll - mã ban đầu bị thiếu tên lớp.
 * Sửa lỗi biến thisndice thành this.ndice - để tránh lỗi cú pháp.
 * Sửa block khởi tạo static bị comment - đảm bảo Random rnd được khởi tạo đúng.
 * Thêm dấu chấm phẩy còn thiếu ở r.addResult(roll);
 * Sửa lỗi cú pháp := thành = trong toString
 * Thêm kiểm tra đầu vào - đảm bảo số xúc xắc và số mặt hợp lệ.
 * Sử dụng StringBuilder trong toString - cải thiện hiệu suất.
 * Thêm JavaDoc chi tiết - tăng tính rõ ràng.
 * Thêm logging bằng java.util.logging - ghi lại các sự kiện khởi tạo, tung xúc xắc, và ngoại lệ.
 * Thêm validation cho RollResult.addResult - đảm bảo giá trị tung hợp lệ (lớn hơn 0).
 * Thêm phương thức helper getTotal trong RollResult - tính tổng các lần tung cộng điểm thưởng.
 * Thêm toString trong RollResult - cải thiện thông báo hiển thị cho người dùng với chi tiết các lần tung, điểm thưởng, và tổng.
 */
public class DieRoll {
    private final int numDice; // Refactored: Đổi tên từ ndice
    private final int numSides; // Refactored: Đổi tên từ nsides
    private final int bonus;
    private static final Random random = new Random(); // Refactored: Đổi tên và khai báo final
    private static final Logger LOGGER = Logger.getLogger(DieRoll.class.getName()); // Logger cho lớp
	private static final boolean IS_DEBUG = System.getProperty("debug", "false").equals("true");

    /**
     * Constructor tạo một lần tung xúc xắc.
     * 
     * @param numDice Số xúc xắc
     * @param numSides Số mặt của mỗi xúc xắc
     * @param bonus Điểm thưởng thêm vào kết quả
     * @throws IllegalArgumentException nếu số xúc xắc hoặc số mặt nhỏ hơn 1
     */
    public DieRoll(int numDice, int numSides, int bonus) {
        if (numDice < 1 || numSides < 1) {
            LOGGER.log(Level.SEVERE, "Đầu vào không hợp lệ: numDice={0}, numSides={1}", new Object[]{numDice, numSides});
            throw new IllegalArgumentException("Số xúc xắc và số mặt phải lớn hơn 0");
        }
        this.numDice = numDice;
        this.numSides = numSides;
        this.bonus = bonus;
        LOGGER.log(Level.INFO, "Khởi tạo DieRoll: {0}d{1}{2}{3}", 
                   new Object[]{numDice, numSides, bonus >= 0 ? "+" : "", bonus});
    }

    /**
     * Thực hiện việc tung xúc xắc và trả về kết quả.
     * 
     * @return Kết quả của lần tung, chứa danh sách các giá trị ngẫu nhiên từ 1 đến numSides và điểm thưởng
     */
    public RollResult makeRoll() {
        if (IS_DEBUG) {
			LOGGER.log(Level.FINE, "Bắt đầu tung {0} xúc xắc {1} mặt", new Object[]{numDice, numSides});
		}		
        RollResult r = new RollResult(bonus);
        for (int i = 0; i < numDice; i++) {
            int roll = random.nextInt(numSides) + 1;
            r.addResult(roll);
            if (IS_DEBUG) {
				LOGGER.log(Level.FINE, "Tung xúc xắc thứ {0}: kết quả = {1}", new Object[]{i + 1, roll});
			}
			
        }
        LOGGER.log(Level.INFO, "Kết quả tung: {0}, bonus: {1}", new Object[]{r.getRolls(), bonus});
        return r;
    }

    /**
     * Trả về chuỗi mô tả lần tung xúc xắc, ví dụ "3d6+2" (3 xúc xắc 6 mặt, cộng 2).
     * 
     * @return Chuỗi định dạng "NdS+B" hoặc "NdS-B"
     */
    @Override
    public String toString() {
        StringBuilder ans = new StringBuilder();
        ans.append(numDice).append("d").append(numSides);
        if (bonus > 0) {
            ans.append("+").append(bonus);
        } else if (bonus < 0) {
            ans.append(bonus);
        }
        String result = ans.toString();
        if (IS_DEBUG) {
			LOGGER.log(Level.FINE, "Chuỗi biểu diễn DieRoll: {0}", result);
		}
        return result;
    }
}

/**
 * Lớp lưu trữ kết quả của một lần tung xúc xắc.
 */
class RollResult {
    private final int bonus;
    private final List<Integer> rolls;
    private static final Logger LOGGER = Logger.getLogger(RollResult.class.getName()); // Logger cho lớp

    /**
     * Khởi tạo RollResult với điểm thưởng.
     * 
     * @param bonus Điểm thưởng
     */
    public RollResult(int bonus) {
        this.bonus = bonus;
        this.rolls = new ArrayList<>();
        LOGGER.log(Level.INFO, "Khởi tạo RollResult với bonus: {0}", bonus);
    }

    /**
     * Thêm kết quả của một lần tung.
     * 
     * @param roll Giá trị tung được, phải lớn hơn 0
     * @throws IllegalArgumentException nếu giá trị tung không hợp lệ (nhỏ hơn hoặc bằng 0)
     */
    public void addResult(int roll) {
        if (roll <= 0) {
            LOGGER.log(Level.SEVERE, "Giá trị tung không hợp lệ: roll={0}", roll);
            throw new IllegalArgumentException("Giá trị tung phải lớn hơn 0");
        }
        rolls.add(roll);
        LOGGER.log(Level.FINE, "Thêm kết quả tung: {0}", roll);
    }

    /**
     * Lấy điểm thưởng.
     * 
     * @return Điểm thưởng
     */
    public int getBonus() {
        return bonus;
    }

    /**
     * Lấy danh sách các lần tung.
     * 
     * @return Bản sao của danh sách các lần tung
     */
    public List<Integer> getRolls() {
        List<Integer> rollsCopy = new ArrayList<>(rolls);
        LOGGER.log(Level.FINE, "Trả về danh sách các lần tung: {0}", rollsCopy);
        return rollsCopy;
    }

    /**
     * Tính tổng kết quả của các lần tung cộng với điểm thưởng.
     * 
     * @return Tổng các giá trị trong danh sách rolls cộng với bonus
     */
    public int getTotal() {
        int total = rolls.stream().mapToInt(Integer::intValue).sum() + bonus;
        LOGGER.log(Level.FINE, "Tổng kết quả tung: {0}", total);
        return total;
    }

    /**
     * Trả về chuỗi mô tả kết quả tung xúc xắc, bao gồm các lần tung, điểm thưởng, và tổng.
     * 
     * @return Chuỗi định dạng, ví dụ: "Rolls: [4, 6], Bonus: +3, Total: 13"
     */
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("Rolls: ").append(rolls);
        result.append(", Bonus: ").append(bonus >= 0 ? "+" : "").append(bonus);
        result.append(", Total: ").append(getTotal());
        String output = result.toString();
        LOGGER.log(Level.FINE, "Chuỗi biểu diễn RollResult: {0}", output);
        return output;
    }
}