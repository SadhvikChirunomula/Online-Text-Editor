package com.epsilon.OnlineTextEditor.controllers;

import com.epsilon.OnlineTextEditor.models.TextEditor.SaveFileRequest;
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
        File directoryPath = new File("src"+File.separator+"main"+File.separator+"resources"+File.separator+"files");
        System.out.println("Content in file is " + saveFileRequest.getFileContent());
        System.out.println();

        if (saveFileRequest.getFileName().equals("")) {
            FileUtils.writeStringToFile(new File("src"+File.separator+"main"+File.separator+"resources"+File.separator+"files"+File.separator+"test" + directoryPath.list().length + ".txt"), saveFileRequest.getFileContent());
        } else {
            FileUtils.writeStringToFile(new File("src"+File.separator+"main"+File.separator+"resources"+File.separator+"files"+File.separator+"" + saveFileRequest.getFileName()), saveFileRequest.getFileContent());

        }
        //List of all files and directories
        System.out.println("Content in file is " + saveFileRequest.getFileContent() + "to test" + directoryPath.list().length);
        return "Success";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getFiles")
    public List<String> getFilesList() throws Exception {
        ArrayList<String> a = new ArrayList<>();
        System.out.println("Searching for files in directory: "+"src"+File.separator+"main"+File.separator+"resources"+File.separator+"files");
        File directoryPath = new File("src"+File.separator+"main"+File.separator+"resources"+File.separator+"files");
        //List of all files and directories
        String contents[] = directoryPath.list();
        System.out.println("List of files and directories in the specified directory:");
        for (int i = 0; i < contents.length; i++) {
            System.out.println(contents[i]);
        }
        return Arrays.asList(contents);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/getFileContent")
    public String getFileContent(@RequestBody String fileName) throws Exception {
        String fileContent = FileUtils.readFileToString(new File("src"+File.separator+"main"+File.separator+"resources"+File.separator+"files"+File.separator+"" + fileName));
        System.out.println("Content in file " + fileName + ": "+File.separator+"n" + fileContent);
        return fileContent;
    }
}