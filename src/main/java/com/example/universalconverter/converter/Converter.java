package com.example.universalconverter.converter;

import com.example.universalconverter.model.Request;

import java.io.*;

public class Converter {
    public String convertUnits(Request request) {
        String convertRule = findCurrent(request);
        return convertRule.split(",")[2];
    }

    public String findCurrent(Request request) {
        ClassLoader cl = getClass().getClassLoader();
        String path = cl.getResource("units.txt").getPath();

        try {
            File file = new File(path);
            FileReader fr = new FileReader(file);
            BufferedReader reader = new BufferedReader(fr);

            String line = reader.readLine();
            while (line != null) {
                String unitsFrom = line.split(",")[0].replaceAll(" ", "");
                String unitsTo = line.split(",")[1].replaceAll(" ", "");
                if (request.getFromUnits().equals(unitsFrom) && request.getToUnits().equals(unitsTo)) {
                    return line;
                } else {

                }
                line = reader.readLine();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
}
