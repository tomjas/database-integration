package com.database.integration.mysql.importer.service;

import com.database.integration.mysql.exception.NoSuchWorldException;
import com.database.integration.mysql.model.SwHomeworld;
import com.database.integration.mysql.repository.SwHomeworldRepository;
import com.database.integration.util.DataMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SwHomeworldService {

    private final SwHomeworldRepository homeworldRepository;

    public List<SwHomeworld> getHomeworlds() {
        return homeworldRepository.findAll();
    }

    public SwHomeworld getHomeworldByName(String name) {
        return homeworldRepository.findByName(name).orElseThrow(() -> new NoSuchWorldException("No world with name " + name));
    }

    public SwHomeworld getHomeworldById(Long id) {
        return homeworldRepository.findById(id).orElseThrow(() -> new NoSuchWorldException("No world with id " + id));
    }

    public SwHomeworld save(String name) {
        Optional<SwHomeworld> homeworld = homeworldRepository.findByName(name);
        return homeworld.orElseGet(() -> {
            SwHomeworld toSave = DataMapper.getHomeworld(name);
            return homeworldRepository.save(toSave);
        });
    }
}
