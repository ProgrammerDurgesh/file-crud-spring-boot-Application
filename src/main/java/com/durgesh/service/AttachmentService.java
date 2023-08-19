package com.durgesh.service;

import com.durgesh.entity.Attachment;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface AttachmentService {
    Attachment save(MultipartFile file) throws Exception;

    Attachment getById(Integer fileId) throws Exception;

    List<Attachment> getAll();

    void deletedById(Integer fileId) throws Exception;

    void deleteAll();

}
