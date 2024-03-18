package com.database.integration.service;

import com.database.integration.mysql.model.Homeworld;
import com.database.integration.mysql.model.SwCharacter;
import com.database.integration.mysql.repository.CharacterRepository;
import com.database.integration.mysql.repository.HomeworldRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CharacterService {

    private CharacterRepository characterRepository;
    private HomeworldRepository homeworldRepository;

    @Transactional
    public List<SwCharacter> getCharacters() {
        return characterRepository.findAll();
    }

    @Transactional
    public List<Homeworld> getHomeworlds() {
        return homeworldRepository.findAll();
    }

}
