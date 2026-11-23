
public class Radio implements Device {

    private boolean on = false;
    private int volume = 5;
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
        return "Радио: " + (on ? "Қосулы" : "Өшірулі")
                + " | Дыбыс: " + volume
                + " | Жиілік: " + channel;
    }

    @Override
    public String getName() {
        return "Радио";
    }
}
