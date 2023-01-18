package org.cft.files.merger.elements;

import org.cft.settings.enums.SortType;

public class IntegerElement implements Comparable<IntegerElement> {

    static public SortType type = SortType.ASC;
    private int element;
    private int index;

    public IntegerElement(int element, int index) {
        this.element = element;
        this.index = index;
    }

    public int getElement() {
        return element;
    }

    public int getIndex() {
        return index;
    }

    public void setElement(int element) {
        this.element = element;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    @Override
    public String toString() {
        return "IntegerElement{" +
                "element=" + element +
                ", index=" + index +
                '}';
    }

    @Override
    public int compareTo(IntegerElement o) {
        if (type == SortType.ASC) {
            if (element < o.element)
                return -1;
            if (element > o.element)
                return 1;
        } else {
            if (element > o.element)
                return -1;
            if (element < o.element)
                return 1;
        }
        return 0;
    }
}
