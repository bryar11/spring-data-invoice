package com.javausergroupcr.springdata.app.controllers;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ErrorsController {

	@ExceptionHandler(Exception.class)
	public String showInternalServerError() {
		return "/common/error_500";

	}
}
