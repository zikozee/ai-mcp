package com.zee.systems.controller;


import com.zee.systems.dto.ChatRequestDto;
import com.zee.systems.dto.ChatResponseDto;
import com.zee.systems.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @dev : Ezekiel Eromosei
 * @date : 19 Oct, 2026
 */

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "chat")
public class ChatController {

    private final ChatService chatService;

    @PostMapping
    public ResponseEntity<ChatResponseDto> chat(@RequestBody ChatRequestDto chatRequestDto) {
        return ResponseEntity.ok(chatService.handleMessage(chatRequestDto.message()));
    }
}
