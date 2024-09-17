package com.wsd.edgardocswrapper.serviceImplementors;

import com.wsd.edgardocswrapper.services.IDocxPreprocessingService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;

@Service
public class DocxPreprocessingServiceImpl implements IDocxPreprocessingService {
    private final WebClient webClient;
    private final String docxPreprocessingApiUrl = "http://localhost:5000/api/v1/http/apply-all-conversion";

    public DocxPreprocessingServiceImpl(WebClient webClient) {
        this.webClient = webClient;
    }


    @Override
    public void downloadFileWithNoParams(MultipartFile file) {
        try {
            // Verify and log the content type of the file before sending
            String mimeType = file.getContentType();
            System.out.println("MIME Type: " + mimeType);
            System.out.println("File name: " + file.getOriginalFilename());

            // Create a MultipartBodyBuilder to prepare the file upload
            MultipartBodyBuilder bodyBuilder = new MultipartBodyBuilder();
            bodyBuilder.part("file", new ByteArrayResource(file.getBytes()) {
                        @Override
                        public String getFilename() {
                            return file.getOriginalFilename(); // Set the correct filename
                        }
                    })
                    .contentType(MediaType.parseMediaType(mimeType)); // Ensure correct MIME type

            // Send the request to the ASP.NET Core API
            webClient.post()
                    .uri(docxPreprocessingApiUrl)
                    .contentType(MediaType.MULTIPART_FORM_DATA)
                    .bodyValue(bodyBuilder.build())
                    .retrieve()
                    .bodyToMono(byte[].class)
                    .doOnSuccess(this::saveFile)
                    .doOnError(throwable -> System.err.println("Error uploading and downloading file: " + throwable.getMessage()))
                    .subscribe();
        } catch (IOException e) {
            System.err.println("Error preparing file for upload: " + e.getMessage());
        }
    }

    private void saveFile(byte[] fileContent) {
        File file = Paths.get("processed_file.docx").toFile();

        try (FileOutputStream fos = new FileOutputStream(file)) {
            // Save the file content
            FileCopyUtils.copy(fileContent, fos);
            System.out.println("File saved successfully: " + file.getAbsolutePath());
        } catch (IOException e) {
            System.err.println("Error saving file: " + e.getMessage());
        }
    }
}
