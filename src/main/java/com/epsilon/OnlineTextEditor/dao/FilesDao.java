package com.epsilon.OnlineTextEditor.dao;

import com.epsilon.OnlineTextEditor.models.request.SaveFileRequest;
import com.epsilon.OnlineTextEditor.models.request.ShareFileRequest;
import com.epsilon.OnlineTextEditor.utilities.EncryptionUtility;

public class FilesDao {
    public static String saveFileContent(int fileId, String fileContent) {
        return "update files.file_data set file_data =\'" + EncryptionUtility.encodeString(fileContent) + "\' where file_id = \'" + fileId + "\'";
    }

    public static String getFileId(int userId, String fileName) {
        return "select fd.file_id as file_id from files.file_data fd inner join files.user_file_lineage uf on uf.file_id = fd.file_id where uf.user_id='" + userId + "' and fd.file_name='" + fileName + "'";
    }

    public static String checkIfFileExists(int userId, String fileName) {
        return "select case when count(file_name)=0 then False else True END AS file_exists from files.file_data where file_name = \'" + fileName + "\' and file_id in (select file_id from files.user_file_lineage where user_id = \'" + userId + "\')";
    }

    public static String createFileAndSaveContent(SaveFileRequest saveFileRequest) {
        return "INSERT INTO files.file_data(file_name, file_data, crt_ts, crt_by, mod_ts, mod_by) VALUES (\'" + saveFileRequest.getFileName() + "\', \'" + EncryptionUtility.encodeString(saveFileRequest.getFileContent()) + "\', 'now()', \'" + saveFileRequest.getUserId() + "\', now(), \'" + saveFileRequest.getUserId() + "\');";
    }

    public static String insertIntoLineage(int fileId, int userId) {
        return "INSERT INTO files.user_file_lineage(user_id, file_id, is_open, crt_ts, crt_by, mod_ts, mod_by) VALUES (" + userId + ", " + fileId + ", 'True', now(), " + userId + ", now(), " + userId + ");";
    }

    public static String getFileIdWithoutUserID(SaveFileRequest saveFileRequest) {
        return "select file_id from files.file_data where file_name='" + saveFileRequest.getFileName() + "' order by file_id desc limit 1";
    }

    public static String listFilesOfUser(int userId) {
        return "select * from files.user_file_lineage fl inner join files.file_data fd on fl.file_id = fd.file_id and fl.user_id = \'" + userId + "\'";
    }

    public static String getFileContent(int userId, int fileId) {
        return "select fl.user_id, fd.file_id, fd.file_name, fd.crt_ts, fd.crt_by, fd.mod_ts, fd.mod_by, convert_from(decode(fd.file_data, 'base64'), 'UTF8') as file_data from files.user_file_lineage fl inner join files.file_data fd on fl.file_id = fd.file_id and fl.user_id = \'" + userId + "\' and fd.file_id=\'" + fileId + "\'";
    }

    public static String getFileContent(int userId, String fileName) {
        return "select fl.user_id, fd.file_name, fd.crt_ts, fd.crt_by, fd.mod_ts, fd.mod_by, convert_from(decode(fd.file_data, 'base64'), 'UTF8') as file_data from files.user_file_lineage fl inner join files.file_data fd on fl.file_id = fd.file_id and fl.user_id = \'" + userId + "\' and fd.file_name=\'" + fileName + "\'";
    }

    public static String shareFile(ShareFileRequest shareFileRequest) {
        return "";
    }
}
