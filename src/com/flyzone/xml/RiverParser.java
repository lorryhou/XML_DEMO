package com.flyzone.xml;

import java.io.InputStream;
import java.util.List;

import com.flyzone.model.River;

public interface RiverParser {
	/**
	 * 解析输入流获得River对象的集合
	 * @param is
	 * @return
	 * @throws Exception
	 */
	public List<River> parse(InputStream is) throws Exception;
	
	/**
	 * 序列化River对象的集合，得到XML格式的字串
	 * @param rivers
	 * @return
	 * @throws Exception
	 */
	public String serialize(List<River> rivers) throws Exception;
}
