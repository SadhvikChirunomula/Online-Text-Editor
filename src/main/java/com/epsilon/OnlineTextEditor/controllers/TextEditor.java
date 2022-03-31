package com.epsilon.OnlineTextEditor.controllers;

import com.epsilon.OnlineTextEditor.models.TextEditor.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.FileUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
@Api(value = "Text Editor", description = "API's related to Text Editor")
public class TextEditor {
    @ApiOperation(value = "Test")
    @RequestMapping(method = RequestMethod.GET, value = "/test")
    public String test() throws Exception {
        return "Hello World!";
    }

    @ApiOperation(value = "Save File")
    @RequestMapping(method = RequestMethod.POST, value = "/saveFile")
    public String saveFile(@RequestBody SaveFileRequest saveFileRequest) throws Exception {
        System.out.println("Request from user: " + saveFileRequest.getUserName());
        File directoryPath = new File("src" + File.separator + "main" + File.separator + "resources" + File.separator + "files" + File.separator + saveFileRequest.getUserName());
        System.out.println("Content in file is " + saveFileRequest.getFileContent());

        if (!directoryPath.exists()) {
            directoryPath.mkdir();
        }

        if (saveFileRequest.getFileName().equals("")) {
            FileUtils.writeStringToFile(new File(directoryPath + File.separator + "test" + directoryPath.list().length + ".txt"), saveFileRequest.getFileContent());
        } else {
            FileUtils.writeStringToFile(new File(directoryPath + File.separator + saveFileRequest.getFileName()), saveFileRequest.getFileContent());

        }
        return "Success";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/getFiles")
    public List<String> getFilesList(@RequestBody GetFilesRequest getFilesRequest) throws Exception {
        ArrayList<String> a = new ArrayList<>();
        System.out.println("Searching for files in directory: " + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "files" + File.separator + getFilesRequest.getUserName());
        File directoryPath = new File("src" + File.separator + "main" + File.separator + "resources" + File.separator + "files" + File.separator + getFilesRequest.getUserName());
        //List of all files and directories
        String contents[] = directoryPath.list();
        System.out.println("List of files and directories in the specified directory:");
        for (int i = 0; i < contents.length; i++) {
            System.out.println(contents[i]);
        }
        return Arrays.asList(contents);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/getFileContent")
    public String getFileContent(@RequestBody GetFilesContent getFilesContent) throws Exception {
        System.out.println("Trying to get file content");
        String fileContent = FileUtils.readFileToString(new File("src" + File.separator + "main" + File.separator + "resources" + File.separator + "files" + File.separator + getFilesContent.getUserName() + File.separator + getFilesContent.getFileName()));
        System.out.println("Reading content");

        System.out.println("Content in file " + getFilesContent.getFileName() + ": " + File.separator + "main" + fileContent);
        return fileContent;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/registerUser")
    public String registerUser(@RequestBody RegisterUserRequest registerUserRequest) throws Exception {
        String usersList = FileUtils.readFileToString(new File("src" + File.separator + "main" + File.separator + "resources" + File.separator + "users.json"));
        ObjectMapper mapper = new ObjectMapper();
        List<Map<String, Object>> data = mapper.readValue(usersList, new TypeReference<List<Map<String, Object>>>() {
        });
        boolean userExists = false;

        for (Map<String, Object> datum : data) {
            if (datum.get("username").equals(registerUserRequest.getUserName())) {
                userExists = true;
            }
        }

        if (userExists) {
            return "Already Exists";
        } else {
            data.add(registerUserRequest.toJson());
            System.out.println("Adding users : " + mapper.writeValueAsString(data));
            FileUtils.writeStringToFile(new File("src" + File.separator + "main" + File.separator + "resources" + File.separator + "users.json"), mapper.writeValueAsString(data));

            // Create directory for User
            File directoryPath = new File("src" + File.separator + "main" + File.separator + "resources" + File.separator + "files" + File.separator + registerUserRequest.getUserName());
            if (!directoryPath.exists()) {
                directoryPath.mkdir();
            }
            FileUtils.writeStringToFile(new File("src" + File.separator + "main" + File.separator + "resources" + File.separator + "files" + File.separator + registerUserRequest.getUserName() + File.separator + "samplefile.txt"), "Hello, Thankyou");


            return "Added User Successfully";
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "/loginUser")
    public String loginUser(@RequestBody LoginUserRequest loginUserRequest) throws Exception {
        String usersList = FileUtils.readFileToString(new File("src" + File.separator + "main" + File.separator + "resources" + File.separator + "users.json"));
        ObjectMapper mapper = new ObjectMapper();
        List<Map<String, Object>> data = mapper.readValue(usersList, new TypeReference<List<Map<String, Object>>>() {
        });
        boolean userExists = false;
        boolean isAuth = false;


        for (Map<String, Object> datum : data) {
            if (datum.get("username").equals(loginUserRequest.getUserName())) {
                userExists = true;
                if (datum.get("password").equals(loginUserRequest.getPassword())) {
                    isAuth = true;
                }
            }
        }

        if (isAuth) {
            return "Success";
        } else {
            if (userExists) {
                return "Authentication Error";
            } else {
                return "User Not Found, Please register";
            }
        }
    }
}