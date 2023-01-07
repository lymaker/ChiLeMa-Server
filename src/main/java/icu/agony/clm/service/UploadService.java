package icu.agony.clm.service;

import icu.agony.clm.controller.upload.param.UploadImageParam;

public interface UploadService {

    /**
     * 上传图片
     *
     * @param param 请求参数
     * @return 图片URL
     */
    String image(UploadImageParam param);

}
