package com.techprimers.springbootneo4jexample1.model;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;

public class DealbucketDet {

    private String dealbucket;
    private String assetList;
    private String hbBucketList;
	public String getDealbucket() {
		return dealbucket;
	}
	public void setDealbucket(String dealbucket) {
		this.dealbucket = dealbucket;
	}
	public String getAssetList() {
		return assetList;
	}
	public void setAssetList(String assetList) {
		this.assetList = assetList;
	}
	public String getHbBucketList() {
		return hbBucketList;
	}
	public void setHbBucketList(String hbBucketList) {
		this.hbBucketList = hbBucketList;
	}
    
    
    
	
}
