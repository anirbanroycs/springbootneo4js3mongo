package com.techprimers.springbootneo4jexample1.model;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;

public class Importeddata {
	private static final long serialVersionUID = 1L; 
    private String agr_no;
    private String version_code;
    private String code;
    private String asset_id;
    private String holdback_code;
	public Importeddata(String agr_no, String version_code, String code, String asset_id, String holdback_code) {
		super();
		this.agr_no = agr_no;
		this.version_code = version_code;
		this.code = code;
		this.asset_id = asset_id;
		this.holdback_code = holdback_code;
	}
	public String getAgr_no() {
		return agr_no;
	}
	public void setAgr_no(String agr_no) {
		this.agr_no = agr_no;
	}
	public String getVersion_code() {
		return version_code;
	}
	public void setVersion_code(String version_code) {
		this.version_code = version_code;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getAsset_id() {
		return asset_id;
	}
	public void setAsset_id(String asset_id) {
		this.asset_id = asset_id;
	}
	public String getHoldback_code() {
		return holdback_code;
	}
	public void setHoldback_code(String holdback_code) {
		this.holdback_code = holdback_code;
	}
	@Override
	public String toString() {
		return "Importeddata [agr_no=" + agr_no + ", version_code=" + version_code + ", code=" + code + ", asset_id="
				+ asset_id + ", holdback_code=" + holdback_code + "]";
	}
    
	
    
    
	
}
