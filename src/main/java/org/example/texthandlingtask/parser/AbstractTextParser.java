package org.example.texthandlingtask.parser;

import org.example.texthandlingtask.composite.CustomTextComponent;

public abstract class AbstractTextParser {
    protected AbstractTextParser nextParser;

    public void setNextParser(AbstractTextParser nextParser) {
        this.nextParser = nextParser;
    }

    public abstract CustomTextComponent parse(String text);
}
