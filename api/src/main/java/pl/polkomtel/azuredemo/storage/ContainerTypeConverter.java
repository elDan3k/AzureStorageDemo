package pl.polkomtel.azuredemo.storage;

import java.beans.PropertyEditorSupport;

public class ContainerTypeConverter extends PropertyEditorSupport {

    public void setAsText(final String text) throws IllegalArgumentException {
        setValue(ContainerType.fromValue(text));
    }
}
