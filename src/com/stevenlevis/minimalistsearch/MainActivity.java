package com.stevenlevis.minimalistsearch;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager.LayoutParams;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

public class MainActivity extends Activity {

	private EditText mSearchBox;
	private Button mClearSearchBoxButton;
	private Button mSearchButton;

	private void launchSearch() {
		String action = Intent.ACTION_WEB_SEARCH;
		String query = mSearchBox.getText().toString();
		Intent searchIntent = new Intent(action);
		searchIntent.putExtra(SearchManager.QUERY, query);
		startActivity(searchIntent);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mSearchBox = (EditText) findViewById(R.id.searchBox);
		mSearchBox.setOnEditorActionListener(new OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView v, int actionId,
					KeyEvent event) {
		        if (actionId == EditorInfo.IME_ACTION_DONE) {
					launchSearch();
					return true;
		        }
				return false;
			}});
		mSearchButton = (Button) findViewById(R.id.searchButton);
		mSearchButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				launchSearch();
			}
		});
		
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

		
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);;
		imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,InputMethodManager.HIDE_IMPLICIT_ONLY);
		imm.showSoftInput(mSearchBox,InputMethodManager.SHOW_FORCED);
		
		super.onResume();
	}


}
