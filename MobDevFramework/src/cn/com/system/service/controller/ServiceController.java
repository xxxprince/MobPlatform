package cn.com.system.service.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.system.service.RfcService;
import cn.com.system.service.domain.RfcParameter;
import net.sf.json.JSONObject;

@Controller
public class ServiceController {

	@Autowired
	private RfcService rfcService;

	@RequestMapping("/service/getRfcParams.do")
	public @ResponseBody String getStatus(@RequestParam("name") String name , @RequestParam("type") String type) {
		Map<String, RfcParameter> params = null;
		try {
			params = rfcService.getParametersByRfcName(name, type);
		} catch (Exception e) {
			return "[can not find rfc with name : " + name + "]";
		}

		JSONObject paramsJson = JSONObject.fromObject(params);
		
		return paramsJson.toString();
	}
	
	

}
