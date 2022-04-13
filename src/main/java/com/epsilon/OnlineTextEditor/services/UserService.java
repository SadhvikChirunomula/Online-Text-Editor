package com.epsilon.OnlineTextEditor.services;

import com.epsilon.OnlineTextEditor.dao.UserDao;
import com.epsilon.OnlineTextEditor.models.request.LoginUserRequest;
import com.epsilon.OnlineTextEditor.models.request.RegisterUserRequest;
import com.epsilon.OnlineTextEditor.models.response.LoginUserResponse;
import com.epsilon.OnlineTextEditor.models.response.RegisterUserResponse;
import com.epsilon.OnlineTextEditor.utilities.EncryptionUtility;
import com.epsilon.OnlineTextEditor.utilities.SqlRendererUtility;
import org.springframework.context.annotation.Bean;

import java.util.List;
import java.util.Map;

public class UserService {
    public RegisterUserResponse registerUser(RegisterUserRequest registerUserRequest) {
        RegisterUserResponse registerUserResponse = new RegisterUserResponse();
        if (checkIfUserExists(registerUserRequest.getUserName())) {
            registerUserResponse.setStatusCode(202);
            registerUserResponse.setUserName(registerUserRequest.getUserName());
            registerUserResponse.setStatusMessage("User Already Exists");
        } else {
            System.out.println("Adding user " + registerUserRequest.getUserName() + " to DB..");
            SqlRendererUtility.runInsertQuery(UserDao.addUserToDb(registerUserRequest));
            registerUserResponse.setStatusCode(200);
            registerUserResponse.setStatusMessage("Added user successfully");
            registerUserResponse.setUserName(registerUserRequest.getUserName());
            registerUserResponse.setUserId(getUserIdFromUserName(registerUserRequest.getUserName()));
        }
        return registerUserResponse;
    }

    public LoginUserResponse loginUser(LoginUserRequest loginUserRequest) {
        LoginUserResponse loginUserResponse = new LoginUserResponse();

        if (!checkIfUserExists(loginUserRequest.getUserName())) {
            loginUserResponse.setStatusCode(404);
            loginUserResponse.setUserId(0);
            loginUserResponse.setStatusMessage("User Not found");
        } else {
            int userId = getUserIdFromUserName(loginUserRequest.getUserName());
            loginUserResponse.setUserId(userId);
            if (authSuccessful(userId, loginUserRequest.getPassword())) {
                loginUserResponse.setStatusCode(200);
                loginUserResponse.setStatusMessage("Auth Success");

                // Need to integrate login_sessions
            }else{
                loginUserResponse.setStatusCode(403);
                loginUserResponse.setUserId(0);
                loginUserResponse.setStatusMessage("Wrong Password");
            }
        }
        return loginUserResponse;
    }

    public boolean authSuccessful(int userId, String password) {
        String encodedPassword = EncryptionUtility.encodeString(password);
        return (boolean) SqlRendererUtility.runSelectQuery(UserDao.validatePassword(userId, encodedPassword)).get(0).get("is_correct");
    }

    public boolean checkIfUserExists(String userName) {
        return SqlRendererUtility.runSelectQuery(UserDao.checkIfUserExists(userName)).get(0).get("userexists").toString().equalsIgnoreCase("true");
    }

    public int getUserIdFromUserName(String userName) {
        return (int) SqlRendererUtility.runSelectQuery(UserDao.getUserId(userName)).get(0).get("user_id");
    }

    public List<Map<String, Object>> listUsers() {
        return SqlRendererUtility.runSelectQuery(UserDao.getAllUsersList());
    }

    public List<Map<String, Object>> listUser(int userId) {
        return SqlRendererUtility.runSelectQuery(UserDao.getUserInfo(userId));

    }
}
