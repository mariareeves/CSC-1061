public class FlipModel extends Phone implements Repairable {

    // constructor sets default specs
    public FlipModel() {
        super("HorseRacer 300", 3, 16); // processor, cache, storage
    }

    // required by Repairable
    @Override
    public String howToRepair() {
        return "Replace screen and check hinge for damage.";
    }

    @Override
    public double costToRepair() {
        return 75.00;
    }

    @Override
    public String toString() {
        return "FlipModel [Processor: " + processor +
                ", Cache: " + cache + "MB, Storage: " + storage + "GB, IMEI: " + getImei() + "]";
    }
}
