package com.wsd.edgardocswrapper.serviceImplementors;

import com.wsd.edgardocswrapper.services.IFileValidationService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

@Service
public class FileValidationServiceImpl implements IFileValidationService {
    private static final Set<String> ALLOWED_MIME_TYPES = Set.of(
            "application/vnd.openxmlformats-officedocument.wordprocessingml.document"
    );

    private static final List<String> ALLOWED_EXTENSIONS = List.of(".docx");
    
    @Override
    public boolean isValidFile(MultipartFile file) {
        if (!ALLOWED_MIME_TYPES.contains(file.getContentType())) {
            System.err.println("Invalid file type: " + file.getContentType());
            return false;
        }
        String filename = file.getOriginalFilename();
        if (filename != null && ALLOWED_EXTENSIONS.stream().noneMatch(filename::endsWith)) {
            System.err.println("Invalid file extension: " + filename);
            return false;
        }
        return true;
    }
}
