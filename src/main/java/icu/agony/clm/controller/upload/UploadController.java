package icu.agony.clm.controller.upload;

import icu.agony.clm.controller.upload.param.UploadImageParam;
import icu.agony.clm.service.UploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/upload")
@RequiredArgsConstructor
public class UploadController {

    private final UploadService uploadService;

    @PostMapping("/image")
    String image(UploadImageParam param) {
        return uploadService.image(param);
    }

}
