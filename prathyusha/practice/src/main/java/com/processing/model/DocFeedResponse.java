package com.processing.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.web.multipart.MultipartFile;

@JsonIgnoreProperties(ignoreUnknown=true)
public class DocFeedResponse {

    private String originalFileName;
    private String errorFileName;
    private int failureRowCount;
    private int successRowCount;
    private Status status;
    @JsonIgnore
    private MultipartFile errorFile;

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getOriginalFileName() {
        return originalFileName;
    }

    public void setOriginalFileName(String originalFileName) {
        this.originalFileName = originalFileName;
    }

    public String getErrorFileName() {
        return errorFileName;
    }

    public void setErrorFileName(String errorFileName) {
        this.errorFileName = errorFileName;
    }

    public int getFailureRowCount() {
        return failureRowCount;
    }

    public void setFailureRowCount(int failureRowCount) {
        this.failureRowCount = failureRowCount;
    }

    public int getSuccessRowCount() {
        return successRowCount;
    }

    public void setSuccessRowCount(int successRowCount) {
        this.successRowCount = successRowCount;
    }

    public MultipartFile getErrorFile() {
        return errorFile;
    }

    public void setErrorFile(MultipartFile errorFile) {
        this.errorFile = errorFile;
    }

    @Override
    public String toString() {
        return "DocFeedResponse{" +
                "originalFileName='" + originalFileName + '\'' +
                ", errorFileName='" + errorFileName + '\'' +
                ", failureRowCount=" + failureRowCount +
                ", successRowCount=" + successRowCount +
                ", status=" + status +
                '}';
    }
}
