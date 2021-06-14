package com.jobseek.speedjobs.domain.member;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PROTECTED;

import com.jobseek.speedjobs.domain.resume.Resume;
import com.jobseek.speedjobs.domain.user.Provider;
import com.jobseek.speedjobs.domain.user.User;
import com.jobseek.speedjobs.domain.user.UserDto;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PRIVATE)
@PrimaryKeyJoinColumn(name = "user_id")
@Table(name = "members")
public class Member extends User {

	@OneToMany(mappedBy = "member", cascade = ALL, orphanRemoval = true)
	private final List<Resume> resumeList = new ArrayList<>();

	private String gender;

	private LocalDate birth;

	private String bio;

	private String oauthId;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Provider provider;

	public Member(UserDto userDto) {
		super(userDto.getName(), userDto.getNickname(), userDto.getEmail(), userDto.getPassword(), userDto.getPicture(),
			userDto.getContact(), userDto.getRole());
		this.gender = userDto.getGender();
		this.birth = userDto.getBirth();
		this.bio = userDto.getBio();
		this.provider = userDto.getProvider();
		this.oauthId = userDto.getOauthId();
	}

	public Member updateCustomMemberInfo(String name, String nickname, String picture,
		String contact, LocalDate birth, String bio, String gender) {
		updateCustomUserInfo(name, nickname, picture, contact);
		this.birth = birth;
		this.bio = bio;
		this.gender = gender;
		return this;
	}

}
