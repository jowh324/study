package com.example.studygroup.Repository;

import com.example.studygroup.Entitiy.Follow;
import com.example.studygroup.Entitiy.FollowId;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public class FollowRepository {
    @PersistenceContext
    private EntityManager em;

    @Transactional
    public Follow save(Follow follow) {
        em.persist(follow);
        return follow;
    }

    @Transactional
    public void delete(FollowId id) {
        Follow f = em.find(Follow.class, id);
        if (f != null) em.remove(f);
    }

    public Optional<Follow> findById(FollowId id) {
        return Optional.ofNullable(em.find(Follow.class, id));
    }

    public List<Follow> findFollowers(Long userId) {
        return em.createQuery(
                        "SELECT f FROM Follow f WHERE f.followee.id = :uid ORDER BY f.followedAt DESC", Follow.class
                )
                .setParameter("uid", userId)
                .getResultList();
    }

    public List<Follow> findFollowing(Long userId) {
        return em.createQuery(
                        "SELECT f FROM Follow f WHERE f.follower.id = :uid ORDER BY f.followedAt DESC", Follow.class
                )
                .setParameter("uid", userId)
                .getResultList();
    }
}
