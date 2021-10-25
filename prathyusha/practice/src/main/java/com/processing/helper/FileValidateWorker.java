package com.processing.helper;

import com.processing.constants.AppConstants;
import com.processing.model.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;


public class FileValidateWorker {

    private static final String[] DOC_TYPES = {"csv"};
    public static final long MAX_FILE_SIZE = 5242880;

    public static final Logger LOGGER = LoggerFactory
            .getLogger(FileValidateWorker.class);


    public static Status validateInput(MultipartFile file) {

        Status status = null;

        try {
            if (file != null) {
                String fileName = file.getOriginalFilename();
                String fileExtn = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());

                // file type validation
                if (!FileValidateWorker.isValidFileType(fileExtn)) {
                    //   response.setStatus(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE);
                    status = new Status();
                    status.setRespCd("002");
                    status.setRespName(AppConstants.ERROR_CODE_002);
                    status.setRespDesc("The document doesn't match with eligible file formats.");
                    return status;
                }

                // content type validation
                if (!isContentTypeValidation(file.getContentType(), fileExtn)) {
                 //   response.setStatus(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE);
                    status = new Status();
                    status.setRespCd("001");
                    status.setRespName(AppConstants.ERROR_CODE_001);
                    status.setRespDesc("The document doesn't match with type.");
                    return status;
                }

                //file size validation
                if (!FileValidateWorker.isValidFileSize(file.getSize(), MAX_FILE_SIZE)) {
                    status = new Status();
                    status.setRespCd("003");
                    status.setRespName(AppConstants.ERROR_CODE_003);
                    status.setRespDesc("The document exceeds the file size limitations.");
                    return status;
                }

                // file existance
                if (file.getSize() == 0) {
                 //   response.setStatus(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE);
                    status = new Status();
                    status.setRespCd("004");
                    status.setRespName(AppConstants.ERROR_CODE_004);
                    status.setRespDesc("The document is empty.");
                    return status;
                }
            }else{
              //  response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                status = new Status();
                status.setRespCd("000");
                status.setRespName(AppConstants.ERROR_CODE_000);
                status.setRespDesc("The document doesn't exists.");
                return status;
            }
        } catch (Exception e) {
            LOGGER.error("Exception in validating the file = {}", e);
        }
        return status;
    }

    /**
     * @param fileExtn
     * @return
     */
    public static boolean isValidFileType(String fileExtn) {
        boolean result = false;
        for (String fileType : DOC_TYPES) {
            if (fileType.equalsIgnoreCase(fileExtn)) {
                return true;
            }
        }
        return result;
    }

    /**
     * @param fileContent
     * @param fileExtn
     * @return
     */
    public static boolean isContentTypeValidation(String fileContent, String fileExtn) {
        if (AppConstants.fileExtensionMap.get(fileExtn.toLowerCase()).equalsIgnoreCase(fileContent)) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isValidFileSize(
            long fileSize, long maxDocFileSize) {
        boolean ret = false;
        if (!(fileSize > maxDocFileSize)) {
            ret = true;
        }
        return ret;
    }
}
