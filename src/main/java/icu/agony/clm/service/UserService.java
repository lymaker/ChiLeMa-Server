package icu.agony.clm.service;

import org.springframework.web.multipart.MultipartFile;

public interface UserService {

    /**
     * 检测用户名是否可用
     *
     * @param username 用户名
     */
    void checkUsername(String username);

    /**
     * 更新用户头像
     *
     * @param file 头像文件
     */
    void updateAvatar(MultipartFile file);

}
