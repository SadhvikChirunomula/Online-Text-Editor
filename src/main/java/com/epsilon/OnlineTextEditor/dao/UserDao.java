package com.epsilon.OnlineTextEditor.dao;

import com.epsilon.OnlineTextEditor.models.request.RegisterUserRequest;
import com.epsilon.OnlineTextEditor.utilities.EncryptionUtility;

public class UserDao {
    public static String checkIfUserExists(String userName) {
        return "select case when count(user_name) = 0 THEN False ELSE True END AS userExists from users.register where user_name=\'" + userName + "\'";
    }

    public static String addUserToDb(RegisterUserRequest registerUserRequest) {
        return "INSERT INTO users.register(user_name, password, user_email, crt_ts, crt_by, mod_ts, mod_by) values ('" + registerUserRequest.getUserName() + "', '" + EncryptionUtility.encodeString(registerUserRequest.getPassword()) + "', '" + registerUserRequest.getUserEmail() + "', now(), 'admin', now(), 'admin');";
    }

    public static String getUserId(String userName) {
        return "select user_id from users.register where user_name=\'" + userName + "\'";
    }

    public static String validatePassword(int userId, String encPassword) {
        return "select case when password=\'" + encPassword + "\' then True else False end as is_correct from users.register where user_id=\'" + userId + "\';";
    }

    public static String getAllUsersList() {
        return "select * from users.register";
    }

    public static String getUserInfo(int userId) {
        return "select * from users.register where user_id='" + userId + "'";
    }
}
