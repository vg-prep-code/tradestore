package com.vg.tradestore.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.vg.tradestore.dto.CustomErrorResponse;
import com.vg.tradestore.dto.GlobalErrorCode;
import com.vg.tradestore.exception.TradeException;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class TradeStoreServiceGlobalExceptionHandler {

	@ExceptionHandler(TradeException.class)
	public ResponseEntity<?> handleTradeException(TradeException ex) {
		CustomErrorResponse errorResponse = CustomErrorResponse.builder().status(HttpStatus.BAD_REQUEST)
				.errorCode(GlobalErrorCode.GENERIC_ERROR).errorMessage(ex.getMessage()).build();
		log.error("TradeStoreServiceGlobalExceptionHandler::handleTradeException exception caught {}", ex.getMessage());
		return ResponseEntity.badRequest().body(errorResponse);
	}
}
