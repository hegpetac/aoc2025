void main() throws IOException {
    String cookie = System.getenv("SESSION_COOKIE");
    URL url = new URL("https://adventofcode.com/2025/day/5/input");
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

    class Range {
        Long start;
        Long end;

        Range(Long start, Long end) {
            this.start = start;
            this.end = end;
        }

        static int compare(Range r1, Range r2) {
            return Long.compare(r1.start, r2.start);
        }

        public void setEnd(Long end) {
            this.end = end;
        }
    }

    List<Range> rangeList = new ArrayList<>(lines.subList(0, 192).stream().map(s -> {
        String[] split = s.split("-");
        return new Range(Long.parseLong(split[0]), Long.parseLong(split[1]));
    }).toList());

    rangeList.sort(Range::compare);
    ArrayList<Range> ranges = new ArrayList<>();
    ranges.add(rangeList.getFirst());

    for(int i = 1; i < rangeList.size(); i++) {
        if (rangeList.get(i).start <= ranges.getLast().end && rangeList.get(i).end > ranges.getLast().end) {
            ranges.getLast().setEnd(rangeList.get(i).end);
        } else if (rangeList.get(i).start > ranges.getLast().end) {
            ranges.add(rangeList.get(i));
        }
    }

    Long result2 = 0L;
    for (Range range : ranges) {
        result2 = result2 + range.end - range.start + 1;
    }

    int result = 0;
    List<Long> values = lines.subList(193, lines.size()).stream().map(Long::parseLong).toList();
    for (Long value : values) {
         int idx = -(Collections.binarySearch(ranges, new Range(value, Long.MAX_VALUE), Range::compare)) - 2;
         if (idx >= 0 && ranges.get(idx).start <= value && value <= ranges.get(idx).end) {
             result++;
         }
    }

    System.out.println(result);
    System.out.println(result2);
}
