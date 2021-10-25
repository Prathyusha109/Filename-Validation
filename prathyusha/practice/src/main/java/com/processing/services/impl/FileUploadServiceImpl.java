package com.processing.services.impl;

import com.processing.helper.FileValidateWorker;
import com.processing.model.DocFeed;
import com.processing.model.DocFeedResponse;
import com.processing.model.Status;
import com.processing.services.FileUploadService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

@Service
public class FileUploadServiceImpl implements FileUploadService {

    private static final Logger LOGGER = LoggerFactory.getLogger("FileUploadServiceImpl.class");

    @Override
    public DocFeedResponse validateFileData(MultipartFile file) throws IOException {
        DocFeedResponse docFeedResponse = new DocFeedResponse();
        docFeedResponse.setOriginalFileName(file.getOriginalFilename());
        final Status status = FileValidateWorker.validateInput(file);
        if (null != status) {
            docFeedResponse.setStatus(status);
            return docFeedResponse;
        }
        InputStream is = file.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String line = "";
        int iteration = 0;
        int successRowCount = 0;
        int failureRowCount = 0;

        String errorFileName = "error_" + file.getOriginalFilename();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(baos));
        try {
            while ((line = br.readLine()) != null) {
                if (iteration == 0) {
                    iteration++;
                } else {
                    String data[] = line.split(",");
                    if (insertData(data)) {
                        successRowCount++;
                    } else {
                        failureRowCount++;
                        writer.write(line);
                        writer.write("\n");
                    }
                }
            }
            writer.flush();
            writer.close();
            byte[] bytes = baos.toByteArray();


            if (failureRowCount == 0) {
                docFeedResponse.setErrorFile(null);
                docFeedResponse.setErrorFileName(null);
            } else {

                docFeedResponse.setErrorFile(new MultipartFile() {
                    @Override
                    public String getName() {
                        return errorFileName;
                    }

                    @Override
                    public String getOriginalFilename() {
                        return errorFileName;
                    }

                    @Override
                    public String getContentType() {
                        return "application/vnd.ms-excel";
                    }

                    @Override
                    public boolean isEmpty() {
                        return false;
                    }

                    @Override
                    public long getSize() {
                        return baos.size();
                    }

                    @Override
                    public byte[] getBytes() throws IOException {
                        return bytes;
                    }

                    @Override
                    public InputStream getInputStream() throws IOException {
                        return new ByteArrayInputStream(bytes);
                    }

                    @Override
                    public void transferTo(File dest) throws IOException, IllegalStateException {

                    }
                });
                docFeedResponse.setErrorFileName(errorFileName);

            }
            docFeedResponse.setFailureRowCount(failureRowCount);
            docFeedResponse.setSuccessRowCount(successRowCount);

        } catch (Exception e) {
            LOGGER.error("exception in reading the file = {}", e);
        }
        return docFeedResponse;
    }

    private static boolean insertData(String[] feed) {
        boolean flag = false;
        try {
            if (feed != null && feed.length == 4) {
                DocFeed docFeed = new DocFeed();
                docFeed.setEmpFirstName(feed[0]);
                docFeed.setEmpLastName(feed[1]);
                docFeed.setEmpCity(feed[2]);
                docFeed.setEmpId(Integer.parseInt(feed[3]));

                //database insertion
                //if there is failure with insertion or any network issue, we will return
                //the boolean as false, will let customer to try again or re-upload.

                flag = true;
            }
        } catch (Exception e) {
            LOGGER.error("error in processing the feed, feed will be sent to error file = {}", e);
            flag = false;
        }
        return flag;
    }
}
