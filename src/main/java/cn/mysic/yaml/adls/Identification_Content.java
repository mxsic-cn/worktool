package cn.mysic.yaml.adls;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuchuan on 9/28/16.
 */
public class Identification_Content {
    private String enabled;
    private List<String> content;

    public Identification_Content() {
        this.enabled = UDPConstant.Enabled.YES+Math.random();
        this.content = new ArrayList<String>();
        this.content.add("0,3,46454c");
        this.content.add("0,3,41434b");
        this.content.add(Math.random()+"");
    }

    public Identification_Content(String enabled, List<String> content) {
        this.enabled = enabled;
        this.content = content;
    }

    public String getEnabled() {
        return enabled;
    }

    public void setEnabled(String enabled) {
        this.enabled = enabled;
    }

    public List<String> getContent() {
        return content;
    }

    public void setContent(List<String> content) {
        this.content = content;
    }
}
