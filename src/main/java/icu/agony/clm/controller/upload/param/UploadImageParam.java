package icu.agony.clm.controller.upload.param;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ToString
public class UploadImageParam {

    @NonNull
    private MultipartFile file;

    @NotBlank
    private String contentType;

}
