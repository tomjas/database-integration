package com.database.integration.mysql.importer.service;

import com.database.integration.mysql.exception.NoSuchWorldException;
import com.database.integration.mysql.model.SwHomeworld;
import com.database.integration.mysql.repository.SwHomeworldRepository;
import com.database.integration.util.DataMapper;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SwHomeworldService {

    private final SwHomeworldRepository homeworldRepository;

    public List<SwHomeworld> getHomeworlds(String name) {
        if (StringUtils.isNotBlank(name)) {
            return getHomeworldByName(name);
        }
        return homeworldRepository.findAll();
    }

    public List<SwHomeworld> getHomeworldByName(String name) {
        return homeworldRepository.findByName(name).stream().collect(Collectors.toList());
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
