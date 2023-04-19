package com.kosuri.rxkolan.model.document.cloud;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.InputStream;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostCloudFileRequest {

    @NotNull
    @NotEmpty
    public InputStream fileStream;

    @NotNull
    public String filePath;
}