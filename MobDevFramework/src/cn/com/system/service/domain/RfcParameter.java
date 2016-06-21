package cn.com.system.service.domain;

import java.util.ArrayList;
import java.util.List;

public class RfcParameter {
	private String paramName;
	private Integer length;

	private String paramType = "String";

	private List<RfcParameter> table = new ArrayList<RfcParameter>();

	private List<RfcParameter> structure = new ArrayList<RfcParameter>();

	public String getParamName() {
		return paramName;
	}

	public void setParamName(String paramName) {
		this.paramName = paramName;
	}

	public String getParamType() {
		return paramType;
	}

	public void setParamType(String paramType) {
		this.paramType = paramType;
	}

	public List<RfcParameter> getTable() {
		return table;
	}

	public void setTable(List<RfcParameter> table) {
		this.table = table;
	}

	public List<RfcParameter> getStructure() {
		return structure;
	}

	public void setStructure(List<RfcParameter> structure) {
		this.structure = structure;
	}

	public Integer getLength() {
		return length;
	}

	public void setLength(Integer length) {
		this.length = length;
	}

}
