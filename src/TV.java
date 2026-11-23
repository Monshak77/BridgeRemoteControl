
public class TV implements Device {

    private boolean on = false;
    private int volume = 10;
    private int channel = 1;

    @Override
    public void turnOn() {
        on = true;
    }

    @Override
    public void turnOff() {
        on = false;
    }

    @Override
    public void volumeUp() {
        if (on && volume < 100) {
            volume = Math.min(100, volume + 5);
        }
    }

    @Override
    public void volumeDown() {
        if (on && volume > 0) {
            volume = Math.max(0, volume - 5);
        }
    }

    @Override
    public void setChannel(int channel) {
        if (on && channel > 0) {
            this.channel = channel;
        }
    }

    @Override
    public String getStatus() {
        return "Теледидар: " + (on ? "Қосулы" : "Өшірулі")
                + " | Дыбыс: " + volume
                + " | Арна: " + channel;
    }

    @Override
    public String getName() {
        return "Теледидар";
    }
}
