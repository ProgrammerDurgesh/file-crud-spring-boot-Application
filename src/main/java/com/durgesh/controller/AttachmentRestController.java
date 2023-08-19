package com.durgesh.controller;

import com.durgesh.entity.Attachment;
import com.durgesh.service.AttachmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping(value = "/file")
public class AttachmentRestController {
    private final AttachmentService attachmentService;

    @Autowired
    public AttachmentRestController(AttachmentService attachmentService) {
        this.attachmentService = attachmentService;
    }

    @PostMapping
    public ResponseEntity<?> saveAttachment(@RequestParam("file") MultipartFile file) throws Exception {
        Attachment attachment = attachmentService.save(file);
        return new ResponseEntity<>(attachment, HttpStatus.OK);
    }

    @GetMapping(value = "/{attachmentId}")
    public ResponseEntity<?> getById(@PathVariable(value = "attachmentId") Integer attachmentId) throws Exception {
        Attachment attachment = attachmentService.getById(attachmentId);
        return new ResponseEntity<>(attachment, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getAll() {
        List<Attachment> attachment = attachmentService.getAll();
        return new ResponseEntity<>(attachment, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{attachmentId}")
    public ResponseEntity<?> deleteById(@PathVariable(value = "attachmentId") Integer attachmentId) throws Exception {
        attachmentService.deletedById(attachmentId);
        Attachment attachment = attachmentService.getById(attachmentId);
        if (ObjectUtils.isEmpty(attachment)) return new ResponseEntity<>("Record Deleted", HttpStatus.OK);
        else return new ResponseEntity<>("Record Not  Deleted", HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteAll() {
        attachmentService.deleteAll();
        List<Attachment> attachments = attachmentService.getAll();
        if (ObjectUtils.isEmpty(attachments)) return new ResponseEntity<>("All Record Deleted", HttpStatus.OK);
        else return new ResponseEntity<>("All Record Not  Deleted", HttpStatus.OK);

    }


}
