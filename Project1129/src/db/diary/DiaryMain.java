package db.diary;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class DiaryMain extends JFrame implements ActionListener {
	DBManager dbManager = DBManager.getInstance();
	DiaryDAO diaryDAO = new DiaryDAO(); // 버튼 누를때마다 메서드 호출하기!

	// 서쪽영역
	JPanel p_west;
	JComboBox<String> box_yy; // 년
	JComboBox<String> box_mm; // 월
	JComboBox<String> box_dd; // 일
	JTextArea area;
	JScrollPane scroll;
	JComboBox<String> box_icon;// 다이어리에 사용할 아이콘
	JButton bt_regist;// 등록및 수정
	JButton bt_del; // 삭제

	// 센터
	JPanel p_center;// 플로우를 적용하기 위한 센터의 최상위 컨테이너
	JPanel p_title;// 현재 연,월 및 이전, 다음 버튼 영역
	JPanel p_dayOfWeek; // 요일 셀이 올 곳
	JPanel p_dayOfMonth; // 날짜 셀이 붙여질 곳
	JButton bt_prev; // 이전 버튼
	JLabel la_title;// 현재 년월
	JButton bt_next; // 다음 버튼

	// 요일 처리
	DayCell[] dayCells = new DayCell[7];
	String[] dayTitle = { "Sun", "Mon", "Tue", "Wed", "Thur", "Fri", "Sat" };

	// 날짜
	DateCell[][] dateCells = new DateCell[6][7];

	// 현재 사용자가 보게 될 날짜 정보!!
	Calendar currentObj = Calendar.getInstance(); // 현재날짜를 가지고 있음

	public DiaryMain() {
		// 서쪽 영역
		p_west = new JPanel();
		box_yy = new JComboBox<String>();
		box_mm = new JComboBox<String>();
		box_dd = new JComboBox<String>();
		area = new JTextArea();
		scroll = new JScrollPane(area);
		box_icon = new JComboBox<String>();
		bt_regist = new JButton("등록");
		bt_del = new JButton("삭제");

		// 센터영역
		p_center = new JPanel();
		p_title = new JPanel();
		p_dayOfWeek = new JPanel();
		p_dayOfMonth = new JPanel();
		bt_prev = new JButton("이전");
		la_title = new JLabel("2022년 12월");
		bt_next = new JButton("다음");

		p_west.add(box_yy);
		p_west.add(box_mm);
		p_west.add(box_dd);
		p_west.add(scroll);
		p_west.add(box_icon);
		p_west.add(bt_regist);
		p_west.add(bt_del);

		p_west.setPreferredSize(new Dimension(150, 500));
		Dimension d = new Dimension(120, 30);
		box_yy.setPreferredSize(d);
		box_mm.setPreferredSize(d);
		box_dd.setPreferredSize(d);
		box_icon.setPreferredSize(d);
		area.setPreferredSize(new Dimension(120, 100));
		bt_regist.setPreferredSize(new Dimension(60, 30));
		bt_del.setPreferredSize(new Dimension(60, 30));

		p_center.add(p_title);
		p_center.add(p_dayOfWeek);
		p_center.add(p_dayOfMonth);

		p_title.add(bt_prev);
		p_title.add(la_title);
		p_title.add(bt_next);

		p_center.setPreferredSize(new Dimension(750, 500));
		p_title.setPreferredSize(new Dimension(750, 50));
		Font font = new Font("NANUM", Font.BOLD, 18);
		bt_prev.setFont(font);
		la_title.setFont(font);
		bt_next.setFont(font);

		p_dayOfWeek.setPreferredSize(new Dimension(750, 50));
		p_dayOfWeek.setBackground(Color.PINK);
		p_dayOfWeek.setLayout(new GridLayout(1, 7));

		p_dayOfMonth.setPreferredSize(new Dimension(750, 400));
		p_dayOfMonth.setBackground(Color.GREEN);
		p_dayOfMonth.setLayout(new GridLayout(6, 7));

		add(p_west, new BorderLayout().WEST);
		add(p_center);

		createDayOfWeek(); // 요일 출력
		createDayOfMonth();// 날짜 출력

		calculate(); // 날짜처리 메서드

		setSize(930, 560);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		// setResizable(false); //윈도우 변경 불가

		// 이전월
		bt_prev.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 현재 날짜 객체정보를 먼저 얻자!!
				int mm = currentObj.get(Calendar.MONTH);
				currentObj.set(Calendar.MONTH, mm - 1);
				System.out.println(currentObj.get(Calendar.MONTH));
				calculate();
			}
		});

		// 다음월
		bt_next.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 현재 날짜 객체정보를 먼저 얻자!!
				int mm = currentObj.get(Calendar.MONTH);
				currentObj.set(Calendar.MONTH, mm + 1);
				// System.out.println(currentObj.get(Calendar.MONTH));
				calculate();
			}
		});
		bt_regist.addActionListener(this);
	}

	// 요일 출력
	public void createDayOfWeek() {
		// 7개를 생성하여 패널에 부착
		for (int i = 0; i < dayCells.length; i++) {
			dayCells[i] = new DayCell(dayTitle[i], "", 15, 20, 20);
			p_dayOfWeek.add(dayCells[i]); // 화면에 부착
		}
	}

	// 날짜 출력
	public void createDayOfMonth() {
		for (int i = 0; i < dateCells.length; i++) {// 층수
			for (int a = 0; a < dateCells[i].length; a++) {// 호수
				dateCells[i][a] = new DateCell(this, "", "", 10, 30, 10);
				// 패널에 부착
				p_dayOfMonth.add(dateCells[i][a]);
			}
		}
	}

	// 제목을 출력한다!
	public void printTitle() {
		int yy = currentObj.get(Calendar.YEAR);
		int mm = currentObj.get(Calendar.MONTH);

		String str = yy + "년" + (mm + 1) + "월";

		la_title.setText(str);
	}

	/*
	 * 달력을 구현하기 위한 2가지 정보 1) 해당 월이 무슨요일부터 시작하는가? -날짜 객체 생성하여, 조작(1일로 보낸다) -1일인 상태에서,
	 * 해당 날짜객체에게 요일을 물어본다 ex) 3요일 --> 화요일
	 * 
	 * 2) 해당 월이 몇일까지 인가? -날짜 객체 생성하여 조작 1) 다음달로 넘겨 0일을 전달 마지막날로 바꾼다.
	 */

	public int getStartDayOfWeek() {
		Calendar cal = Calendar.getInstance();
		int yy = currentObj.get(Calendar.YEAR); // 년도
		int mm = currentObj.get(Calendar.MONTH); // 월
		cal.set(yy, mm, 1); // 1일로 조작하자!!
		int day = cal.get(Calendar.DAY_OF_WEEK);// 요일 추출!!
		return day;
	}

	public int getLastDayOfMonth() {
		Calendar cal = Calendar.getInstance();
		int yy = currentObj.get(Calendar.YEAR);
		int mm = currentObj.get(Calendar.MONTH);

		cal.set(yy, mm + 1, 0); // 조작 완료!!
		int date = cal.get(Calendar.DATE);
		return date;
	}

	// 날짜 출력
	public void printDate() { // 새로 출력이 아닌 글씨만 바꾼다
		// 모든 셀에 접근하여, 알맞는 문자 출력
		int n = 0; // 시작 시점 지표
		int d = 0; // 날짜 출력 변수
		for (int i = 0; i < dateCells.length; i++) {
			for (int a = 0; a < dateCells[i].length; a++) {
				DateCell cell = dateCells[i][a];
				n++;

				if (n >= getStartDayOfWeek() && d < getLastDayOfMonth()) {
					d++;// 꾹 참았다가, n이 시작요일이상일때부터 ++증가예정
					cell.title = Integer.toString(d);
				} else {
					cell.title = "";
				}
			}
		}
		p_dayOfMonth.repaint();
	}

	public void calculate() {
		printTitle(); // 제목출력
		// getStartDayOfWeek();
		// getLastDayOfMonth();
		printDate(); // 날짜 번호 출력

		printLog();// 기록된 다이어리 출력
	}

	public void printLog() {
		int yy = currentObj.get(Calendar.YEAR);
		int mm = currentObj.get(Calendar.MONTH);

		List<Diary> diaryList = diaryDAO.selectAll(yy, mm);
		System.out.println("등록된 다이러리 수는" + diaryList.size());

		// 현재 월의 모든 날짜를 대상으로 반복문 수행 1~31일까지 비교할려고!
		for (int i = 0; i < dateCells.length; i++) {
			for (int a = 0; a < dateCells[i].length; a++) {
				if (dateCells[i][a].title.equals("") == false) { // 숫자가 아닌 ""과 같은 문자열은 정수화 시킬수 없으므로 조건문으로 걸러내자!!
					int date = Integer.parseInt(dateCells[i][a].title); // 날짜 숫자 추출하기

					// 불러온 데이터만큼...
					for (int x = 0; x < diaryList.size(); x++) {
						Diary Obj = diaryList.get(x); // 다이어리 한 건 추출!
						if (date == Obj.getDd()) { // 다이어리에 기록한 그 녀석
							// 해당 셀에 데이터 표현!!
							dateCells[i][a].color = Color.CYAN;
							dateCells[i][a].content = Obj.getContent();
						}
					}

				}
			}
		}
		p_dayOfMonth.repaint();
	}

	// 데이터베이스와 관련된 쿼리로직을 중복 정의하지 않기 위해
	// 즉 코드 재사용을 위해, 쿼리만을 전담하는 객체를 별도로 정의하여
	// 유지보수성을 높이자!! 이러한 목적으로 정의되는 객체를 어플리케이션 설계
	// 분야에서는 DAO(Data Access Object)라 한다.
	// What to make? How to make? 우린 how to make!
	public void regist() {
		// 이 안에 쿼리문을 작성한다면 유지보수하기 힘듬 !! 1회성임
		// 테이블 갯수만큼 DAO를 만들어줘야 된다.
		// Diary DTO생성!!!(Empty 상태 =텅빈 상태)
		Diary d = new Diary();
		System.out.println(" 호출 전 d" + d);

		// 객체가 기본자료형으로 바뀜 ==UnBoxing(객체형이 기본자료형으로 변경되는 것)
		String yy = (String) box_yy.getSelectedItem();
		String mm = (String) box_mm.getSelectedItem();
		String dd = (String) box_dd.getSelectedItem();

		String content = area.getText();
		String icon = (String) box_icon.getSelectedItem();

		// 레코드 한건 채워넣기!
		d.setYy(Integer.parseInt(yy));
		d.setMm(Integer.parseInt(mm));
		d.setDd(Integer.parseInt(dd));
		d.setContent(content);
		d.setIcon(icon);

		int result = diaryDAO.insert(d);
		if (result > 0) { // 성공이라면
			JOptionPane.showMessageDialog(this, "등록성공");
			printLog();
		}
	}

	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if (obj == bt_regist) {
			regist();
		} else if (obj == bt_del) {

		}
	}

	// 셀을 선택시, 콤보박스의 값 변경
	public void setDateInfo(String title) {
		// 콤보박스에 아이템을 누적하지 말고, 싹 지운상태에서 추가
		box_yy.removeAllItems();
		box_mm.removeAllItems();
		box_dd.removeAllItems();

		int yy = currentObj.get(Calendar.YEAR);
		int mm = currentObj.get(Calendar.MONTH);

		box_yy.addItem(Integer.toString(yy)); // 객체화 된 걸 감쌓았다 int-->Object(boxing)
		box_mm.addItem(Integer.toString(mm));
		box_dd.addItem(title);
	}

	public static void main(String[] args) {
		new DiaryMain();
	}

}