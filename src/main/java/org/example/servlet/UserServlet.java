package org.example.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.example.model.User;
import org.example.model.UserDto;
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
@WebServlet(urlPatterns = "/user/*")
public class UserServlet extends HttpServlet {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private UserService userService = new UserServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String pathVariable = req.getPathInfo();
        resp.setContentType("application/json");
        resp.setStatus(200);
        Integer id = Integer.parseInt(pathVariable.substring(1));
        UserDto userDto = userService.getById(id);
        resp.getWriter().print(objectMapper.writeValueAsString(userDto));
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
            User user = objectMapper.readValue(jsonBody, User.class);
            userService.create(user);
            resp.setContentType("application/json");
            resp.setStatus(201);
            resp.getWriter().print(objectMapper.writeValueAsString(user));
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
            User user = objectMapper.readValue(jsonBody, User.class);
            userService.update(user);
            resp.setContentType("application/json");
            resp.setStatus(200);
            resp.getWriter().print(objectMapper.writeValueAsString(user));
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        Integer id = Integer.parseInt(req.getPathInfo().substring(1));
        resp.setStatus(200);
        userService.delete(id);
    }
}
