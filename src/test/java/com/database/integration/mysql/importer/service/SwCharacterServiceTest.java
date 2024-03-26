package com.database.integration.mysql.importer.service;

import com.database.integration.mongodb.service.IntegrationService;
import com.database.integration.mysql.exception.CharacterAlreadyExistingException;
import com.database.integration.mysql.exception.NoSuchCharacterException;
import com.database.integration.mysql.importer.dto.SwCharacterInDto;
import com.database.integration.mysql.model.SwCharacter;
import com.database.integration.mysql.model.SwHomeworld;
import com.database.integration.mysql.repository.SwCharacterRepository;
import com.database.integration.util.DataMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class SwCharacterServiceTest {

    @Mock
    private SwCharacterRepository characterRepository;

    @Mock
    private SwHomeworldService homeworldService;

    @Mock
    private IntegrationService integrationService;

    @Mock
    private SwCharacterInDto inDto;

    private SwCharacterService service;

    private SwCharacter character;

    @BeforeEach
    void setUp() {
        service = new SwCharacterService(characterRepository, homeworldService, integrationService);
        SwHomeworld homeworld = SwHomeworld.builder().id(321L).name("test homeworld").build();
        character = SwCharacter.builder().id(123L).name("test name").pictureUrl("http://url.com").homeworld(homeworld).build();
    }

    @Test
    void getCharactersWithoutName() {
        service.getCharacters(null);
        verify(characterRepository, times(1)).findAll();
        verify(characterRepository, times(0)).findByName(anyString());
    }

    @Test
    void getCharactersWithName() {
        service.getCharacters("name");
        verify(characterRepository, times(0)).findAll();
        verify(characterRepository, times(1)).findByName(anyString());
    }

    @Test
    void addShouldThrowException() {
        when(characterRepository.findByName(anyString())).thenReturn(Optional.of(character));
        when(inDto.name()).thenReturn("test name");
        CharacterAlreadyExistingException exception = assertThrows(CharacterAlreadyExistingException.class,
                () -> service.add(inDto));
        assertEquals(exception.getMessage(), "Character test name already existing. Try update.");
    }

    @Test
    void add() {
        when(characterRepository.findByName(anyString())).thenReturn(Optional.empty());
        when(characterRepository.save(any(SwCharacter.class))).thenReturn(character);
        when(inDto.homeworld()).thenReturn("test homeworld");
        when(inDto.name()).thenReturn("test name");
        try (MockedStatic<DataMapper> mocked = mockStatic(DataMapper.class)) {
            when(DataMapper.map(any(SwCharacterInDto.class), any(SwHomeworld.class))).thenReturn(character);
        }
        service.add(inDto);
        verify(homeworldService, times(1)).save(anyString());
        verify(characterRepository, times(1)).save(any(SwCharacter.class));
        verify(integrationService, times(1)).send(any(SwCharacter.class));
    }

    @Test
    void updateShouldThrowException() {
        when(characterRepository.findById(anyLong())).thenReturn(Optional.empty());
        NoSuchCharacterException exception = assertThrows(NoSuchCharacterException.class,
                () -> service.update(inDto, 123L));
        assertEquals(exception.getMessage(), "No character with id 123. Try add.");
    }

    @Test
    void update() {
        when(characterRepository.findById(anyLong())).thenReturn(Optional.of(character));
        when(inDto.homeworld()).thenReturn("test homeworld");
        when(characterRepository.save(any(SwCharacter.class))).thenReturn(character);

        service.update(inDto, 123L);
        verify(homeworldService, times(1)).save(anyString());
        verify(characterRepository, times(1)).save(any(SwCharacter.class));
        verify(integrationService, times(1)).send(any(SwCharacter.class));
    }

    @Test
    void getCharacterByName() {
        when(characterRepository.findByName(anyString())).thenReturn(Optional.empty());
        assertEquals(service.getCharacterByName("name"), Collections.emptyList());

        when(characterRepository.findByName(anyString())).thenReturn(Optional.of(character));
        List<SwCharacter> expected = service.getCharacterByName("name");
        assertEquals(expected.size(), 1);
        assertEquals(expected.get(0).getId(), 123L);
        assertEquals(expected.get(0).getName(), "test name");
        assertEquals(expected.get(0).getPictureUrl(), "http://url.com");
        assertNotNull(expected.get(0).getHomeworld());
        assertEquals(expected.get(0).getHomeworld().getId(), 321L);
        assertEquals(expected.get(0).getHomeworld().getName(), "test homeworld");
    }

    @Test
    void getCharacterById() {
        when(characterRepository.findById(anyLong())).thenReturn(Optional.empty());
        NoSuchCharacterException exception = assertThrows(NoSuchCharacterException.class,
                () -> service.getCharacterById(123L));
        assertEquals(exception.getMessage(), "No character with id 123");
    }
}