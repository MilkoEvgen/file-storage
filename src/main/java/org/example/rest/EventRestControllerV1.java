package org.example.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.dto.EventDto;
import org.example.repository.hibernateImpl.EventRepositoryImpl;
import org.example.service.EventService;
import org.example.service.impl.EventServiceImpl;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet(urlPatterns = "/api/v1/events/*")
public class EventRestControllerV1 extends HttpServlet {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final EventService eventService = new EventServiceImpl(new EventRepositoryImpl());


    @Operation(method = "GET",
            description = "Get Event by id",
            parameters = {
            @Parameter(name = "eventId", description = "ID of the event", required = true)
    })
    @Tag(name="event", description="Operations with events")
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String pathVariable = req.getPathInfo();
        resp.setContentType("application/json");
        resp.setStatus(200);
        if (pathVariable != null && !pathVariable.equals("/")){
            Integer id = Integer.parseInt(pathVariable.substring(1));
            EventDto eventDto = eventService.getById(id);
            resp.getWriter().print(objectMapper.writeValueAsString(eventDto));
        }
        else {
            resp.getWriter().print(objectMapper.writeValueAsString(eventService.getAll()));
        }
    }
}
