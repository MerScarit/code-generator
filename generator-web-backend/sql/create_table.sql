# 数据库初始化
# @author <a href="https://github.com/liyupi">程序员鱼皮</a>
# @from <a href="https://yupi.icu">编程导航知识星球</a>

-- 创建库
create database if not exists my_db;

-- 切换库
use my_db;

-- 用户表
create table if not exists user
(
    id           bigint auto_increment comment 'id' primary key,
    userAccount  varchar(256)                       not null comment '用户账号',
    userPassword varchar(512)                       not null comment '用户密码',
    userName     varchar(256)                       null comment '用户昵称',
    userAvatar   varchar(1024)                      null comment '用户头像',
    userProfile  varchar(512)                       null comment '用户简介',
    userRole     varchar(256)     default 'user'    not null comment '用户角色: user/admin/ban',
    createTime   datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime   datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete     tinyint  default 0                 not null comment '是否删除: 0/1',
    INDEX idx_userAccount(userAccount)
) comment '用户' collate = utf8mb4_unicode_ci;

-- 代码生成器表
create table if not exists generator
(
    id          bigint auto_increment comment 'id' primary key,
    name        varchar(128)                       null comment '名称',
    description text                               null comment '描述',
    basePackage varchar(128)                       null comment '基础包',
    version     varchar(128)                       null comment '版本号',
    author      varchar(128)                       null comment '作者',
    tags        varchar(1024)                      null comment '标签列表（JSON数组）',
    picture     varchar(256)                       null comment '图片',
    fileConfig  text                               null comment '文件配置（JSON字符串）',
    modelConfig text                               null comment '模型配置（JSON字符串）',
    distPath    text                               null comment '代码生成器产物路径',
    status      int      default 0                 not null comment '状态: 0/1',
    userId      bigint   default 0                 not null comment '创建用户ID',
    createTime  datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '创建时间',
    updateTime  datetime default CURRENT_TIMESTAMP not null comment '更新时间',
    isDelete    tinyint  default 0                 not null comment '是否删除: 0/1',
    INDEX idx_userId(userId)
) comment '代码生成器' collate = utf8mb4_unicode_ci;

-- 用户表模拟数据
INSERT INTO my_db.user(id, userAccount, userPassword, userName, userAvatar, userProfile, userRole)
VALUES (1, 'ADI', 'b0dd3697a192885d7c055db46155b26a', '阿迪',
        'https://gw.alipayobjects.com/zos/rmsportal/BiazfanxmamNRoxxVxka.png', '只因你太美', 'admin');

INSERT INTO my_db.user(id, userAccount, userPassword, userName, userAvatar, userProfile, userRole)
VALUES (2, 'kun', 'b0dd3697a192885d7c055db46155b26a', '坤',
        'https://gw.alipayobjects.com/zos/rmsportal/BiazfanxmamNRoxxVxka.png', '厉不厉害你坤哥', 'user');


-- 代码生成器表模拟数据
INSERT INTO my_db.generator(id, name, description, basePackage, version, author, tags,
                            picture, fileConfig, modelConfig, distPath, status, userId)
VALUES (1, 'ACM模板项目', 'ACM模板项目代码生成器', 'com.scarit', '1.0', 'ADI', '["Java"]',
        'https://pic.yupi.icu/1/_r0_c1851-bf115939332e.jpg', '{}', '{}', null, 0, 1);

INSERT INTO my_db.generator(id, name, description, basePackage, version, author, tags,
                            picture, fileConfig, modelConfig, distPath, status, userId)
VALUES (2, 'Spring Boot初始化模板', 'Spring Boot初始化模板模板项目代码生成器', 'com.scarit', '1.0', 'ADI', '["Java"]',
        'https://pic.yupi.icu/1/_r0_c0726-7e30f8db802a.jpg', '{}', '{}', null, 0, 1);

INSERT INTO my_db.generator(id, name, description, basePackage, version, author, tags,
                            picture, fileConfig, modelConfig, distPath, status, userId)
VALUES (3, '外卖初始化模板', '外卖模板项目代码生成器', 'com.scarit', '1.0', 'ADI', '["Java","前端"]',
        'https://pic.yupi.icu/1/_r1_ccf7-f8e4bd865b4b.jpg', '{}', '{}', null, 0, 1);

INSERT INTO my_db.generator(id, name, description, basePackage, version, author, tags,
                            picture, fileConfig, modelConfig, distPath, status, userId)
VALUES (4, '电商初始化模板', '电商模板项目代码生成器', 'com.scarit', '1.0', 'ADI', '["Java","前端"]',
        'https://pic.yupi.icu/1/_r1_c0709-8e80689ac1da.jpg', '{}', '{}', null, 0, 1);

