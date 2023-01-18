package org.cft.files.merger.elements;

import org.cft.settings.enums.SortType;

public class StringElement implements Comparable<StringElement> {

    static public SortType type = SortType.ASC;
    private String element;
    private int index;

    public StringElement(String element, int index) {
        this.element = element;
        this.index = index;
    }

    public String getElement() {
        return element;
    }

    public int getIndex() {
        return index;
    }

    public void setElement(String element) {
        this.element = element;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    @Override
    public String toString() {
        return "StringElement{" +
                "element='" + element + '\'' +
                ", index=" + index +
                '}';
    }


    @Override
    public int compareTo(StringElement o) {
        if (type == SortType.ASC) {
            return element.compareTo(o.getElement());
        }
        return -element.compareTo(o.getElement());
    }
}
