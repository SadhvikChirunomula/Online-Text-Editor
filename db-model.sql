drop database if exists ote;
create database ote;

drop schema if exists users;
drop schema if exists files;
create schema if not exists users;
create schema if not exists files;

create table users.register (
    user_id int,
    user_name text,
    password text,
    user_email text,
    crt_ts timestamp,
    crt_by text,
    mod_ts timestamp,
    mod_by text
)

create table users.login_sessions (
    user_id int,
    last_login_ts timestamp,
    login_count int,
    successive_retry_attempt int,
    crt_ts timestamp,
    crt_by text,
    mod_ts timestamp,
    mod_by text
)

create table files.file_data (
    file_id int,
    file_name text,
    file_data text,
    crt_ts timestamp,
    crt_by text,
    mod_ts timestamp,
    mod_by text
)

create table files.user_file_lineage (
    user_id int,
    file_id int,
    is_open boolean,
    crt_ts timestamp,
    crt_by text,
    mod_ts timestamp,
    mod_by text
)