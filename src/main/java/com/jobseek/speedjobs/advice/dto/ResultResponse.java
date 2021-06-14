package com.jobseek.speedjobs.advice.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Builder
@Getter
@Setter
public class ResultResponse {

	private String status;
	private String message;

}

