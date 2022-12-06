package com.edu.shop.domain;

public class SubCategory {
	private int subcategory_idx;
	private int topcategory_idx;
	private String subcategory_name;
	
	
	public int getSubcategory_idx() {
		return subcategory_idx;
	}
	public void setSubcategory_idx(int subcategory_idx) {
		this.subcategory_idx = subcategory_idx;
	}
	public int getTopcategory_idx() {
		return topcategory_idx;
	}
	public void setTopcategory_idx(int topcategory_idx) {
		this.topcategory_idx = topcategory_idx;
	}
	public String getSubcategory_name() {
		return subcategory_name;
	}
	public void setSubcategory_name(String subcategory_name) {
		this.subcategory_name = subcategory_name;
	}
	
	
}
