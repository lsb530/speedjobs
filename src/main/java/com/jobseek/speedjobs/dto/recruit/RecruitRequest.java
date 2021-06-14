package com.jobseek.speedjobs.dto.recruit;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jobseek.speedjobs.domain.recruit.Experience;
import com.jobseek.speedjobs.domain.recruit.Position;
import com.jobseek.speedjobs.domain.recruit.Recruit;
import com.jobseek.speedjobs.domain.recruit.Status;
import java.time.LocalDateTime;
import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RecruitRequest {

	@NotBlank
	private String title;

	@NotNull
	@JsonFormat(pattern = "yyyy-MM-dd kk:mm:ss", timezone = "Asia/Seoul")
	private LocalDateTime openDate;

	@NotNull
	@JsonFormat(pattern = "yyyy-MM-dd kk:mm:ss", timezone = "Asia/Seoul")
	private LocalDateTime closeDate;

	@NotNull
	private Status status;

	private String thumbnail;

	@NotNull
	private Experience experience; // 경력

	@NotNull
	private Position position; // 고용 형태

	@NotBlank
	private String content;

	private List<Long> tagIds;

	@Builder
	public RecruitRequest(String title, LocalDateTime openDate, LocalDateTime closeDate,
		Status status, String thumbnail, Experience experience,
		Position position, String content, List<Long> tagIds) {
		this.title = title;
		this.openDate = openDate;
		this.closeDate = closeDate;
		this.status = status;
		this.thumbnail = thumbnail;
		this.experience = experience;
		this.position = position;
		this.content = content;
		this.tagIds = tagIds;
	}

	public Recruit toEntity() {
		return Recruit
			.createRecruit(title, openDate,
				closeDate, status, thumbnail,
				experience, position, content);
	}

}
