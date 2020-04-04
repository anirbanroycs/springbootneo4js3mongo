package com.techprimers.springbootneo4jexample1.resource;

import com.techprimers.springbootneo4jexample1.model.Movie;
import com.techprimers.springbootneo4jexample1.model.Person;
import com.techprimers.springbootneo4jexample1.model.TestDate;
import com.techprimers.springbootneo4jexample1.model.User;
import com.techprimers.springbootneo4jexample1.repository.MovieRepository;
import com.techprimers.springbootneo4jexample1.repository.PersonRepository;
import com.techprimers.springbootneo4jexample1.repository.TestDateRepository;
import com.techprimers.springbootneo4jexample1.repository.UserRepository;
import com.techprimers.springbootneo4jexample1.service.UserService;

import org.neo4j.ogm.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.neo4j.template.Neo4jTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

import javax.websocket.server.PathParam;

@RestController
@RequestMapping("/rest/neo4j/person")
public class PersonResource {

	@Autowired
	PersonRepository personRepository;

	@Autowired
	AmazonClient amazonClient;

	@SuppressWarnings("deprecation")
	@Autowired
	org.neo4j.ogm.session.SessionFactory sf;
	

	@GetMapping
	public String getAll() throws ParseException {

		/*Person p1 = new Person("Child1", "10");
		Person p2 = new Person("Child2", "15");
		Person p3 = new Person("Child3", "20");
		Person p4 = new Person("Child4", "25");

		Person p5 = new Person("Parent1", "60");
		Person p6 = new Person("Parent2", "65");

		Person p7 = new Person("GrandParent", "80");

		personRepository.save(p1);
		personRepository.save(p2);
		personRepository.save(p3);
		personRepository.save(p4);

		p5.setP1(p1);
		p5.setP2(p2);

		p6.setP1(p3);
		p6.setP2(p4);

		personRepository.save(p5);
		personRepository.save(p6);

		p7.setP1(p5);
		p7.setP2(p6);*/
		
		for(long i=28000;i<100000;i++){

		personRepository.save(new Person("Test"+i, "15","10000","test address"));
		
		}

		return "done";
	}

	@GetMapping("/page/{pagenum}")
	public List<Person> paginatedList(@PathVariable("pagenum") int num) throws ParseException {

		Pageable paging = new PageRequest(num, 2);

		Page<Person> pagedResult = personRepository.findAll(paging);

		if (pagedResult.hasContent()) {
			return pagedResult.getContent();
		} else {
			return new ArrayList<Person>();
		}
	}

	@GetMapping("/export")
	public String exportListToCSV(String num) throws ParseException, IOException, InterruptedException, ExecutionException {

		// List from DB
		//List<Person> personList = (List<Person>) personRepository.findAll();
		Pageable paging = new PageRequest(2 * Integer.parseInt(num), 100);
		
		Page<Person> pagedResult = personRepository.findAll(paging);
		List<Person> personList = pagedResult.getContent();
		System.out.println("completed db with records " + personList.size());
		
		
		// Convert to CSV

		String csvText = threadBasedCSVConvert(personList);

		System.out.println("completed csv step " );

		
		// Move file to S3
		String s3path = moveFileToS3(csvText,num);
		
		System.out.println("completed s3 movement " );


		return s3path;
	}

	private String threadBasedCSVConvert(List<Person> personList) throws InterruptedException, ExecutionException{
		StringBuffer finalString = new StringBuffer();
		
		ExecutorService executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
		int count = 100;
		 for (int i = 0; i < personList.size(); i=i+count) {
			 	int lastindex = i+count;
			 	if(lastindex>personList.size()){
			 		lastindex = personList.size();
			 	}
			 	
			 	List<Person> subList = personList.subList(i, lastindex);
			 	CSVConverterUtil calculator = new CSVConverterUtil(subList);
	            Future<String> result = (Future<String>) executor.submit(calculator);
	            finalString.append(result.get());
	        }
		
		return finalString.toString();
		
	}
	
	
	
	private String moveFileToS3(String csvText, String num) throws IOException {

		String awsFileName = amazonClient.uploadFileTos3bucket("person"+num+".csv", csvText);
		return awsFileName;

	}

}