package com.database.integration.mysql.importer.service;

import com.database.integration.mongodb.service.IntegrationService;
import com.database.integration.mysql.exception.CharacterAlreadyExistingException;
import com.database.integration.mysql.exception.NoSuchCharacterException;
import com.database.integration.mysql.importer.dto.SwCharacterInDto;
import com.database.integration.mysql.model.SwCharacter;
import com.database.integration.mysql.model.SwHomeworld;
import com.database.integration.mysql.repository.SwCharacterRepository;
import com.database.integration.util.DataMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SwCharacterService {

    private final SwCharacterRepository characterRepository;
    private final SwHomeworldService homeworldService;
    private final IntegrationService integrationService;

    public List<SwCharacter> getCharacters() {
        return characterRepository.findAll();
    }

    //TODO test
    @Transactional
    public SwCharacter add(SwCharacterInDto dto) {
        Optional<SwCharacter> optional = characterRepository.findByName(dto.name());
        if (optional.isPresent()) {
            throw new CharacterAlreadyExistingException("Character " + dto.name() + " already existing. Try update.");
        }
        SwHomeworld homeworld = homeworldService.save(dto.homeworld());
        SwCharacter character = DataMapper.map(dto, homeworld);
        SwCharacter persisted = characterRepository.save(character);
        integrationService.send(persisted);
        return persisted;
    }

    //TODO test
    @Transactional
    public SwCharacter update(SwCharacterInDto dto, Long id) {
        Optional<SwCharacter> optional = characterRepository.findById(id);
        SwCharacter toSave = optional.orElseThrow(() -> new NoSuchCharacterException("No character with id " + id + ". Try add."));
        toSave.setName(dto.name());
        toSave.setPictureUrl(dto.pic());
        SwHomeworld homeworld = homeworldService.save(dto.homeworld());
        toSave.setHomeworld(homeworld);
        SwCharacter saved = characterRepository.save(toSave);
        integrationService.send(saved);
        return saved;
    }

    public SwCharacter getCharacterByName(String name) {
        return characterRepository.findByName(name).orElseThrow(() -> new NoSuchCharacterException("No character with name " + name));
    }

    public SwCharacter getCharacterById(Long id) {
        return characterRepository.findById(id).orElseThrow(() -> new NoSuchCharacterException("No character with id " + id));
    }
}
