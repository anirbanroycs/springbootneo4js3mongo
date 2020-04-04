package com.techprimers.springbootneo4jexample1.resource;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

@Service
public class AmazonClient {

	private AmazonS3 s3client;

	@Value("${amazonProperties.endpointUrl}")
	private String endpointUrl;
	@Value("${amazonProperties.bucketName}")
	private String bucketName;
	@Value("${amazonProperties.accessKey}")
	private String accessKey;
	@Value("${amazonProperties.secretKey}")
	private String secretKey;

	@PostConstruct
	private void initializeAmazon() {
		AWSCredentials credentials = new BasicAWSCredentials(this.accessKey, this.secretKey);
		this.s3client = new AmazonS3Client(credentials);
	}

	public String uploadFileTos3bucket(String fileName, String content) {
		// s3client.putObject(new PutObjectRequest(bucketName, fileName,
		// content).withCannedAcl(CannedAccessControlList.PublicRead));

		// create meta-data for your folder and set content-length to 0
		ObjectMetadata metadata = new ObjectMetadata();
		metadata.setContentLength(content.getBytes().length);
		// create empty content
		InputStream emptyContent = new ByteArrayInputStream(content.getBytes());
		// create a PutObjectRequest passing the folder name suffixed by /
		PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, fileName, emptyContent, metadata)
				.withCannedAcl(CannedAccessControlList.PublicRead);
		// send request to S3 to create folder
		s3client.putObject(putObjectRequest);

		// s3client.putObject(bucketName, fileName, content);
		return endpointUrl.replace("s3", bucketName + ".s3" ) + "/" + fileName;
	}

}