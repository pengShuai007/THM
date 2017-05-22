package com.util.OSSTools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;
import java.util.List;

import APP.Comm.Util.HLogger;

import com.aliyun.openservices.ClientException;
import com.aliyun.openservices.ServiceException;
import com.aliyun.openservices.oss.OSSClient;
import com.aliyun.openservices.oss.OSSErrorCode;
import com.aliyun.openservices.oss.OSSException;
import com.aliyun.openservices.oss.model.CannedAccessControlList;
import com.aliyun.openservices.oss.model.GetObjectRequest;
import com.aliyun.openservices.oss.model.OSSObjectSummary;
import com.aliyun.openservices.oss.model.ObjectListing;
import com.aliyun.openservices.oss.model.ObjectMetadata;

/**
 * 该示例代码展示了如果在OSS中创建和删除一个Bucket，以及如何上传和下载一个文件。
 * 
 * 该示例代码的执行过程是：
 * 1. 创建一个Bucket（如果已经存在，则忽略错误码）；
 * 2. 上传一个文件到OSS；
 * 3. 下载这个文件到本地；
 * 4. 清理测试资源：删除Bucket及其中的所有Objects。
 * 
 * 尝试运行这段示例代码时需要注意：
 * 1. 为了展示在删除Bucket时除了需要删除其中的Objects,
 *    示例代码最后为删除掉指定的Bucket，因为不要使用您的已经有资源的Bucket进行测试！
 * 2. 请使用您的API授权密钥填充ACCESS_ID和ACCESS_KEY常量；
 * 3. 需要准确上传用的测试文件，并修改常量uploadFilePath为测试文件的路径；
 *    修改常量downloadFilePath为下载文件的路径。
 * 4. 该程序仅为示例代码，仅供参考，并不能保证足够健壮。
 *
 */


/**
 * 
 * <pre>
 * CopyRight(c) 2014-2015
 * 创建人：石起/罗代华
 * 日期：2014年11月6日23:59:00
 * 描述：OSS上传工具
 * 任务：KYEEAPP-949 
 *</pre>
 */
public class OSSObject {

    public static final int SUCCESS=1;//上传或下载成功
    public static final int FAIL=0;//上传或下载失败
    // 创建Bucket.
    public static void ensureBucket(OSSClient client, String bucketName)
            throws OSSException, ClientException{

        try {
            // 创建bucket
            client.createBucket(bucketName);
        } catch (ServiceException e) {
            if (!OSSErrorCode.BUCKES_ALREADY_EXISTS.equals(e.getErrorCode())) {
                // 如果Bucket已经存在，则忽略
                throw e;
            }
        }
    }

    // 删除一个Bucket和其中的Objects 
    public static void deleteBucket(OSSClient client, String bucketName)
            throws OSSException, ClientException {

        ObjectListing ObjectListing = client.listObjects(bucketName);
        List<OSSObjectSummary> listDeletes = ObjectListing
                .getObjectSummaries();
        for (int i = 0; i < listDeletes.size(); i++) {
            String objectName = listDeletes.get(i).getKey();
            // 如果不为空，先删除bucket下的文件
            client.deleteObject(bucketName, objectName);
        }
        client.deleteBucket(bucketName);
    }

    // 把Bucket设置为所有人可读
    public static void setBucketPublicReadable(OSSClient client, String bucketName)
            throws OSSException, ClientException {
        //创建bucket
        client.createBucket(bucketName);

        //设置bucket的访问权限，public-read-write权限
        client.setBucketAcl(bucketName, CannedAccessControlList.PublicRead);
    }

