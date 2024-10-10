package show.ywy.service.file.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.common.auth.CredentialsProviderFactory;
import com.aliyun.oss.common.auth.EnvironmentVariableCredentialsProvider;
import com.aliyun.oss.internal.OSSHeaders;
import com.aliyun.oss.model.*;
import com.aliyuncs.exceptions.ClientException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import show.ywy.config.OosConfig;
import show.ywy.service.file.entity.FileEntity;
import show.ywy.service.file.service.FileService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author yzs
 */
@Service("OSSFileService")
public class FileOSSServiceImpl extends FileService {

    @Resource
    private OosConfig oosConfig;

    @Override
    public boolean uploadImpl(FileEntity fileEntity, MultipartFile file) {
        try {
            EnvironmentVariableCredentialsProvider credentialsProvider = CredentialsProviderFactory.newEnvironmentVariableCredentialsProvider();
            OSS ossClient = new OSSClientBuilder().build(oosConfig.getEndpoint(), credentialsProvider);
            InputStream inputStream = file.getInputStream();
            // 创建PutObjectRequest对象。
            PutObjectRequest putObjectRequest = new PutObjectRequest(oosConfig.getBucketName(), file.getName(), inputStream);
            // 如果需要上传时设置存储类型和访问权限，请参考以下示例代码。
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setHeader(OSSHeaders.OSS_STORAGE_CLASS, StorageClass.Standard.toString());
            metadata.setObjectAcl(CannedAccessControlList.Private);
            putObjectRequest.setMetadata(metadata);
            // 创建PutObject请求。
            PutObjectResult result = ossClient.putObject(putObjectRequest);
            System.out.println("upload");
        } catch (ClientException | IOException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    @Override
    public void downloadImpl(FileEntity fileEntity, HttpServletResponse response) {
        System.out.println("download");
        EnvironmentVariableCredentialsProvider credentialsProvider;
        try {
            credentialsProvider = CredentialsProviderFactory.newEnvironmentVariableCredentialsProvider();
        } catch (ClientException e) {
            throw new RuntimeException(e);
        }
        OSS ossClient = new OSSClientBuilder().build(oosConfig.getEndpoint(), credentialsProvider);
        try {
            // ossObject包含文件所在的存储空间名称、文件名称、文件元数据以及一个输入流。
            OSSObject ossObject = ossClient.getObject(oosConfig.getBucketName(), fileEntity.getFileName());
            // 读取文件内容。
            InputStream objectContent = ossObject.getObjectContent();
        } catch (OSSException oe) {
            System.out.println("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            System.out.println("Error Message:" + oe.getErrorMessage());
            System.out.println("Error Code:" + oe.getErrorCode());
            System.out.println("Request ID:" + oe.getRequestId());
            System.out.println("Host ID:" + oe.getHostId());
        } catch (Throwable ce) {
            System.out.println("Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message:" + ce.getMessage());
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }

        return new File("");
    }

    @Override
    public boolean deleteImpl(FileEntity fileName) {
        return false;
    }
}
