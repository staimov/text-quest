package com.staimov.textquest.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CustomErrorController implements ErrorController {

    private static final Logger logger = LoggerFactory.getLogger(CustomErrorController.class);

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        Exception e = (Exception) request.getAttribute(RequestDispatcher.ERROR_EXCEPTION);

        String message = "Что-то пошло не так!";
        logger.error(message);

        if (status != null) {
            int statusCode = Integer.parseInt(status.toString());

            if(statusCode == HttpStatus.NOT_FOUND.value()) {
                message = "Извините, мы не смогли найти страницу, которую вы ищете";
            }

            logger.error("HTTP Status: {}", status);
        }

        if (e != null) {
            logger.error("An exception occurred!", e);
        }

        model.addAttribute("message", message);
        model.addAttribute("status", status);
        model.addAttribute("exception", e);

        return "error";
    }
}
