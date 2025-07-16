package com.example.studygroup.Service;

import com.example.studygroup.DTO.BoardDto;
import com.example.studygroup.DTO.BoardRequestDto;
import com.example.studygroup.Entitiy.Board;
import com.example.studygroup.Entitiy.Category;
import com.example.studygroup.Entitiy.Users;
import com.example.studygroup.Repository.BoardRepository;
import com.example.studygroup.Repository.CategoryRepository;
import com.example.studygroup.Repository.UserRepository;
import com.example.studygroup.exception.CategoryNotFoundException;
import com.example.studygroup.exception.UserNotFoundException;
import jakarta.persistence.EntityNotFoundException;
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


    @Transactional
    public BoardDto create(Long userId, BoardRequestDto req) {
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
        Category category = categoryRepository.findByName(req.getCategory())
                .orElseThrow(() -> new CategoryNotFoundException(req.getCategory()));
        Board board = Board.builder()
                .title(req.getTitle())
                .content(req.getContent())
                .status(req.getStatus())
                .category(category)
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
    public void delete(Long boardId, Long currentUserId) { // 메소드에 userId 파라미터 추가
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new EntityNotFoundException("Board not found"));

        // 게시글의 작성자 ID와 현재 로그인한 사용자의 ID가 같은지 확인
        if (!board.getUser().getId().equals(currentUserId)) {
            throw new IllegalStateException("You do not have permission to delete this board."); // 권한 없음 예외 발생
        }

        boardRepository.deleteById(boardId);
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
