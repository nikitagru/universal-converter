package com.example.universalconverter.converter;

import com.example.universalconverter.model.FoundException;
import com.example.universalconverter.model.Request;

import java.io.*;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.DecimalFormat;

public class Converter {

    private String convertRule;
    private FoundException foundException;

    public FoundException getFoundException() {
        return foundException;
    }

    public String convertUnits(Request request) {
        findRule(request);
        if (convertRule != null) {
            MathContext context = new MathContext(15, RoundingMode.HALF_UP);
            double answer = Double.parseDouble(convertRule.split(",")[2]);
            return new BigDecimal(answer, context).toString();
        } else {
            return "";
        }
    }

    public void findRule(Request request) {
        ClassLoader cl = getClass().getClassLoader();
        String path = cl.getResource("units.txt").getPath();

        try {
            File file = new File(path);
            boolean isCorrect = isCorrectRequest(file, request);

            BufferedReader bfReader = new BufferedReader(new FileReader(file));
            String convertLine = bfReader.readLine();

            while (convertLine != null && isCorrect) {
                String unitsFrom = convertLine.split(",")[0].replaceAll(" ", "").toLowerCase();
                String unitsTo = convertLine.split(",")[1].replaceAll(" ", "").toLowerCase();

                if (request.getFrom().equals(unitsFrom) && request.getTo().equals(unitsTo)) {
                    foundException = FoundException.OK;
                    convertRule = convertLine;
                } else if ((request.getFrom().equals(unitsFrom) || request.getTo().equals(unitsTo))
                            && foundException != FoundException.OK) {

                    foundException = FoundException.NOT_FOUND;
                }
                convertLine = bfReader.readLine();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean isCorrectRequest(File file, Request request) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));

        foundException = FoundException.BAD_REQUEST;
        String line = bufferedReader.readLine();

        boolean isExistUnitsFrom = false;
        boolean isExistUnitsTo = false;

        while (line != null) {
            String unitsFrom = line.split(",")[0].replaceAll(" ", "").toLowerCase();
            String unitsTo = line.split(",")[1].replaceAll(" ", "").toLowerCase();

            if (request.getFrom().equals(unitsFrom)) {
                isExistUnitsFrom = true;
            }
            if (request.getTo().equals(unitsTo)) {
                isExistUnitsTo = true;
            }

            line = bufferedReader.readLine();
        }

        if (isExistUnitsFrom && isExistUnitsTo) {
            return true;
        }

        return false;
    }
}
