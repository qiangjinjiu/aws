import java.io.File;
import java.io.IOException;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.regions.ServiceAbbreviations;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.PutObjectRequest;

public class UploadObjectSingleOperation {
	private static String bucketName     = "km-dp-slide";
	private static String keyName        = "KM14-000062_Cam5.2_3_KM140049 .ndpi";
	private static String uploadFileName = "e:/KM14-000062_Cam5.2_3_KM140049 .ndpi";
	
	public static void main(String[] args) throws IOException {
        AmazonS3 s3client = new AmazonS3Client(new ProfileCredentialsProvider());
        String regionName = "cn-north-1";
        Region region = Region.getRegion(Regions.fromName(regionName));
        s3client.setRegion(region);
        final String serviceEndpoint = region.getServiceEndpoint(ServiceAbbreviations.S3);
        s3client.setEndpoint(serviceEndpoint);
        try {
            System.out.println("Uploading a new object to S3 from a file\n");
            File file = new File(uploadFileName);
            Long start = System.currentTimeMillis();
            s3client.putObject(new PutObjectRequest(bucketName, keyName, file));
            Long end = System.currentTimeMillis();
            System.out.println("上传成功，耗时: " + (end - start) + "毫秒");

         } catch (AmazonServiceException ase) {
            System.out.println("Caught an AmazonServiceException, which " +
            		"means your request made it " +
                    "to Amazon S3, but was rejected with an error response" +
                    " for some reason.");
            System.out.println("Error Message:    " + ase.getMessage());
            System.out.println("HTTP Status Code: " + ase.getStatusCode());
            System.out.println("AWS Error Code:   " + ase.getErrorCode());
            System.out.println("Error Type:       " + ase.getErrorType());
            System.out.println("Request ID:       " + ase.getRequestId());
        } catch (AmazonClientException ace) {
            System.out.println("Caught an AmazonClientException, which " +
            		"means the client encountered " +
                    "an internal error while trying to " +
                    "communicate with S3, " +
                    "such as not being able to access the network.");
            System.out.println("Error Message: " + ace.getMessage());
        }
    }
}