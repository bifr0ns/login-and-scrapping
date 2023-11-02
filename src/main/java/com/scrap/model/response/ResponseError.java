package com.scrap.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * Response Error
 */
@Data
@Builder
@AllArgsConstructor
public class ResponseError {
	private String mensaje;
	private List<String> detalles;
}
