package com.stevenlevis.minimalistsearch;

import com.stevenlevis.minimalistsearch.R;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.app.Activity;
import android.app.SearchManager;
import android.content.Intent;

public class MainActivity extends Activity {

	private EditText mSearchBox;
	private Button mClearSearchBoxButton;
	private Button mSearchButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		OnClickListener go = new OnClickListener() {
			@Override
			public void onClick(View v) {
				String action = Intent.ACTION_WEB_SEARCH;
				String query = mSearchBox.getText().toString();
				Intent searchIntent = new Intent(action);
				searchIntent.putExtra(SearchManager.QUERY, query);
				startActivity(searchIntent);
			}
		};
		mSearchBox = (EditText) findViewById(R.id.searchBox);
		mSearchBox.setOnClickListener(go);
		mSearchButton = (Button) findViewById(R.id.searchButton);
		mSearchButton.setOnClickListener(go);
		
		mClearSearchBoxButton = (Button) findViewById(R.id.clearSearchBoxButton);
		mClearSearchBoxButton.setOnClickListener( new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mSearchBox.setText("");
			}
		});
		
	}

	@Override
	protected void onResume() {
		this.getWindow().setSoftInputMode(LayoutParams.SOFT_INPUT_STATE_VISIBLE);
		
		super.onResume();
	}


}
