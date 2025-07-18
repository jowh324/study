package com.example.studygroup.Controller;

import com.example.studygroup.DTO.BoardDto;
import com.example.studygroup.DTO.BoardRequestDto;
import com.example.studygroup.Service.BoardService;
import com.example.studygroup.Service.CustomUserDetails;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/boards")
public class BoardController {
    private final BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }


    @PostMapping
    public ResponseEntity<BoardDto> createBoard(
            @AuthenticationPrincipal CustomUserDetails userDetails, // <-- 이렇게 변경
            @Valid @RequestBody BoardRequestDto request
    ) {
        // userDetails 객체에서 사용자 ID를 안전하게 가져옵니다.
        Long currentUserId = userDetails.getUserId();

        BoardDto dto = boardService.create(currentUserId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @GetMapping
    public ResponseEntity<List<BoardDto>> listBoards() {
        return ResponseEntity.ok(boardService.listAll());
    }

    @GetMapping("/search/title")
    public ResponseEntity<List<BoardDto>> searchByTitle(
            @RequestParam String keyword
    ) {
        return ResponseEntity.ok(boardService.searchByTitle(keyword));
    }

    @GetMapping("/search/category")
    public ResponseEntity<List<BoardDto>> searchByCategory(
            @RequestParam String category
    ) {
        return ResponseEntity.ok(boardService.searchByCategory(category));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBoard(
            @PathVariable Long id,
            @AuthenticationPrincipal CustomUserDetails userDetails // userDetails 추가
    ) {
        Long currentUserId = userDetails.getUserId(); // userId 가져오기
        boardService.delete(id, currentUserId); // 서비스에 두 ID 모두 전달
        return ResponseEntity.noContent().build();
    }
}
