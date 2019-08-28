package br.com.zoot.api.exception;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExpenseExceptionHandler extends ResponseEntityExceptionHandler {

	// Obtem as mensagens cadastradas em resources/messages.properties
	@Autowired
	private MessageSource messageSource;
	
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			                                                      HttpHeaders headers, 
			                                                      HttpStatus status, 
			                                                      WebRequest request) {
		String msgUsuario = messageSource.getMessage("mensagem.invalida", null, LocaleContextHolder.getLocale());
		String msgDesenv = ex.getCause() != null ? ex.getCause().toString() : ex.toString();
		List<StandardErro> lstErros = Arrays.asList(new StandardErro(msgUsuario, msgDesenv));	
		
		return handleExceptionInternal(ex, lstErros, headers, HttpStatus.BAD_REQUEST, request);
	}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			                                                      HttpHeaders headers, 
			                                                      HttpStatus status, 
			                                                      WebRequest request) {
		List<StandardErro> lstErros = criarListaDeErros(ex.getBindingResult());
		return handleExceptionInternal(ex, lstErros, headers, HttpStatus.BAD_REQUEST, request);
	}
	
	// Como não há um método para sobrescrever, escrevemos um genérico	
	@ExceptionHandler({EmptyResultDataAccessException.class})
	public ResponseEntity<Object> handleEmptyResultDataAccessException(EmptyResultDataAccessException ex,
			                                                           WebRequest request) {
		String msgUsuario = messageSource.getMessage("recurso.notfound", null, LocaleContextHolder.getLocale());
		String msgDesenv = ex.toString();
		List<StandardErro> lstErros = Arrays.asList(new StandardErro(msgUsuario, msgDesenv));	
		
		return handleExceptionInternal(ex, lstErros, new HttpHeaders(), HttpStatus.NOT_FOUND, request); 
	}
	
	// Como não há um método para sobrescrever, escrevemos um genérico	
	@ExceptionHandler({ConstraintViolationException.class})
	public ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException ex,
			                                                           WebRequest request) {
		String msgUsuario = messageSource.getMessage("constraint.violada", null, LocaleContextHolder.getLocale());
		String msgDesenv = ExceptionUtils.getRootCauseMessage(ex);
		
		List<StandardErro> lstErros = Arrays.asList(new StandardErro(msgUsuario, msgDesenv));	
		
		return handleExceptionInternal(ex, lstErros, new HttpHeaders(), HttpStatus.NOT_FOUND, request); 
	}

	
	private List<StandardErro> criarListaDeErros(BindingResult bindingResult){
		List<StandardErro> lstErros = new ArrayList<>();
		
		for(FieldError fe : bindingResult.getFieldErrors()) {
			String msgU = messageSource.getMessage(fe, LocaleContextHolder.getLocale());
			String msgD = fe.toString();
			lstErros.add(new StandardErro(msgU, msgD));
		}
		
		return lstErros;
	}
	
}
