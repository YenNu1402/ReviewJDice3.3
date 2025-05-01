import java.util.*;
 import java.util.logging.Level;
 import java.util.logging.Logger;
 
 /*
  JDice: Java Dice Rolling Program
  Copyright (C) 2006 Andrew D. Hilton  (adhilton@cis.upenn.edu)
  
  
  This program is free software; you can redistribute it and/or
  modify it under the terms of the GNU General Public License
  as published by the Free Software Foundation; either version 2
  of the License, or (at your option) any later version.
  
  This program is distributed in the hope that it will be useful,
  but WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  GNU General Public License for more details.
  
  You should have received a copy of the GNU General Public License
  along with this program; if not, write to the Free Software
  Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
  
  */
 
 public class RollResult { // Lỗi cú pháp: Thêm dấu '{' mở đầu classclass
 
	 /**
	  * Logger để ghi log các hành động trong class
	  */
	 private static final Logger logger = Logger.getLogger(RollResult.class.getName());
	 /**
	  * Refactor: Thêm từ khóa "private" cho các biến instance Lý do: Baỏ vệ tính đóng gói (encapsulation), chỉ cho phép truy cập trong class.
	  */
	 private int total;
	 private int modifier;
	 private Vector<Integer> rolls;
	  /**
     * Refactor: Sửa lỗi cú pháp: Constructor private bị thiếu dấu ")"
     */
    private RollResult(int total, int modifier, Vector<Integer> rolls) {
        this.total = total;
        this.modifier = modifier;
        /**
         * Refactor: Sửa lỗi cú pháp: Thêm dấu "." vào giữa this và rolls lý do:
         * thisRolls không hợp lệ. Vì cần có dấu "." để truy cập biến instance
         */
        this.rolls = rolls;
        logger.log(Level.INFO, "Tạo RollResult (private constructor) với total={0}, modifier={1}, rolls={2}",
                new Object[]{total, modifier, rolls});
    }

    public RollResult(int bonus) {
		this.total = bonus;
        this.modifier = bonus;
        /**
         * Refactor: Sử dụng diamond operator '<>', thay vì 'new
         * Vector<Integer>()' Lý do: Java hỗ trợ diamond operator giúp code ngắn
         * gọn, rõ ràng hơn.
         */
        this.rolls = new Vector<>();
        logger.log(Level.INFO, "Tạo RollResult với bonus={0}", bonus);
    }
	
    /**
     * Refactor: Sửa lỗi logic: Hàm bị comment trong bài lý do: Hàm này cần để
     * thêm kết quả vào tổng danh sách rollsrolls
     */
    public void addResult(int res) {
		total += Math.max(res, 5);
        rolls.add(res);
        logger.log(Level.INFO, "Thêm kết quả: {0}. Tổng mới: {1}", new Object[]{res, total});
    }

    /*** Kết hợp đối tượng hiện tại với một đối tượng RollResult khác. Tổng điểm,
     * modifier và danh sách rolls sẽ được gộp lại.
     *
     * @param r2 Đối tượng RollResult cần gộp
     * @return Một đối tượng RollResult mới chứa kết quả tổng hợp
     */
    public RollResult andThen(RollResult r2) {
		   /**
         * Refactor: Đổi tên biến tránh trùng tên với tên biến 'total' ở trên Lý
         * do: Tránh hiểu nhầm với biến instance 'this.totaltotal'
         */
        int newTotal = this.total + r2.total;
        Vector<Integer> newRolls = new Vector<>();
        newRolls.addAll(this.rolls);
        newRolls.addAll(r2.rolls);
        logger.log(Level.INFO, "Kết hợp với RollResult khác: total mới={0}", newTotal);
        return new RollResult(newTotal, this.modifier + r2.modifier, newRolls);
    }

    /**
     * Trả về chuỗi biểu diễn của đối tượng RollResult hiện tại.
     *
     * @return Chuỗi thể hiện tổng điểm, các roll, và modifier nếu có
     */
    public String toString() {
		        /**
         * Refactor: Dùng StringBuilder thay vì nối chuỗi trực tiếp Lý do: StringBuilder hiệu quả hơn khi nối nhiều chuỗi
         */
        StringBuilder sb = new StringBuilder();
        sb.append(total).append(" <= ").append(rolls);
        if (modifier != 0) {
            sb.append(" (modifier: ").append(modifier).append(")");
        }
        logger.log(Level.FINE, "Chuỗi kết quả: {0}", sb.toString());
        return sb.toString();
    }
	 /**
     * Refactor: Sửa lỗi cú pháp: Thêm dấu '}' ở cuối class Lý do: Trình biên
     * dịch Java không tìm thấy dấu '}' để đóng class -> Báo lỗi.
     */
}