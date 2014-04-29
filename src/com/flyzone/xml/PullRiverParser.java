package com.flyzone.xml;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;

import android.util.Xml;

import com.flyzone.model.River;

public class PullRiverParser implements RiverParser{

	@Override
	public List<River> parse(InputStream is) throws Exception {
		List<River> rivers = new ArrayList<River>();
		River river = null;
	
		XmlPullParser pullParser = Xml.newPullParser();
		pullParser.setInput(is, "utf-8");
		
		int eventType = pullParser.getEventType();
		while (eventType != XmlPullParser.END_DOCUMENT) {
			switch (eventType) {
			case XmlPullParser.START_TAG:
				String tag =  pullParser.getName();
				if("river".equalsIgnoreCase(tag)){
					river = new River();
					river.setName(pullParser.getAttributeValue(null, "name"));
					river.setLength(Integer.parseInt(pullParser.getAttributeValue(null, "length")));
				}else if("introduction".equalsIgnoreCase(tag)){
					river.setDesc(pullParser.nextText());
				}else if("imageurl".equalsIgnoreCase(tag)){
					river.setImageurl(pullParser.nextText());
				}
				break;
			case XmlPullParser.END_TAG:
				// 如果遇到river标签结束，则把river对象添加进集合中
				if (pullParser.getName().equalsIgnoreCase("river")
						&& river != null) {
					rivers.add(river);
					river = null;
				}
				break;

			default:
				break;
			}
			//如果xml没有结束，则导航到下一个river节点
			eventType = pullParser.next();
		}
		
		return rivers;
	}

	@Override
	public String serialize(List<River> rivers) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
}
