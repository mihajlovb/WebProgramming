package mk.ukim.finki.wp.lab.web.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mk.ukim.finki.wp.lab.service.WorkshopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.io.IOException;

@WebServlet(name = "workshopListServlet", urlPatterns = "/")
public class WorkshopListServlet extends HttpServlet {

    @Autowired
    private WorkshopService workshopService;

    @Autowired
    private SpringTemplateEngine templateEngine;

    @Override
    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport
                .processInjectionBasedOnCurrentContext(this);

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String text = request.getParameter("text");
        String maxPriceParameter = request.getParameter("maxPrice");
        String errorMessage = request.getParameter("errorMessage");
        Double maxPrice = null;

        if (maxPriceParameter != null && !maxPriceParameter.isBlank()) {
            try {
                maxPrice = Double.parseDouble(maxPriceParameter.trim());
                if (maxPrice < 0) {
                    throw new NumberFormatException("Negative price");
                }
            } catch (NumberFormatException exception) {
                errorMessage = "Please enter a valid non-negative maximum price.";
                maxPrice = null;
            }
        }

        boolean hasText = text != null && !text.isBlank();
        boolean hasMaxPrice = maxPriceParameter != null && !maxPriceParameter.isBlank() && maxPrice != null;
        var workshops = (hasText || hasMaxPrice)
                ? workshopService.search(hasText ? text.trim() : null, maxPrice)
                : workshopService.listAll();

        Context context = new Context();
        context.setVariable("workshops", workshops);
        context.setVariable("errorMessage", errorMessage);
        context.setVariable("searchText", text == null ? "" : text);
        context.setVariable("maxPrice", maxPriceParameter == null ? "" : maxPriceParameter);
        context.setVariable("noResults", workshops.isEmpty());

        response.setContentType("text/html; charset=UTF-8");
        templateEngine.process("listWorkshops", context, response.getWriter());
    }
}
