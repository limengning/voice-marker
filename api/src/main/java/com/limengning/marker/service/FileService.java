package com.limengning.marker.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.limengning.marker.config.MinioConfiguration;
import com.limengning.marker.entity.FileEntity;
import com.limengning.marker.entity.ProjectEntity;
import com.limengning.marker.mapper.FileMapper;
import com.limengning.marker.mapper.ProjectMapper;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
public class FileService extends ServiceImpl<FileMapper, FileEntity> {

    private final MinioConfiguration minioConfiguration;
    private final ProjectMapper projectMapper;

    public FileService(MinioConfiguration minioConfiguration, ProjectMapper projectMapper) {
        this.minioConfiguration = minioConfiguration;
        this.projectMapper = projectMapper;
    }

    public IPage<FileEntity> page(long pageIndex, long pageSize, Integer projectId) {
        return lambdaQuery()
                .eq(FileEntity::getProjectId, projectId)
                .page(new Page<>(pageIndex, pageSize));
    }

    public FileEntity save(InputStream inputStream, String contentType, String fileName, Integer projectId) {
        if (!projectExist(projectId)) {
            throw new RuntimeException("项目不存在");
        }
        var src = upload(inputStream, contentType);
        fileName = getUniqueFileName(projectId, fileName);
        var file = new FileEntity();
        file.setName(fileName);
        file.setSrc(src);
        file.setProjectId(projectId);
        save(file);
        return file;
    }

    private boolean projectExist(Integer projectId) {
        LambdaQueryChainWrapper<ProjectEntity> queryChainWrapper = new LambdaQueryChainWrapper<>(projectMapper);
        return queryChainWrapper.eq(ProjectEntity::getId, projectId).oneOpt().isPresent();
    }

    private String getUniqueFileName(Integer projectId, String fileName) {
        var fileNames = lambdaQuery()
                .eq(FileEntity::getProjectId, projectId)
                .likeRight(FileEntity::getName, fileName)
                .select(FileEntity::getName)
                .list()
                .stream()
                .map(FileEntity::getName)
                .collect(Collectors.toSet());
        var format = fileName + " (%d)";
        int i = 1;
        while (fileNames.contains(fileName)) {
            fileName = String.format(format, i);
            i++;
        }
        return fileName;
    }

    private String upload(InputStream inputStream, String contentType) {
        try {
            var client = MinioClient.builder()
                    .endpoint(minioConfiguration.getUrl())
                    .credentials(minioConfiguration.getAccessKey(), minioConfiguration.getSecretKey())
                    .build();
            var objName = UUID.randomUUID().toString();
            client.putObject(
                    PutObjectArgs.builder()
                            .bucket(minioConfiguration.getBucketName())
                            .stream(inputStream, -1, 10485760)
                            .object(objName)
                            .contentType(contentType)
                            .build());
            return String.join("/", minioConfiguration.getUrl(), minioConfiguration.getBucketName(), objName);
        } catch (Throwable ex) {
            log.error("Minio exception: ", ex);
            throw new RuntimeException("Minio exception: ", ex);
        }
    }

    public void rename(Integer id, String name) {
        name = getUniqueFileName(id, name);
        lambdaUpdate()
                .eq(FileEntity::getId, id)
                .set(FileEntity::getName, name)
                .update();
    }
}
