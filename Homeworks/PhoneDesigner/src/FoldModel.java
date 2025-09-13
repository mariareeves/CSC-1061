public class FoldModel extends Phone {

    public FoldModel() {
        super("SpeedRacer 800", 6, 64);
    }

    @Override
    public String toString() {
        return "FoldModel [Processor: " + processor +
                ", Cache: " + cache + "MB, Storage: " + storage + "GB, IMEI: " + getImei() + "]";
    }
}

