package cn.siqishangshu.json;

import java.util.HashSet;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: lxu
 * Date: 12/27/13
 * Time: 3:54 PM
 * To change this template use File | Settings | File Templates.
 */
public class PolicyGroup {
    private String sourceName = "";
    private String destinationName = "";
    private Set<String> sourceGroup;
    private Set<String> destinationGroup;

    public PolicyGroup(Set<String> sourceGroup, Set<String> destinationGroup) {
        this.sourceGroup = sourceGroup;
        this.destinationGroup = destinationGroup;
    }

    public PolicyGroup(String sourceName, Set<String> sourceGroup, String destinationName, Set<String> destinationGroup) {
        this.sourceName = sourceName;
        this.destinationName = destinationName;
        this.sourceGroup = sourceGroup;
        this.destinationGroup = destinationGroup;
    }

    public PolicyGroup() {
        this.sourceGroup = new HashSet<>();
        this.destinationGroup = new HashSet<>();
    }

    public String getSourceName() {
        return this.sourceName;
    }

    public String getDestinationName() {
        return this.destinationName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public void setDestinationName(String destinationName) {
        this.destinationName = destinationName;
    }

    public Set<String> getDestinationGroup() {
        return destinationGroup;
    }

    public void setDestinationGroup(Set<String> destinationGroup) {
        this.destinationGroup = destinationGroup;
    }

    public Set<String> getSourceGroup() {
        return sourceGroup;
    }

    public void setSourceGroup(Set<String> sourceGroup) {
        this.sourceGroup = sourceGroup;
    }
}
