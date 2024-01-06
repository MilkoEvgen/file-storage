package org.example.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.dto.FileDto;
import org.example.repository.hibernateImpl.EventRepositoryImpl;
import org.example.repository.hibernateImpl.FileRepositoryImpl;
import org.example.repository.hibernateImpl.UserRepositoryImpl;
import org.example.service.FileService;
import org.example.service.impl.FileServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;


@MultipartConfig
@WebServlet(urlPatterns = "/api/v1/files/*")
public class FileRestControllerV1 extends HttpServlet {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final FileService fileService = new FileServiceImpl(new FileRepositoryImpl(),
            new UserRepositoryImpl(), new EventRepositoryImpl());

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String pathVariable = req.getPathInfo();
        resp.setContentType("application/json");
        resp.setStatus(200);
        if (pathVariable != null && !pathVariable.equals("/")) {
            Integer id = Integer.parseInt(pathVariable.substring(1));
            FileDto fileDto = fileService.getById(id);
            resp.getWriter().print(objectMapper.writeValueAsString(fileDto));
        } else {
            resp.getWriter().print(objectMapper.writeValueAsString(fileService.getAll()));
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String pathVariable = req.getPathInfo();
        if (pathVariable.equals("/upload")) {
            Part filePart = req.getPart("file-data");
            String fileName = filePart.getSubmittedFileName();
            Path uploadDir = getUploadDir();
            Path filePath = uploadDir.resolve(fileName);
            try (InputStream input = filePart.getInputStream()) {
                Files.copy(input, filePath, StandardCopyOption.REPLACE_EXISTING);
            }
            Integer userId = Integer.parseInt(req.getHeader("User_id"));

            FileDto fileDto = FileDto.builder()
                    .name(fileName)
                    .filePath(filePath.toString())
                    .build();
            fileDto = fileService.createWithEvent(fileDto, userId);
            resp.setContentType("application/json");
            resp.setStatus(201);
            resp.getWriter().print(objectMapper.writeValueAsString(fileDto));
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();

        try (BufferedReader bufferedReader = req.getReader()) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
            String jsonBody = stringBuilder.toString();
            FileDto fileDto = objectMapper.readValue(jsonBody, FileDto.class);
            fileService.update(fileDto);
            resp.setContentType("application/json");
            resp.setStatus(200);
            resp.getWriter().print(objectMapper.writeValueAsString(fileDto));
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        Integer id = Integer.parseInt(req.getPathInfo().substring(1));
        resp.setStatus(200);
        fileService.delete(id);
    }

    private Path getUploadDir() throws IOException {
        String uploadPath = getServletContext().getRealPath("") + "resources";
        Path uploadDir = Paths.get(uploadPath);
        Files.createDirectories(uploadDir);
        return uploadDir;
    }

}
