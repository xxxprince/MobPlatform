package cn.com.framework.rfc;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Hashtable;
import java.util.List;

import com.sap.mw.jco.JCO.Field;
import com.sap.mw.jco.JCO.FieldIterator;
import com.sap.mw.jco.JCO.ParameterList;
import com.sap.mw.jco.JCO.Structure;
import com.sap.mw.jco.JCO.Table;



public class SapRfcInf {
	
	public static void main(String[] args){
//		System.out.println(new SapRfcInf().getPicture());
		try {
			JCOUtil.getConnection().ping();
//			testConnection2();
//			testFunc("ZFM_R_MOB_SET_PASSWORD_STATUS");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		new SapRfcInf();
	}
	
	public static void testConnection() throws Exception{
		Function func = new Function("ZFM_R_MOB_GET_PASSWORD_STATUS");
		ParameterList inputList = func.getImportParameterList();
		inputList.setValue("IBM_TONGZX", "I_USER");
		func.execute();
		ParameterList list = func.getExportParameterList();
		String userName = list.getField("ES_PAST").getStructure().getString("USER_ID");
		String userStatus = list.getField("EV_CFLAG").getString();
		System.out.println(userName);
		System.out.println(userStatus);
	}
	
	public static void testConnection2() throws Exception{
		Function func = new Function("ZFM_R_MOB_SET_PASSWORD_STATUS");
		ParameterList inputList = func.getImportParameterList();
		inputList.setValue("IBM_TONGZX", "I_USER");
		func.execute();
		System.out.println(func.getExportParameterList().getField("EV_SUBRC").getInt());
	}
	
	public static void testFunc(String funcName) throws Exception{
		Function fun = null;
		try {
			fun = new Function(funcName);
			
			FieldIterator exp = fun.getExportParameterList().fields();
			while(exp.hasNextFields()){
				Field field = exp.nextField();
				System.out.println("{exp}  "+ field.getTypeAsString());
			}
			
			FieldIterator imp = fun.getImportParameterList().fields();
			while(imp.hasNextFields()){
				Field field = imp.nextField();
				System.out.println( "{imp}  " + field.getTypeAsString());
			}
		} catch (Exception e) {
			System.err.println("sap fun '" + funcName + "' not exits!");
		}
	}
	
	StringBuffer sb = new StringBuffer();
	
	public String getPicture()throws Exception{
		Function func = null;
		File file = new File("C://111.jpg");
		FileOutputStream fos = new FileOutputStream(file);
		try{
			System.out.println("111");
//			func = new Function("ZPZTEST01");
			func = new Function("ZP001_HR_FC0177");
			ParameterList inputList = func.getImportParameterList();
			Table table = inputList.getTable("ZYGJSPERNRTAB");
			inputList.setValue("1", "ZFLAG");
			
			table.setValue(13610568L, "PERNR");
			
			func.execute();
			Table basicTable = (Table) func.getFieldValue("ZIT_DATA");
			byte[] result = (byte[])basicTable.getField("ZPHOTO").getValue();
//			byte[] result = (byte[])func.getFieldValue("A1");
			fos.write(result);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return sb.toString();
	}
	
	
	
//	public SapRfcInf(File file){
//		List li = readFun(file);
//		for(int i  = 0; i < li.size(); i++){
//			getParam(li.get(i).toString());
//		}
//		
//	}
//	public List readFun(File file){
//		List li = new ArrayList();
//		Cell[] cell = null;
//		try {
//			Workbook wb = Workbook.getWorkbook(file);
//
//			ByteArrayOutputStream targetFile = new ByteArrayOutputStream();
//			WritableWorkbook wwb = Workbook.createWorkbook(targetFile, wb);
//			cell = wwb.getSheet(0).getColumn(0);
//		} catch (Exception e){}
//		for(int i = 0; i < cell.length; i++) {
//			if(cell[i] != null && !"".equals(cell[i].getContents().toString())){
//				li.add(cell[i].getContents().toString().trim());
//			}
//		}
//		
//		return li;
//	}
	public String getParam(String name){
		writeFile("---------------func [" +name+ "] start------------------");
		Function fun = null;
		try {
			fun = new Function(name);
			
			FieldIterator exp = fun.getExportParameterList().fields();
			while(exp.hasNextFields()){
				Field field = exp.nextField();
				printSys("{exp}  ", field);
			}
			
			FieldIterator imp = fun.getImportParameterList().fields();
			while(imp.hasNextFields()){
				Field field = imp.nextField();
				printSys( "{imp}  ", field);
			}
		} catch (Exception e) {
			System.err.println("sap fun '" + name + "' not exits!");
		}
		return sb.toString();
		
	}
	
	public void printSys( String type, Field field) {
		if("TABLE".equals(field.getTypeAsString())){
			
			com.sap.mw.jco.JCO.Table tab = field.getTable();
			writeFile(type + "Table FieldCount " + tab.getFieldCount());
			for(int i = 0; i < tab.getFieldCount(); i++){
				Field f = tab.getField(i);
				writeFile(type + "TABLE  Field:" + f.getName() 
						+ " --> Type:" + f.getTypeAsString());
			}
			
		}else if("STRUCTURE".equals(field.getTypeAsString())){
			
			Structure str = field.getStructure();
			writeFile(type + "Structure FieldCount " + str.getFieldCount());
			for(int i = 0; i < str.getFieldCount(); i++) {
				Field f = str.getField(i);
				writeFile(type + "STRUCTURE  Field:" + f.getName() 
						+ " --> FieldType:" + f.getTypeAsString());
			}
		} else {
			writeFile(type + "COMMON  Field:" + field.getName() 
					+ " --> FieldType:" + field.getTypeAsString());
		}
	}
	
	public void writeFile(String arg) {
		String filename = System.currentTimeMillis()+"rfc_fun.txt";
		File file = new File("C:\\rfc_fun.txt");
		Writer writer = null;
		try {
			writer = new FileWriter(file,true);
			writer.write(arg + "\r\n");
			
		} catch (IOException e) {
			System.err.println("write file error!");
		} finally {
			try {
				writer.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public SapRfcInf() {
		super();
	}
	
	public static void testConnection1() throws Exception{
		Function func = new Function("ZFI00_FM00164");
		ParameterList inputList = func.getImportParameterList();
//		Table table = inputList.getTable("ZYGJSPERNRTAB");
		inputList.setValue("", "IN_EQUNR");
//		Map map = new HashMap();
//		Map map = func.getImpStructure("I_BUKRS");
//		Table inputTable = inputList.getTable("I_BUKRS");
//		inputTable.appendRow();
//		inputTable.setValue("I", "SIGN");
//		inputTable.setValue("BT", "OPTION");
//		inputTable.setValue("0300", "LOW");
//		inputTable.setValue("0300", "HIGH");

//		map.put("SIGN", "I");
//		map.put("OPTION", "BT");
//		map.put("LOW", "0300");
//		map.put("HIGH", "0300");
		
		Table gsdmTable = func.getTableParameterList().getTable("I_BUKRS");
		gsdmTable.appendRow();
		gsdmTable.setRow(0);

		gsdmTable.setValue("I", "SIGN");
		gsdmTable.setValue("EQ", "OPTION");
		gsdmTable.setValue("0300", "LOW");
		gsdmTable.setValue("", "HIGH");
		
//		func.getTable("I_BUKRS").setValue(map);
//		func.("I_BUKRS", map);
//		inputList.set
		try{
			func.execute();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		Object out = func.getFieldValue("WA_OUTPUT");
		cn.com.framework.rfc.Table outTable = func.getTable("I_ITAB1");
		List list = outTable.getResultSet();
		Hashtable tbale = (Hashtable) list.get(0);
//		Assert.notNull(out,"error,get null value");
		System.out.println(out);
	}
	
}
