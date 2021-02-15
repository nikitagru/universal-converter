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
        if (convertRule != null) {
            return convertRule.split(",")[2];
        } else {
            return "";
        }
    }

    public void findRule(Request request) {
        ClassLoader cl = getClass().getClassLoader();
        String path = cl.getResource("units.txt").getPath();

        try {
            File file = new File(path);
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));

            foundException = FoundException.BAD_REQUEST;
            String line = bufferedReader.readLine();

            boolean isExistUnitsFrom = false;
            boolean isExistUnitsTo = false;

            while (line != null) {
                String unitsFrom = line.split(",")[0].replaceAll(" ", "").toLowerCase();
                String unitsTo = line.split(",")[1].replaceAll(" ", "").toLowerCase();

                if (request.getFromUnits().equals(unitsFrom)) {
                    isExistUnitsFrom = true;
                }
                if (request.getToUnits().equals(unitsTo)) {
                    isExistUnitsTo = true;
                }

                line = bufferedReader.readLine();
            }

            BufferedReader bfReader = new BufferedReader(new FileReader(file));
            String convertLine = bfReader.readLine();


            while (convertLine != null && isExistUnitsFrom && isExistUnitsTo) {
                String unitsFrom = convertLine.split(",")[0].replaceAll(" ", "").toLowerCase();
                String unitsTo = convertLine.split(",")[1].replaceAll(" ", "").toLowerCase();

                if (request.getFromUnits().equals(unitsFrom) && request.getToUnits().equals(unitsTo)) {
                    foundException = FoundException.OK;
                    convertRule = convertLine;
                } else if (request.getFromUnits().equals(unitsFrom)){
                    if (foundException != FoundException.OK) {
                        foundException = FoundException.NOT_FOUND;
                    }
                }
                if (request.getToUnits().equals(unitsTo)) {
                    if (foundException != FoundException.OK) {
                        foundException = FoundException.NOT_FOUND;
                    }
                }
                convertLine = bfReader.readLine();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
