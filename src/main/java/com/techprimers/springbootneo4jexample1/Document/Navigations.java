package com.techprimers.springbootneo4jexample1.Document;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Navigations {

    @Id
    private long id;
    private String sqlQuery;
    private String s3link;
    private String status;
    
	
	
	public Navigations(long id, String sqlQuery, String s3link, String status) {
		super();
		this.id = id;
		this.sqlQuery = sqlQuery;
		this.s3link = s3link;
		this.status = status;
	}

	public Navigations() {
		super();
	}

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}


	public String getSqlQuery() {
		return sqlQuery;
	}
	public void setSqlQuery(String sqlQuery) {
		this.sqlQuery = sqlQuery;
	}
	public String getS3link() {
		return s3link;
	}
	public void setS3link(String s3link) {
		this.s3link = s3link;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
    
    
    
}
