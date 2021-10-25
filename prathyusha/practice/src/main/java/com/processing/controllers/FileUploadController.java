package com.processing.controllers;

import com.processing.helper.FileUploadHelper;
import com.processing.model.FileUploadResponse;
import com.processing.services.FileUploadService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "/customer")
public class FileUploadController {

    private static final Logger LOGGER = LoggerFactory.getLogger("FileUploadController.class");

    @Autowired
    public FileUploadService fileUploadService;

    @RequestMapping(
            value = {"/docUpload"},
            method = RequestMethod.POST)
    @ResponseBody
    public Object docUpload(HttpServletResponse response, @RequestParam(value = "file", required = true) List<MultipartFile> files)
            throws Exception {
        LOGGER.info("Entering FileUploadsController.docUpload() : ");
        try {
            FileUploadResponse uploadResponse = FileUploadHelper.getResponse(fileUploadService, files);
            return uploadResponse;

        } finally {
            files = null; //save memory, before GC grabs it
        }
    }

}
