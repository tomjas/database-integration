package com.database.integration.mongodb.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "characters")
@Builder
@Data
public class MonogCharacter {

    @Id
    private String id;

    @Field(name = "name")
    private String name;

    @Field(name = "pictureUrl")
    private String pictureUrl;

    @Field(name = "homeworld")
    private String homeworld;
}
