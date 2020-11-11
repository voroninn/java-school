package org.javaschool.exception;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
@Log4j2
class GlobalDefaultExceptionHandler {

    private static final String DEFAULT_ERROR_VIEW = "error";
    private static final String ILLEGAL_OPERATION = "illegalOperation";
    private static final String TRAINS_NOT_FOUND = "searchFailed";

    @ExceptionHandler(value = Exception.class)
    public ModelAndView defaultErrorHandler(HttpServletRequest request, Exception exception) {
        ModelAndView modelAndView = new ModelAndView();
        log.error("Error encountered in the program", exception);
        modelAndView.addObject("exception", exception);
        modelAndView.addObject("url", request.getRequestURL());
        modelAndView.setViewName(DEFAULT_ERROR_VIEW);
        return modelAndView;
    }

    @ExceptionHandler(value = IllegalOperationException.class)
    public ModelAndView illegalOperationHandler(Exception exception) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("message", exception.getMessage());
        modelAndView.setViewName(ILLEGAL_OPERATION);
        log.warn(exception.getMessage());
        return modelAndView;
    }

    @ExceptionHandler(value = TrainsNotFoundException.class)
    public String trainsNotFoundHandler(Exception exception) {
        log.warn(exception.getMessage());
        return TRAINS_NOT_FOUND;
    }
}