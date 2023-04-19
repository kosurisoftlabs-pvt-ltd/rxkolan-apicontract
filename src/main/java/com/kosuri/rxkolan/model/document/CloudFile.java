package com.kosuri.rxkolan.model.document;

public interface CloudFile {

    String getFileUrl();

    String getFileName();

    String getFilePublicUrl();

    byte[] getContentBytes();
}