package com.edu.shop.model.table;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.edu.shop.admin.AdminMain;
import com.edu.shop.domain.Product;

public class ProductModel extends AbstractTableModel{
	
	String[] columnName = {"상품번호","상위카테고리","하위카테고리","상품명","브랜드명","가격"};
	String[] column = {"product_idx","topcategory_name","subcategory_name","product_name","brand","price"};
	List<Product> productList;
	//ArrayList<> data = new ArrayList();
	
	//ProductDAO productDAO = new ProductDAO();
	AdminMain adminMain;
	
	public ProductModel(AdminMain adminMain) {
		this.adminMain = adminMain;
		getProductList();
	}
	
	public void getProductList() {
		productList = adminMain.productDAO.selectAll();
	}
		
	
	@Override
	public int getRowCount() {
		return productList.size();
	}

	@Override
	public int getColumnCount() {
		return column.length;
	}

	@Override
	public Object getValueAt(int row, int col) {
		Product product=productList.get(row);
		String value = null;
		switch(col) {
			case 0: value = Integer.toString(product.getProduct_idx());break;
			case 1: value = product.getSubcategory().getTopcategory().getTopcategory_name();break;
			case 2: value = product.getSubcategory().getSubcategory_name();break;
			case 3: value = product.getProduct_name();break;
			case 4: value = product.getBrand();break;
			case 5: value = Integer.toString(product.getPrice());break;
		}
		return value;
	}
	
	@Override
	public String getColumnName(int column) {
		return columnName[column];
	}
	
	

}
