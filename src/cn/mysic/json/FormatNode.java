package cn.mysic.json;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lxu on 1/31/14.
 */
public class FormatNode {
    public String name;
    public String displayName;
    public NodeType type;
    public boolean isL2 = false;
    public String openPorts = "";
    private boolean active = false;
    public String range = "NA";
    private String parentName = "";
    public FormatNode parent;
    private List<FormatNode> children = new ArrayList<>();


    public FormatNode(String name) {
        this.name = name;
        this.active = false;
    }

    public FormatNode() {
        this.active = false;
    }

    public String getName() {
        return name;
    }

    public void setParentName(String parentName){
        this.parentName = parentName;
    }

    public String getParentName(){
        return this.parentName;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean getActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setIsL2(boolean isL2) {
        this.isL2 = isL2;
    }

    public boolean getIsL2() {
        return this.isL2;
    }

    public void setOpenPorts(String openPorts) {
        this.openPorts = openPorts;
    }

    public String getOpenPorts() {
        return this.openPorts;
    }

    public void setRange(String Range) {this.range = Range;}

    public String getRange() { return this.range; }

    public String getDisplayName() {
        return displayName;
    }

    public void setParent(FormatNode parent) {
        this.parent = parent;
    }

    public FormatNode getParent() {
        return parent;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public NodeType getType() {
        return type;
    }

    public void setType(NodeType type) {
        this.type = type;
    }

    public List<FormatNode> getChildren() {
        return children;
    }

    public void setChildren(List<FormatNode> children) {
        this.children = children;
    }

    public void addChild(FormatNode child) {
        this.children.add(child);
    }

    public void addChild(FormatNode child, boolean addToBegain) {
        if (addToBegain){
            this.children.add(0,child);
        } else {
            this.children.add(child);
        }
    }

    public List<FormatNode> getActiveChildren() {
        List<FormatNode> result = new ArrayList<>();
        for (FormatNode oneChild : this.children){
            if (oneChild.getActive()){
                result.add(oneChild);
            }
        }
        return result;
    }

    public FormatNode getChild(String name) {
        for (FormatNode oneChild : this.children){
            if (oneChild.getName().equals(name) || oneChild.getDisplayName().equals("NONSTANDARD")){
                return oneChild;
            }
        }
        return null;
    }

    public FormatNode getChildbyDisplay(String name) {
        for (FormatNode oneChild : this.children){
            if (oneChild.getDisplayName().equals(name) || oneChild.getDisplayName().equals("NONSTANDARD")){
                return oneChild;
            }
        }
        return null;
    }

}
