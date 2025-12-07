void main() throws IOException {
    String cookie = System.getenv("SESSION_COOKIE");
    URL url = new URL("https://adventofcode.com/2025/day/7/input");
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

    HashMap<Integer, Long> oldIndexesMap = new HashMap<>();
    oldIndexesMap.put(lines.getFirst().indexOf('S'), 1L);
    HashMap<Integer, Long> newIndexesMap = new HashMap<>();

    int result = 0;
    for (int i = 1; i < lines.size(); i++) {
        for (Map.Entry entry : oldIndexesMap.entrySet()) {
            int oldIndex = (Integer) entry.getKey();
            Long value = oldIndexesMap.get(oldIndex);
            if (lines.get(i).charAt(oldIndex) == '^') {
                result++;
                if (!newIndexesMap.containsKey(oldIndex - 1)) {
                    newIndexesMap.put(oldIndex - 1, value);
                } else {
                    newIndexesMap.put(oldIndex - 1, newIndexesMap.get(oldIndex - 1) + value);
                }
                if (!newIndexesMap.containsKey(oldIndex + 1)) {
                    newIndexesMap.put(oldIndex + 1, value);
                } else {
                    newIndexesMap.put(oldIndex + 1, newIndexesMap.get(oldIndex + 1) + value);
                }
            } else  if (!newIndexesMap.containsKey(oldIndex)) {
                newIndexesMap.put(oldIndex, oldIndexesMap.get(oldIndex));
            } else {
                newIndexesMap.put(oldIndex, newIndexesMap.get(oldIndex) + value);
            }
        }
        oldIndexesMap = newIndexesMap;
        newIndexesMap = new HashMap<>();
    }
    System.out.println(result);

    Long result2 = 0L;
    for (Map.Entry entry : oldIndexesMap.entrySet()) {
        result2 += (Long) entry.getValue();
    }
    System.out.println(result2);
}
