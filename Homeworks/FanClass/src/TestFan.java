public class TestFan {
    public static void main(String[] args) {
        // First fan
        Fan fan1 = new Fan();
        fan1.setSpeed(Fan.FAST);
        fan1.setRadius(10);
        fan1.setColor("yellow");
        fan1.turnOn();

        // Second fan
        Fan fan2 = new Fan();
        fan2.setSpeed(Fan.MEDIUM);
        fan2.setRadius(5);
        fan2.setColor("blue");
        fan2.turnOff();

        // Display using toString
        System.out.println(fan1.toString());
        System.out.println(fan2.toString());
    }
}
