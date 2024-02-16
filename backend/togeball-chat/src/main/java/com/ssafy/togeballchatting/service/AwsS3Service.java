package com.ssafy.togeballchatting.service;

import io.awspring.cloud.s3.ObjectMetadata;
import io.awspring.cloud.s3.S3Resource;
import io.awspring.cloud.s3.S3Template;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AwsS3Service {

    private final S3Template s3Template;

    @Value("${spring.cloud.aws.s3.bucket}")
    private String bucket;

    public String upload(MultipartFile multipartFile) throws IOException {
        String originalFilename = multipartFile.getOriginalFilename();
        UUID uuid = UUID.nameUUIDFromBytes(originalFilename.getBytes());

        ObjectMetadata metadata = ObjectMetadata.builder()
                .contentLength(multipartFile.getSize())
                .contentType(multipartFile.getContentType())
                .build() ;

        S3Resource resource = s3Template.upload(bucket, uuid.toString(), multipartFile.getInputStream(), metadata);
        return resource.getURL().toString();
    }
}
