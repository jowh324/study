package com.example.studygroup.Repository;

import com.example.studygroup.Entitiy.Message;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public class MessageRepository {
    @PersistenceContext
    private EntityManager em;

    @Transactional
    public Message save(Message msg) {
        em.persist(msg);
        return msg;
    }

    public Optional<Message> findById(Long id) {
        return Optional.ofNullable(em.find(Message.class, id));
    }

    public List<Message> findSentByUser(Long userId) {
        return em.createQuery(
                        "SELECT m FROM Message m WHERE m.sender.id = :uid ORDER BY m.sentAt DESC", Message.class
                )
                .setParameter("uid", userId)
                .getResultList();
    }

    public List<Message> findReceivedByUser(Long userId) {
        return em.createQuery(
                        "SELECT m FROM Message m WHERE m.receiver.id = :uid ORDER BY m.sentAt DESC", Message.class
                )
                .setParameter("uid", userId)
                .getResultList();
    }

    @Transactional
    public void deleteById(Long id) {
        Message msg = em.find(Message.class, id);
        if (msg != null) em.remove(msg);
    }
}