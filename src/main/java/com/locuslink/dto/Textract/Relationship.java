package com.locuslink.dto.Textract;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Relationship {

	private final String type;

    private final List<String> ids;

    @JsonCreator
    public Relationship(@JsonProperty("Type") String type,
                        @JsonProperty("Ids") List<String> ids) {
        this.type = type;
        this.ids = ids;
    }

    @JsonGetter("Type")
    public String type() {
        return type;
    }

    public final String typeAsString() {return type;}

    public final boolean hasIds() {return ids != null;}

    @JsonGetter("Ids")
    public List<String> ids() {
        return ids;
    }

    @Override
    public String toString() {
        return "Relationship[Type: %s, Ids: %s]".formatted(typeAsString(), ids().toString());
    }
}
