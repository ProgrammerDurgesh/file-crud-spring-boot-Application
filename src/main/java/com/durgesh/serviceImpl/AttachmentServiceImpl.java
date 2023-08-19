package com.durgesh.serviceImpl;

import com.durgesh.entity.Attachment;
import com.durgesh.repo.AttachmentRepo;
import com.durgesh.service.AttachmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class AttachmentServiceImpl implements AttachmentService {

    private final AttachmentRepo attachmentRepo;

    @Autowired
    public AttachmentServiceImpl(AttachmentRepo attachmentRepo) {
        this.attachmentRepo = attachmentRepo;
    }

    @Override
    public Attachment save(MultipartFile file) throws Exception {
        //save file In DB
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        try {
            if (fileName.contains("..")) {
                throw new Exception("Filename contains invalid path sequence " + fileName);
            }

            Attachment attachment = new Attachment(fileName, file.getContentType(), file.getBytes());
            return attachmentRepo.save(attachment);

        } catch (Exception e) {
            throw new Exception("Could not save File: " + fileName);
        }
    }

    @Override
    public Attachment getById(Integer fileId) throws Exception {
        return attachmentRepo.findById(fileId).orElseThrow(() -> new Exception("Record Not Found "));
    }

    @Override
    public List<Attachment> getAll() {
        List<Attachment> attachments = null;
        try {
            attachments = attachmentRepo.findAll();
            if (ObjectUtils.isEmpty(attachments)) throw new Exception("Record Not Found");
            else return attachments;
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return attachments;
    }

    @Override
    public void deletedById(Integer fileId) throws Exception {
        try {
            if (!ObjectUtils.isEmpty(this.getById(fileId))) {
                attachmentRepo.deleteById(fileId);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void deleteAll() {
        try {
            if (!ObjectUtils.isEmpty(this.getAll())) attachmentRepo.deleteAll();
        } catch (Exception exception) {
            exception.printStackTrace();
        }

    }
}
