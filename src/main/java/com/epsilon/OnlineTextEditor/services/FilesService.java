package com.epsilon.OnlineTextEditor.services;

import com.epsilon.OnlineTextEditor.dao.FilesDao;
import com.epsilon.OnlineTextEditor.models.request.SaveFileRequest;
import com.epsilon.OnlineTextEditor.models.request.ShareFileRequest;
import com.epsilon.OnlineTextEditor.models.response.SaveFileResponse;
import com.epsilon.OnlineTextEditor.models.response.ShareFileResponse;
import com.epsilon.OnlineTextEditor.utilities.SqlRendererUtility;

import java.util.List;
import java.util.Map;

public class FilesService {

    public static SaveFileResponse saveFile(SaveFileRequest saveFileRequest) {
        SaveFileResponse saveFileResponse = new SaveFileResponse();
        if (fileAlreadyExists(saveFileRequest.getFileName(), saveFileRequest.getUserId())) {
            int fileId = (int) SqlRendererUtility.runSelectQuery(FilesDao.getFileId(saveFileRequest.getUserId(), saveFileRequest.getFileName())).get(0).get("file_id");
            SqlRendererUtility.runInsertQuery(FilesDao.saveFileContent(fileId, saveFileRequest.getFileContent()));
            saveFileResponse.setFileId(fileId);
            saveFileResponse.setStatusCode(200);
            saveFileResponse.setUserId(saveFileRequest.getUserId());
            saveFileResponse.setStatusMessage("Saved File Successfully");
        } else {
            SqlRendererUtility.runInsertQuery(FilesDao.createFileAndSaveContent(saveFileRequest));
            int fileId = (int) SqlRendererUtility.runSelectQuery(FilesDao.getFileIdWithoutUserID(saveFileRequest)).get(0).get("file_id");
            SqlRendererUtility.runInsertQuery(FilesDao.insertIntoLineage(fileId, saveFileRequest.getUserId()));
            saveFileResponse.setUserId(saveFileRequest.getUserId());
            saveFileResponse.setFileId(fileId);
            saveFileResponse.setStatusCode(200);
            saveFileResponse.setStatusMessage("Created and Saved File Successfully");

        }
        return saveFileResponse;
    }

    public static boolean fileAlreadyExists(String fileName, int userId) {
        return (boolean) SqlRendererUtility.runSelectQuery(FilesDao.checkIfFileExists(userId, fileName)).get(0).get("file_exists");
    }

    public static List<Map<String, Object>> listFilesOfUser(int userId) {
        return SqlRendererUtility.runSelectQuery(FilesDao.listFilesOfUser(userId));
    }

    public static List<Map<String, Object>> getFileContent(int userId, int fileId) {
        return SqlRendererUtility.runSelectQuery(FilesDao.getFileContent(userId, fileId));
    }

    public static List<Map<String, Object>> getFileContent(int userId, String fileName) {
        return SqlRendererUtility.runSelectQuery(FilesDao.getFileContent(userId, fileName));
    }

    public static ShareFileResponse shareFile(ShareFileRequest shareFileRequest) {
        ShareFileResponse shareFileResponse = new ShareFileResponse();
        int fileId = (int) SqlRendererUtility.runSelectQuery(FilesDao.getFileId(shareFileRequest.getSenderUserId(), shareFileRequest.getFileName())).get(0).get("file_id");
        SqlRendererUtility.runInsertQuery(FilesDao.insertIntoLineage(fileId, shareFileRequest.getReceiverUserId()));
        shareFileResponse.setReceiverUserId(shareFileRequest.getReceiverUserId());
        shareFileResponse.setSenderUserId(shareFileRequest.getSenderUserId());

        shareFileResponse.setStatusMessage("File shared successfully");
        shareFileResponse.setStatusCode(200);

        return shareFileResponse;
    }
}
