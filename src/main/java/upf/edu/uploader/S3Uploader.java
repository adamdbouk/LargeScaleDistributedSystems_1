package upf.edu.uploader;

import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.auth.profile.ProfilesConfigFile;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

import java.io.File;

public class S3Uploader implements Uploader {
    AmazonS3 s3Client;
    String bucketName;
    String prefix; // filePath

    public S3Uploader(String bucketName, String prefix, String name) {
        // We check if the given profile name is valid, we want to stop the execution if not so we use a try catch block.
        try {
            if (new ProfilesConfigFile().getAllBasicProfiles().get(name) == null) {
                throw new NullPointerException("No profile named " + name + " in your configuration file.");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }

        // We create the s3 client with the profile corresponding to name
        // with region US_EAST_1 as it is the only one we can use.
        s3Client = AmazonS3ClientBuilder.standard()
                .withRegion(Regions.US_EAST_1)
                .withCredentials(new ProfileCredentialsProvider(name))
                .build();

        this.bucketName = bucketName;
        this.prefix = prefix;

    }

    @Override
    public void upload(String file) {
        /* Not creating the bucket as it is created public and we don't want a public bucket.
        if (!s3Client.doesBucketExist(bucketName)) {
            s3Client.createBucket(bucketName);
        } */

        try {
            File fileObj = new File(file); // The file we will upload
            String fullKey = this.prefix + "/" + fileObj.getName(); // filePath "s3://" + bucketName + "/" + prefix + "/" filename;
            s3Client.putObject(this.bucketName, fullKey, fileObj);
        } catch (Exception ex) {
            System.out.println("The bucket does not exist or it already exists but you don't have access to it.");
            System.out.println("Check the profile credentials and the access to the bucket.");
        }
    }
}
