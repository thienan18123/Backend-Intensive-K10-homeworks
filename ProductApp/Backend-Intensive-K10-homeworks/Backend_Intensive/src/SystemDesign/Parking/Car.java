package SystemDesign.Parking;

public class Car {
    private final String plate;

    public Car(String plate) {
        this.plate = plate;
    }

    public String getPlate() {
        return plate;
    }

    @Override
    public String toString() {
        return "Car [" + plate + "]";
    }

    
}
