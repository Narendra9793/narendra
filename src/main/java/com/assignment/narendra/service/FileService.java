package com.assignment.narendra.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
@Service
public class FileService {

    @Autowired
    private Cloudinary cloudinary;

    public String uploadMedia(MultipartFile file, Long userID) throws IOException {
        Map uploadResult = cloudinary.uploader().upload(file.getBytes(),
                ObjectUtils.asMap("resource_type", "auto"));
        return uploadResult.get("secure_url").toString(); // Get the URL of uploaded file
    }

    
    public String deleteFile(String publicId) {
        try {
            Map result = cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
            return result.get("result").toString(); // "ok" if deleted successfully
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }
    
    
} 