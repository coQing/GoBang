package main;


import javax.swing.*;


/*
 * 画布类
 */
public class GamePanel extends JPanel{
	private static final long serialVersionUID = 1L;
	GamePanel gamePanel=this;
	private JFrame mainFrame=null;


	//构造里面初始化相关参数
	public GamePanel(JFrame frame){
		this.setLayout(null);
		this.setOpaque(false);
		mainFrame = frame;

		mainFrame.requestFocus();
		mainFrame.setVisible(true);
	}
	


}
