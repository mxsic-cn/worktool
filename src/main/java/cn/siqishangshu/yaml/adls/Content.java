package cn.siqishangshu.yaml.adls;

/**
 * Created by liuchuan on 9/28/16.
 */
public class Content {
    private String content_name;
    private String contentlen;
    private String content_datatype;
    private String packlen;
    private String packetlen_datatype;
    private String type;

    public Content() {
        this.content_name = "serviceID"+Math.random();
        this.contentlen = "0,24"+Math.random();
        this.content_datatype = UDPConstant.DataType.STRING+Math.random();
        this.packlen = "4,26"+Math.random();
        this.packetlen_datatype = UDPConstant.DataType.INTEGER+Math.random();
        this.type = "type"+Math.random();
    }

    public Content(String content_name, String contentlen, String content_datatype, String packlen, String packetlen_datatype, String type) {
        this.content_name = content_name;
        this.contentlen = contentlen;
        this.content_datatype = content_datatype;
        this.packlen = packlen;
        this.packetlen_datatype = packetlen_datatype;
        this.type = type;
    }

    public String getContent_name() {
        return content_name;
    }

    public void setContent_name(String content_name) {
        this.content_name = content_name;
    }

    public String getContentlen() {
        return contentlen;
    }

    public void setContentlen(String contentlen) {
        this.contentlen = contentlen;
    }

    public String getContent_datatype() {
        return content_datatype;
    }

    public void setContent_datatype(String content_datatype) {
        this.content_datatype = content_datatype;
    }

    public String getPacklen() {
        return packlen;
    }

    public void setPacklen(String packlen) {
        this.packlen = packlen;
    }

    public String getPacketlen_datatype() {
        return packetlen_datatype;
    }

    public void setPacketlen_datatype(String packetlen_datatype) {
        this.packetlen_datatype = packetlen_datatype;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
