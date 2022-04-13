package com.epsilon.OnlineTextEditor.utilities;

import com.epsilon.OnlineTextEditor.models.request.DataDecryptionRequest;
import com.epsilon.OnlineTextEditor.models.request.DataEncryptionRequest;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Base64;

public class EncryptionUtility {
    public static String encodeString(DataEncryptionRequest dataEncryptionRequest) {
        return Base64.getEncoder().encodeToString(dataEncryptionRequest.getData().getBytes());
    }

    public static String decodeString(DataDecryptionRequest dataDecryptionRequest) {
        return new String(Base64.getDecoder().decode(dataDecryptionRequest.getData()));
    }

    public static String encodeString(String data) {
        return Base64.getEncoder().encodeToString(data.getBytes());
    }

    public static String decodeString(String data) {
        return new String(Base64.getDecoder().decode(data));
    }

}
