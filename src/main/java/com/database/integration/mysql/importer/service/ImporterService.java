package com.database.integration.mysql.importer.service;

import com.database.integration.mysql.importer.dto.CharacterListDto;
import com.database.integration.mysql.model.MysqlCharacter;
import com.database.integration.mysql.model.MysqlHomeworld;
import com.database.integration.mysql.repository.MysqlCharacterRepository;
import com.database.integration.mysql.repository.MysqlHomeworldRepository;
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

    private final MysqlHomeworldRepository homeworldRepository;
    private final MysqlCharacterRepository characterRepository;

    @Transactional
    public void persist(CharacterListDto characterListDto) {
        Set<MysqlHomeworld> mysqlHomeworlds = DataMapper.getHomeworlds(characterListDto);
        Set<MysqlHomeworld> persistedHomeworlds = persistHomeworlds(mysqlHomeworlds);
        Set<MysqlCharacter> persistedCharacters = persistCharacters(characterListDto, persistedHomeworlds);
        log.debug("Imported Star Wars {} homewrolds and {} characters", persistedHomeworlds.size(), persistedCharacters.size());
    }

    private Set<MysqlCharacter> persistCharacters(CharacterListDto characterListDto, Set<MysqlHomeworld> persistedSet) {
        Map<String, MysqlHomeworld> homeworldMap = DataMapper.asMap(persistedSet);
        Set<MysqlCharacter> characters = DataMapper.asMysqlCharacters(characterListDto, homeworldMap);
        for (MysqlCharacter character : characters) {
            characterRepository.findByName(character.getName()).ifPresent(v -> character.setId(v.getId()));
        }
        return characters;
    }

    private Set<MysqlHomeworld> persistHomeworlds(Set<MysqlHomeworld> mysqlHomeworlds) {
        Set<MysqlHomeworld> persistedSet = new HashSet<>();
        for (MysqlHomeworld mysqlHomeworld : mysqlHomeworlds) {
            homeworldRepository.findByName(mysqlHomeworld.getName()).ifPresent(v -> mysqlHomeworld.setId(v.getId()));
            MysqlHomeworld persisted = homeworldRepository.save(mysqlHomeworld);
            persistedSet.add(persisted);
        }
        return persistedSet;
    }
}
