package com.excilys.cdb.pagination;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Page Pojo.
 *
 * @param <T>
 */
public class Page<T> {
    ArrayList<T> elementList = null;
    int start = -1;
    int offset = -1;
    static final Logger LOGGER = LoggerFactory.getLogger(Page.class);

    /**
     * Page Constructor.
     * @param elementList list of element to display
     * @param start start index
     * @param offset page offset
     */
    public Page(ArrayList<T> elementList, int start, int offset) {
        LOGGER.debug("Page creation");
        this.elementList = elementList;
        this.start = start;
        this.offset = offset;
    }

    public ArrayList<T> getElementList() {
        return elementList;
    }

    public void setElementList(ArrayList<T> elementList) {
        this.elementList = elementList;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    /**
     * return a string representation of a Page.
     * @return string representation
     */
    public String toString() {
        return this.elementList.toString();
    }
}
