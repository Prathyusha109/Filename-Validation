package com.processing.services;

import com.processing.model.DocFeedResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileUploadService {
    public DocFeedResponse validateFileData(MultipartFile file) throws IOException;
}

