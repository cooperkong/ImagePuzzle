package com.littlesword.puzzle.mainscreen;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.littlesword.puzzle.R;

public class MainActivity extends AppCompatActivity {

	private static final String FRAGMENT_TAG = "game_board_fragment";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity);
	}
}