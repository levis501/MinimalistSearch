package com.stevenlevis.minimalistsearch;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.ClipboardManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager.LayoutParams;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

@SuppressWarnings("deprecation")
public class MainActivity extends Activity {

	private EditText mSearchBox;
	private Button mClearSearchBoxButton;
	private ImageButton mSearchButton;
	private ImageButton mPasteAndSearchButton;
	private ImageButton mPasteButton;

	private void launchSearch() {
		String action = Intent.ACTION_WEB_SEARCH;
		String query = mSearchBox.getText().toString();
		Intent searchIntent = new Intent(action);
		searchIntent.putExtra(SearchManager.QUERY, query);
		startActivity(searchIntent);
	}
	
	private boolean insertClipboardTextAtSearchCursor() {
		ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
		if (clipboard.hasText()) {
			String clipboardText = " " + clipboard.getText().toString() + " ";
			
			int start = mSearchBox.getSelectionStart();
			int end = mSearchBox.getSelectionEnd();
			mSearchBox.getText().replace(Math.min(start, end), Math.max(start,end), clipboardText, 0, clipboardText.length());
			return true;
		} else {
			Log.d("MinimalistSearch","clipboard reports no text");
			return false;
		}
		
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
		mSearchButton = (ImageButton) findViewById(R.id.searchButton);
		mSearchButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				launchSearch();
			}
		});
		
		mPasteAndSearchButton = (ImageButton) findViewById(R.id.pasteAndSearchButton);
		mPasteAndSearchButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (insertClipboardTextAtSearchCursor()) {
					launchSearch();
				}
			}
		});
		
		mPasteButton = (ImageButton) findViewById(R.id.pasteButton);
		mPasteButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				insertClipboardTextAtSearchCursor();
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
		mSearchBox.setText("");
		this.getWindow().setSoftInputMode(LayoutParams.SOFT_INPUT_STATE_VISIBLE);

		
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);;
		imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,InputMethodManager.HIDE_IMPLICIT_ONLY);
		imm.showSoftInput(mSearchBox,InputMethodManager.SHOW_FORCED);
		
		super.onResume();
	}


}
