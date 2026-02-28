package model;

public class ElementMetadata {

    public String tag;
    public String id;
    public String name;
    public String className;
    public String text;
    public String healedLocator;
    public String locatorStrategy;
    public String locatorValue;

    public ElementMetadata() {
    }

    public ElementMetadata(String tag, String id, String className, String text) {
        this.tag = tag;
        this.id = id;
        this.name=name;
        this.className = className;
        this.text = text;
    }
}