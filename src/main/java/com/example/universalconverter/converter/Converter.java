package com.example.universalconverter.converter;

import com.example.universalconverter.model.FoundException;
import com.example.universalconverter.model.Request;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.*;

public class Converter {

    private String convertRule;
    private FoundException foundException;

    public FoundException getFoundException() {
        return foundException;
    }

    public String convertUnits(Request request) {
        findRule(request);
        if (foundException == FoundException.OK) {
            return convertRule.split(",")[2];
        } else {
            return foundException.name();
        }

    }

    public void findRule(Request request) {
        ClassLoader cl = getClass().getClassLoader();
        String path = cl.getResource("units.txt").getPath();

        try {
            File file = new File(path);
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            foundException = FoundException.BAD_REQUEST;
            String line = bufferedReader.readLine();
            while (line != null) {
                String unitsFrom = line.split(",")[0].replaceAll(" ", "").toLowerCase();
                String unitsTo = line.split(",")[1].replaceAll(" ", "").toLowerCase();
                if (request.getFromUnits().equals(unitsFrom) && request.getToUnits().equals(unitsTo)) {
                    foundException = FoundException.OK;
                    convertRule = line;
                } else if (request.getFromUnits().equals(unitsFrom) || request.getToUnits().equals(unitsTo)) {
                    if (foundException != FoundException.OK) {
                        foundException = FoundException.NOT_FOUND;
                    }
                }
                line = bufferedReader.readLine();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
