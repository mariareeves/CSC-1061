public class BarModel extends Phone implements Repairable {

    public BarModel() {
        super("DragonSlayer 600", 8, 32);
    }

    @Override
    public String howToRepair() {
        return "Replace battery and clean motherboard.";
    }

    @Override
    public double costToRepair() {
        return 60.00;
    }

    @Override
    public String toString() {
        return "BarModel [Processor: " + processor +
                ", Cache: " + cache + "MB, Storage: " + storage + "GB, IMEI: " + getImei() + "]";
    }
}
