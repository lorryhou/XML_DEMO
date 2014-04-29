package com.flyzone.xml;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.flyzone.model.Book;

public class SaxBookParser implements BookParser {

	@Override
	public List<Book> parse(InputStream is) throws Exception {
		// TODO Auto-generated method stub
		SAXParserFactory factory = SAXParserFactory.newInstance();
		SAXParser parser = factory.newSAXParser();
		MyHandler handler = new MyHandler();
		parser.parse(is, handler);
		return handler.getBooks();
	}

	@Override
	public String serialize(List<Book> books) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
	public class MyHandler extends DefaultHandler{
		private List<Book> books;
		private Book book;
		private StringBuilder builder;
		
		public List<Book> getBooks(){
			return books;
		}

		@Override
		public void startDocument() throws SAXException {
			// TODO Auto-generated method stub
			super.startDocument();
			books = new ArrayList<Book>();
			builder = new StringBuilder();
		}

		@Override
		public void characters(char[] ch, int start, int length)
				throws SAXException {
			// TODO Auto-generated method stub
			super.characters(ch, start, length);
			builder.append(ch, start, length);
		}

		@Override
		public void endElement(String uri, String localName, String qName)
				throws SAXException {
			// TODO Auto-generated method stub
			super.endElement(uri, localName, qName);
			if ("id".equals(localName)) {
				book.setId(Integer.parseInt(builder.toString()));
			} else if (localName.equals("name")) {
				book.setName(builder.toString());
			} else if (localName.equals("price")) {
				book.setPrice(Float.parseFloat(builder.toString()));
			} else if (localName.equals("book")) {
				books.add(book);
			}
		}

		@Override
		public void startElement(String uri, String localName, String qName,
				Attributes attributes) throws SAXException {
			// TODO Auto-generated method stub
			super.startElement(uri, localName, qName, attributes);
			if("book".equals(localName)){
				book = new Book();
			}
			builder.setLength(0);
		}
		
	}

}
