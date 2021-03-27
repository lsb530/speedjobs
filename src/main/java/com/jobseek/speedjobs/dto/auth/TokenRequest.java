package com.jobseek.speedjobs.dto.auth;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TokenRequest {

	@NotBlank
	@Email
	private String email;

	@NotBlank
	private String password;

}
