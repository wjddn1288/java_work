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
	TopCategoryDAO topCategoryDAO; // has a 관계
	SubCategoryDAO subCategoryDAO;
	public ProductDAO productDAO;
	
	DBManager dbManager = DBManager.getInstance();

	// 서쪽영역
	JPanel p_west;
	JComboBox<String> box_top; // 상위 카테고리
	JComboBox<String> box_sub; // 하위 카테고리
	JTextField t_name; // 상품명
	JTextField t_brand; // 브랜드명
	JTextField t_price; // 가격
	JPanel preview; // 이미지 미리보기 영역 (145,145)
	JTextField t_url; // 이미지의 웹 주소
	JButton bt_preview; // 이미지 미리보기 버튼
	JButton bt_regist; // 등록버튼

	// 센터영역
	JPanel p_center; // 북쪽과 센터로 구분되어질 패널
	JPanel p_search; // 검색기능이 올려질 패널
	JComboBox<String> box_category; // 검색 구분
	JTextField t_keyword; // 검색어
	JButton bt_search; // 검색버튼
	ProductModel model; //jtable이 
	JTable table;
	JScrollPane scroll;

	// 동쪽영역
	JPanel p_east;
	JComboBox<String> box_top2; // 상위 카테고리
	JComboBox<String> box_sub2; // 하위 카테고리
	JTextField t_name2; // 상품명
	JTextField t_brand2; // 브랜드명
	JTextField t_price2; // 가격
	JPanel preview2; // 이미지 미리보기 영역
	JTextField t_url2; // 이미지의 웹 주소
	JButton bt_preview2; // 이미지 미리보기 버튼
	JButton bt_edit; // 수정 버튼
	JButton bt_del; // 삭제 버튼

	// 하위카테고리 선택시 담아놓을 pk(subcategory_idx)
	List<Integer> subIdxList = new ArrayList();
	String dir = "C://java_workspace2//data//shop//product/";
	String filename; // 서쪽영역에서 미리보기될 이미지명
	Image image; // 서쪽영역에서 미리보기될 이미지

	public AdminMain() {
		topCategoryDAO = new TopCategoryDAO();
		subCategoryDAO = new SubCategoryDAO();
		productDAO = new ProductDAO();

		// 서쪽영역
		p_west = new JPanel();
		box_top = new JComboBox<String>();
		box_sub = new JComboBox<String>();
		t_name = new JTextField();
		t_brand = new JTextField();
		t_price = new JTextField("0"); // 가격 실수방지 차원
		preview = new JPanel() {
			protected void paintComponent(Graphics g) {
				Graphics2D g2 = (Graphics2D) g;

				g2.drawImage(image, 0, 0, 140, 140, AdminMain.this);
			}
		};
		t_url = new JTextField();
		bt_preview = new JButton("가져오기");
		bt_regist = new JButton("등록");

		p_west.setPreferredSize(new Dimension(150, 500));
		preview.setPreferredSize(new Dimension(140, 140));
		preview.setBackground(Color.BLACK);
		Dimension d = new Dimension(140, 25);
		box_top.setPreferredSize(d);
		box_sub.setPreferredSize(d);
		t_name.setPreferredSize(d);
		t_brand.setPreferredSize(d);
		t_price.setPreferredSize(d);
		t_url.setPreferredSize(d);

		p_west.add(box_top);
		p_west.add(box_sub);
		p_west.add(t_name);
		p_west.add(t_brand);
		p_west.add(t_price);
		p_west.add(preview);
		p_west.add(t_url);
		p_west.add(bt_preview);
		p_west.add(bt_regist);

		// 센터영역
		p_center = new JPanel();
		p_search = new JPanel();
		box_category = new JComboBox<String>();
		t_keyword = new JTextField(25);
		bt_search = new JButton("검색");
		
		table = new JTable(model=new ProductModel(this));
		scroll = new JScrollPane(table);

		p_search.add(box_category);
		p_search.add(t_keyword);
		p_search.add(bt_search);

		p_center.setLayout(new BorderLayout());
		p_center.add(p_search, BorderLayout.NORTH);
		p_center.add(scroll);

		box_category.setPreferredSize(d);

		// 동쪽영역
		p_east = new JPanel();
		box_top2 = new JComboBox<String>();
		box_sub2 = new JComboBox<String>();
		t_name2 = new JTextField();
		t_brand2 = new JTextField();
		t_price2 = new JTextField();
		preview2 = new JPanel();
		t_url2 = new JTextField();
		bt_preview2 = new JButton("가져오기");
		bt_edit = new JButton("수정");
		bt_del = new JButton("삭제");

		preview2.setPreferredSize(new Dimension(140, 140));
		preview2.setBackground(Color.BLACK);
		Dimension d2 = new Dimension(140, 25);
		box_top2.setPreferredSize(d2);
		box_sub2.setPreferredSize(d2);
		t_name2.setPreferredSize(d2);
		t_brand2.setPreferredSize(d2);
		t_price2.setPreferredSize(d2);
		t_url2.setPreferredSize(d2);

		p_east.add(box_top2);
		p_east.add(box_sub2);
		p_east.add(t_name2);
		p_east.add(t_brand2);
		p_east.add(t_price2);
		p_east.add(preview2);
		p_east.add(t_url2);
		p_east.add(bt_preview2);
		p_east.add(bt_edit);
		p_east.add(bt_del);

		p_east.setPreferredSize(new Dimension(150, 500));

		add(p_west, BorderLayout.WEST);
		add(p_center);
		add(p_east, BorderLayout.EAST);

		getTopList();

		setSize(900, 500);
		setVisible(true);
		// setDefaultCloseOperation(EXIT_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				dbManager.release(dbManager.getConnection());
				System.exit(0);
			}
		});

		box_top.addItemListener(new ItemListener() {

			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					// System.out.println(e.getItem());
					int topCategory_idx = topCategoryDAO.getTopCategoryIdx((String) e.getItem());
					// System.out.println("topCategory_idx : "+topCategory_idx);

					// 하위 카테고리 출력
					getSupList(topCategory_idx);
				}
			}
		});

		// 버튼들과 리스너 연결
		bt_regist.addActionListener(this);
		bt_preview.addActionListener(this);
		bt_search.addActionListener(this);
		bt_preview2.addActionListener(this);
		bt_edit.addActionListener(this);
		bt_del.addActionListener(this);

	}

	// 상위 카테고리 가져오기
	public void getTopList() {
		List<TopCategory> topList = topCategoryDAO.selectAll();

		box_top.addItem("카테고리 선택 ▼");
		for (TopCategory topCategory : topList) {
			box_top.addItem(topCategory.getTopcategory_name());
		}
	}

	// 하위 카테고리 가져오기
	public void getSupList(int topCategory_idx) {
		// 하이-> 청바지,반바지, 면바지,
		List<SubCategory> subList = subCategoryDAO.selectByTopCategory(topCategory_idx);
		// System.out.println("subList.size() : "+subList.size());

		// 기존 아이템 싹 지우기
		box_sub.removeAllItems();
		subIdxList.removeAll(subIdxList);

		box_sub.addItem("하위 카테고리▼");
		// 해당 index에서 요소로 들어있는 DTO꺼내기!
		for (int i = 0; i < subList.size(); i++) {
			SubCategory subCategory = subList.get(i);
			box_sub.addItem(subCategory.getSubcategory_name());
			// 한글로된 아이템 이름만 출력하지 말고 해당 pk도 보관하자!
			subIdxList.add(subCategory.getSubcategory_idx());
		}
		System.out.println("보관된 하위카테고리 idx 수는 " + subIdxList);
	}

	public boolean downLoad() {
		// 로컬에 있는 이미지가 아닌 웹의 url상의 이미지를
		// 로컬로 수집한 후 보유하잦
		// finally에서 닫을 예정이므로, try문 밖으로 빼놓자!
		InputStream is = null;
		FileOutputStream fos = null;
		boolean flag = false;
		
		try {
			URL url = new URL(t_url.getText());
			System.out.println(url);
			is = url.openStream();
			// 파일 출력스트림이 생성할 빈 파일의 이름을 결정짓자
			String ext = StringUtil.getExtend(url.toString());
			System.out.println(ext);

			filename = StringUtil.createFilename(url.toString());
			fos = new FileOutputStream(dir + filename);

			int data = -1;

			while (true) {
				data = is.read(); // 1byte 읽기
				if (data == -1)
					break;
				// 내뱉기
				fos.write(data);
			}
			JOptionPane.showMessageDialog(this, "수집완료");
			flag = true;

		} catch (MalformedURLException e) {
			e.printStackTrace();
			flag = false;
		} catch (IOException e) {
			e.printStackTrace();
			flag = false;
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return flag;
	}

	public void preview() {
		File file = new File(dir + filename);
		try {
			image = ImageIO.read(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		preview.repaint(); // 서쪽패널 다시 그리기!!
	}
	//모든상품 레코드 가져오기(단 ㅏ위카테고리와 조인된 상태로..)
	public void getProductList() {
		List productList=productDAO.
	}
	//상품등록
    public void regist() {
        //ProductDAO에게 insert 시키기

        //유저가 지금 선택한 하위 카테고리 콤보박스의 index
        int index=box_sub.getSelectedIndex();

        int subcategory_idx = subIdxList.get(index-1);
        String product_name=t_name.getText();
        String brand=t_brand.getText();
        int price=Integer.parseInt(t_price.getText());

        Product product=new Product(); //empty 텅빈 상태
        SubCategory subcategory=new SubCategory();
        product.setSubcategory(subcategory);

        subcategory.setSubcategory_idx(subcategory_idx);
        product.setProduct_name(product_name);
        product.setBrand(brand);
        product.setPrice(price);
        product.setFilename(filename);

        int result=productDAO.insert(product);    //productDAO.insert(채워진 DTO);
        if(result>0) {
            JOptionPane.showMessageDialog(this, "등록성공");
        }

    }

	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();

		// equals() : 내용비교
		if (obj.equals(bt_regist)) {
			regist();
			return;
		}

		if (obj.equals(bt_preview)) {

			if (downLoad()) {
				preview();
			} else {
				JOptionPane.showMessageDialog(this, "수집 실패");
			}
			return;
		}

		if (obj.equals(bt_search)) {
			return;
		}

		if (obj.equals(bt_preview2)) {
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