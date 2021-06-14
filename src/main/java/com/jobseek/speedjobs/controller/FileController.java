package com.jobseek.speedjobs.controller;

import com.jobseek.speedjobs.common.file.dto.File;
import com.jobseek.speedjobs.common.file.dto.FileResponses;
import com.jobseek.speedjobs.utils.S3Util;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Api(tags = {"File"})
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/file")
public class FileController {

	private final S3Util s3Util;

	@ApiOperation(value = "파일 저장", notes = "파일을 S3에 저장한다.")
	@PreAuthorize("hasAnyRole('MEMBER', 'COMPANY', 'ADMIN')")
	@PostMapping("")
	public ResponseEntity<FileResponses> save(@RequestPart List<MultipartFile> files) {
		List<File> result = files
			.stream()
			.map(file -> File.builder()
				.baseName(FilenameUtils.getBaseName(file.getOriginalFilename()))
				.extension(FilenameUtils.getExtension(file.getOriginalFilename()))
				.url(s3Util.upload(file))
				.build())
			.collect(Collectors.toList());
		return ResponseEntity.ok().body(FileResponses.of(result));
	}
}
