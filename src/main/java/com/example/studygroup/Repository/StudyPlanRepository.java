package com.example.studygroup.Repository;

import com.example.studygroup.Entitiy.StudyPlan;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public class StudyPlanRepository {
    @PersistenceContext
    private EntityManager em;

    @Transactional
    public StudyPlan save(StudyPlan plan) {
        em.persist(plan);
        return plan;
    }

    @Transactional
    public StudyPlan update(StudyPlan plan) {
        return em.merge(plan);
    }

    public Optional<StudyPlan> findById(Long id) {
        return Optional.ofNullable(em.find(StudyPlan.class, id));
    }

    public List<StudyPlan> findByUserId(Long userId) {
        return em.createQuery(
                        "SELECT sp FROM StudyPlan sp WHERE sp.user.id = :uid ORDER BY sp.startDate", StudyPlan.class
                )
                .setParameter("uid", userId)
                .getResultList();
    }

    @Transactional
    public void deleteById(Long id) {
        StudyPlan sp = em.find(StudyPlan.class, id);
        if (sp != null) em.remove(sp);
    }
}
