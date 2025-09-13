import java.util.ArrayList;

//abstract class for all phones
public abstract class Phone implements Cloneable, Comparable<Phone>{
    protected String processor;
    protected int cache;
    protected int storage;
    protected ArrayList<Character> imei;

    //constructor
    public Phone(String processor, int cache, int storage){
        this.processor = processor;
        this.cache = cache;
        this.storage = storage;
        this.imei = new ArrayList<>();
    }

    //sets imei from a string
    public void setImei(String imeiStr){
        //remove old imei if necessary
        imei.clear();
        for(char c: imeiStr.toCharArray()){
            imei.add(c);
        }
    }

    //then return imei as a strin
    public String getImei(){
        StringBuilder sb = new StringBuilder();
        for(char c :  imei){
            sb.append(c);
        }
        return sb.toString();
    }

    //make a copy of the phone and its imei list
    public Phone clone() {
        try {
            // clone the phone
            Phone copy = (Phone) super.clone();

            // make a new imei list so it's not shared with original
            copy.imei = new ArrayList<>();
            for (Character c : this.imei) {
                copy.imei.add(c); // copy one char at a time
            }

            return copy;
        } catch (CloneNotSupportedException e) {
            // this should never happen since we're Cloneable
            throw new RuntimeException("Clone not supported", e);
        }
    }

    // compares two phones
    public int compareTo(Phone other) {
        if (this.cache != other.cache) {
            return Integer.compare(this.cache, other.cache);
        } else {
            return Integer.compare(this.storage, other.storage);
        }
    }

    // all phones must implement this method
    public abstract String toString();
}
