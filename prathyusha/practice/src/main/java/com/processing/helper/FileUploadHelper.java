package com.processing.helper;

import com.processing.model.DocFeedResponse;
import com.processing.model.FileUploadResponse;
import com.processing.services.FileUploadService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class FileUploadHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger("FileUploadHelper.class");

    public static FileUploadResponse getResponse(FileUploadService fileUploadService, List<MultipartFile> files) throws ExecutionException, InterruptedException, IOException {

        FileUploadResponse response = new FileUploadResponse();
        List<DocFeedResponse> docFeedResponses = new ArrayList<>();
        ExecutorService executor = Executors.newFixedThreadPool(files.size());
        List<Future<DocFeedResponse>> list = new ArrayList<>();
        for (MultipartFile obj : files) {
            Callable<DocFeedResponse> callable = new FileUploadExecutor(fileUploadService, obj);
            Future<DocFeedResponse> future = executor.submit(callable);
            list.add(future);
        }
        for (Future<DocFeedResponse> fut : list) {
            docFeedResponses.add(fut.get());
            fut.cancel(true);
        }
        executor.shutdown();
        if (executor.isShutdown()) {
            LOGGER.info("FileUploadExecutor shutdown");
        }
        response.setZip(zip(docFeedResponses));
        response.setDocFeedResponseList(docFeedResponses);
        return response;
    }

    public static File zip(List<DocFeedResponse> docFeedResponses) {
        File zipfile = new File("failed");
        // Create a buffer for reading the files
        byte[] buf = new byte[10 * 1024];
        try {
            // create the ZIP file
            ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipfile));
            // compress the files
            for (DocFeedResponse docFeedResponse : docFeedResponses) {
                if (null != docFeedResponse.getErrorFile()) {
                    InputStream inputStream =  new BufferedInputStream(docFeedResponse.getErrorFile().getInputStream());
                    // add ZIP entry to output stream
                    out.putNextEntry(new ZipEntry(docFeedResponse.getErrorFile().getOriginalFilename()));
                    // transfer bytes from the file to the ZIP file
                    int len;
                    while ((len = inputStream.read(buf)) > 0) {
                        out.write(buf, 0, len);
                    }
                    // complete the entry
                    out.closeEntry();
                    inputStream.close();
                }
            }
            // complete the ZIP file
            out.close();
            return zipfile;
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
        return null;
    }
}
