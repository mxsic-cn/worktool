package cn.siqishangshu.yaml.adls;

/**
 * Created by liuchuan on 9/28/16.
 */
public class Protocol_Identification {
    private Identification_Port identification_port;
    private Identification_Content identification_content;

    public Protocol_Identification() {
        this.identification_port = new Identification_Port();
        this.identification_content = new Identification_Content();
    }

    public Protocol_Identification(Identification_Port identification_port, Identification_Content identification_content) {
        this.identification_port = identification_port;
        this.identification_content = identification_content;
    }

    public Identification_Port getIdentification_port() {
        return identification_port;
    }

    public void setIdentification_port(Identification_Port identification_port) {
        this.identification_port = identification_port;
    }

    public Identification_Content getIdentification_content() {
        return identification_content;
    }

    public void setIdentification_content(Identification_Content identification_content) {
        this.identification_content = identification_content;
    }
}
