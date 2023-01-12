-- 创建用户
SET @userId = 'd0cd7faa2bf0919382173c3de335bcf4';
INSERT INTO clm_user (id, nickname, username, password, email, phone, avatar_image_url)
VALUES (@userId, 'Anna', '1265894132', 'Nv6w5wTg9feVSnr/DKfgVA==', '1265894132@qq.com', '13127777777',
        'https://default-1300725964.cos.ap-guangzhou.myqcloud.com/avatar.png');
INSERT INTO clm_user_role
VALUES (@userId, 1),
       (@userId, 2),
       (@userId, 3);