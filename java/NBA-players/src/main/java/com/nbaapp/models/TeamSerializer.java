package com.nbaapp.models;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class TeamSerializer extends JsonSerializer<Team> {
    @Override
    public void serialize(Team team, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {

        jsonGenerator.writeStartObject();

        jsonGenerator.writeNumberField("id", team.getId());
        jsonGenerator.writeStringField("name", team.getName());
        jsonGenerator.writeStringField("logo", "images/teamspics/" + team.getId() + ".png");

        jsonGenerator.writeEndObject();
    }
}
