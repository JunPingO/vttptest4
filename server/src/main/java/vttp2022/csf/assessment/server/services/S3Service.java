package vttp2022.csf.assessment.server.services;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3ObjectInputStream;

@Service
public class S3Service {
    
    @Autowired
    private AmazonS3 s3Client;

    public String upload(byte[] map) throws IOException {

        Map<String, String> userData = new HashMap<>();
        userData.put("name", "ojp");

        // Metadata of the file
        // ObjectMetadata metadata = new ObjectMetadata();
        // metadata.setContentLength(file.getSize());
        // metadata.setContentType(file.getContentType());
        // metadata.setUserMetadata(userData);

        String key = UUID.randomUUID().toString().substring(0, 8);

        try (FileOutputStream fos = new FileOutputStream("C:/portableWorkspace/csf-assessment-template/test")) {
            fos.write(map);
        }
        // MultipartFile uploadedFile = MultipartHttpServletRequest(request).getFile("C:/portableWorkspace/csf-assessment-template/test");
        
        InputStream targetStream = new ByteArrayInputStream(map);

        // PutObjectRequest putReq = new PutObjectRequest(
        //     "trololo", // bucket name
        //     "vttptest4/%s".formatted(key), //key
        //     uploadedFile.toInputStream()); //inputstream;

        // PutObjectRequest.builder().bucket("trololo").build(),ByteArrayInputStream(map);


        // PutObjectResponse   response = s3.putObject();

        // Allow public access
        // putReq.withCannedAcl(CannedAccessControlList.PublicRead);

        // s3Client.putObject(putReq);

        return key;
    }
}
