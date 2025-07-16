package com.example.studygroup.Repository;

import com.example.studygroup.Entitiy.Category;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public class CategoryRepository {
    @PersistenceContext
    private EntityManager em;

    @Transactional
    public Category save(Category category) {
        em.persist(category);
        return category;
    }

    public Optional<Category> findById(Long id) {
        return Optional.ofNullable(em.find(Category.class, id));
    }

    public Optional<Category> findByName(String name) {
        List<Category> categories = em.createQuery(
                        "SELECT c FROM Category c WHERE c.name = :name", Category.class
                )
                .setParameter("name", name)
                .getResultList();

        return categories.stream().findFirst();
    }

    public List<Category> findAll() {
        return em.createQuery("SELECT c FROM Category c", Category.class)
                .getResultList();
    }

    @Transactional
    public void deleteById(Long id) {
        Category category = em.find(Category.class, id);
        if (category != null) {
            em.remove(category);
        }
    }
}
