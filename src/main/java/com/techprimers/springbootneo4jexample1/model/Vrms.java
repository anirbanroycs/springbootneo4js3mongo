package com.techprimers.springbootneo4jexample1.model;

import java.util.List;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@NodeEntity
public class Vrms {

    @GraphId
    private Long id;
    private String agrNo;
    private String version;
    private List<DealbucketDet> dealbklist;
	public String getAgrNo() {
		return agrNo;
	}
	public void setAgrNo(String agrNo) {
		this.agrNo = agrNo;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public List<DealbucketDet> getDealbklist() {
		return dealbklist;
	}
	public void setDealbklist(List<DealbucketDet> dealbklist) {
		this.dealbklist = dealbklist;
	}
    
    	
    
    
}
