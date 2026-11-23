
public interface Device {
    void turnOn();
    void turnOff();
    void volumeUp();
    void volumeDown();
    void setChannel(int channel);
    String getStatus();
    String getName();
}
