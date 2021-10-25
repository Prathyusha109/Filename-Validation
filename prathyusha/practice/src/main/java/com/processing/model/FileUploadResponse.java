package com.processing.model;

import java.io.File;
import java.util.List;

public class FileUploadResponse {
    List<DocFeedResponse> docFeedResponseList;
    File zip;

    public List<DocFeedResponse> getDocFeedResponseList() {
        return docFeedResponseList;
    }

    public void setDocFeedResponseList(List<DocFeedResponse> docFeedResponseList) {
        this.docFeedResponseList = docFeedResponseList;
    }

    public File getZip() {
        return zip;
    }

    public void setZip(File zip) {
        this.zip = zip;
    }
}
