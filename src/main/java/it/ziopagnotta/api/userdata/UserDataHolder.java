package it.ziopagnotta.api.userdata;

import it.ziopagnotta.api.property.PropertyHolder;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class UserDataHolder {
    private final HashMap<UUID, UserData> userData;

    public UserDataHolder() {
        userData = new HashMap<>();
    }

    public boolean exists(UUID uuid) {
        return userData.containsKey(uuid);
    }

    public UserData createFreshAndRegister(UUID uuid) {
        if(exists(uuid))
            throw new UnsupportedOperationException("provided uuid already registered into the map: (" + uuid.toString() + ")");

        return userData.put(uuid, new UserData(uuid));
    }

    public UserData createAndRegister(UUID uuid, PropertyHolder propertyHolder) {
        if(exists(uuid))
            throw new UnsupportedOperationException("provided uuid already registered into the map: (" + uuid.toString() + ")");
        UserData d = new UserData(uuid);
        d.importProperties(propertyHolder);
        return userData.put(uuid, d);
    }

    public void remove(UUID uuid) {
        userData.remove(uuid);
    }

    public Map<UUID, UserData> getUserData() {
        return Collections.unmodifiableMap(userData);
    }
}
