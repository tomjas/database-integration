package com.database.integration.mysql.importer.service;

import com.database.integration.mysql.importer.dto.SwCharacterWrapperDto;
import com.database.integration.mysql.model.SwHomeworld;
import com.database.integration.mysql.model.SwCharacter;
import com.database.integration.mysql.repository.SwCharacterRepository;
import com.database.integration.mysql.repository.SwHomeworldRepository;
import com.database.integration.util.DataMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Component
@RequiredArgsConstructor
@Slf4j
public class ImporterService {

    private final SwHomeworldRepository homeworldRepository;
    private final SwCharacterRepository characterRepository;

    @Transactional
    public void persist(SwCharacterWrapperDto wrapperDto) {
        Set<SwHomeworld> homeworlds = DataMapper.getHomeworlds(wrapperDto);
        Set<SwHomeworld> persistedHomeworlds = persistHomeworlds(homeworlds);
        Set<SwCharacter> persistedCharacters = persistCharacters(wrapperDto, persistedHomeworlds);
        log.debug("Imported Star Wars {} homeworlds and {} characters", persistedHomeworlds.size(), persistedCharacters.size());
    }

    private Set<SwCharacter> persistCharacters(SwCharacterWrapperDto wrapperDto, Set<SwHomeworld> persistedSet) {
        Map<String, SwHomeworld> homeworldMap = DataMapper.asMap(persistedSet);
        Set<SwCharacter> characters = DataMapper.asSwCharacters(wrapperDto, homeworldMap);
        for (SwCharacter character : characters) {
            characterRepository.findByName(character.getName()).ifPresent(v -> character.setId(v.getId()));
        }
        return characters;
    }

    private Set<SwHomeworld> persistHomeworlds(Set<SwHomeworld> homeworlds) {
        Set<SwHomeworld> persistedSet = new HashSet<>();
        for (SwHomeworld swHomeworld : homeworlds) {
            homeworldRepository.findByName(swHomeworld.getName()).ifPresent(v -> swHomeworld.setId(v.getId()));
            SwHomeworld persisted = homeworldRepository.save(swHomeworld);
            persistedSet.add(persisted);
        }
        return persistedSet;
    }
}
