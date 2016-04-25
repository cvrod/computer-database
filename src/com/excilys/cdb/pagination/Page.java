package com.excilys.cdb.pagination;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Page Pojo
 * @param <T>
 */
public class Page<T> {
	ArrayList<T> elementList = null;
	int start = -1;
	int offset = -1;
	final static Logger logger = LoggerFactory.getLogger(Page.class);
	
	public Page(ArrayList<T> elementList, int start, int offset){
		logger.debug("Page creation");
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
}
