package com.techprimers.springbootneo4jexample1.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.techprimers.springbootneo4jexample1.Document.Navigations;
import com.techprimers.springbootneo4jexample1.model.Person;
import com.techprimers.springbootneo4jexample1.repository.NavigationsRepository;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

@RestController
@RequestMapping("/sendmessage")
public class Sendmessageresource {

	 private static final String RESPONSE_QUEUE = "MyQueue";
	 @Autowired
	 JmsTemplate jmsTemplate;
	 
	 @Autowired
	 MongoTemplate mt;
	 
	 @Autowired
	 NavigationsRepository nr;
	
    @GetMapping
    public String sendMessage()  {    	  	
    	
    	// Saving a record in db to store s3link later 
    	Pageable request = new PageRequest(0, 1, new Sort(Direction.DESC, "id"));
    	Page<Navigations> on = nr.findAll(request);
    	Navigations oldN = on.getContent().get(0);
    	long currentId = (oldN.getId() + 1);
    	
    	Navigations n = nr.insert(new Navigations(currentId, "", "", "Pending"));
    	
    	// calling jms queue to send the newly inserted id
    	this.jmsTemplate.send(RESPONSE_QUEUE, new MessageCreator() {
            public Message createMessage(Session session) throws JMSException {
              return session.createTextMessage(String.valueOf(n.getId()));
            }
        });
    	return "message sent";
    }

}
