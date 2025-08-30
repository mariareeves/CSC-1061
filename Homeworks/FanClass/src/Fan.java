//Maria Reeves, CSC1061
// Assignment: FanClass
public class Fan {
    //fan speed constants
    public static final int SLOW = 1;
    public static final int MEDIUM = 2;
    public static final int FAST = 3;

    //specifies the speed of the fan
    private int speed = SLOW;
    //fan is on or off
    private boolean on = false;
    //radius of fan
    private double radius = 5.0;
    //color of fan
    String color = "blue";

    //id field
    private static int nextId = 1;
    private final int id;


    // No-arg constructor
    public Fan() {
        this.id = nextId++;
    }

    // getters
    public int getId() { return id; }
    public int getSpeed() { return speed; }
    public boolean isOn() { return on; }
    public double getRadius() { return radius; }
    public String getColor() { return color; }

    // setters
    public void setSpeed(int speed) { this.speed = speed; }
    public void setOn(boolean on) { this.on = on; }
    public void turnOn() { this.on = true; }
    public void turnOff() { this.on = false; }
    public void setRadius(double radius) { this.radius = radius; }
    public void setColor(String color) { this.color = color; }

    // toString method
    public String toString() {
        if (on) {
            return "A " + color + " fan, radius " + radius +
                    ", running at speed " + speed + ".";
        } else {
            return "A " + color + " fan, radius " + radius +
                    ", is off.";
        }
    }


}
