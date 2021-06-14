package com.jobseek.speedjobs.dto.user.company;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jobseek.speedjobs.domain.company.CompanyDetail;
import java.time.LocalDate;
import javax.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class CompanyUpdateRequest {

	//유저
	@NotBlank
	@Length(min = 2, max = 15)
	private String name;

	@NotBlank
	@Length(min = 2, max = 15)
	private String nickname;

	private String password;
	private String picture;
	private String contact;

	private String companyName;
	private int scale;
	private String registrationNumber;
	private String description;
	private String homepage;
	private String address;
	private Integer avgSalary; // 단위: 만원
	private Double latitude; // 위도
	private Double longitude; // 경도
	private Double rating; // 평가점수

	public CompanyDetail toCompanyDetail() {
		return CompanyDetail.from(registrationNumber, description, homepage, address, avgSalary,
			latitude, longitude, rating);
	}
}
