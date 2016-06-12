
import java.io.File;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.regions.ServiceAbbreviations;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.Upload;

public class UploadObjectMultipartUploadUsingHighLevelAPI {

    public static void main(String[] args) throws Exception {
        String existingBucketName = "km-dp-slide";
        String keyName            = "KM13-000373_HE_1_KM130378.ndpi";
        //String filePath           = "e:/KM14-000174_HE_2_KM140169.ndpi";  
        String filePath ="e:/BucketExplorer64.exe";
        
        AmazonS3 s3client = new AmazonS3Client(new ProfileCredentialsProvider());
        String regionName = "cn-north-1";
        Region region = Region.getRegion(Regions.fromName(regionName));
        s3client.setRegion(region);
        final String serviceEndpoint = region.getServiceEndpoint(ServiceAbbreviations.S3);
        s3client.setEndpoint(serviceEndpoint);
        
        //TransferManager tm = new TransferManager(new ProfileCredentialsProvider());   
        TransferManager tm = new TransferManager(s3client);
        
        System.out.println("Hello");
        // TransferManager processes all transfers asynchronously, 
        // so this call will return immediately.
        Upload upload = tm.upload(existingBucketName, keyName, new File(filePath));
        System.out.println("Hello2");

        try {
        	// Or you can block and wait for the upload to finish
        	Long start = System.currentTimeMillis();
        	upload.waitForCompletion();
        	Long end = System.currentTimeMillis();
        	System.out.println("Upload complete.  " + (end-start) + " 毫秒");
        } catch (AmazonClientException amazonClientException) {
        	System.out.println("Unable to upload file, upload was aborted.");
        	amazonClientException.printStackTrace();
        }
    }
}
