package org.example.texthandlingtask.composite;

import java.util.List;

public interface CustomTextComponent {
    void add(CustomTextComponent component);
    void remove(CustomTextComponent component);
    List<CustomTextComponent> getComponents();
    String restore();
    ComponentType getType();
}
