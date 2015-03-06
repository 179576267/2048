package com.wzf.game2048;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridLayout;
import android.widget.Toast;

public class GameLayout extends GridLayout {
	private Card[][] cardMap = new Card[4][4];
	private List<Point> emptyPoints = new ArrayList<Point>();
	private ScoreAdd scoreAdd;

	public void setScoreAdd(ScoreAdd scoreAdd) {
		this.scoreAdd = scoreAdd;
	}

	public GameLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initView();
	}

	public GameLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView();
	}

	public GameLayout(Context context) {
		super(context);
		initView();
	}

	private void initView() {
		setBackgroundColor(0xffBBADA0);
		setColumnCount(4);
		setOnTouchListener(new OnTouchListener() {
			float startX, startY, offSetX, offSetY;

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					startX = event.getX();
					startY = event.getY();
					break;

				case MotionEvent.ACTION_UP:
					offSetX = event.getX() - startX;
					offSetY = event.getY() - startY;
					judgeGestures(offSetX, offSetY);
					break;
				}
				return true;
			}
		});
	}

	/**
	 * 横竖屏切换，初次创建时会加载一次
	 */
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		int length = (Math.min(w, h) - 10) / 4;
		addCard(length);
		startGame();
	}

	private void addCard(int length) {
		Card card;
		for (int y = 0; y < 4; y++) {
			for (int x = 0; x < 4; x++) {
				card = new Card(getContext());
				this.addView(card, length, length);
				cardMap[x][y] = card;
			}
		}
	}

	private void judgeGestures(float offSetX, float offSetY) {
		if (Math.abs(offSetX) > Math.abs(offSetY)) {
			if (offSetX < -10) {
				// Toast.makeText(getContext(), "left", 0).show();
				actoinLeft();
			} else if (offSetX > 10) {
				// Toast.makeText(getContext(), "right", 0).show();
				actoinRight();
			}
		} else {
			if (offSetY < -10) {
				// Toast.makeText(getContext(), "up", 0).show();
				actoinUp();
			} else if (offSetY > 10) {
				// Toast.makeText(getContext(), "down", 0).show();
				actoinDown();
			}
		}
		
		checkGameOver();

	}

	public void startGame() {
		if (scoreAdd != null) {
			scoreAdd.score(0,true);
		}
		
		for (int y = 0; y < 4; y++) {
			for (int x = 0; x < 4; x++) {
				cardMap[x][y].setNum(0);
			}
		}

		addRandomNum();
	}

	private void addRandomNum() {
		emptyPoints.clear();
		for (int y = 0; y < 4; y++) {
			for (int x = 0; x < 4; x++) {
				if ((cardMap[x][y]).getNum() == 0) {
					emptyPoints.add(new Point(x, y));
				}
			}
		}

		Point point1 = emptyPoints.remove((int) (Math.random() * emptyPoints
				.size()));
		cardMap[point1.x][point1.y].setNum(Math.random() > 0.1 ? 2 : 4);
		// Point point2 = emptyPoints.remove((int) (Math.random() * emptyPoints
		// .size()));
		// cardMap[point2.x][point2.y].setNum(Math.random() > 0.1 ? 2 : 4);
	}

	private void addScore(int num) {
		if (scoreAdd != null) {
			scoreAdd.score(num,false);
		}
	}

	/**
	 * 有合并才添加
	 */
	private void actoinUp() {
		boolean megre = false;
		for (int x = 0; x < 4; x++) {
			for (int y = 0; y < 4; y++) {
				ll:
				for (int y1 = y + 1; y1 < 4; y1++) {
					if (cardMap[x][y1].getNum() > 0) {// 当前位置有值
						if (cardMap[x][y].getNum() == 0) {// 前面的位置是空的
							cardMap[x][y].setNum(cardMap[x][y1].getNum());
							cardMap[x][y1].setNum(0);
							y--;
							megre = true;
							break;
						} else if (cardMap[x][y].equals(cardMap[x][y1])) {// 两张卡片相同
							for(int y2 = y;y2<y1;y2++){
								if(cardMap[x][y2].getNum()!=0&&!cardMap[x][y2].equals(cardMap[x][y1])){
									break ll;
								}
							}
							addScore(cardMap[x][y].getNum());
							cardMap[x][y].setNum(cardMap[x][y].getNum() * 2);
							cardMap[x][y1].setNum(0);
							megre = true;
							break;
						}
					}
				}
			}
		}

		if (megre) {
			addRandomNum();
		}
	}

	private void actoinDown() {
		boolean megre = false;
		for (int x = 0; x < 4; x++) {
			for (int y = 3; y >= 0; y--) {
				ll:
				for (int y1 = y - 1; y1 >= 0; y1--) {
					if (cardMap[x][y1].getNum() > 0) {// 当前位置有值
						if (cardMap[x][y].getNum() == 0) {// 前面的位置是空的
							cardMap[x][y].setNum(cardMap[x][y1].getNum());
							cardMap[x][y1].setNum(0);
							y++;
							megre = true;
							break;
						} else if (cardMap[x][y].equals(cardMap[x][y1])) {// 两张卡片相同
							for(int y2 = y;y2>y1;y2--){
								if(cardMap[x][y2].getNum()!=0&&!cardMap[x][y2].equals(cardMap[x][y1])){
									break ll;
								}
							}
							
							addScore(cardMap[x][y].getNum());
							cardMap[x][y].setNum(cardMap[x][y].getNum() * 2);
							cardMap[x][y1].setNum(0);
							megre = true;
							break;
						}
					}
				}
			}
		}
		if (megre) {
			addRandomNum();
		}

	}

	private void actoinRight() {
		boolean megre = false;
		for (int y = 0; y < 4; y++) {
			for (int x = 3; x >= 0; x--) {
				ll:
				for (int x1 = x - 1; x1 >= 0; x1--) {
					if (cardMap[x1][y].getNum() > 0) {// 当前位置有值
						if (cardMap[x][y].getNum() == 0) {// 前面的位置是空的
							cardMap[x][y].setNum(cardMap[x1][y].getNum());
							cardMap[x1][y].setNum(0);
							x++;
							megre = true;
							break;
						} else if (cardMap[x][y].equals(cardMap[x1][y])) {// 两张卡片相同
							for(int x2 = x;x2>x1;x2--){
								if(cardMap[x2][y].getNum()!=0&&!cardMap[x2][y].equals(cardMap[x1][y])){
									break ll;
								}
							}
							
							addScore(cardMap[x][y].getNum());
							cardMap[x][y].setNum(cardMap[x][y].getNum() * 2);
							cardMap[x1][y].setNum(0);
							megre = true;
							break;
						}
					}
				}
			}
		}
		if (megre) {
			addRandomNum();
		}
	}

	private void actoinLeft() {
		boolean megre = false;
		for (int y = 0; y < 4; y++) {
			for (int x = 0; x < 4; x++) {
				ll:
				for (int x1 = x + 1; x1 < 4; x1++) {
					if (cardMap[x1][y].getNum() > 0) {// 当前位置有值
						if (cardMap[x][y].getNum() == 0) {// 前面的位置是空的
							cardMap[x][y].setNum(cardMap[x1][y].getNum());
							cardMap[x1][y].setNum(0);
							x--;
							megre = true;
							break;
						} else if (cardMap[x][y].equals(cardMap[x1][y])) {// 两张卡片相同,且中间没有其他卡片
							for(int x2 = x;x2<x1;x2++){
								if(cardMap[x2][y].getNum()!=0&&!cardMap[x2][y].equals(cardMap[x1][y])){
									break ll;
								}
							}
							addScore(cardMap[x][y].getNum());
							cardMap[x][y].setNum(cardMap[x][y].getNum() * 2);
							cardMap[x1][y].setNum(0);
							megre = true;
							break;
						}
					}
				}
			}
		}
		if (megre) {
			addRandomNum();
		}
	}
	
	private void checkGameOver(){
		boolean isOver = true;
		for (int y = 0; y < 4; y++) {
			for (int x = 0; x < 4; x++) {
				if ((cardMap[x][y]).getNum() == 0||//当前和周边的都不相同
						(x>0&&cardMap[x][y].equals(cardMap[x-1][y])||
						(x<3&&cardMap[x][y].equals(cardMap[x+1][y])||
						(y>0&&cardMap[x][y].equals(cardMap[x][y-1])||
						(y<3&&cardMap[x][y].equals(cardMap[x][y+1])))))) {
					isOver = false;
					y=10;//跳出外层循环
					break;
				}
			}
		}
		if (isOver) {
			new AlertDialog.Builder(getContext()).setTitle("GAMEOVER").setMessage("游戏结束").setPositiveButton("重新开始", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					startGame();
				}
			}).show();
		}
	}

	public interface ScoreAdd {
		public void score(int score,boolean start);
	}

}
