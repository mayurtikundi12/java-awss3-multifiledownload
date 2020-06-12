package org.example;



import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.transfer.MultipleFileDownload;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.TransferManagerBuilder;

import java.io.File;
/**
 * Download objects from an Amazon S3 bucket using S3 TransferManager.
 *
 * This code expects that you have AWS credentials set up per:
 * http://docs.aws.amazon.com/java-sdk/latest/developer-guide/setup-credentials.html
 */
public class App {
    public static void downloadDir(String bucket_name, String key_prefix,
                                   String dir_path, boolean pause) {
        AWSCredentials credentials = new BasicAWSCredentials(
                "AKIA3HFAEGQUHW5DL6V2",
                "A6YcfWFpscu850VKHV6lyOgIXFYFyG62o8VyFQbz"
        );

        AmazonS3 s3client = AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(Regions.AP_SOUTH_1)
                .build();

        System.out.println("downloading to directory: " + dir_path +
                (pause ? " (pause)" : ""));

        TransferManager xfer_mgr = TransferManagerBuilder.standard().withS3Client(s3client).build();

        try {
            MultipleFileDownload xfer = xfer_mgr.downloadDirectory(
                    bucket_name, key_prefix, new File(dir_path),true);



        } catch (AmazonServiceException e) {
            System.err.println(e.getErrorMessage());
            System.exit(1);
        }
        xfer_mgr.shutdownNow();
        System.out.println("completed downloading");
    }



    public static void main(String[] args) {

        downloadDir("m-source-bucket", "folder1/", "/home/ruyam/projects/wahidSirProjects/backend/sirLatest/temp/awss3multifiledownload/downloads/", false);

    }
//    export AWS_ACCESS_KEY_ID=XXXXX
//    export AWA_SECRET=Xxxxx
//    export AWS_REGION="ap-south-1"

}
