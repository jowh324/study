package com.example.studygroup.Repository;

import com.example.studygroup.DTO.LoginRequestDto;
import com.example.studygroup.DTO.LoginResponseDto;
import com.example.studygroup.Entitiy.Users;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserRepository {
    @PersistenceContext
    private EntityManager em;

    @Transactional
    public Users save(Users user) {
        em.persist(user);
        return user;
    }

    public Optional<Users> findById(Long id) {
        return Optional.ofNullable(em.find(Users.class, id));
    }
    public Optional<Users> findByEmail(String email) {
        List<Users> users = em.createQuery(
                        "SELECT u FROM Users u WHERE u.email = :email", Users.class
                )
                .setParameter("email", email)
                .getResultList(); // getResultStream() 대신 getResultList() 사용

        return users.stream().findFirst();
    }

    public boolean existsByEmail(String email) {
        var query = em.createQuery(
                "SELECT COUNT(u) FROM Users u WHERE u.email = :email", Long.class
        );
        query.setParameter("email", email);
        return query.getSingleResult() > 0;
    }
    public boolean existsById(Long id) {
        // COUNT 쿼리를 사용하여 해당 ID의 사용자가 존재하는지 확인하고 boolean으로 반환합니다.
        var query = em.createQuery(
                "SELECT COUNT(u) FROM Users u WHERE u.id = :id", Long.class
        );
        query.setParameter("id", id);
        return query.getSingleResult() > 0;
    }

    @Transactional
    public void deleteById(Long id) {
        Users user = em.find(Users.class, id);
        if (user != null) {
            em.remove(user);
        }
    }

}
