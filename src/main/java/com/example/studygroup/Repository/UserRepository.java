package com.example.studygroup.Repository;

import com.example.studygroup.DTO.LoginRequestDto;
import com.example.studygroup.DTO.LoginResponseDto;
import com.example.studygroup.Entitiy.Users;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

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
        var query = em.createQuery(
                "SELECT u FROM Users u WHERE u.email = :email", Users.class
        );
        query.setParameter("email", email);
        return query.getResultStream().findFirst();
    }

    public boolean existsByEmail(String email) {
        var query = em.createQuery(
                "SELECT COUNT(u) FROM Users u WHERE u.email = :email", Long.class
        );
        query.setParameter("email", email);
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
