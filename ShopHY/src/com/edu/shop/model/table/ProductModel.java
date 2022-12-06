package com.edu.shop.model.table;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.edu.shop.admin.AdminMain;
import com.edu.shop.domain.Product;

public class ProductModel extends AbstractTableModel {
	AdminMain adminMain;

	// 넘겨받는 이유는? 기존의 AdminMain이 보유중인 각종 객체들을 사용하기 위함...
	// (ProductDAO,등등... 필요한 것이 많다..)
	List<Product> productList;

	public ProductModel(AdminMain adminMain) {
		this.adminMain = adminMain;
		getProductList();
	}

	public void getProductList() {
		productList = adminMain.productDAO.selectAll();
	}

	public int getRowCount() {
		return productList.size();
	}

	public int getColumnCount() {
		return 7;
	}

	public Object getValueAt(int row, int col) {
		// 우리가 보유한 List는 현재 일차원이므로, row,col형태로 데이터에 접근할 수 없다..
		// 하지만, 객체지향적인 직관성을 버리지 말고 개발에 적용해보자!! 따라서 col은 버린다!!
		Product product = productList.get(row);

		// col변수는 Table에서 몇번째 호수에 넣어야할지를 결정짓는다
		String value = null;
		if (col == 0) {
			value = Integer.toString(product.getSubcategory().getSubcategory_idx());
		}else if (col == 1) { // 몇번째 호수에 넣어야 될지 결정!!
			value = product.getSubcategory().getSubcategory_name();
		} else if (col == 2) {
			value = product.getProduct_name();
		} else if (col == 3) {
			value = product.getBrand();
		} else if (col == 4) {
			value = Integer.toString(product.getProduct_idx());
		} else if (col == 5) {
			value = Integer.toString(product.getPrice());
		} else if (col == 6) {
			value = product.getFilename();
		}

		return value;
	}

}
