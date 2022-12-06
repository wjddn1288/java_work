package com.edu.shop.domain;

public class SubCategory {
	private int subcategory_idx;
	private TopCategory topcategory;
	private String subcategory_name;
	
	public int getSubcategory_idx() {
		return subcategory_idx;
	}
	public void setSubcategory_idx(int subcategory_idx) {
		this.subcategory_idx = subcategory_idx;
	}
	
	public TopCategory getTopcategory() {
		return topcategory;
	}
	public void setTopcategory(TopCategory topcategory) {
		this.topcategory = topcategory;
	}
	public String getSubcategory_name() {
		return subcategory_name;
	}
	public void setSubcategory_name(String subcategory_name) {
		this.subcategory_name = subcategory_name; 
	}
}
