package cn.com.framework.rfc;

import java.io.IOException;
import java.util.HashMap;



/**
 * 读取SAP 登录信息
 * <p>
 * 创建日期：2010-06-17<br>
 * 修改历史：<br>
 * 修改日期：<br>
 * 修改人：<br>
 * 修改内容：<br>
 * 
 * @author K.X.
 * @since 1.0
 */
public class SAPConstant {
	private static final HashMap<String, String> configMap = initConfg();
	
	private static final String sapClient ="jco.client.client";
	private static final String sapUser = "jco.client.user";
	private static final String sapPwd = "jco.client.passwd";
	private static final String sapHost = "jco.client.ashost";
	private static final String sapSysnr = "jco.client.sysnr";
	private static final String r3name = "jco.client.r3name";
	private static final String group = "jco.client.group";
	private static final String msHost = "jco.client.msHost";
	private static final String asHost = "jco.client.asHost";
	

	/**
	 * 初始化系统参数
	 * 
	 * @return
	 */
	private static HashMap<String, String> initConfg() {
		HashMap<String , String> map = new HashMap<String , String>();
		map.put(sapClient, "300");
		map.put(sapUser, "liqy10");
		map.put(sapPwd, "1qaz!QAZ");
		map.put(sapHost, "10.0.13.51");
		map.put(sapSysnr, "00");
		map.put(r3name, "CRD");
		map.put(group, "");
		map.put(asHost, "10.0.13.51");
//		OrderedProperties prop = null;
//		try {
//			prop = OrderedProperties.load("config.properties");
//		} catch (IOException e) {
//			throw new RuntimeException("can not find file config.properties");
//		}
//		
//		map.put(sapClient, prop.getProperty("sapClient"));
//		map.put(sapUser, prop.getProperty("sapUser"));
//		map.put(sapPwd, prop.getProperty("sapPwd"));
//		map.put(sapHost, prop.getProperty("sapHost"));
//		map.put(sapSysnr, prop.getProperty("sapSysnr"));
//		map.put(r3name, prop.getProperty("r3name"));
//		map.put(group, prop.getProperty("group"));
//		map.put(asHost, prop.getProperty("asHost"));
//		map.put(msHost, prop.getProperty("msHost"));
		return map;
	}

	/**
	 * 取得系统参数值
	 * 
	 * @param code
	 * @return
	 */
	private static String getSysParam(String code) {
		if (configMap == null || configMap.isEmpty())
			return null;
		return (String) configMap.get(code);
	}

	/**
	 * 取得SAP集团号
	 * 
	 * @return
	 */
	public static String getSapClient() {
		return getSysParam(sapClient);
	}
	
	/**
	 * 取得SAP用户
	 * 
	 * @return
	 */
	public static String getSapUser() {
		return getSysParam(sapUser);
	}
	
	/**
	 * 取得SAP用户密码
	 * 
	 * @return
	 */
	public static String getSapPwd() {
		return getSysParam(sapPwd);
	}
	
	/**
	 * 取得SAP group
	 * 
	 * @return
	 */
	public static String getGroup() {
		return getSysParam(group);
	}
	
	/**
	 * 取得SAP r3name
	 * 
	 * @return
	 */
	public static String getR3name() {
		return getSysParam(r3name);
	}
	
	/**
	 * 取得SAP msHost
	 * 
	 * @return
	 */
	public static String getMsHost() {
		return getSysParam(msHost);
	}
	
	/**
	 * 取得SAP服务器IP
	 * 
	 * @return
	 */
	public static String getSapHost() {
		return getSysParam(sapHost);
	}
	
	/**
	 * 取得SAP系统编号
	 * 
	 * @return
	 */
	public static String getSapSysnr() {
		return getSysParam(sapSysnr);
	}

	/**
	 * 获取asHost
	 * @return
	 */
	public static String getAsHost() {
		return  getSysParam(asHost);
	}
	
}
