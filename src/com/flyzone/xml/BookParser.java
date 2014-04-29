package com.flyzone.xml;

import java.io.InputStream;
import java.util.List;

import com.flyzone.model.Book;

public interface BookParser {
	/**
	 * 解析输入流，得到Book对象
	 * @param is
	 * @return
	 * @throws Exception
	 */
	public List<Book> parse(InputStream is) throws Exception;
	
	/**
	 * 序列化Book对象的集合，得到XML格式的字串
	 * @param books
	 * @return
	 * @throws Exception
	 */
	public String serialize(List<Book> books) throws Exception;
}
