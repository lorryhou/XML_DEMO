package com.flyzone.xml;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.flyzone.model.River;

public class SaxRiverParser implements RiverParser {
	InputSource inputSource=null; 

	@Override
	public List<River> parse(InputStream is) throws Exception {
		SAXParserFactory factory = SAXParserFactory.newInstance();
		SAXParser parser = factory.newSAXParser();
		MyHandler handler = new MyHandler();
		//InputStreamReader streamReader = new InputStreamReader(is,"utf-8"); 
		/*inputSource = new InputSource();
		inputSource.setByteStream(is);
		inputSource.setEncoding("UTF-8");*/
		
		parser.parse(is, handler);
		return handler.getRivers();
	}

	@Override
	public String serialize(List<River> rivers) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
	public class MyHandler extends DefaultHandler{
		private River river;
		private List<River> rivers;
		private StringBuffer buffer;
		private int id = 0;
		
		private List<River> getRivers(){
			return rivers;
		}

		@Override
		public void characters(char[] ch, int start, int length)
				throws SAXException {
			// TODO Auto-generated method stub
			super.characters(ch, start, length);
			buffer.append(ch, start, length);
		}
		//01-01 05:16:41.833: W/System.err(16107): org.apache.harmony.xml.ExpatParser$ParseException: At line 1, column 0: not well-formed (invalid token)

		@Override
		public void endElement(String uri, String localName, String qName)
				throws SAXException {
			// TODO Auto-generated method stub
			super.endElement(uri, localName, qName);
			if("introduction".equals(localName)){
				river.setDesc(buffer.toString());
			}else if("imageurl".equals(localName)){
				river.setImageurl(buffer.toString());
			}else if("river".equals(localName)){
				river.setId(id);
				id++;
				rivers.add(river);
			}
		}

		@Override
		public void startDocument() throws SAXException {
			// TODO Auto-generated method stub
			super.startDocument();
			rivers = new ArrayList<River>();
			buffer = new StringBuffer();
		}

		@Override
		public void startElement(String uri, String localName, String qName,
				Attributes attributes) throws SAXException {
			// TODO Auto-generated method stub
			super.startElement(uri, localName, qName, attributes);
			if("river".equals(localName)){
				river = new River();
				river.setName(attributes.getValue("name"));
				river.setLength(Integer.parseInt(attributes.getValue("length")));
			}
			buffer.setLength(0);
		}
		
	}

}
