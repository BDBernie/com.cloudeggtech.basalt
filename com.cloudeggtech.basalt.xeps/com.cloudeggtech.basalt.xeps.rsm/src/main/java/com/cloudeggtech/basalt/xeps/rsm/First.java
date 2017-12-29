package com.cloudeggtech.basalt.xeps.rsm;

import com.cloudeggtech.basalt.protocol.oxm.convention.annotations.Text;

public class First {
	private int index;
	@Text
	private String text;
	
	public First() {}
	
	public First(String text) {
		this.text = text;
	}
	
	public First(int index, String text) {
		this.index = index;
		this.text = text;
	}
	
	public int getIndex() {
		return index;
	}
	
	public void setIndex(int index) {
		this.index = index;
	}
	
	public String getText() {
		return text;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
}
