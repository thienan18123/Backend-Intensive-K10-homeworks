package SystemDesign.Parking;

public class ParkingMain {
    public static void main(String[] args) {

        // Bãi 3 tầng, mỗi tầng 4 chỗ = 12 chỗ
        ParkingLot lot = new ParkingLot(3, 4);
        lot.printStatus();

        // Đỗ vài xe
        lot.park(new Car("51A-123"));    // Tầng 0 - Chỗ 0
        lot.park(new Car("51B-456"));    // Tầng 0 - Chỗ 1
        lot.park(new Car("51C-789"));    // Tầng 0 - Chỗ 2
        lot.printStatus();

        // Tìm xe
        System.out.println("Car 51B-456 is in: " + lot.findCar("51B-456"));

        // Lấy xe
        lot.leave("51B-456");
        lot.printStatus();

        // Đỗ xe mới, tái dùng chỗ vừa trả
        lot.park(new Car("51D-999"));
        lot.printStatus();

        // Test lỗi
        lot.leave("99X-000");            // không có trong bãi
        lot.park(new Car("51A-123"));    // đã đỗ rồi
    }
}
    

