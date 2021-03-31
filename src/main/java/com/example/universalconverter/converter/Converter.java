package com.example.universalconverter.converter;

import com.example.universalconverter.model.FoundException;
import com.example.universalconverter.model.Request;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;

public class Converter {

    private String convertRule;     // convert rule which gets from method findRule
    private FoundException foundException;
    private String path;        // path to file with converting rules

    public FoundException getFoundException() {
        return foundException;
    }

    /***
     * Main method which start convert algorithm
     * @param request request from browser
     * @param path path to file with converting rules
     * @return answer from converting rule
     */
    public String convertingUnits(Request request, String path) {
        this.path = path;
        findRule(request);
        if (convertRule != null) {
            return roundAnswer(convertRule.split(",")[2]).replaceAll(" ", "");
        } else {
            return "";
        }
    }

    /***
     * finds rule for convert request
     * @param request request from browser
     */
    public void findRule(Request request) {
        File file = new File(this.path);
        if (file.exists()) {
            try {
                boolean isCorrect = requestChecking(request);

                BufferedReader bfReader = new BufferedReader(new InputStreamReader(
                        new FileInputStream(this.path), StandardCharsets.UTF_8));
                String convertLine = bfReader.readLine();

                while (convertLine != null && isCorrect) {
                    String unitsFrom = convertLine.split(",")[0].replaceAll(" ", "");
                    String unitsTo = convertLine.split(",")[1].replaceAll(" ", "");

                    if (request.getFrom().equals(unitsFrom)
                            && request.getTo().equals(unitsTo)) {
                        foundException = FoundException.OK;
                        convertRule = convertLine;
                    } else if ((request.getFrom().equals(unitsFrom)
                            || request.getTo().equals(unitsTo))
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
        } else {
            foundException = FoundException.NOT_FOUND;
        }

    }

    /***
     * Checks request errors
     * @param request request from browser
     * @return true or false
     * @throws IOException
     */
    private boolean requestChecking(Request request) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
                new FileInputStream(this.path), StandardCharsets.UTF_8));

        String line = bufferedReader.readLine();

        boolean isExistUnitsFrom = false;
        boolean isExistUnitsTo = false;
        boolean isConvertable = false;

        if (request.getFrom().equals("") || request.getTo().equals("")) {       // if request not empty
            foundException = FoundException.NOT_FOUND;
        } else {
            while (line != null) {
                String unitsFrom = line.split(",")[0].replaceAll(" ", "");
                String unitsTo = line.split(",")[1].replaceAll(" ", "");

                if (request.getFrom().equals(unitsFrom)) {
                    isExistUnitsFrom = true;
                }
                if (request.getTo().equals(unitsTo)) {
                    isExistUnitsTo = true;
                }

                if (request.getFrom().equals(unitsFrom)
                        && request.getTo().equals(unitsTo)) {
                    isConvertable = true;
                }

                line = bufferedReader.readLine();
            }

            boolean isExist = isExistUnitsFrom || isExistUnitsTo;

            if (isExist) {      // if request is correct
                if (isConvertable) {
                    return true;
                } else {        // if request is non-convertible but correct
                    foundException = FoundException.NOT_FOUND;
                    return false;
                }
            }
            foundException = FoundException.BAD_REQUEST;
            return false;
        }

        return false;
    }

    /***
     * Method for rounding answer
     * @param answer answer after converting
     * @return rounded answer
     */
    private String roundAnswer(String answer) {
        if (answer.contains(".")) {
            String wholeNumber = answer.split("\\.")[0].replaceAll(" ", "");
            String fractionalNumber = answer.split("\\.")[1].replaceAll(" ", "");

            if (wholeNumber.length() > 1) {
                int roundPower = 15 - wholeNumber.length();
                DecimalFormat format = new DecimalFormat(createFormat(roundPower));
                String newAnswer = format.format(Double.parseDouble(answer));
                return newAnswer.replaceAll(",", ".");
            } else if (fractionalNumber.length() > 14) {
                DecimalFormat format = new DecimalFormat("#.##############");
                String newAnswer = format.format(Double.parseDouble(answer));
                return newAnswer.replaceAll(",", ".");
            }
        }
        return answer;
    }

    /***
     * creates format for rational number
     * @param roundPower how many numbers needs for number length
     * @return format for number (degree)
     */
    private String createFormat(int roundPower) {
        StringBuilder format = new StringBuilder("#.");
        for (int i = 0; i < roundPower; i++) {
            format.append("#");
        }

        return format.toString();
    }
}
