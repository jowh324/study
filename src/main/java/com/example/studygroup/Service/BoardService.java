package com.example.studygroup.Service;

import com.example.studygroup.DTO.BoardDto;
import com.example.studygroup.DTO.BoardRequestDto;
import com.example.studygroup.Entitiy.Board;
import com.example.studygroup.Entitiy.Users;
import com.example.studygroup.Repository.BoardRepository;
import com.example.studygroup.Repository.UserRepository;
import com.example.studygroup.exception.UserNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BoardService {
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    public BoardService(BoardRepository boardRepository, UserRepository userRepository, CategoryRepository categoryRepository) {
        this.boardRepository = boardRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
    }
    private final UserRepository userRepository;

    public BoardService(BoardRepository boardRepository, UserRepository userRepository) {
        this.boardRepository = boardRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public BoardDto create(Long userId, BoardRequestDto req) {
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
        Board board = Board.builder()
                .title(req.getTitle())
                .content(req.getContent())
                .status(req.getStatus())
                .category(req.getCategory())
                .user(user)
                .build();
        boardRepository.save(board);
        return toDto(board);
    }

    @Transactional(readOnly = true)
    public List<BoardDto> listAll() {
        return boardRepository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<BoardDto> searchByTitle(String keyword) {
        return boardRepository.findByTitleContaining(keyword).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<BoardDto> searchByCategory(String categoryName) {
        return boardRepository.findByCategory(categoryName).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public void delete(Long id) {
        boardRepository.deleteById(id);
    }

    private BoardDto toDto(Board b) {
        return new BoardDto(
                b.getId(),
                b.getTitle(),
                b.getContent(),
                b.getStatus(),
                b.getCategory().getName(),
                b.getUser().getName(),
                b.getCreatedAt()
        );
    }
}
