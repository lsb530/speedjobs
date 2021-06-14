package com.jobseek.speedjobs.controller;

import com.jobseek.speedjobs.dto.tag.TagRequest;
import com.jobseek.speedjobs.dto.tag.TagResponses;
import com.jobseek.speedjobs.service.TagService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"Tag"})
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/tag")
public class TagController {

	private final TagService tagService;

	@ApiOperation(value = "태그 등록", notes = "태그를 등록한다.")
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping
	public ResponseEntity<Long> saveTag(@Valid @RequestBody TagRequest tagRequest) {
		return ResponseEntity.ok().body(tagService.saveTag(tagRequest));
	}

	@ApiOperation(value = "태그 조회", notes = "태그를 조회한다.")
	@GetMapping
	public ResponseEntity<TagResponses> findTagsByType() {
		return ResponseEntity.ok().body(tagService.findTagsByType());
	}

	@ApiOperation(value = "태그 삭제", notes = "태그를 삭제한다.")
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{tagId}")
	public ResponseEntity<Void> deleteTag(@PathVariable Long tagId) {
		tagService.deleteTag(tagId);
		return ResponseEntity.noContent().build();
	}

	@ApiOperation(value = "태그 수정", notes = "태그를 수정한다.")
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/{tagId}")
	public ResponseEntity<Void> updateTag(@PathVariable Long tagId,
		@Valid @RequestBody TagRequest tagRequest) {
		tagService.updateTag(tagId, tagRequest);
		return ResponseEntity.noContent().build();
	}
}
