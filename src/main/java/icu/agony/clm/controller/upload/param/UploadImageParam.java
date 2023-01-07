package icu.agony.clm.controller.upload.param;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
public class UploadImageParam {

    @NotNull
    private MultipartFile file;

    @NotBlank
    private String contentType;

}
