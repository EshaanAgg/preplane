package com.preplane.dev.models;

import java.util.HashMap;
import java.util.Map;

public class Tag {
    public enum Type {
        COMPANY, TOPIC, DIFFICULTY
    }

    private int id;
    private Type type;
    private String name;

    public Tag(int id, String type, String name) {
        this.id = id;
        this.type = Type.valueOf(type);
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Debugging and mapping related implementations
    public Map<String, Object> toMap() {
        Map<String, Object> parameters = new HashMap<>();

        parameters.put("id", this.id);
        parameters.put("type", this.type.toString());
        parameters.put("name", this.name);

        return parameters;
    }

    @Override
    public String toString() {
        return "Tag {\n" +
                "  id = " + this.id + ",\n" +
                "  type = " + this.type + ",\n" +
                "  name = '" + this.name + "\'\n" +
                "}";
    }
}
