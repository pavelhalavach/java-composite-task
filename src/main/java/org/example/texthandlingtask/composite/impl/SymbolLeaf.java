package org.example.texthandlingtask.composite.impl;

import org.example.texthandlingtask.composite.ComponentType;
import org.example.texthandlingtask.composite.CustomTextComponent;

import java.util.List;

public class SymbolLeaf implements CustomTextComponent {
    private final char symbol;

    public SymbolLeaf(char symbol) {
        this.symbol = symbol;
    }
    @Override
    public void add(CustomTextComponent component) {
        throw new UnsupportedOperationException("Leaf cannot contain children");
    }

    @Override
    public void remove(CustomTextComponent component) {
        throw new UnsupportedOperationException("Leaf cannot contain children");
    }

    @Override
    public List<CustomTextComponent> getComponents() {
        throw new UnsupportedOperationException("Leaf cannot contain children");
    }

    @Override
    public String restore() {
        return String.valueOf(symbol);
    }

    @Override
    public ComponentType getType() {
        return ComponentType.SYMBOL;
    }
}
