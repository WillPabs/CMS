package com.fdmgroup.fdmcmsgroupproject.controllers;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * ErrorController: Controller to handle error requests
 *
 * @version 1.0
 */
@Controller
public class ErrorController {
	
	private static Logger logger = LogManager.getLogger();
	
	@RequestMapping(value = "errors", method = {RequestMethod.GET, RequestMethod.POST})
	public String renderErrorPage(Model model, HttpServletRequest request) {
		logger.warn("An issue has occurred while processing a request");
		Integer errorCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
		String errorMsg = "";
		
		switch (errorCode) {
			case 400: {
	            errorMsg = "Http Error Code: 400. Bad Request";
	            break;
	        }
	        case 401: {
	            errorMsg = "Http Error Code: 401. Unauthorized";
	            break;
	        }
	        case 404: {
	            errorMsg = "Http Error Code: 404. Resource not found";
	            break;
	        }
	        case 500: {
	            errorMsg = "Http Error Code: 500. Internal Server Error";
	            break;
	        }		
        }
		
		model.addAttribute("errorMsg", errorMsg);
		return "errorsPage";
	}
}
