package com.example.studygroup.Repository;

import com.example.studygroup.Entitiy.Board;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class BoardRepository {
    @PersistenceContext
    private EntityManager em;

    @Transactional
    public Board save(Board board) {
        em.persist(board);
        return board;
    }

    public Optional<Board> findById(Long id) {
        return Optional.ofNullable(em.find(Board.class, id));
    }

    public List<Board> findAll() {
        return em.createQuery("SELECT b FROM Board b ORDER BY b.createdAt DESC", Board.class)
                .getResultList();
    }
    public List<Board> findByTitleContaining(String keyword) {
        return em.createQuery(
                        "SELECT b FROM Board b WHERE b.title LIKE :kw ORDER BY b.createdAt DESC", Board.class
                )
                .setParameter("kw", "%" + keyword + "%")
                .getResultList();
    }
    public List<Board> findByCategory(String categoryName) {
        return em.createQuery(
                        "SELECT b FROM Board b WHERE b.category.name = :cat ORDER BY b.createdAt DESC", Board.class
                )
                .setParameter("cat", categoryName)
                .getResultList();
    }

    @Transactional
    public void deleteById(Long id) {
        Board board = em.find(Board.class, id);
        if (board != null) {
            em.remove(board);
        }
    }
}
