
public class RemoteControl {

    private Device device;

    public RemoteControl(Device device) {
        this.device = device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

    public void powerOn() {
        device.turnOn();
    }

    public void powerOff() {
        device.turnOff();
    }

    public void volumeUp() {
        device.volumeUp();
    }

    public void volumeDown() {
        device.volumeDown();
    }

    public void changeChannel(int channel) {
        device.setChannel(channel);
    }

    public String getStatus() {
        return device.getStatus();
    }

    public String getDeviceName() {
        return device.getName();
    }
}
