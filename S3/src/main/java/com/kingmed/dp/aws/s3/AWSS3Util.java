package com.kingmed.dp.aws.s3;

import java.io.File;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.amazonaws.AmazonClientException;
import com.amazonaws.HttpMethod;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.regions.ServiceAbbreviations;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.Upload;

public class AWSS3Util {

    public static void main(String[] args) throws Exception {
    	String dateExpStr= "2016-06-19";
    	Date expiration = new SimpleDateFormat("yyyy-MM-dd").parse(dateExpStr);
        String s = generatePresignedUrl("kingmed-dp", "test/KFBIO/xxx.kfbio", "cn-north-1", expiration);
    }
    
    public static String generatePresignedUrl(String bucketName, String keyName, String regionName, Date expiration) {
    	AmazonS3 s3client = new AmazonS3Client(new ProfileCredentialsProvider());
        Region region = Region.getRegion(Regions.fromName(regionName));
        s3client.setRegion(region);
        final String serviceEndpoint = region.getServiceEndpoint(ServiceAbbreviations.S3);
        s3client.setEndpoint(serviceEndpoint);
        
    	GeneratePresignedUrlRequest urlRequest = new GeneratePresignedUrlRequest(bucketName, keyName);
    	urlRequest.setExpiration(expiration);
    	//URL url = s3client.generatePresignedUrl(urlRequest); 
    	URL url = s3client.generatePresignedUrl(bucketName, keyName, expiration, HttpMethod.GET);
    	System.out.println(url);
    	return null;
    }
}
