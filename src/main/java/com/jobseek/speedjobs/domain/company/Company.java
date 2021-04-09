package com.jobseek.speedjobs.domain.company;

import com.jobseek.speedjobs.domain.recruit.Recruit;
import com.jobseek.speedjobs.domain.user.User;
import lombok.*;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.LAZY;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
@Table(name = "companies")
public class Company {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "company_id")
	private Long id;

	private String companyName;

	private String logoImage;

	private int scale;

	@Embedded
	private CompanyDetail companyDetail;

	@OneToOne(fetch = LAZY, cascade = ALL)
	@JoinColumn(name = "user_id")
	private User user;

	@OneToMany(mappedBy = "company", cascade = ALL)
	private List<Recruit> recruitList = new ArrayList<>();

	@Builder
	public Company(String companyName, String logoImage, int scale,
		CompanyDetail companyDetail) {
		this.companyName = companyName;
		this.logoImage = logoImage;
		this.scale = scale;
		this.companyDetail = companyDetail;
	}

	public void setRecruitList(List<Recruit> recruitList) {
		this.recruitList = recruitList;
	}


}