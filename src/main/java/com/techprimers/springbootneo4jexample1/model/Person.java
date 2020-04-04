package com.techprimers.springbootneo4jexample1.model;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@NodeEntity
@AllArgsConstructor
@NoArgsConstructor
public class Person {

    @GraphId
    private Long id;
    private String name;
    private String age;
    private String salary;
    private String address;
    
    
    
	public Person() {
		super();
		// TODO Auto-generated constructor stub
	}


	public Person(String name, String age, String salary, String address) {
		super();
		this.name = name;
		this.age = age;
		this.salary = salary;
		this.address = address;
	}
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public String getSalary() {
		return salary;
	}
	public void setSalary(String salary) {
		this.salary = salary;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
    
    
    
    
    /*@Relationship(type = "CHILD", direction = Relationship.OUTGOING)
    private Person p1;

    @Relationship(type = "CHILD", direction = Relationship.OUTGOING)
    private Person p2;*/

    
	
	
    
}
