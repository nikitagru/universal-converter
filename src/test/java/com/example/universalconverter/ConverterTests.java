package com.example.universalconverter;

import com.example.universalconverter.converter.Converter;
import com.example.universalconverter.model.FoundException;
import com.example.universalconverter.model.Request;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ConverterTests {

    private String path = getClass().getClassLoader().getResource("unitsForTests.csv").getPath();

    @Test
    public void simpleCorrect() {
        Converter converter = new Converter();
        String result = converter.convertingUnits(new Request("м", "см"), path);
        assertEquals(result, "100");
    }

    @Test
    public void complexFromWithoutTo() {
        Converter converter = new Converter();
        String result = converter.convertingUnits(new Request("км/м", ""), path);
        assertEquals(result, "1000");
    }

    @Test
    public void complexToWithoutFrom() {
        Converter converter = new Converter();
        String result = converter.convertingUnits(new Request("", "км/м"), path);
        assertEquals(result, "1000");
    }

    @Test
    public void nonExistentRequest() {
        Converter converter = new Converter();
        String result = converter.convertingUnits(new Request("fdbdfx", "vcxbdfb"), path);
        assertEquals(FoundException.BAD_REQUEST, converter.getFoundException());
    }

    @Test
    public void incorrectRequest() {
        Converter converter = new Converter();
        String result = converter.convertingUnits(new Request("кг", "мин"), path);
        assertEquals(FoundException.NOT_FOUND, converter.getFoundException());
    }

    @Test
    public void hardTo() {
        Converter converter = new Converter();
        String result = converter.convertingUnits(new Request("Па", "кг* м^(-1)*с^(-2)"), path);
        assertEquals(result, "1");
    }

    @Test
    public void hardFrom() {
        Converter converter = new Converter();
        String result = converter.convertingUnits(new Request("кг*м^2*с^(-3)", "Вт"), path);
        assertEquals(result, "1");
    }

    @Test
    public void emptyRequest() {
        Converter converter = new Converter();
        String result = converter.convertingUnits(new Request("", ""), path);
        assertEquals(FoundException.NOT_FOUND, converter.getFoundException());
    }

    @Test
    public void emptyTo() {
        Converter converter = new Converter();
        String result = converter.convertingUnits(new Request("м*с", ""), path);
        assertEquals(FoundException.NOT_FOUND, converter.getFoundException());
    }

    @Test
    public void emptyFrom() {
        Converter converter = new Converter();
        String result = converter.convertingUnits(new Request("", "м*с"), path);
        assertEquals(FoundException.NOT_FOUND, converter.getFoundException());
    }

    @Test
    public void halfCorrect() {
        Converter converter = new Converter();
        String result = converter.convertingUnits(new Request("с", "1/км"), path);
        assertEquals(FoundException.NOT_FOUND, converter.getFoundException());
    }

    @Test
    public void incorrectForTwoExisting() {
        Converter converter = new Converter();
        String result = converter.convertingUnits(new Request("с", "м"), path);
        assertEquals(FoundException.NOT_FOUND, converter.getFoundException());
    }

    @Test
    public void correctRequestWithRounding() {
        Converter converter = new Converter();
        String result = converter.convertingUnits(new Request("Прл", "кн/п"), path);
        assertEquals(result, "7343.57648765348");
    }

    @Test
    public void complexFromWithoutToWithRounding() {
        Converter converter = new Converter();
        String result = converter.convertingUnits(new Request("кн/п", ""), path);
        assertEquals(result, "3025346843.45485");
    }

}
