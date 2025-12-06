void main() throws IOException {
    String cookie = System.getenv("SESSION_COOKIE");
    URL url = new URL("https://adventofcode.com/2025/day/4/input");
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

    char[][] MAP = new char[lines.size()][lines.getFirst().length()];

    for (int i = 0; i < lines.size(); i++) {
        for (int j = 0; j < lines.getFirst().length(); j++) {
            MAP[i][j] = lines.get(i).charAt(j);
        }
    }
    int count = 0;
    int oldcount = -1;
    while (count != oldcount) {
        oldcount = count;
        for (int i = 0; i < MAP.length; i++) {
            for (int j = 0; j < MAP[i].length; j++) {
                if (MAP[i][j] == '@') {
                    int adjacentCount = 0;
                    boolean notTopRow = i != 0;
                    boolean notBottomRow = i != MAP.length - 1;
                    boolean notLeftMost = j != 0;
                    boolean notRightMost = j != MAP[i].length - 1;
                    if (notTopRow && notLeftMost && MAP[i - 1][j - 1] == '@') adjacentCount++;
                    if (notTopRow && MAP[i - 1][j] == '@') adjacentCount++;
                    if (notTopRow && notRightMost && MAP[i - 1][j + 1] == '@') adjacentCount++;
                    if (notRightMost && MAP[i][j + 1] == '@') adjacentCount++;
                    if (notBottomRow && notRightMost && MAP[i + 1][j + 1] == '@') adjacentCount++;
                    if (notBottomRow && MAP[i + 1][j] == '@') adjacentCount++;
                    if (notBottomRow && notLeftMost && MAP[i + 1][j - 1] == '@') adjacentCount++;
                    if (notLeftMost && MAP[i][j - 1] == '@') adjacentCount++;

                    if (adjacentCount < 4) {
                        count++;
                        MAP[i][j] = '.';
                    }
                }
            }
        }
    }
    System.out.println(count);
}
