package com.techprimers.springbootneo4jexample1.service;

import java.io.IOException;
import java.text.ParseException;
import java.util.concurrent.ExecutionException;

import javax.jms.JMSException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;

import com.techprimers.springbootneo4jexample1.Document.Navigations;
import com.techprimers.springbootneo4jexample1.repository.NavigationsRepository;
import com.techprimers.springbootneo4jexample1.resource.PersonResource;

@Component
public class MessageReceiver {
	
	@Autowired
	PersonResource ps;
 
	@Autowired
	 NavigationsRepository nr;
	
    static final Logger LOG = LoggerFactory.getLogger(MessageReceiver.class);
    private static final String RESPONSE_QUEUE = "MyQueue";
     
    @JmsListener(destination = RESPONSE_QUEUE)
    public void receiveMessage(final Message<String> message) throws JMSException, InterruptedException, ParseException, IOException, ExecutionException {
        LOG.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++");
       //Thread.sleep(10000);
        String db_inserted_id = message.getPayload();
        
        String s3path = ps.exportListToCSV(db_inserted_id);
        
        System.out.println("S3 Path = " + s3path );
        
        // saving s3 path
        Navigations nav = new Navigations();
        nav.setId(Long.parseLong(db_inserted_id));
        nav.setS3link(s3path);
        nav.setStatus("Completed");
        nr.save(nav);
        
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++");

    }
}