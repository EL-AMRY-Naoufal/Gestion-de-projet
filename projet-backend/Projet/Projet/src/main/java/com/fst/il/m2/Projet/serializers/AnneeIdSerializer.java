package com.fst.il.m2.Projet.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.fst.il.m2.Projet.models.Annee;

import java.io.IOException;

public class AnneeIdSerializer extends StdSerializer<Annee> {

    public AnneeIdSerializer() {
        this(null);
    }

    public AnneeIdSerializer(Class<Annee> t) {
        super(t);
    }

    @Override
    public void serialize(Annee value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeNumber(value.getId());
    }
}
