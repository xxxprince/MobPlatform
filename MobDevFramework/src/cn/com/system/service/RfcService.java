package cn.com.system.service;

import java.util.Map;

import cn.com.system.service.domain.RfcParameter;

public interface RfcService {
	public static final String IMPORT = "import";
	public static final String EXPORT = "export";
	public static final String TABLE = "table";
	public Map<String , RfcParameter> getParametersByRfcName(String name , String type);
}
