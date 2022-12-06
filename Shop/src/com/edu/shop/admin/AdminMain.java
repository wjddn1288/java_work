package com.edu.shop.admin;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import com.edu.shop.domain.Product;
import com.edu.shop.domain.SubCategory;
import com.edu.shop.domain.TopCategory;
import com.edu.shop.model.repository.ProductDAO;
import com.edu.shop.model.repository.SubCategoryDAO;
import com.edu.shop.model.repository.TopCategoryDAO;
import com.edu.shop.model.table.ProductModel;
import com.edu.shop.util.DBManager;

import util.StringUtil;

public class AdminMain extends JFrame implements ActionListener {

	TopCategoryDAO topCategoryDAO; //has a 관계
	SubCategoryDAO subCategoryDAO;
	public ProductDAO productDAO;
	DBManager dbManager = DBManager.getInstanse();

	//서쪽 영역
	JPanel p_west;
	JComboBox<String> box_top; //상위 카테고리
	JComboBox<String> box_sub; //하위 카테고리
	JTextField t_name; //상품명
	JTextField t_brand; //브랜드명
	JTextField t_price; //가격
	JPanel preview;//  이미지 미리보기 영역 (145,145)
	JTextField t_url;  // 이미지의 웹 주소
	JButton bt_prieview;// 이미지 미리보기 버튼
	JButton bt_regist;// 등록버튼

	// 센터영역
	JPanel p_center;// 북쪽과 센터로 구분되어질 패널
	JPanel p_search;// 검색기능이 올려질 패널
	JComboBox<String> box_category;// 검색구분
	JTextField t_keyword;// 검색어
	ProductModel model;
	JButton bt_search;// 검색 버튼
	JTable table;
	JScrollPane scroll;

	JPanel p_east;
	JComboBox<String> box_top2;
	JComboBox<String> box_sub2;
	JTextField t_name2;
	JTextField t_brand2;
	JTextField t_price2;
	JPanel preview2;// 145 145
	JTextField t_url2;
	JButton bt_prieview2;
	JButton bt_edit;
	JButton bt_del;

	ArrayList<Integer> subIdxList = new ArrayList();

	String filePath = "C:/shin/java_workspace2/data/shop/";
	String fileName;
	Image image;

