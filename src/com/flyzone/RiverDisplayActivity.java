package com.flyzone;

import java.io.InputStream;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.flyzone.model.River;
import com.flyzone.xml.PullRiverParser;
import com.flyzone.xml.SaxRiverParser;

public class RiverDisplayActivity extends Activity implements OnClickListener{
	private static final String TAG = "RiverDisplayActivity";
	private Button btnSaxReader;
	private Button btnPullReader;
	private ListView listView;
	private RiverAdapter riverAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_river_display);
		
		getView();
		
		btnSaxReader.setOnClickListener(this);
		btnPullReader.setOnClickListener(this);
	}
	
	public void getView(){
		btnSaxReader = (Button) findViewById(R.id.btn_sax_parse);
		btnPullReader = (Button) findViewById(R.id.btn_pull_parse);
		listView = (ListView) findViewById(R.id.listview);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.river_display, menu);
		return true;
	}

	private class RiverAdapter extends BaseAdapter{
		private Context mContext;
		private List<River> rivers;
		public RiverAdapter(Context context, List<River> rivers){
			this.mContext = context;
			this.rivers = rivers;
		}
		
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return rivers.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return rivers.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		//用以生成在ListView中展示的一个个元素View  
        public View getView(int position, View convertView, ViewGroup parent) {  
			//优化ListView  
            if(convertView==null){  
                convertView=LayoutInflater.from(mContext).inflate(R.layout.river_list_item, null);  
                ItemViewCache viewCache=new ItemViewCache();  
                viewCache.mtvName=(TextView)convertView.findViewById(R.id.textView1);  
                viewCache.mImageView=(ImageView)convertView.findViewById(R.id.imageView1);
                viewCache.mtvLength = (TextView)convertView.findViewById(R.id.textView2);
                viewCache.mtvDesc = (TextView)convertView.findViewById(R.id.textView3);
                convertView.setTag(viewCache);  
            }  
            ItemViewCache cache=(ItemViewCache)convertView.getTag();  
            //设置文本和图片，然后返回这个View，用于ListView的Item的展示  
            River river = rivers.get(position);
            cache.mtvName.setText(river.getName());  
            cache.mtvLength.setText(river.getLength()+"km");  
            cache.mtvDesc.setText(river.getDesc());  
            //cache.mImageView.setImageResource(images[position]);  
            return convertView;  
		}
		
		//元素的缓冲类,用于优化ListView  
	    private class ItemViewCache{  
	        public TextView mtvName;
	        public ImageView mImageView;  
	        public TextView mtvLength;  
	        public TextView mtvDesc;  
	    } 
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		InputStream is;
		switch (v.getId()) {
		case R.id.btn_sax_parse:
			try {
				is = getResources().getAssets().open("rivers.xml");
				List<River> rivers = new SaxRiverParser().parse(is);
				//List<Book> books = new SaxBookParser().parse(is);
				
				riverAdapter = new RiverAdapter(this, rivers);
				listView.setAdapter(riverAdapter);
			} catch (Exception e) {
				Log.e(TAG, "解析错误:"+e.getMessage());
			}
			break;
		case R.id.btn_pull_parse:
			try {
				is = getResources().getAssets().open("rivers.xml");
				List<River> rivers = new PullRiverParser().parse(is);
				
				riverAdapter = new RiverAdapter(this, rivers);
				listView.setAdapter(riverAdapter);
			} catch (Exception e) {
				Log.e(TAG, "解析错误:"+e.getMessage());
			}
			break;
		default:
			break;
		}
	}
}
