package com.wzf.game2048;

import com.wzf.game2048.GameLayout.ScoreAdd;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;
import android.os.Build;

public class MainActivity extends Activity {
	private GameLayout gameLayout;
	private TextView tv_score;
	private int sum;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.fragment_main);
		tv_score = (TextView) findViewById(R.id.tv_score);
		tv_score.setText("总分："+sum);
		gameLayout = (GameLayout) findViewById(R.id.layout);
		findViewById(R.id.start).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				sum=0;
				tv_score.setText("总分："+sum);
				gameLayout.startGame();
			}
		});
		gameLayout.setScoreAdd(new ScoreAdd() {
			
			@Override
			public void score(int score,boolean start) {
				if (start) {
					sum = 0;
				}else{
					sum+=score;					
				}
				tv_score.setText("总分："+sum);
			}
		});

	}

}
