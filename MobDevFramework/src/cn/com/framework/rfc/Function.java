package cn.com.framework.rfc;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.sap.mw.jco.JCO;

public class Function {
	private JCO.Function sFunc;
	private JCO.Client sConn;
	private JCO.Repository sRepository;
	
	/**
	 * @param funcName 方法名称
	 * @throws Exception
	 */
	public Function(String funcName) throws Exception {
		sConn = JCOUtil.getConnection();
		sRepository = new JCO.Repository(JCOUtil.REPOSITORY_NAME,sConn);
		sFunc = sRepository.getFunctionTemplate(funcName).getFunction();
		if (sFunc == null) {
			throw new RuntimeException("Not find the Function!");
		}
	}
	
	public JCO.Function getsFunc() {
		return sFunc;
	}

	public JCO.Client getsConn() {
		return sConn;
	}

	/**
	 * 获得所有TABLE对象参数列表
	 * @return
	 */
	public JCO.ParameterList getTableParameterList(){
		
		return sFunc.getTableParameterList();
	}
	
	/**
	 * 获得所有输入参数列表
	 * @return
	 */
	public JCO.ParameterList getImportParameterList(){
		
		return sFunc.getImportParameterList();
	}
	
	/**
	 * 获得所有输出参数列表
	 * @return
	 */
	public JCO.ParameterList getExportParameterList(){
		
		return sFunc.getExportParameterList();
	}
	
	/**
	 * 获得表名为tableName的表对象
	 * @param tableName
	 * @return
	 */
	public Table getTable(String tableName){
		return new Table(getTableParameterList().getTable(tableName));
	}
	
	
	
	/**
	 * 获得输入结构值
	 * @param name
	 * @return
	 */
	public HashMap getImpStructure(String name){
		JCO.Structure structure = getImportParameterList().getStructure(name);
		HashMap map = new HashMap();
		for (JCO.FieldIterator e = structure.fields(); e.hasMoreElements();) {
			JCO.Field field = e.nextField();
			map.put(field.getName().toString(), field.getValue());
		}
		return map;
	}
	/**
	 * 设置输入结构的值
	 * @param strName
	 * @param map
	 */
	public void setImpStructure(String strName,Map map){
		JCO.Structure structure = getImportParameterList().getStructure(strName);
		for (Iterator iterator = map.keySet().iterator(); iterator.hasNext();) {
			String key = (String) iterator.next();
			structure.setValue(map.get(key), key);
		}
	}
	/**
	 * 设置输出结构的值
	 * @param strName
	 * @param map
	 */
	public void setExpStructure(String strName,Map map){
		JCO.Structure structure = getExportParameterList().getStructure(strName);
		for (Iterator iterator = map.keySet().iterator(); iterator.hasNext();) {
			String key = (String) iterator.next();
			structure.setValue(map.get(key), key);
		}
	}
	
	/**
	 * 设置输出结构的值
	 * @param strName
	 * @param obj
	 */
	public void setExpStructure(String strName,Object obj){
		JCO.Structure structure = getExportParameterList().getStructure(strName);
		Class klass = obj.getClass();
	    Method[] methods = klass.getMethods();
	    for (int i = 0; i < methods.length;i++ ) {
	      try {
	    	Method method = methods[i];
	        String name = method.getName();
	        String key = "";
	        if (name.startsWith("get"))
	          key = name.substring(3);
	        else if (name.startsWith("is"))
	          key = name.substring(2);

	        if ((key.length() > 0) && 
	          (Character.isUpperCase(key.charAt(0))) && 
	          (method.getParameterTypes().length == 0)) {
	          if (key.length() == 1)
	            key = key.toLowerCase();
	          else if (!(Character.isUpperCase(key.charAt(1))))
	            key = key.substring(0, 1).toLowerCase() + 
	              key.substring(1);
	          structure.setValue(method.invoke(obj, null), key);
	        }
	      }
	      catch (Exception ex)
	      {
	      }
	    }
	}
	
	/**
	 * 设置输入结构的值
	 * @param strName
	 * @param obj
	 */
	public void setImpStructure(String strName,Object obj){
		JCO.Structure structure = getImportParameterList().getStructure(strName);
		Class klass = obj.getClass();
	    Method[] methods = klass.getMethods();
	    for (int i = 0; i < methods.length;i++ ) {
	      try {
	    	Method method = methods[i];
	        String name = method.getName();
	        String key = "";
	        if (name.startsWith("get"))
	          key = name.substring(3);
	        else if (name.startsWith("is"))
	          key = name.substring(2);

	        if ((key.length() > 0) && 
	          (Character.isUpperCase(key.charAt(0))) && 
	          (method.getParameterTypes().length == 0)) {
	          if (key.length() == 1)
	            key = key.toLowerCase();
	          else if (!(Character.isUpperCase(key.charAt(1))))
	            key = key.substring(0, 1).toLowerCase() + 
	              key.substring(1);
	          structure.setValue(method.invoke(obj, null), key);
	        }
	      }
	      catch (Exception method)
	      {
	      }
	    }
	}
	/**
	 * 获得输出结构值
	 * @param name
	 * @return
	 */
	public HashMap getExpStructure(String name){
		JCO.Structure structure = getExportParameterList().getStructure(name);
		HashMap map = new HashMap();
		for (JCO.FieldIterator e = structure.fields(); e.hasMoreElements();) {
			JCO.Field field = e.nextField();
			map.put(field.getName().toString(), field.getValue()==null?"":field.getValue());
		}
		return map;
	}
	
	/**
	 * 获得某输出参数的值
	 * @param fieldName
	 * @return
	 */
	public Object getFieldValue(String fieldName){
		return getExportParameterList().getField(fieldName).getValue();
		
	}
	
	/**
	 * 设置输入参数的值
	 * @param key
	 * @param value
	 */
	public void setFieldValue(String key,Object value){
		getImportParameterList().getField(key).setValue(value);
	}
	
	/**
	 * 执行该方法
	 */
	public void execute(){
		sConn.execute(sFunc);
	}
	
	/**
	 * 释放连接 
	 */
	public void release(){
		JCOUtil.releaseConnection(sConn);
	}
}
