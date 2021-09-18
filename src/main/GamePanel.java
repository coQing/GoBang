package main;


import common.ImageValue;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;


/*
 * 画布类
 */
public class GamePanel extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;
	GamePanel gamePanel=this;
	private JFrame mainFrame=null;
	JMenuBar jmb=null;
	public String gameFlag="";

	public static final int ROWS=15;
	public static final int COLS=15;

	public Pointer points[][] =  new Pointer[ROWS][COLS];


	List qizis = new ArrayList();

	//构造里面初始化相关参数
	public GamePanel(JFrame frame){
		this.setLayout(null);
		this.setOpaque(false);
		mainFrame = frame;

		//创建按钮建
		initMenu();

		ImageValue.init();

		init();

		//添加鼠标监听
		createMouseListener();

		mainFrame.requestFocus();
		mainFrame.setVisible(true);
	}

	private void  initMenu(){
		// 创建菜单及菜单选项
		jmb = new JMenuBar();
		JMenu jm1 = new JMenu("主菜单");
		jm1.setFont(new Font("思源宋体", Font.BOLD, 18));// 设置菜单显示的字体
		JMenu jm2 = new JMenu("帮助");
		jm2.setFont(new Font("思源宋体", Font.BOLD, 18));// 设置菜单显示的字体

		JMenuItem jmi1 = new JMenuItem("开始新游戏");
		JMenuItem jmi2 = new JMenuItem("退出");
		jmi1.setFont(new Font("思源宋体", Font.BOLD, 18));
		jmi2.setFont(new Font("思源宋体", Font.BOLD, 18));

		JMenuItem jmi3 = new JMenuItem("操作说明");
		jmi3.setFont(new Font("思源宋体", Font.BOLD, 18));
		JMenuItem jmi4 = new JMenuItem("成功/失败判定");
		jmi4.setFont(new Font("思源宋体", Font.BOLD, 18));

		jm1.add(jmi1);
		jm1.add(jmi2);

		jm2.add(jmi3);
		jm2.add(jmi4);

		jmb.add(jm1);
		jmb.add(jm2);
		mainFrame.setJMenuBar(jmb);// 菜单Bar放到JFrame上
		jmi1.addActionListener(this);
		jmi1.setActionCommand("Restart");
		jmi2.addActionListener(this);
		jmi2.setActionCommand("Exit");

		jmi3.addActionListener(this);
		jmi3.setActionCommand("help");
		jmi4.addActionListener(this);
		jmi4.setActionCommand("lost");
	}

	//重新开始
	public void restart() {
		//游戏开始标记
		gameFlag="start";
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		System.out.println(command);
		UIManager.put("OptionPane.buttonFont", new FontUIResource(new Font("思源宋体", Font.ITALIC, 18)));
		UIManager.put("OptionPane.messageFont", new FontUIResource(new Font("思源宋体", Font.ITALIC, 18)));
		if ("Exit".equals(command)) {
			Object[] options = { "确定", "取消" };
			int response = JOptionPane.showOptionDialog(this, "您确认要退出吗", "",
					JOptionPane.YES_OPTION, JOptionPane.QUESTION_MESSAGE, null,
					options, options[0]);
			if (response == 0) {
				System.exit(0);
			}
		}else if("Restart".equals(command)){
			if(!"end".equals(gamePanel.gameFlag)){
				JOptionPane.showMessageDialog(null, "正在游戏中无法重新开始！",
						"提示！", JOptionPane.INFORMATION_MESSAGE);
			}else{
				if(gamePanel!=null) {
					gamePanel.restart();
				}
			}
		}else if("help".equals(command)){
			JOptionPane.showMessageDialog(null, "鼠标在指示器位置点下，则落子！",
					"提示！", JOptionPane.INFORMATION_MESSAGE);
		}else if("lost".equals(command)){
			JOptionPane.showMessageDialog(null, "五子连珠方获得胜利！",
					"提示！", JOptionPane.INFORMATION_MESSAGE);
		}

	}

	@Override
	public void paint(java.awt.Graphics g) {
		super.paint(g);

		//绘制网格
		drawGrid(g);

		//绘制5个黑点
		draw5Point(g);

		//绘制棋子
		Qizi qizi=null;
		for (int i = 0; i < qizis.size(); i++) {
			qizi = (Qizi)qizis.get(i);
			qizi.draw(g);
		}

		//绘制指示器
		Pointer pointer = null;
		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLS; j++) {
				pointer = points[i][j] ;
				if(pointer!=null){
					pointer.draw(g);
				}
			}
		}





	}

	//绘制网格
	private void drawGrid(Graphics g) {
		Graphics2D g_2d=(Graphics2D)g;
		int start=26;
		int x1=start;
		int y1;
		int x2=586;
		int y2;
		for (int i = 0; i < ROWS; i++) {
			y1 = start + 40*i;
			y2 = y1;
			g_2d.drawLine(x1, y1, x2, y2);
		}

		y1=start;
		y2=586;
		for (int i = 0; i < COLS; i++) {
			x1 = start + 40*i;
			x2 = x1;
			g_2d.drawLine(x1, y1, x2, y2);
		}
	}

	//绘制5个黑点
	private void draw5Point(Graphics g) {
		//第1个点
		g.fillArc(142, 142, 8, 8, 0, 500);
		//第2个点
		g.fillArc(462, 142, 8, 8, 0, 360);

		//第3个点
		g.fillArc(142, 462, 8, 8, 0, 360);
		//第4个点
		g.fillArc(462, 462, 8, 8, 0, 360);

		//中心点
		g.fillArc(302, 302, 8, 8, 0, 360);
	}

	//初始化相关对象
	private void init() {
		createArr();
		//游戏开始标记
		gameFlag="start";
	}


	//创建二维数组
	private void createArr() {
		int x=0,y=0;
		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLS; j++) {
				y = 26 + 40*i;
				x = 26 + 40*j;
				Pointer pointer = new Pointer(x, y, i,j,new Color(255,0,0), this);
				points[i][j] = pointer;
			}
		}
	}


	//鼠标事件的创建
	private void createMouseListener() {
		MouseAdapter mouseAdapter = new MouseAdapter() {

			@Override
			public void mouseMoved(MouseEvent e) {
				//鼠标移入对应的格子后显示指针
				if(!"start".equals(gameFlag)) return ;

				int x = e.getX();
				int y = e.getY();
				Pointer pointer;
				for (int i = 0; i <ROWS; i++) {
					for (int j = 0; j < COLS; j++) {
						pointer = points[i][j];
						if(pointer==null)continue;
						//被选中，且没有棋子，则显示指示器
						if(pointer.isPoint(x, y) && pointer.getQizi()==0){
							pointer.setShow(true);
						}else{
							pointer.setShow(false);
						}
					}
				}
				//重绘画布
				repaint();
			}


			@Override
			public void mouseClicked(MouseEvent e) {
				//在合适的位置点击则进行落子操作
				if(!"start".equals(gameFlag)) return ;

				int x = e.getX();
				int y = e.getY();

				Pointer pointer;
				for (int i = 0; i <ROWS; i++) {
					for (int j = 0; j < COLS; j++) {
						pointer = points[i][j];
						if(pointer==null)continue;
						//被点击，且没有棋子，则可以落子
						if(pointer.isPoint(x, y) && pointer.getQizi()==0){
							Qizi qizi = new Qizi(pointer.getX(), pointer.getY(), 2, gamePanel);
							pointer.setQizi(2);
							qizis.add(qizi);
							//重绘画布
							repaint();

							return ;
						}
					}
				}
			}




		};
		addMouseMotionListener(mouseAdapter);
		addMouseListener(mouseAdapter);
	}

}
