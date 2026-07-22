package SystemDesign.Parking;

public class Slot implements Comparable<Slot> {
    private final int floor;
    private final int index;
    private boolean occupied;
    private Car car;

    public Slot(int floor, int index) {
        this.floor = floor;
        this.index = index;
        this.occupied = false;
    }

    public void park(Car car) {
        this.car = car;
        this.occupied = true;
    }

    public void release(){
        this.car = null;
        this.occupied = false;
    }

    public boolean isOccupied() {
        return occupied;
    }

    public int getFloor() {
        return floor;
    }

    public int getIndex() {
        return index;
    }

    public Car getCar() {
        return car;
    }

    @Override
    public String toString() {
        return "Floor " + floor + " space " + index;
    }

    @Override
    public int compareTo(Slot other) {
        if (this.floor != other.floor) {
            return this.floor - other.floor;
        }
        return this.index - other.index;
    }
}
