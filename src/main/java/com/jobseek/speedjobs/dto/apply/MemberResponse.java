package com.jobseek.speedjobs.dto.apply;

import com.jobseek.speedjobs.domain.member.Member;
import com.jobseek.speedjobs.domain.resume.Apply;
import com.jobseek.speedjobs.domain.resume.Resume;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

// 회사가 자신의 공고로 지원된 이력서내역들을 조회할 때
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberResponse {

	private Long applyId;
	private Long resumeId;
	private String title;
	private Long memberId;
	private String email;
	private String contact;
	private String name;

	public static MemberResponse of(Apply apply) {
		Resume resume = apply.getResume();
		Member member = resume.getMember();

		return MemberResponse.builder()
			.applyId(apply.getId())
			.resumeId(resume.getId())
			.title(resume.getTitle())
			.memberId(member.getId())
			.email(member.getEmail())
			.contact(member.getContact())
			.name(member.getName())
			.build();
	}
}
