package cn.mysic.yaml.adls;

/**
 * Created by liuchuan on 9/27/16.
 */
public class Protocol {
    private adl.Protocol_Parser protocol_parser;
    private Protocol_Identification protocol_identification;
    private String transport_protocol;
    private String protocol_name;

    public Protocol() {
        this.protocol_parser = new Protocol_Parser();
        this.protocol_identification = new Protocol_Identification();
        this.transport_protocol = UDPConstant.ProtocolType.TCP+Math.random();
        this.protocol_name = "S7"+Math.random();
    }

    public Protocol(Protocol_Parser protocol_parser, Protocol_Identification protocol_identification, String transport_protocol, String protocol_name) {
        this.protocol_parser = protocol_parser;
        this.protocol_identification = protocol_identification;
        this.transport_protocol = transport_protocol;
        this.protocol_name = protocol_name;
    }

    public Protocol_Parser getProtocol_parser() {
        return protocol_parser;
    }

    public void setProtocol_parser(Protocol_Parser protocol_parser) {
        this.protocol_parser = protocol_parser;
    }

    public Protocol_Identification getProtocol_identification() {
        return protocol_identification;
    }

    public void setProtocol_identification(Protocol_Identification protocol_identification) {
        this.protocol_identification = protocol_identification;
    }

    public String getTransport_protocol() {
        return transport_protocol;
    }

    public void setTransport_protocol(String transport_protocol) {
        this.transport_protocol = transport_protocol;
    }

    public String getProtocol_name() {
        return protocol_name;
    }

    public void setProtocol_name(String protocol_name) {
        this.protocol_name = protocol_name;
    }
}
