package cn.mysic.yaml.adls;

/**
 * Created by liuchuan on 9/28/16.
 */
public class Identification_Port {
    private String enabled;
    private double port;

    public Identification_Port() {
        this.enabled = UDPConstant.Enabled.YES+Math.random();
        this.port = Math.floor(Math.random()*1000);
    }

    public Identification_Port(String enabled, double port) {
        this.enabled = enabled;
        this.port = port;
    }

    public String getEnabled() {
        return enabled;
    }

    public void setEnabled(String enabled) {
        this.enabled = enabled;
    }

    public double getPort() {
        return port;
    }

    public void setPort(double port) {
        this.port = port;
    }
}
