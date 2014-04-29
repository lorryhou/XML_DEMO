package com.flyzone;

import java.io.InputStream;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.flyzone.model.Book;
import com.flyzone.xml.SaxBookParser;

public class MainActivity extends Activity implements OnClickListener{
	private static final String TAG = "SAX_XML";
	private Button btnReadXML;
	private TextView tvShowXML;
	private StringBuffer buffer = new StringBuffer();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		btnReadXML = (Button) findViewById(R.id.btn_read_xml);
		btnReadXML.setOnClickListener(this);
		tvShowXML = (TextView) findViewById(R.id.show_xml_msg);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_read_xml:
			SaxBookParser parser = new SaxBookParser();
			try {
				InputStream is = MainActivity.this.getAssets().open("books.xml");
				List<Book> books = parser.parse(is);
				for (int i = 0; i < books.size(); i++) {
					buffer.append(books.get(i).toString()+"\n");
				}
				tvShowXML.setText(buffer);
			} catch (Exception e) {
				Log.e(TAG, e.getMessage());
			}
			break;

		default:
			break;
		}
		
	}

}
