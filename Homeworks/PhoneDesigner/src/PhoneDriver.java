import java.util.ArrayList;
import java.util.Collections;

public class PhoneDriver {
    public static void main(String[] args) {

        // create one of each phone
        FlipModel flip = new FlipModel();
        FoldModel fold = new FoldModel();
        BarModel bar = new BarModel();

        // set IMEIs
        flip.setImei("123456789012345");
        fold.setImei("987654321098765");
        bar.setImei("555555555555555");

        // print phones
        System.out.println("Original phones:");
        System.out.println(flip);
        System.out.println(fold);
        System.out.println(bar);

        // clone a phone and change its IMEI
        Phone flipClone = flip.clone();
        flipClone.setImei("000000000000000");

        System.out.println("\nFlip Clone with new IMEI:");
        System.out.println(flipClone);


        // test compareTo()
        System.out.println("\nComparing Flip and Fold:");
        int result = flip.compareTo(fold);
        if (result < 0) {
            System.out.println("Flip is smaller than Fold (based on cache + storage)");
        } else if (result > 0) {
            System.out.println("Flip is larger than Fold (based on cache + storage)");
        } else {
            System.out.println("Flip and Fold are equal (based on cache + storage)");
        }

        // test repairable methods
        System.out.println("\nRepair info:");
        System.out.println("FlipModel: " + flip.howToRepair() + " | Cost: $" + flip.costToRepair());
        System.out.println("BarModel: " + bar.howToRepair() + " | Cost: $" + bar.costToRepair());

        // test sorting
        System.out.println("\nSorting phones:");
        ArrayList<Phone> phoneList = new ArrayList<>();
        phoneList.add(flip);
        phoneList.add(fold);
        phoneList.add(bar);
        Collections.sort(phoneList);

        for (Phone p : phoneList) {
            System.out.println(p);
        }
    }
}
