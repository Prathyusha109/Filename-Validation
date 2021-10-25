package com.processing.helper;

import com.processing.model.DocFeedResponse;
import com.processing.services.FileUploadService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.util.concurrent.Callable;

public class FileUploadExecutor implements Callable<DocFeedResponse> {
    private static final Logger LOGGER = LoggerFactory.getLogger("FileUploadExecutor.class");

    @Autowired
    FileUploadService fileUploadService;
    MultipartFile multipartFile;

    public FileUploadExecutor(FileUploadService fileUploadService, MultipartFile multipartFile) {
        this.fileUploadService = fileUploadService;
        this.multipartFile = multipartFile;
    }

    @Override
    public DocFeedResponse call() throws Exception {
        DocFeedResponse docFeedResponse = fileUploadService.validateFileData(multipartFile);
        return docFeedResponse;
    }
}
