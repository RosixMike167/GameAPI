package it.ziopagnotta.api.property;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.Collections;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;

public class PropertyHolder {
    private final HashMap<String, Property<?>> properties;

    public PropertyHolder() {
        properties = new HashMap<>();
    }

    public Map<String, Property<?>> getProperties() {
        return Collections.unmodifiableMap(properties);
    }

    public boolean existsProperty(String identifier) {
        return properties.containsKey(identifier);
    }

    public void addProperty(String identifier, Property<?> value) {
        if(existsProperty(identifier))
            throw new UnsupportedOperationException("Cannot add a new property, identifier already exists: " + identifier);

        properties.put(identifier, value);
    }

    public void importProperties(@NonNull PropertyHolder other) {
        for(Map.Entry<String, Property<?>> e : other.getProperties().entrySet()) {
            addProperty(e.getKey(), e.getValue());
        }
    }

    public void overwriteProperties(@NonNull PropertyHolder other) {
        properties.clear();
        importProperties(other);
    }

    public void removeProperty(String identifier) {
        properties.remove(identifier);
    }

    public <T> T getProperty(@NonNull String identifier, @NonNull Class<T> clazz) {
        Property<?> property = properties.getOrDefault(identifier, null);

        if(property == null || !clazz.isInstance(property.getValue()))
            throw new InputMismatchException("Cannot get the property, probably not found or cast exception, provided identifier: " + identifier);

        return clazz.cast(property.getValue());
    }
}
