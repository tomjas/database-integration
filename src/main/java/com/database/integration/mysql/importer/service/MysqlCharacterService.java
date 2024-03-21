package com.database.integration.mysql.importer.service;

import com.database.integration.mongodb.service.IntegrationService;
import com.database.integration.mysql.exception.NoSuchUserException;
import com.database.integration.mysql.exception.UserAlreadyExistingException;
import com.database.integration.mysql.importer.dto.CharacterDto;
import com.database.integration.mysql.model.MysqlCharacter;
import com.database.integration.mysql.model.MysqlHomeworld;
import com.database.integration.mysql.repository.MysqlCharacterRepository;
import com.database.integration.mysql.repository.MysqlHomeworldRepository;
import com.database.integration.util.DataMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MysqlCharacterService {

    private final MysqlCharacterRepository characterRepository;
    private final MysqlHomeworldRepository homeworldRepository;
    private final IntegrationService integrationService;

    public List<MysqlCharacter> getCharacters() {
        return characterRepository.findAll();
    }

    public List<MysqlHomeworld> getHomeworlds() {
        return homeworldRepository.findAll();
    }

    //TODO test
    @Transactional
    public MysqlCharacter add(CharacterDto dto) {
        Optional<MysqlCharacter> character = characterRepository.findByName(dto.name());
        if (character.isPresent()) {
            throw new UserAlreadyExistingException("User " + dto.name() + " already existing. Try update.");
        }
        MysqlHomeworld homeworld = persist(dto.homeworld());
        MysqlCharacter mapped = DataMapper.map(dto, homeworld);
        MysqlCharacter saved = characterRepository.save(mapped);
        integrationService.send(saved);
        return saved;
    }

    //TODO test
    @Transactional
    public MysqlCharacter update(CharacterDto dto, Long id) {
        Optional<MysqlCharacter> character = characterRepository.findById(id);
        MysqlCharacter toSave = character.orElseThrow(() -> new NoSuchUserException("No user with id " + id + ". Try add."));
        toSave.setName(dto.name());
        toSave.setPictureUrl(dto.pic());
        MysqlHomeworld homeworld = persist(dto.homeworld());
        toSave.setHomeworld(homeworld);
        MysqlCharacter saved = characterRepository.save(toSave);
        integrationService.send(saved);
        return saved;
    }

    private MysqlHomeworld persist(String name) {
        Optional<MysqlHomeworld> homeworld = homeworldRepository.findByName(name);
        return homeworld.orElseGet(() -> {
            MysqlHomeworld toSave = DataMapper.getHomeworld(name);
            return homeworldRepository.save(toSave);
        });
    }
}
