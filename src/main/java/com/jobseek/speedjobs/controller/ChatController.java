package com.jobseek.speedjobs.controller;

import com.jobseek.speedjobs.dto.message.MessageRequest;
import com.jobseek.speedjobs.dto.message.MessageResponse;
import com.jobseek.speedjobs.service.ChatService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Api(tags = {"Chat"})
@RequiredArgsConstructor
@Controller
public class ChatController {

	private final ChatService chatService;

	@MessageMapping("/message")
	public void saveMessage(MessageRequest messageRequest) {
		chatService.saveMessage(messageRequest);
	}

	@ApiOperation(value = "채팅 조회", notes = "채팅 내역을 조회한다.")
	@GetMapping("/api/chat/{roomId}")
	public ResponseEntity<Page<MessageResponse>> findAll(@PathVariable Long roomId,
		Pageable pageable) {
		return ResponseEntity.ok().body(chatService.findAll(roomId, pageable));
	}

	@ApiOperation(value = "메시지 삭제", notes = "메시지를 삭제한다.")
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/api/chat/{roomId}/message/{messageId}")
	public ResponseEntity<Void> deleteMessage(@PathVariable Long roomId,
		@PathVariable Long messageId) {
		chatService.deleteMessage(messageId);
		return ResponseEntity.created(URI.create("/api/chat/" + roomId)).build();
	}
}
