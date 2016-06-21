package cn.com.framework.rfc;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.sap.mw.jco.JCO;

public class Table {
	private JCO.Table table;
	
	public Table() {
		
	}
	public Table(JCO.Table table) {
		this.table = table;
	}

	public void setTable(JCO.Table table) {
		this.table = table;
	}
	
	public String getName(){
		return this.table.getName();
	}
	
//	public void setValue(Collection col){
//		if(col !=null){
//			for (Iterator iterator = col.iterator(); iterator.hasNext();) {
//				Object object = (Object) iterator.next();
//				this.setValue(object);
//			}
//		}
//	}
	
	public void setValue(Map map){
		if(map !=null){
			this.table.appendRow();
			for (Iterator iterator = map.keySet().iterator(); iterator.hasNext();) {
				String key = (String) iterator.next();
				this.table.setValue(map.get(key), key.toUpperCase());
			}
		}
	}
	
	public void setValue(Object obj){
		Class klass = obj.getClass();
	    Method[] methods = klass.getMethods();
	    this.table.appendRow();
		
	    for (int i = 0; i < methods.length;i++ ) {
	      try {
	    	Method method = methods[i];
	        String name = method.getName();
	        String key = "";
	        if (name.startsWith("get") && !name.equals("getClass"))
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
	          //map.put(key,method.invoke(obj, null));
	          
	          this.table.setValue(method.invoke(obj, null), key.toUpperCase());
	        }
	      }
	      catch (Exception method)
	      {
	      }
	    }
	}
	
	public List getResultSet(){
		List resultList = new ArrayList();
		if (this.table.getNumRows() > 0) {
			do {
				Hashtable hashtable = new Hashtable();
				for (JCO.FieldIterator e = this.table.fields(); e.hasMoreElements();) {
					JCO.Field field = e.nextField();
					if(2 == field.getType()){
						hashtable.put(field.getName().toString(), field.getBigDecimal()==null?"":field.getBigDecimal());
					} else {
						hashtable.put(field.getName().toString(), field.getValue()==null?"":field.getValue());
					}
				}
				resultList.add(hashtable);
			} while (this.table.nextRow());
		}
		
		return resultList;
	}
}
