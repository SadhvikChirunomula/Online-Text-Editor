package com.epsilon.OnlineTextEditor.controllers;

import com.epsilon.OnlineTextEditor.utilities.DBConnectionManager;
import com.epsilon.OnlineTextEditor.utilities.SqlRendererUtility;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(value = "DB Editor", description = "DB supporting Text Editor")

public class DbController {
    @ApiOperation(value = "Test DB Connection")
    @RequestMapping(method = RequestMethod.GET, value = "/db/test/connection")
    public String test() throws Exception {
        DBConnectionManager dbConnectionManager = new DBConnectionManager();
        if (dbConnectionManager.getConnection() == null) {
            return "Error";
        } else {
            return "Success";
        }
    }

    @ApiOperation(value = "Test Database Connection")
    @RequestMapping(method = RequestMethod.GET, value = "/db/initialize")
    public String initializeDb() throws Exception {
        SqlRendererUtility.runInsertQuery(
                "\n" +
                "drop schema if exists users cascade;\n" +
                "drop schema if exists files cascade;\n" +
                "create schema if not exists users;\n" +
                "create schema if not exists files;\n" +
                "\n" +
                "create table users.register (\n" +
                "    user_id serial,\n" +
                "    user_name text,\n" +
                "    password text,\n" +
                "    user_email text,\n" +
                "    crt_ts timestamp,\n" +
                "    crt_by text,\n" +
                "    mod_ts timestamp,\n" +
                "    mod_by text\n" +
                ");\n" +
                "\n" +
                "create table users.login_sessions (\n" +
                "    user_id int,\n" +
                "    last_login_ts timestamp,\n" +
                "    login_count int,\n" +
                "    successive_retry_attempt int,\n" +
                "    crt_ts timestamp,\n" +
                "    crt_by text,\n" +
                "    mod_ts timestamp,\n" +
                "    mod_by text\n" +
                ");\n" +
                "\n" +
                "create table files.file_data (\n" +
                "    file_id serial,\n" +
                "    file_name text,\n" +
                "    file_data text,\n" +
                "    crt_ts timestamp,\n" +
                "    crt_by text,\n" +
                "    mod_ts timestamp,\n" +
                "    mod_by text\n" +
                ");\n" +
                "\n" +
                "create table files.user_file_lineage (\n" +
                "    user_id int,\n" +
                "    file_id int,\n" +
                "    is_open boolean,\n" +
                "    crt_ts timestamp,\n" +
                "    crt_by text,\n" +
                "    mod_ts timestamp,\n" +
                "    mod_by text\n" +
                ");");
        return "Schema and Tables are created successfully";
    }
}
