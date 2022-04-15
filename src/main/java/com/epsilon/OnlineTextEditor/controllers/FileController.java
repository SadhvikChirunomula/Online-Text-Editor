package com.epsilon.OnlineTextEditor.controllers;

import com.epsilon.OnlineTextEditor.models.request.SaveFileRequest;
import com.epsilon.OnlineTextEditor.models.request.ShareFileRequest;
import com.epsilon.OnlineTextEditor.models.response.SaveFileResponse;
import com.epsilon.OnlineTextEditor.models.response.ShareFileResponse;
import com.epsilon.OnlineTextEditor.services.FilesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@Api(value = "File Controller", description = "File API's related to Text Editor")
public class FileController {
    @ApiOperation(value = "Create and Save File")
    @RequestMapping(method = RequestMethod.POST, value = "/file/save")
    public SaveFileResponse saveFile(@RequestBody SaveFileRequest saveFileRequest) throws Exception {
        return FilesService.saveFile(saveFileRequest);

    }

    @RequestMapping(method = RequestMethod.GET, value = "/list/files")
    public List<Map<String, Object>> listFilesOfUser(@RequestParam int userId) throws Exception {
        return FilesService.listFilesOfUser(userId);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/file/content/id")
    public Map<String, Object> getFileContentUsingId(@RequestParam int userId, int fileId) throws Exception {
        return FilesService.getFileContent(userId, fileId);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/file/content/name")
    public Map<String, Object> getFileContentUsingName(@RequestParam int userId, String fileName) throws Exception {
        return FilesService.getFileContent(userId, fileName);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/file/share")
    public ShareFileResponse shareFile(@RequestBody ShareFileRequest shareFileRequest) throws Exception {
        return FilesService.shareFile(shareFileRequest);
    }

}
