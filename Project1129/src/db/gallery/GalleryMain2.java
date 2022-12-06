package db.gallery;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class GalleryMain2 extends JFrame{
	//서쪽 영역
	JPanel p_west;
	JTextField t_title;
	JTextField t_writer;
	JTextArea t_content;
	JPanel p_sign; 
	JPanel p_preveiw;
	JButton bt_open;
	JButton bt_regist;
	
	//센터영역
	JTable table;
	JScrollPane scroll;
	
	//동쪽영역
	JPanel p_east;
	JTextField t_title2;
	JTextField t_writer2;
	JTextArea t_content2;
	JPanel p_preveiw2;
	JButton bt_edit;
	JButton bt_del;
	
	public GalleryMain2() {
		//서쪽 영역 생성
		p_west=new JPanel();
		t_title=new JTextField(12);
		t_writer=new JTextField(12);
		t_content=new JTextArea();
		bt_open=new JButton("첨부");
		bt_regist=new JButton("등록");
		
		p_west.setPreferredSize(new Dimension(150,500));
		t_content.setPreferredSize(new Dimension(140,70));
		
		p_west.add(t_title);
		p_west.add(t_writer);
		p_west.add(t_content);
		p_west.add(bt_open);
		p_west.add(bt_regist);
		add(p_west,BorderLayout.WEST);
		
		//센터영역 생성
		table =new JTable();
		scroll=new JScrollPane();
		add(scroll);
		
		//동쪽영역 생성
		p_east=new JPanel();
		t_title2=new JTextField();
		t_writer2=new JTextField();
		t_content2=new JTextArea();
		
		
		
	}
	
	public static void main(String[]args) {
		
	}
}
