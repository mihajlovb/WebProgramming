package mk.ukim.finki.wp.lab.web.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mk.ukim.finki.wp.lab.model.Registration;
import mk.ukim.finki.wp.lab.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@WebServlet(urlPatterns = "/registration")
public class RegistrationServlet extends HttpServlet {

    @Autowired
    private RegistrationService registrationService;

    @Autowired
    private SpringTemplateEngine templateEngine;

    @Override
    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding(StandardCharsets.UTF_8.name());

        String workshopTitle = request.getParameter("workshopTitle");
        String participantName = request.getParameter("participantName");
        String participantEmail = request.getParameter("participantEmail");
        String seatsParameter = request.getParameter("numSeats");

        try {
            int numberOfSeats;
            try {
                numberOfSeats = Integer.parseInt(seatsParameter == null ? "" : seatsParameter.trim());
            } catch (NumberFormatException exception) {
                throw new IllegalArgumentException("Please enter a valid number of seats.");
            }

            Registration registration = registrationService.register(
                    workshopTitle, participantName, participantEmail, numberOfSeats);

            Context context = new Context();
            context.setVariable("registration", registration);
            context.setVariable("clientIp", request.getRemoteAddr());
            context.setVariable("browser", request.getHeader("User-Agent"));

            response.setContentType("text/html; charset=UTF-8");
            templateEngine.process("registrationConfirmation", context, response.getWriter());
        } catch (IllegalArgumentException exception) {
            String message = exception.getMessage();
            if (message == null || message.isBlank()) {
                message = "Invalid registration details.";
            }
            response.sendRedirect("/?errorMessage=" + URLEncoder.encode(message, StandardCharsets.UTF_8));
        }
    }
}
