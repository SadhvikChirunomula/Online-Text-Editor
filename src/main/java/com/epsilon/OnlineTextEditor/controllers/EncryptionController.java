package com.epsilon.OnlineTextEditor.controllers;

import com.epsilon.OnlineTextEditor.models.request.DataDecryptionRequest;
import com.epsilon.OnlineTextEditor.models.request.DataEncryptionRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Base64;

@RestController
@Api(value = "Encryption Controller", description = "Encryption API's related to Text Editor")
public class EncryptionController {

    @ApiOperation(value = "Encrypt String")
    @RequestMapping(method = RequestMethod.POST, value = "/data/encrypt")
    public String encodeString(@RequestBody DataEncryptionRequest dataEncryptionRequest) {
        return Base64.getEncoder().encodeToString(dataEncryptionRequest.getData().getBytes());
    }

    @ApiOperation(value = "Decrypt String")
    @RequestMapping(method = RequestMethod.POST, value = "/data/decrypt")
    public String decodeString(@RequestBody DataDecryptionRequest dataDecryptionRequest) {
        return new String(Base64.getDecoder().decode(dataDecryptionRequest.getData()));
    }
}