    /**
     * 
     * 描述: 上传文件<br/>
     * 创建人: shiqi <br/>
     *
     * @param client
     * @param bucketName
     * @param key
     * @param filename
     * @return
     * @throws OSSException
     * @throws ClientException
     * @throws IOException 
     * @since Ver 1.1
     */
    public static int uploadImageFile(OSSClient client, String bucketName, String key, InputStream input)
            throws OSSException, ClientException, IOException {
    	HLogger.info("OSSUtil function uploadImageFile start!");
		HLogger.info("uploadImageFile 图片以流的形式进行上传");
        ObjectMetadata objectMeta = new ObjectMetadata();
        //Edit begin shiqi 2014年11月25日17:20:03 任务号：KYEEAPP-1021 修改原因：设置上传文件大小
        objectMeta.setContentLength(input.available());//长度设为最大值
        //Edit end shiqi 2014年11月25日17:20:03 任务号：KYEEAPP-1021
        // 可以在metadata中标记文件类型
        objectMeta.setContentType("image/jpeg");
        Object result=client.putObject(bucketName, key, input, objectMeta);
        if(result!=null){
        	HLogger.info("OSSUtil function uploadImageFile end!");
    		HLogger.info("uploadImageFile 上传图片成功");
            return SUCCESS;
            
        }
        HLogger.info("OSSUtil function uploadImageFile end!");
		HLogger.info("uploadImageFile 上传图片失败");
        return FAIL;
    }

    // 下载文件
    public static int downloadFile(OSSClient client, String bucketName, String key, String filename)
            throws OSSException, ClientException {
        Object result=client.getObject(new GetObjectRequest(bucketName, key),new File(filename));
        if(result!=null){
            return SUCCESS;
        }
        return FAIL;
    }
    
    /**
     * 
     * 描述: 查询OSS文件外网地址<br/>
     * 创建人: shiqi <br/>
     *
     * @param client
     * @param bucketName
     * @param key
     * @return
     * @throws OSSException
     * @throws ClientException
     * @throws FileNotFoundException 
     * @since Ver 1.1
     */
    public static String queryOSSFile(OSSClient client, String bucketName, String key)
            throws OSSException, ClientException, FileNotFoundException {
        //超时时间设为5秒
        Date expiration=new Date(System.currentTimeMillis()+5000);
        //生成一个用HTTP GET方法访问OSSObject的URL
        URL url=client.generatePresignedUrl(bucketName, key, expiration);
        String fileUrl=url.toString();
        //截取文件外网地址
        fileUrl=fileUrl.substring(0, fileUrl.indexOf("?"));
        return fileUrl;
    }
    
    /**
     * 
     * 描述: 查询OSS文件内网地址<br/>
     * 创建人: shiqi <br/>
     *
     * @param client
     * @param bucketName
     * @param key
     * @return
     * @throws OSSException
     * @throws ClientException
     * @throws FileNotFoundException 
     * @since Ver 1.1
     */
    public static String queryOSSFileInternal(OSSClient client, String bucketName, String key)
            throws OSSException, ClientException, FileNotFoundException {
        //超时时间设为5秒
        Date expiration=new Date(System.currentTimeMillis()+5000);
        //生成一个用HTTP GET方法访问OSSObject的URL
        URL url=client.generatePresignedUrl(bucketName, key, expiration);
        String fileUrl=url.toString();
//        //将外网地址替换为，内网地址
//        fileUrl=fileUrl.replace("oss-cn-qingdao", "oss-cn-qingdao-internal");

        //地址中bucket末尾index
        int bucketLastIndex=fileUrl.indexOf(bucketName)+bucketName.length()+1;
        //key的开头index
        int keyIndex=fileUrl.indexOf(key)-"aliyuncs.com".length()-2;
        //获取区域地址
        String addressOSS=fileUrl.substring(bucketLastIndex, keyIndex);
        //将外网地址，替换为内网地址
        fileUrl=fileUrl.replace(addressOSS, addressOSS+"-internal");
        //截取文件内网地址
        fileUrl=fileUrl.substring(0, fileUrl.indexOf("?"));
        
        return fileUrl;
    }
    
    /**
     * 
     * 描述: 删除指定bucket中的object文件<br/>
     * 创建人: shiqi <br/>
     *
     * @param client
     * @param bucketName
     * @param key
     * @throws OSSException
     * @throws ClientException 
     * @since Ver 1.1
     */
    public static void delOSSFile(OSSClient client, String bucketName, String key)
            throws OSSException, ClientException {
        client.deleteObject(bucketName, key);
    }
}
