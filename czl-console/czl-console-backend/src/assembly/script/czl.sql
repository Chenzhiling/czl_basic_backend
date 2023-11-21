set names utf8mb4;
set foreign_key_checks = 0;


-- ----------------------------
-- Table structure for sys_log
-- ----------------------------
drop table if exists sys_log;
create table sys_log
(
    id               int auto_increment
        primary key,
    user_name        varchar(100) not null,
    description      varchar(100) null,
    exception_detail text         null,
    method           text         null,
    log_type         varchar(100) null,
    params           text         null,
    create_time             timestamp default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '创建时间',
    update_time             timestamp default CURRENT_TIMESTAMP not null comment '更新时间',
    is_delete               tinyint   default 0                 not null comment '是否删除'
) DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


-- ----------------------------
-- Table structure for sys_quartz_job
-- ----------------------------
drop table if exists sys_quartz_job;
create table sys_quartz_job
(
    id              bigint auto_increment comment 'ID'
        primary key,
    bean_name       varchar(255)                        null comment 'Spring Bean名称',
    cron_expression varchar(255)                        null comment 'cron 表达式',
    is_pause        bit                                 null comment '状态：1暂停、0启用',
    job_name        varchar(255)                        null comment '任务名称',
    method_name     varchar(255)                        null comment '方法名称',
    params          varchar(255)                        null comment '参数',
    remark          varchar(255)                        null comment '备注',
    create_time             timestamp default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '创建时间',
    update_time             timestamp default CURRENT_TIMESTAMP not null comment '更新时间',
    is_delete               tinyint   default 0                 not null comment '是否删除'
) DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
drop table if exists sys_user;
create table sys_user
(
    id          bigint auto_increment
        primary key,
    user_name   varchar(50)                         not null,
    password    text                                not null,
    email       varchar(100)                        null,
    dept_id     bigint                              null,
    is_admin    bit                                 null comment '是否为管理员',
    enabled     bit                                 null comment '是否启用',
    create_time             timestamp default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '创建时间',
    update_time             timestamp default CURRENT_TIMESTAMP not null comment '更新时间',
    is_delete               tinyint   default 0                 not null comment '是否删除',
    constraint user_user_name_uindex
        unique (user_name)
) DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

BEGIN;
INSERT INTO sys_user (id, user_name, password, email, dept_id, is_admin, enabled, create_time, update_time, is_delete) VALUES (1, 'admin', '$2a$10$7M2ajT0dhEltYGwxXKD2Zu4Lv41e8NPTokbjuwbeSIFzTJr2D7h/.', '122769965@qq.com', 0, true, true, '2022-08-10 10:31:21', '2022-08-10 10:31:21', 0);
COMMIT;


-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
drop table if exists sys_role;
create table sys_role
(
    id          bigint auto_increment comment 'ID'
        primary key,
    name        varchar(255) not null comment '名称',
    level       int(255)     null comment '角色级别',
    description varchar(255) null comment '描述',
    data_scope  varchar(255) null comment '数据权限',
    create_time             timestamp default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '创建时间',
    update_time             timestamp default CURRENT_TIMESTAMP not null comment '更新时间',
    is_delete               tinyint   default 0                 not null comment '是否删除',
    constraint uniq_name
        unique (name)
) DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

create index role_name_index
    on sys_role (name);

BEGIN;
INSERT INTO sys_role (id, name, level, description, data_scope, create_time, update_time, is_delete) VALUES (1, '超级管理员', 1, '-', '全部', '2018-11-23 11:04:37', '2020-08-06 16:10:24', 0);
COMMIT;

-- ----------------------------
-- Table structure for sys_users_roles
-- ----------------------------
drop table if exists sys_users_roles;
create table sys_users_roles
(
    user_id bigint not null comment '用户ID',
    role_id bigint not null comment '角色ID',
    primary key (user_id, role_id)
) DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

BEGIN;
INSERT INTO sys_users_roles (user_id, role_id) VALUES (1, 1);
COMMIT;

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------

drop table if exists sys_menu;
create table sys_menu
(
    id          bigint auto_increment comment 'ID'
        primary key,
    pid         bigint              null comment '上级菜单ID',
    sub_count   int(5) default 0    null comment '子菜单数目',
    type        int                 null comment '菜单类型',
    title       varchar(255)        not null comment '菜单标题',
    name        varchar(255)        not null comment '组件名称',
    component   varchar(255)        null comment '组件',
    menu_sort   int(5)              null comment '排序',
    icon        varchar(255)        null comment '图标',
    path        varchar(255)        null comment '链接地址',
    i_frame     bit                 null comment '是否外链',
    cache       bit    default b'0' null comment '缓存',
    hidden      bit    default b'0' null comment '隐藏',
    permission  varchar(255)        null comment '权限',
    create_time             timestamp default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '创建时间',
    update_time             timestamp default CURRENT_TIMESTAMP not null comment '更新时间',
    is_delete               tinyint   default 0                 not null comment '是否删除',
    constraint uniq_name
        unique (name),
    constraint uniq_title
        unique (title)
) DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

create index inx_pid
    on sys_menu (pid);


-- ----------------------------
-- Table structure for sys_roles_menus
-- ----------------------------

drop table if exists sys_roles_menus;
create table sys_roles_menus
(
    menu_id bigint not null comment '菜单ID',
    role_id bigint not null comment '角色ID',
    primary key (menu_id, role_id)
)
    DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

BEGIN;
INSERT INTO sys_roles_menus (menu_id, role_id) VALUES (1, 1);
INSERT INTO sys_roles_menus (menu_id, role_id) VALUES (2, 1);
INSERT INTO sys_roles_menus (menu_id, role_id) VALUES (3, 1);
INSERT INTO sys_roles_menus (menu_id, role_id) VALUES (4, 1);
INSERT INTO sys_roles_menus (menu_id, role_id) VALUES (5, 1);
INSERT INTO sys_roles_menus (menu_id, role_id) VALUES (6, 1);
INSERT INTO sys_roles_menus (menu_id, role_id) VALUES (7, 1);
INSERT INTO sys_roles_menus (menu_id, role_id) VALUES (8, 1);
INSERT INTO sys_roles_menus (menu_id, role_id) VALUES (9, 1);
INSERT INTO sys_roles_menus (menu_id, role_id) VALUES (10, 1);
COMMIT;

set foreign_key_checks = 1;
