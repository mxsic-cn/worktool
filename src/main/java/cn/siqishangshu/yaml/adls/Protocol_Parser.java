package cn.siqishangshu.yaml.adls;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuchuan on 9/28/16.
 */
public class Protocol_Parser {
    private List<Content>  content;

    public Protocol_Parser() {
        this.content = new ArrayList<Content>();
        this.content.add(new Content());
        this.content.add(new Content());
        this.content.add(new Content());
    }

    public Protocol_Parser(List<Content> content) {
        this.content = content;
    }

    public List<Content> getContent() {
        return content;
    }

    public void setContent(List<Content> content) {
        this.content = content;
    }
}
