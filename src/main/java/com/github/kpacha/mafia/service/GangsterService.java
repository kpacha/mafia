package com.github.kpacha.mafia.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.github.kpacha.mafia.model.Gangster;

public interface GangsterService {

    public Gangster find(Long gangsterId);

    public Page<Gangster> findAll(Pageable pageable);

    public Page<Gangster> findByNameLike(String query, Pageable pageable);

    public Gangster enroleSubordinate(Gangster boss, Gangster subordinate);

    public Gangster getUpdatedInstance(Gangster gangster);

    public void release(Gangster convicted);

    public Gangster sendToJail(Gangster convicted);

    public Gangster getBoss(Gangster gangster);

    public Integer getLevel(Gangster gangster);

    public Integer countAllSubordinates(Gangster gangster);

}
