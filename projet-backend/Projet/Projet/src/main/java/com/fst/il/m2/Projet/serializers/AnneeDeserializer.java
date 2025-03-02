package com.fst.il.m2.Projet.serializers;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fst.il.m2.Projet.models.Annee;

import java.io.IOException;

public class AnneeDeserializer extends JsonDeserializer<Annee> {

    @Override
    public Annee deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        Long id = jsonParser.getLongValue();
        Annee annee = new Annee();
        annee.setId(id);
        return annee;
    }
}
