package com.nbaapp.models;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class PlayerSerializer extends JsonSerializer<Player> {
    @Override
    public void serialize(Player player, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {

        jsonGenerator.writeStartObject();

        jsonGenerator.writeNumberField("id", player.getId());
        jsonGenerator.writeStringField("name", player.getName());
        jsonGenerator.writeStringField("pos", player.getPosition());
        jsonGenerator.writeNumberField("teamId", player.getTeam().getId());

        jsonGenerator.writeEndObject();

    }
}
