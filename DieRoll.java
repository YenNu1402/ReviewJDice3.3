import java.util.Random;

/**
 * Class DieRoll - đại diện cho một lần tung nhiều xúc xắc với số mặt và điểm thưởng xác định.
 * 
 * <p>Các thay đổi được refactor gồm:</p>
 * <ul>
 *   <li>**Thêm tên lớp DieRoll** - mã ban đầu bị thiếu tên lớp.</li>
 *   <li>**Sửa lỗi biến `thisndice` thành `this.ndice`** - để tránh lỗi cú pháp.</li>
 *   <li>**Sửa block khởi tạo static bị comment** - đảm bảo `Random rnd` được khởi tạo đúng.</li>
 *   <li>**Thêm dấu chấm phẩy còn thiếu ở `r.addResult(roll);`**.</li>
 *   <li>**Sửa lỗi cú pháp `:=` thành `=` trong `toString`**.</li>
 *   <li>**Thêm kiểm tra đầu vào** - đảm bảo số xúc xắc và số mặt hợp lệ.</li>
 *   <li>**Sử dụng StringBuilder trong `toString`** - cải thiện hiệu suất.</li>
 *   <li>**Thêm JavaDoc chi tiết** - tăng tính rõ ràng.</li>
 * </ul>
 */
public class DieRoll {
    private final int numDice; // Refactored: Đổi tên từ ndice
    private final int numSides; // Refactored: Đổi tên từ nsides
    private final int bonus;
    private static final Random random = new Random(); // Refactored: Đổi tên và khai báo final

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
            throw new IllegalArgumentException("Số xúc xắc và số mặt phải lớn hơn 0");
        }
        this.numDice = numDice; // Refactored: Sửa lỗi chính tả từ "thisndice"
        this.numSides = numSides;
        this.bonus = bonus;
    }

    /**
     * Thực hiện việc tung xúc xắc và trả về kết quả.
     * 
     * @return Kết quả của lần tung, chứa danh sách các lần tung và điểm thưởng
     */
    public RollResult roll() { // Refactored: Đổi tên từ makeRoll
        RollResult r = new RollResult(bonus);
        for (int i = 0; i < numDice; i++) {
            int roll = random.nextInt(numSides) + 1;
            r.addResult(roll); // Refactored: Thêm dấu chấm phẩy
        }
        return r;
    }

    /**
     * Trả về chuỗi mô tả lần tung xúc xắc, ví dụ "3d6+2" (3 xúc xắc 6 mặt, cộng 2).
     * 
     * @return Chuỗi định dạng "NdS+B" hoặc "NdS-B"
     */
    @Override
    public String toString() {
        StringBuilder ans = new StringBuilder(); // Refactored: Dùng StringBuilder
        ans.append(numDice).append("d").append(numSides);
        if (bonus > 0) {
            ans.append("+").append(bonus);
        } else if (bonus < 0) {
            ans.append(bonus); // Refactored: Sửa lỗi ":="
        }
        return ans.toString();
    }
}

/**
 * Lớp lưu trữ kết quả của một lần tung xúc xắc.
 */
class RollResult {
    private final int bonus;
    private final List<Integer> rolls;

    /**
     * Khởi tạo RollResult với điểm thưởng.
     * 
     * @param bonus Điểm thưởng
     */
    public RollResult(int bonus) {
        this.bonus = bonus;
        this.rolls = new ArrayList<>();
    }

    /**
     * Thêm kết quả của một lần tung.
     * 
     * @param roll Giá trị tung được
     */
    public void addResult(int roll) {
        rolls.add(roll);
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
        return new ArrayList<>(rolls);
    }
}