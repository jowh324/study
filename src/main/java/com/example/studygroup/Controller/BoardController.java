package com.example.studygroup.Controller;

import com.example.studygroup.DTO.BoardDto;
import com.example.studygroup.DTO.BoardRequestDto;
import com.example.studygroup.Service.BoardService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
            @RequestParam Long userId,
            @Valid @RequestBody BoardRequestDto request
    ) {
        BoardDto dto = boardService.create(userId, request);
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
    public ResponseEntity<Void> deleteBoard(@PathVariable Long id) {
        boardService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
