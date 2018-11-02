package pl.polkomtel.azuredemo.storage;

import java.util.Arrays;

public enum ContainerType {
    IMAGE("img"), THUMBNAIL("thb"), ATTACHMENT("att");

    private String value;

    ContainerType(String value) {
        this.value = value;
    }

    public static ContainerType fromValue(String value) {
        for (ContainerType type : values()) {
            if (type.value.equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException(String.format("Unknown enum type %s, Allowed values are %s", value, Arrays.toString(values())));
    }
}
