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

import java.io.IOException;
import java.util.Map;
import java.util.Set;

@Component
@RequiredArgsConstructor
@Slf4j
public class ImporterService {

    private final DataReader reader;
    private final MysqlHomeworldRepository homeworldRepository;
    private final MysqlCharacterRepository characterRepository;

    @Transactional
    public Set<MysqlCharacter> persist() throws IOException {
        CharacterListDto characterListDto = reader.read();
        Set<MysqlHomeworld> mysqlHomeworlds = DataMapper.getHomeworlds(characterListDto);
        for (MysqlHomeworld mysqlHomeworld : mysqlHomeworlds) {
            MysqlHomeworld finalMysqlHomeworld = mysqlHomeworld;
            homeworldRepository.findByName(mysqlHomeworld.getName()).ifPresent(v ->
                    finalMysqlHomeworld.setId(v.getId())
            );
            mysqlHomeworld = homeworldRepository.save(finalMysqlHomeworld);
        }

        Map<String, MysqlHomeworld> homeworldMap = DataMapper.asMap(mysqlHomeworlds);
        Set<MysqlCharacter> characters = DataMapper.getSwCharacters(characterListDto, homeworldMap);
        for (MysqlCharacter character : characters) {
            MysqlCharacter finalCharacter = character;
            characterRepository.findByName(character.getName()).ifPresent(v -> finalCharacter.setId(v.getId()));
            character = characterRepository.save(finalCharacter);
        }

        log.debug("Imported {} homewrolds and {} Star Wars characters", mysqlHomeworlds.size(), characters.size());

        return characters;
    }
}
