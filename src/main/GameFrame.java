package main;


import javax.swing.*;
import java.awt.*;

/*
 * 游戏窗体类
 */
public class GameFrame extends JFrame {
	
	public GameFrame() {
		setTitle("简单的五子棋");//设置标题
		setSize(620, 670);//设定尺寸
		getContentPane().setBackground(new Color(150,178,220));//添加背景色
		setLayout(new BorderLayout());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//点击关闭按钮是关闭程序
        setLocationRelativeTo(null);   //设置居中
    	setResizable(false); //不允许修改界面大小
	}
}
