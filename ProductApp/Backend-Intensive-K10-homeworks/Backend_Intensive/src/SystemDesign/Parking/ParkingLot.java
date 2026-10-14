package SystemDesign.Parking;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

public class ParkingLot {
    private final int floors;
    private final int slotsPerFloor;

    private final Slot[][] slots;
    private final Queue<Slot> freeSlots;
    private final Map<String, Slot> parkedCars;

    public ParkingLot(int floors, int slotsPerfloor) {
        this.floors = floors;
        this.slotsPerFloor = slotsPerfloor;
        this.slots = new Slot[floors][slotsPerfloor];
        this.freeSlots = new PriorityQueue<>();
        this.parkedCars = new HashMap<>();


        //setup parking place
        for (int f = 0; f < floors; f++) {
            for (int s = 0; s < slotsPerFloor; s++) {
                Slot slot = new Slot(f, s);
                slots[f][s] = slot;
                freeSlots.add(slot);            
            }
        }
    }

    //đỗ xe 0(1) 
    public Slot park(Car car) {
        if (freeSlots.isEmpty()) {
            System.out.println("OUT OF SPACE " + car + " cannot park.");
            return null;
        }
        if (parkedCars.containsKey(car.getPlate())) {
            System.out.println(car + " parked, no parking again.");
            return null;
        }

        Slot slot = freeSlots.poll(); //lấy chỗ trống
        slot.park(car);
        parkedCars.put(car.getPlate(), slot);
        System.out.println(car + " parked at: " + slot);
        return slot;
    }

    //xe rời đi 0(1)
    public void leave(String plate) {
        Slot slot = parkedCars.get(plate);
        if (slot == null) {
            System.out.println("Cannot find the car in the parking lot.");
            return;
        }

        slot.release();
        parkedCars.remove(plate); //xóa khỏi Map
        freeSlots.add(slot); // trả chỗ
        System.out.println(" Getting car [" + plate + "] from " + slot);
    }

    //Trạng thái
    public int availableCount() {
        return freeSlots.size();
    }

    public int occupiedCount() {
        return parkedCars.size();
    }

    public Slot findCar(String plate) {         // xe đang ở đâu — O(1)
        return parkedCars.get(plate);
    }

    public void printStatus() {
        System.out.println("\n=== Parking Status ===");
        for (int f = 0; f < floors; f++) {
            System.out.print("Floor " + f + ": ");
            for (int s = 0; s < slotsPerFloor; s++) {
                if (slots[f][s].isOccupied()) {
                    System.out.print("[X]");
                } else {
                    System.out.print("[ ]");
                }
                System.out.println();
            }
            System.out.println("Available: " + availableCount() + " | Parked: " + occupiedCount());
            System.out.println("=========================\n");
        }
    }
}
