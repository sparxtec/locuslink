package com.locuslink.dto.Textract;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class AwsTextractBlockDTO {

	private String blockType;

    private Float confidence;

    private String text;

    private String textType;

    private Integer rowIndex;

    private Integer columnIndex;

    private Integer rowSpan;

    private Integer columnSpan;

    private Geometry geometry;

    private String id;

    private List<Relationship> relationships;

    private List<String> entityTypes;

    private String selectionStatus;

    private Integer page;

    private Query query;

    public AwsTextractBlockDTO() {
    }
    
    @JsonCreator
    public AwsTextractBlockDTO(@JsonProperty("BlockType") String blockType,
                               @JsonProperty("Confidence") Float confidence,
                               @JsonProperty("Text") String text,
                               @JsonProperty("TextType") String textType,
                               @JsonProperty("RowIndex") Integer rowIndex,
                               @JsonProperty("ColumnIndex") Integer columnIndex,
                               @JsonProperty("RowSpan") Integer rowSpan,
                               @JsonProperty("ColumnSpan") Integer columnSpan,
                               @JsonProperty("Geometry") Geometry geometry,
                               @JsonProperty("Id") String id,
                               @JsonProperty("Relationships") List<Relationship> relationships,
                               @JsonProperty("EntityTypes") List<String> entityTypes,
                               @JsonProperty("SelectionStatus") String selectionStatus,
                               @JsonProperty("Page") Integer page,
                               @JsonProperty("Query") Query query) {
        this.blockType = blockType;
        this.confidence = confidence;
        this.text = text;
        this.textType = textType;
        this.rowIndex = rowIndex;
        this.columnIndex = columnIndex;
        this.rowSpan = rowSpan;
        this.columnSpan = columnSpan;
        this.geometry = geometry;
        this.id = id;
        this.relationships = relationships;
        this.entityTypes = entityTypes;
        this.selectionStatus = selectionStatus;
        this.page = page;
        this.query = query;
    }
    
    @JsonGetter("BlockType")
    public final String blockTypeAsString() {return blockType;}

    @JsonGetter("Confidence")
    public final Float confidence() {return confidence;}

    @JsonGetter("Text")
    public final String text() {return text;}

    @JsonGetter("TextType")
    public final String textTypeAsString() {return textType;}

    @JsonGetter("RowIndex")
    public final Integer rowIndex() {return rowIndex;}

    @JsonGetter("ColumnIndex")
    public final Integer columnIndex() {return columnIndex;}

    @JsonGetter("RowSpan")
    public final Integer rowSpan() {return rowSpan;}

    @JsonGetter("ColumnSpan")
    public final Integer columnSpan() {return columnSpan;}

    @JsonGetter("Geometry")
    public final Geometry geometry() {return geometry;}

    @JsonGetter("Id")
    public final String id() {return id;}

    public final boolean hasRelationships() {
        return relationships != null;
    }

    @JsonGetter("Relationships")
    public final List<Relationship> relationships() {return relationships;}

    @JsonGetter("EntityTypes")
    public final List<String> entityTypes() {return entityTypes;}

    public final boolean hasEntityTypes() {
        return entityTypes != null;
    }

    public List<String> entityTypesAsStrings() {return entityTypes;}

    @JsonGetter("SelectionStatus")
    public final String selectionStatus() {return selectionStatus;}

    public final String selectionStatusAsString() {return selectionStatus;}

    @JsonGetter("Page")
    public final Integer page() {return page;}

    @JsonGetter("Query")
    public final Query query() {return query;}

    @Override
    public String toString() {
        return ("AwsTextractBlockDTO[BlockType: \"%s\", Confidence: %f, Text: \"%s\", TextType: \"%s\", RowIndex: %d, " +
                "ColumnIndex: %d, RowSpan: %d, ColumnSpan: %d, %s, Relationships: %s, EntityTypes: %s, SelectionStatus: " +
                "\"%s\", Page: %d, Query: %s]").formatted(blockTypeAsString(), confidence(), text(), textTypeAsString(),
                rowIndex(), columnIndex(), rowSpan(), columnSpan(), geometry(), relationships(), entityTypesAsStrings(),
                selectionStatusAsString(), page(), query());
    }
}

class Geometry {

    private final BoundingBox boundingBox;

    private final List<Point> polygon;

    @JsonCreator
    public Geometry(@JsonProperty("BoundingBox") BoundingBox boundingBox,
                    @JsonProperty("Polygon") List<Point> polygon) {
        this.boundingBox = boundingBox;
        this.polygon = polygon;
    }

    @JsonGetter("BoundingBox")
    public BoundingBox boundingBox() {
        return boundingBox;
    }

    public final boolean hasPolygon() {return polygon != null;}

    @JsonGetter("Polygon")
    public List<Point> polygon() {
        return polygon;
    }

    @Override
    public String toString() {
        return "Geometry[%s, %s]".formatted(boundingBox().toString(), polygon().toString());
    }
}

class BoundingBox {

    private final Float width;

    private final Float height;

    private final Float left;

    private final Float top;

    @JsonCreator
    public BoundingBox(@JsonProperty("Width") Float width,
                       @JsonProperty("Height") Float height,
                       @JsonProperty("Left") Float left,
                       @JsonProperty("Top") Float top) {
        this.width = width;
        this.height = height;
        this.left = left;
        this.top = top;
    }

    @JsonGetter("Width")
    public Float width() {
        return width;
    }

    @JsonGetter("Height")
    public Float height() {
        return height;
    }

    @JsonGetter("Left")
    public Float left() {
        return left;
    }

    @JsonGetter("Top")
    public Float top() {
        return top;
    }

    @Override
    public String toString() {
        return "BoundingBox[Width: %f, Height: %f, Left: %f, Top: %f]".formatted(width(), height(), left(), top());
    }
}

class Point {

    private final Float x;

    private final Float y;

    @JsonCreator
    public Point(@JsonProperty("X") Float x,
                 @JsonProperty("Y") Float y) {
        this.x = x;
        this.y = y;
    }

    @JsonGetter("X")
    public Float x() {
        return x;
    }

    @JsonGetter("Y")
    public Float y() {
        return y;
    }

    @Override
    public String toString() {
        return "{%f, %f}".formatted(x(), y());
    }
}

class Query {

    private final String text;

    private final String alias;

    private final List<String> pages;

    @JsonCreator
    public Query(@JsonProperty("Text") String text,
                 @JsonProperty("Alias") String alias,
                 @JsonProperty("Pages") List<String> pages) {
        this.text = text;
        this.alias = alias;
        this.pages = pages;
    }

    @JsonGetter("Text")
    public String text() {
        return text;
    }

    @JsonGetter("Alias")
    public String alias() {
        return alias;
    }

    @JsonGetter("Pages")
    public List<String> pages() {
        return pages;
    }

    public final boolean hasPages() {
        return pages != null;
    }

    @Override
    public String toString() {
        return "Query[Text: %s, Alias: %s, Pages: %s]".formatted(text(), alias(), pages().toString());
    }
}