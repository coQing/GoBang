package main;

import java.util.Random;

public class AI {
	//AI进行下一步
	static void next(GamePanel gamePanel){
		luoziRandom(gamePanel);
	}
	//判断五子连珠
	static boolean has5(Pointer pointer1,GamePanel gamePanel){
		
		return false;
	}

	//随机落子
	static boolean luoziRandom(GamePanel gamePanel){

		Pointer pointer = getRandomPointer(gamePanel);

		luozi(pointer, gamePanel,1);

		return true;
	}

	//获取随机下的棋子
	static Pointer getRandomPointer(GamePanel gamePanel){
		Random random = new Random();
		int i = random.nextInt(gamePanel.ROWS);
		int j = random.nextInt(gamePanel.COLS);
		//取得随机到的格子
		Pointer pointer = gamePanel.points[i][j];
		if(pointer.getQizi()!=0){//如果当前格子已经下了棋子，则递归重新取
			pointer = getRandomPointer(gamePanel);
		}
		return pointer;
	}

	//AI落子操作
	static void luozi(Pointer pointer,GamePanel gamePanel,int type){
		if(pointer.getQizi()==0){//如果没有棋子，则落子
			Qizi qizi = new Qizi(pointer.getX(), pointer.getY(), type, gamePanel);
			qizi.setLast(true);
			pointer.setQizi(type);
			gamePanel.qizis.add(qizi);

			//重绘画布
			gamePanel.repaint();

			//判断电脑有没有五子连珠的情况
			if(AI.has5(pointer, gamePanel)){
				gamePanel.gameOver();
			}
		}
	}


}
