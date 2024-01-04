package org.example.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.model.Event;
import org.example.model.File;
import org.example.model.User;
import org.example.model.UserDto;
import org.example.service.EventService;
import org.example.service.FileService;
import org.example.service.UserService;
import org.example.service.impl.EventServiceImpl;
import org.example.service.impl.FileServiceImpl;
import org.example.service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;


@WebServlet(urlPatterns = "/file/*")
public class FileServlet extends HttpServlet {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final FileService fileService = new FileServiceImpl();

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String pathVariable = req.getPathInfo();
        resp.setContentType("application/json");
        resp.setStatus(200);
        Integer id = Integer.parseInt(pathVariable.substring(1));
        File file = fileService.getById(id);
        resp.getWriter().print(objectMapper.writeValueAsString(file));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        Integer userId = Integer.parseInt(req.getHeader("User_id"));
        try (BufferedReader bufferedReader = req.getReader()) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
            String jsonBody = stringBuilder.toString();
            File file = objectMapper.readValue(jsonBody, File.class);
            file = fileService.createWithEvent(file, userId);
            resp.setContentType("application/json");
            resp.setStatus(201);
            resp.getWriter().print(objectMapper.writeValueAsString(file));
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
            File file = objectMapper.readValue(jsonBody, File.class);
            fileService.update(file);
            resp.setContentType("application/json");
            resp.setStatus(200);
            resp.getWriter().print(objectMapper.writeValueAsString(file));
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        Integer id = Integer.parseInt(req.getPathInfo().substring(1));
        resp.setStatus(200);
        fileService.delete(id);
    }
}
