package com.wzf.game2048;

import android.content.Context;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.TextView;

public class Card extends FrameLayout {
	private int num;
	private TextView textView;

	public Card(Context context) {
		super(context);
		initView();
	}

	private void initView() {

		textView = new TextView(getContext());
		textView.setTextSize(15);
		textView.setTextColor(0xff0000ff);
		// textView.setBackgroundResource(R.drawable.ic_launcher);
		LayoutParams lp = new LayoutParams(-1, -1);
		lp.setMargins(10, 10, 0, 0);
		setNum(0);
		this.addView(textView, lp);
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
		if (num == 0) {
			textView.setText("");
		} else {
			textView.setText(num + "");
		}

		if (num > 1000) {
			num = 1000;
		}

		switch (num) {
		case 0:
			textView.setBackgroundColor(0xffCDC0B4);
			break;
		case 2:
			textView.setBackgroundColor(0xffEEE4DA);
			break;
		case 4:
			textView.setBackgroundColor(0xffEBDFC5);
			break;
		case 8:
			textView.setBackgroundColor(0xffF2B179);
			break;
		case 16:
			textView.setBackgroundResource(R.drawable.wzf);
			break;
		case 32:
			textView.setBackgroundResource(R.drawable.wzh);
			break;
		case 64:
			textView.setBackgroundResource(R.drawable.tg);
			break;
		case 128:
			textView.setBackgroundResource(R.drawable.ds);
			break;
		case 256:
			textView.setBackgroundResource(R.drawable.gq);
			break;
		case 512:
			textView.setBackgroundResource(R.drawable.qpf);
			break;
		case 1000:
			textView.setBackgroundColor(0xffFD1E1D);
			break;
		}

	}

	public boolean equals(Card o) {
		// TODO Auto-generated method stub
		return o.getNum() == num;
	}
}
