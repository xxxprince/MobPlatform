package cn.com.system.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.sap.mw.jco.JCO.Field;
import com.sap.mw.jco.JCO.FieldIterator;
import com.sap.mw.jco.JCO.Structure;

import cn.com.framework.rfc.Function;
import cn.com.system.service.RfcService;
import cn.com.system.service.domain.RfcParameter;

@Service("rfcService")
public class RfcServiceRfcImpl implements RfcService {
	@Override
	public Map<String, RfcParameter> getParametersByRfcName(String name , String type) {
		Function func = null;
		try {
			func = new Function(name);
		} catch (Exception e) {
			throw new RuntimeException("Can not find rfc with Name :" + name);
		}
		FieldIterator fields = null;
		if(RfcService.IMPORT.equals(type)){
			fields = func.getImportParameterList().fields();
		}else if(RfcService.EXPORT.equals(type)){
			fields = func.getExportParameterList().fields();
		}else if(RfcService.TABLE.equals(type)){
			fields = func.getTableParameterList().fields();
		}else{
			throw new RuntimeException("unknown type with function : " + name);
		}
		Map<String, RfcParameter> paramMap = new HashMap<String, RfcParameter>();
		while(fields.hasNextFields()){
			Field field = fields.nextField();
			RfcParameter rp = formatParam(field);
			paramMap.put(rp.getParamName(), rp);
		}
		
		return paramMap;
	}
	
	private RfcParameter formatParam(Field field) {
		RfcParameter param = new RfcParameter();
		param.setParamName(field.getName());
		param.setParamType(field.getTypeAsString());
		if("TABLE".equals(field.getTypeAsString())){
			List<RfcParameter> list = new ArrayList<RfcParameter>();
			com.sap.mw.jco.JCO.Table tab = field.getTable();
			for(int i = 0; i < tab.getFieldCount(); i++){
				Field f = tab.getField(i);
				list.add(formatParam(f));
			}
			param.setTable(list);
			
		}else if("STRUCTURE".equals(field.getTypeAsString())){
			List<RfcParameter> list = new ArrayList<RfcParameter>();
			Structure str = field.getStructure();
			param.setParamName(field.getName());
			param.setParamType(field.getTypeAsString());
			for(int i = 0; i < str.getFieldCount(); i++){
				Field f = str.getField(i);
				list.add(formatParam(f));
			}
			param.setStructure(list);
		} else {
			param.setParamName(field.getName());
			param.setLength(field.getLength());
			param.setParamType(field.getTypeAsString());
		}
		
		return param;
	}
	
}
