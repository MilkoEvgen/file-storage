package org.example.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.example.dto.UserDto;
import org.example.repository.hibernateImpl.UserRepositoryImpl;
import org.example.service.UserService;
import org.example.service.impl.UserServiceImpl;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

@NoArgsConstructor
@AllArgsConstructor
@WebServlet(urlPatterns = "/api/v1/users/*")
public class UserRestControllerV1 extends HttpServlet {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private UserService userService = new UserServiceImpl(new UserRepositoryImpl());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String pathVariable = req.getPathInfo();
        resp.setContentType("application/json");
        resp.setStatus(200);
        if (pathVariable != null && !pathVariable.equals("/")){
            Integer id = Integer.parseInt(pathVariable.substring(1));
            UserDto userDto = userService.getById(id);
            resp.getWriter().print(objectMapper.writeValueAsString(userDto));
        } else {
            resp.getWriter().print(objectMapper.writeValueAsString(userService.getAll()));
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();

        try (BufferedReader bufferedReader = req.getReader()) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
            String jsonBody = stringBuilder.toString();
            UserDto userDto = objectMapper.readValue(jsonBody, UserDto.class);
            resp.setContentType("application/json");
            resp.setStatus(201);
            resp.getWriter().print(objectMapper.writeValueAsString(userService.create(userDto)));
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
            UserDto userDto = objectMapper.readValue(jsonBody, UserDto.class);
            resp.setContentType("application/json");
            resp.setStatus(200);
            resp.getWriter().print(objectMapper.writeValueAsString(userService.update(userDto)));
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        Integer id = Integer.parseInt(req.getPathInfo().substring(1));
        resp.setStatus(200);
        userService.delete(id);
    }
}
