package com.banking_api.banking_api.controller;

import com.banking_api.banking_api.service.StorageService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/files")
public class FileUploadController {

    private StorageService storageService;

    public FileUploadController(StorageService storageService) {
        this.storageService = storageService;
    }
@GetMapping
    public fileUpload()





}
