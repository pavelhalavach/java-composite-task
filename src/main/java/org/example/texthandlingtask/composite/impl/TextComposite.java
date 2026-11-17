package org.example.texthandlingtask.composite.impl;

import org.example.texthandlingtask.composite.ComponentType;
import org.example.texthandlingtask.composite.CustomTextComponent;

import java.util.ArrayList;
import java.util.List;

public class TextComposite implements CustomTextComponent {
    private final ComponentType componentType;
    private final List<CustomTextComponent> components = new ArrayList<>();

    public TextComposite(ComponentType componentType) {
        this.componentType = componentType;
    }

    @Override
    public void add(CustomTextComponent component) {
        components.add(component);
    }

    @Override
    public void remove(CustomTextComponent component) {
        components.remove(component);
    }

    @Override
    public List<CustomTextComponent> getComponents() {
        return new ArrayList<>(components);
    }

    @Override
    public String restore() {
        StringBuilder sb = new StringBuilder();
        for (CustomTextComponent component : components) {
            sb.append(component.restore());
        }
        return sb.toString();
    }

    @Override
    public ComponentType getType() {
        return componentType;
    }
}
