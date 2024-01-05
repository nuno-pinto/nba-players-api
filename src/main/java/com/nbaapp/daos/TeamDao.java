package com.nbaapp.daos;

import com.nbaapp.models.Team;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TeamDao {

    @PersistenceContext
    protected EntityManager em;

    public void setEm(EntityManager em) {
        this.em = em;
    }

    public List<Team> findAll() {

        String query = "SELECT t FROM Team t";

        return em.createQuery(query, Team.class).getResultList();
    }

    @Transactional
    public void saveOrUpdate(Team team) {
        em.merge(team);
    }


}
