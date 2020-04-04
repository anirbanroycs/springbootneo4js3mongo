package com.techprimers.springbootneo4jexample1.resource;

import org.neo4j.ogm.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.techprimers.springbootneo4jexample1.model.Person;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rest/neo4j/user")
public class UserResource {

    @SuppressWarnings("deprecation")
	@Autowired
	org.neo4j.ogm.session.SessionFactory sf;

    @GetMapping
    public List<Person> getAll() throws ParseException {    	  	
    	
    	// input part
    	Person p = new Person();
    	p.setName("'Child1','Child3'");
    	p.setAge("'10','15'");
    	
    	// dynamic query building
    	StringBuilder dq= new StringBuilder(); 
    	
    	if(p.getName()!=null){
    	    dq.append(" AND p.name IN ["+p.getName()+"]");
    	    }
    	    if(p.getAge()!=null){
    	    dq.append(" AND p.age IN ["+p.getAge()+"]");
    	    }
    	
    	Map<String, Object> params = new HashMap<>();
        params.put("names", p.getName());
        params.put("ages", p.getAge());
    	
    	
        // actual query
    	String query ="MATCH (p:Person) WHERE id(p)>0"+dq.toString()+" RETURN p";
    	
    	
    	
    	// execute query
       Session sess = sf.openSession();
       List<Person> personList = (List<Person>) sess.query(Person.class, 
                query, params);
    	
    
        return personList;
    }

}