	public AdminMain() {

		topCategoryDAO = new TopCategoryDAO();
		subCategoryDAO = new SubCategoryDAO();
		productDAO = new ProductDAO();
		p_west = new JPanel();
		box_top = new JComboBox();
		box_sub = new JComboBox();
		t_name = new JTextField(12);
		t_brand = new JTextField(12);
		t_price = new JTextField("0", 12);
		preview = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				Graphics2D g2 = (Graphics2D) g;
				g2.fillRect(0, 0, 145, 145);
				g2.drawImage(image, 0, 0, 145, 145, this);
			}
		};// 145 145
		t_url = new JTextField(12);
		bt_prieview = new JButton("가져오기");
		bt_regist = new JButton("등록");

		p_center = new JPanel();
		p_search = new JPanel();
		box_category = new JComboBox();
		t_keyword = new JTextField(30);
		bt_search = new JButton("검색");
		table = new JTable(model = new ProductModel(this));
		scroll = new JScrollPane(table);

		p_east = new JPanel();
		box_top2 = new JComboBox();
		box_sub2 = new JComboBox();
		t_name2 = new JTextField(12);
		t_brand2 = new JTextField(12);
		t_price2 = new JTextField(12);
		preview2 = new JPanel();// 145 145
		t_url2 = new JTextField(12);
		bt_prieview2 = new JButton("가져오기");
		bt_edit = new JButton("수정");
		bt_del = new JButton("삭제");

		Dimension b_side = new Dimension(160, 500); // 320
		Dimension b_combo = new Dimension(140, 30);
		p_west.setPreferredSize(b_side);
		p_west.setBackground(Color.YELLOW);
		preview.setPreferredSize(new Dimension(145, 145));
		box_top.setPreferredSize(b_combo);
		box_sub.setPreferredSize(b_combo);

		// p_search.setPreferredSize(new Dimension(580,50));
		// p_center.setPreferredSize(new Dimension(00,500));
		box_category.setPreferredSize(new Dimension(100, 30));
		scroll.setPreferredSize(new Dimension(560, 450));

		p_east.setPreferredSize(b_side);
		p_east.setBackground(Color.YELLOW);
		preview2.setPreferredSize(new Dimension(145, 145));
		box_top2.setPreferredSize(b_combo);
		box_sub2.setPreferredSize(b_combo);

		p_west.add(box_top);
		p_west.add(box_sub);
		p_west.add(t_name);
		p_west.add(t_brand);
		p_west.add(t_price);
		p_west.add(preview);
		p_west.add(t_url);
		p_west.add(bt_prieview);
		p_west.add(bt_regist);

		p_search.add(box_category);
		p_search.add(t_keyword);
		p_search.add(bt_search);

		p_center.add(p_search);
		p_center.add(scroll);

		p_east.add(box_top2);
		p_east.add(box_sub2);
		p_east.add(t_name2);
		p_east.add(t_brand2);
		p_east.add(t_price2);
		p_east.add(preview2);
		p_east.add(t_url2);
		p_east.add(bt_prieview2);
		p_east.add(bt_edit);
		p_east.add(bt_del);

		add(p_west, BorderLayout.WEST);
		add(p_center);
		add(p_east, BorderLayout.EAST);

		getTopList();

		setSize(900, 500);
		setVisible(true);
		// setDefaultCloseOperation(EXIT_ON_CLOSE);

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				dbManager.release(dbManager.getConnection());
				System.exit(0);
			}
		});
		box_top.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					int topCategory_idx = topCategoryDAO.getTopCategoryIdx((String) e.getItem());
					getSubList(topCategory_idx);

				}
			}
		});
		bt_prieview.addActionListener(this);
		bt_regist.addActionListener(this);
		bt_search.addActionListener(this);
		bt_prieview2.addActionListener(this);
		bt_edit.addActionListener(this);
		bt_del.addActionListener(this);
	}

	// 상위카테고리 가져오기
	public void getTopList() {
		List<TopCategory> topList = topCategoryDAO.selectAll();
		box_top.addItem("상위 카테고리선택");
		for (TopCategory t : topList) {
			box_top.addItem(t.getTopcategory_name());
		}
	}

	public void getSubList(int topCategory_idx) {
		List<SubCategory> subList = subCategoryDAO.selectAll(topCategory_idx);
		box_sub.removeAllItems();
		box_sub.addItem("하위 카테고리선택");
		subIdxList.removeAll(subIdxList);
		for (SubCategory s : subList) {
			box_sub.addItem(s.getSubcategory_name());
			subIdxList.add(s.getSubcategory_idx());
		}
	}

	public boolean downLoad() {
		boolean flag = false;
		FileOutputStream fos = null;
		InputStream is = null;
		try {
			URL url = new URL(t_url.getText());
			is = url.openStream();
			fileName = StringUtil.createFileName(url.toString());

			fos = new FileOutputStream(filePath + fileName);
			int data = -1;
			while (true) {
				data = is.read();
				if (data == -1) {
					break;
				}
				fos.write(data);
			}
			flag = true;
		} catch (MalformedURLException e) {
			flag = false;
			e.printStackTrace();
		} catch (IOException e) {
			flag = false;
			e.printStackTrace();
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return flag;
	}

	public void preview() {
		File file = null;
		try {
			file = new File(filePath + fileName);
			image = ImageIO.read(file).getScaledInstance(140, 140, Image.SCALE_SMOOTH);
			preview.repaint();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 모든레코드 상품 레코드 가져오기(단 조인된 상태)
	public void getProductList() {
		List productList = productDAO.selectAll();

	}

	public void regist() {
		Product product = new Product();
		SubCategory subCategory = new SubCategory();
		product.setSubcategory(subCategory);
		int index = box_sub.getSelectedIndex();
		// product.setSubcategory_idx(subIdxList.get(index-1));
		subCategory.setSubcategory_idx(subIdxList.get(index - 1));
		product.setProduct_name(t_name.getText());
		product.setBrand(t_brand.getText());
		product.setPrice(Integer.parseInt(t_price.getText()));
		product.setFilename(fileName);

		int result = 0;

		result = productDAO.insert(product);
		if (result > 0) {
			JOptionPane.showMessageDialog(this, "등록성공");
		} else {
			JOptionPane.showMessageDialog(this, "등록실패");
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if (obj.equals(bt_prieview)) {
			if (downLoad()) {
				preview();
			} else {
				JOptionPane.showMessageDialog(this, "이미지 가져오기 실패");
			}
			return;
		}
		if (obj.equals(bt_regist)) {

			regist();
			return;
		}
		if (obj.equals(bt_search)) {
			return;
		}
		if (obj.equals(bt_edit)) {
			return;
		}
		if (obj.equals(bt_del)) {
			return;
		}
	}

	public static void main(String[] args) {
		new AdminMain();
	}
}
