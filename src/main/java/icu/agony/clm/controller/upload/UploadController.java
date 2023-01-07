package icu.agony.clm.controller.upload;

import icu.agony.clm.controller.upload.param.UploadImageParam;
import icu.agony.clm.exception.BadRequestException;
import icu.agony.clm.service.UploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/upload")
@RequiredArgsConstructor
public class UploadController {

    private final UploadService uploadService;

    @PostMapping("/image")
    String image(@Valid UploadImageParam param, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new BadRequestException("上传图片表单校验不通过");
        }
        return uploadService.image(param);
    }

}
