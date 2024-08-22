package com.banking_api.banking_api.controller;

import com.banking_api.banking_api.infra.FileStorageProperties;
import com.banking_api.banking_api.service.StorageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/api/files")
@Tag(name = "Files / Arquivos", description = "Upload, Download or list a text or image file")
public class FileStorageController {

    private final Path fileStorageLocation;

    public FileStorageController(FileStorageProperties fileStorageProperties) {
        this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir())
                .toAbsolutePath().normalize();
    }

    @PostMapping(path = "/upload")
    @ModelAttribute
    @Operation(summary = "Upload", description = "Insira um arquivo de texto ou imagem/ Upload text file or image")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            Path targetLocation = fileStorageLocation.resolve(fileName);
            file.transferTo(targetLocation);

            String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/api/files/download/")
                    .path(fileName)
                    .toUriString();

            return ResponseEntity.ok("File uploaded successfully. Download link: " + fileDownloadUri);
        } catch (IOException ex) {
            ex.printStackTrace();
            return ResponseEntity.badRequest().body("File upload failed.");
        }
    }

        @GetMapping ("/download/{fileName:.+}")
        @Operation(summary = "Download", description = " um arquivo de texto ou imagem/ download text file or image")

        public  ResponseEntity<Resource> downloadFile (@PathVariable String fileName, HttpServletRequest request) throws IOException {

        Path filePath = fileStorageLocation.resolve(fileName).normalize();
try{
        Resource resource = new UrlResource(filePath.toUri());
        String contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());

        if(contentType == null)
            contentType = "aplication/octet-stream";

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFile() + "\"")
                    .body(resource);

        } catch (MalformedURLException e) {
    return ResponseEntity.badRequest().build();
        }
    }

@GetMapping("/list")
@Operation(summary = "List files /Listar arquivos ", description = " Lista os arquivos da pasta de uploads")

    public ResponseEntity<List<String>> listFiles () throws IOException {

        List<String> fileNames = Files.list(fileStorageLocation)
                .map(Path::getFileName)
                .map(Path::toString)
                .collect(Collectors.toList());
        return  ResponseEntity.ok(fileNames);

    }

}