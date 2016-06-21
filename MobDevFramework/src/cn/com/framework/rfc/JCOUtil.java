package cn.com.framework.rfc;

import com.sap.mw.jco.JCO;

public class JCOUtil {
	
	static final String SID = "pools";
	public static final String REPOSITORY_NAME = "ibm";
	static final int MAX_CONNECTION = 100;
	static JCO.Pool pool;
	static {
		pool = JCO.getClientPoolManager().getPool(SID);
		if(pool == null ){
//			OrderedProperties logonProperties;
			try {
//				logonProperties = OrderedProperties.load("cfg/logon.properties"); 
				String [][] sapLogonProperties = {
						{"jco.client.client", SAPConstant.getSapClient()},
						{"jco.client.user", SAPConstant.getSapUser()},
						{"jco.client.passwd", SAPConstant.getSapPwd()},
//						{"jco.client.ashost", SAPConstant.getSapHost()}, 
//						{"jco.client.r3name", SAPConstant.getR3name()},  //EP1   
//						{"jco.client.group", SAPConstant.getGroup()},   //ECC_LG
//						{"jco.client.mshost", SAPConstant.getMsHost()}, //172.16.3.84
						{"jco.client.ashost", SAPConstant.getAsHost()}, //172.16.3.84
						{"jco.client.sysnr", SAPConstant.getSapSysnr()}
					};
//				
//				String [][] sapLogonProperties = {
//						{"jco.client.client", "300"},
//						{"jco.client.user", "MM_RFC"},
//						{"jco.client.passwd", "JSPSAP"},
//						{"jco.client.r3name", "ED1"},
//						{"jco.client.group", "ECC_LG"},
//						{"jco.client.mshost", "192.168.1.6"},
//						{"jco.client.sysnr", "02"}
//					};
				JCO.addClientPool(SID, MAX_CONNECTION, sapLogonProperties);
			} catch (Exception e) {
				//throw new Exception("Not find file!");
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 返回一个连接
	 * @return
	 */
	public static JCO.Client getConnection(){
		
		return JCO.getClient(SID);
	}
	
	public static void releaseConnection(JCO.Client conn){
		JCO.releaseClient(conn);
	}
	
	
	
}
