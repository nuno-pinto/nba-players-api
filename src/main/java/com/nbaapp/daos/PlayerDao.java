package com.nbaapp.daos;

import com.nbaapp.models.Player;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PlayerDao {

    @PersistenceContext
    protected EntityManager em;

    public void setEm(EntityManager em) {
        this.em = em;
    }

    public List<Player> findAll() {

        String query = "SELECT p FROM Player p";

        return em.createQuery(query, Player.class).getResultList();
    }

    @Transactional
    public void saveOrUpdate(Player player) {
        em.merge(player);
    }

}
