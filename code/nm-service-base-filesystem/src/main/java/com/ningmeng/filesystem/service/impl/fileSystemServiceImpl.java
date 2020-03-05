package com.ningmeng.filesystem.service.impl;

import com.alibaba.fastjson.JSON;
import com.ningmeng.filesystem.dao.CoursePicRepository;
import com.ningmeng.filesystem.dao.FileSystemRepository;
import com.ningmeng.filesystem.service.FileSystemService;
import com.ningmeng.framework.domain.course.CoursePic;
import com.ningmeng.framework.domain.filesystem.FileSystem;
import com.ningmeng.framework.domain.filesystem.response.FileSystemCode;
import com.ningmeng.framework.domain.filesystem.response.UploadFileResult;
import com.ningmeng.framework.exception.CustomExceptionCast;
import com.ningmeng.framework.model.response.CommonCode;
import com.ningmeng.framework.model.response.ResponseResult;
import org.apache.commons.lang3.StringUtils;
import org.csource.fastdfs.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;
import java.util.Optional;

@Service
public class fileSystemServiceImpl implements FileSystemService {
    private static final Logger LOGGER = LoggerFactory.getLogger(FileSystemService.class);
    @Value("${ningmeng.fastdfs.tracker_servers}")
    String tracker_servers;
    @Value("${ningmeng.fastdfs.connect_timeout_in_seconds}")
    int connect_timeout_in_seconds;
    @Value("${ningmeng.fastdfs.network_timeout_in_seconds}")
    int network_timeout_in_seconds;
    @Value("${ningmeng.fastdfs.charset}")
    String charset;
    @Autowired
    private FileSystemRepository fileSystemRepository;
    @Autowired
    private CoursePicRepository coursePicRepository;

    //加载fdfs的配置
    private void initFdfsConfig() {
        try {
            ClientGlobal.initByTrackers(tracker_servers);
            ClientGlobal.setG_connect_timeout(connect_timeout_in_seconds);
            ClientGlobal.setG_network_timeout(network_timeout_in_seconds);
            ClientGlobal.setG_charset(charset);
        } catch (Exception e) {
            e.printStackTrace();
            //初始化文件系统出错
            CustomExceptionCast.cast(FileSystemCode.FS_UPLOADFILE_FILEISNULL);
        }
    }

    //上传文件
    public UploadFileResult upload(MultipartFile file,
                                   String filetag,
                                   String businesskey,
                                   String metadata) {
        if (file == null) {
            CustomExceptionCast.cast(FileSystemCode.FS_UPLOADFILE_FILEISNULL);
        }
        //上传文件到fdfs
        String fileId = fdfs_upload(file); //创建文件信息对象
        FileSystem fileSystem = new FileSystem(); //文件id
        fileSystem.setFileId(fileId); //文件在文件系统中的路径
        fileSystem.setFilePath(fileId); //业务标识
        fileSystem.setBusinesskey(businesskey); //标签
        fileSystem.setFiletag(filetag); //元数据
        if (StringUtils.isNotEmpty(metadata)) {
            try {
                Map map = JSON.parseObject(metadata, Map.class);
                fileSystem.setMetadata(map);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }//名称
        fileSystem.setFileName(file.getOriginalFilename()); //大小
        fileSystem.setFileSize(file.getSize()); //文件类型
        fileSystem.setFileType(file.getContentType());
        fileSystemRepository.save(fileSystem);
        return new UploadFileResult(CommonCode.SUCCESS, fileSystem);
    }//上传文件到fdfs，返回文件id

    public String fdfs_upload(MultipartFile file) {
        try {//加载fdfs的配置 initFdfsConfig(); //创建tracker client
            TrackerClient trackerClient = new TrackerClient(); //获取trackerServer \
            TrackerServer trackerServer = trackerClient.getConnection(); //获取storage
            StorageServer storeStorage = trackerClient.getStoreStorage( trackerServer ); //创建storage
            StorageClient1 storageClient1 = new StorageClient1( trackerServer, storeStorage ); //上传文件 //文件字节 \
            byte[] bytes = file.getBytes(); //文件原始名称
            String originalFilename = file.getOriginalFilename(); //文件扩展名 \
            String extName = originalFilename.substring( originalFilename.lastIndexOf( "." ) + 1 ); //文件id
            String file1 = storageClient1.upload_file1( bytes, extName, null );
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    //添加课程图片
    @Transactional
    public ResponseResult saveCoursePic(String courseId, String pic) {
        //查询课程图片
        Optional<CoursePic> picOptional = coursePicRepository.findById(courseId);
        CoursePic coursePic = null;
        if (picOptional.isPresent()) {
            coursePic = picOptional.get();
        }
        //没有课程图片则新建对象
        if (coursePic == null) {
            coursePic = new CoursePic();
        }
        coursePic.setCourseid(courseId);
        coursePic.setPic(pic); //保存课程图片
        coursePicRepository.save(coursePic);
        return new ResponseResult(CommonCode.SUCCESS);
    }
    public CoursePic findCoursepic(String courseId) {
        return coursePicRepository.findOne(courseId );
    }
    //删除课程图片
    @Transactional
    public ResponseResult deleteCoursePic(String courseId) {
        //执行删除，返回1表示删除成功，返回0表示删除失败
        long result = coursePicRepository.deleteByCourseid(courseId);
        if(result>0){
            return new ResponseResult(CommonCode.SUCCESS);
        }
        return new ResponseResult(CommonCode.FAIL);
    }
}
