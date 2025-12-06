void main() throws IOException {
    String cookie = System.getenv("SESSION_COOKIE");
    URL url = new URL("https://adventofcode.com/2025/day/6/input");
    URLConnection connection = url.openConnection();
    connection.setRequestProperty("Cookie", "session=" + cookie);
    List<String> lines = new ArrayList<>();

    try (BufferedReader reader = new BufferedReader(
            new InputStreamReader(connection.getInputStream()))) {

        String line;
        while ((line = reader.readLine()) != null) {
            lines.add(line);
        }
    }


    Long[][] values = new Long[lines.size() - 1][];
    String[] operands = null;
    for (String line : lines) {
        String modifiedLine = line;
        while(!modifiedLine.replaceAll("  ", " ").equals(modifiedLine)) {
            modifiedLine = modifiedLine.replaceAll("  ", " ");
        }
        if (!lines.getLast().equals(line)) {
            String[] split = modifiedLine.split(" ");
            values[lines.indexOf(line)] = new Long[split.length];
            for (int i = 0; i < split.length; i++) {
                values[lines.indexOf(line)][i] = Long.parseLong(split[i]);
            }
        } else {
            operands = modifiedLine.split(" ");
        }
    }

    Long result = 0L;
    for (int j = 0; j < values[0].length; j++) {
        Long currentResult = values[0][j];
        for (int i = 1; i < values.length; i++) {
            if (operands[j].equals("+")) {
                currentResult += values[i][j];
            } else {
                currentResult *= values[i][j];
            }
        }
        result += currentResult;
    }

    System.out.println(result);

    //PART2
    Long result2 = 0L;

    char[][] originalInput = new char[lines.size() - 1][lines.getFirst().length()];
    for (int i = 0; i < lines.size() - 1; i++) {
        for (int j = 0; j < lines.get(i).length(); j++) {
            originalInput[i][j] = lines.get(i).charAt(j);
        }
    }

    char[][] rotatedInput = new char[lines.getFirst().length()][lines.size() - 1];
    for (int i = 0; i < lines.getFirst().length(); i++) {
        for (int j = 0; j < lines.size() - 1; j++) {
            rotatedInput[i][j] = lines.get(j).charAt(i);
        }
    }

    String[] rotatedLines = new String[rotatedInput.length];
    for (int i = 0; i < rotatedInput.length; i++) {
        String newLine = "";
        for (int j = 0; j < rotatedInput[i].length; j++) {
            newLine += rotatedInput[i][j];
        }
        rotatedLines[i] = newLine;
    }

    int operandIdx = 0;
    Long currentSubresult = 0L;
    for (String rotatedLine : rotatedLines) {
        String trimmedLine = rotatedLine.trim();
        if (trimmedLine.isEmpty()) {
            result2 += currentSubresult;
            operandIdx++;
            currentSubresult = 0L;
        } else {
            Long lineValue = Long.parseLong(trimmedLine);
            if (currentSubresult == 0) {
                currentSubresult = lineValue;
            } else {
                if (operands[operandIdx].equals("+")) {
                    currentSubresult += lineValue;
                } else {
                    currentSubresult *= lineValue;
                }
            }
        }
    }
    System.out.println(result2 + currentSubresult);
}
