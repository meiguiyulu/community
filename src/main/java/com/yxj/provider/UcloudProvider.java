package com.yxj.provider;

import cn.ucloud.ufile.UfileClient;
import cn.ucloud.ufile.api.object.ObjectConfig;
import cn.ucloud.ufile.auth.ObjectAuthorization;
import cn.ucloud.ufile.auth.UfileObjectLocalAuthorization;
import cn.ucloud.ufile.bean.PutObjectResultBean;
import cn.ucloud.ufile.exception.UfileClientException;
import cn.ucloud.ufile.exception.UfileServerException;
import com.yxj.exception.CustomizeException;
import com.yxj.exception.MyErrorCode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.UUID;

/**
 * @author LYJ
 * @create 2021-07-31 16:36
 */

@Service
public class UcloudProvider {

    @Value("${ucloud.ufile.public-key}")
    private String publicKey;

    @Value("${ucloud.ufile.private-key}")
    private String privateKey;

    @Value("${ucloud.ufile.bucket-name}")
    private String bucketName;

    @Value("${ucloud.ufile.region}")
    private String region;

    @Value("${ucloud.ufile.proxySuffix}")
    private String proxySuffix;

    @Value("${ucloud.ufile.experis}")
    private int experis;

    public String upload(InputStream fileStream, String mimeType, String fileName) {


        String generateFileName;
        String[] split = fileName.split("\\.");
        if (split.length > 1) {
            generateFileName = UUID.randomUUID().toString() + "." + split[split.length - 1];
            System.out.println("generateFileName" + generateFileName);
        } else {
            throw new CustomizeException((MyErrorCode.FILE_UPLOAD_FAIL));
        }

        try {
            // Bucket相关API的授权器
            ObjectAuthorization objectAuthorization = new UfileObjectLocalAuthorization(
                    publicKey, privateKey);
            // 对象操作需要ObjectConfig来配置您的地区和域名后缀
            ObjectConfig config = new ObjectConfig(region, proxySuffix);
            PutObjectResultBean response = UfileClient.object(objectAuthorization, config)
                    .putObject(fileStream, mimeType)
                    .nameAs(generateFileName)
                    .toBucket(bucketName)
                    .setOnProgressListener((bytesWritten, contentLength) -> {
                    })
                    .execute();
            if (response != null && response.getRetCode() == 0) {
                String url = UfileClient.object(objectAuthorization, config)
                        .getDownloadUrlFromPrivateBucket(generateFileName, bucketName, experis)
                        .createUrl();
                return url;
            } else {
                throw new CustomizeException((MyErrorCode.FILE_UPLOAD_FAIL));
            }

        } catch (UfileClientException | UfileServerException e) {
            throw new CustomizeException((MyErrorCode.FILE_UPLOAD_FAIL));
        }
    }

}
