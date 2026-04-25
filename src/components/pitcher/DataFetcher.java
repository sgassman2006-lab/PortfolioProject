package components.pitcher;

import java.io.Reader;
import java.io.StringReader;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import components.map.Map;
import components.map.Map1L;

/**
 * Fetches pitcher statistics from the MLB Stats API and Baseball Savant.
 * Combines traditional season stats with Statcast metrics into one map.
 */
public final class DataFetcher {

    /**
     * Mapper for statistics.
     */
    private static final ObjectMapper MAPPER = new ObjectMapper();

    /**
     * Private constructor for utility class.
     */
    private DataFetcher() {

    }

    /**
     * Fetches all stats for a pitcher and returns them as a Map<String, Double>
     * keyed by stat name.
     *
     * @param playerId
     *            MLB player ID
     * @param year
     *            Season year
     * @return Map of stat name with value
     */
    public static Map<String, Double> fetchAllStats(int playerId, int year) {
        Map<String, Double> stats = new Map1L<>();

        fetchTraditionalStats(playerId, year, stats);
        fetchStatcastStats(playerId, year, stats);

        return stats;
    }

    /**
     * Private Helper Method that fetches traditional stats from MLB's Stats API
     * of a specific player and season.
     *
     * @param playerId
     * @param year
     * @param stats
     */
    public static void fetchTraditionalStats(int playerId, int year,
            Map<String, Double> stats) {
        try {
            String url = "https://statsapi.mlb.com/api/v1/people/" + playerId
                    + "/stats" + "?stats=season&group=pitching&season=" + year
                    + "&sportId=1";

            Document doc = Jsoup.connect(url).userAgent("Mozilla/5.0")
                    .ignoreContentType(true).timeout(10000).get();

            JsonNode root = MAPPER.readTree(doc.text());
            JsonNode splits = root.path("stats").get(0).path("splits");

            if (splits.isEmpty()) {
                System.out.println("No traditional stats found for player "
                        + playerId + " in " + year);
                return;
            }

            JsonNode s = splits.get(0).path("stat");

            putIfExists(stats, "era", s, "era");
            putIfExists(stats, "whip", s, "whip");
            putIfExists(stats, "wins", s, "wins");
            putIfExists(stats, "losses", s, "losses");
            putIfExists(stats, "saves", s, "saves");
            putIfExists(stats, "gamesPlayed", s, "gamesPlayed");
            putIfExists(stats, "gamesStarted", s, "gamesStarted");
            // Stores as number of innings as 132.1 instead of 132 1/3
            putIfExists(stats, "inningsPitched", s, "inningsPitched");
            putIfExists(stats, "strikeOuts", s, "strikeOuts");
            putIfExists(stats, "baseOnBalls", s, "baseOnBalls");
            putIfExists(stats, "hits", s, "hits");
            putIfExists(stats, "homeRuns", s, "homeRuns");
            putIfExists(stats, "earnedRuns", s, "earnedRuns");
            putIfExists(stats, "strikeoutsPer9Inn", s, "strikeoutsPer9Inn");
            putIfExists(stats, "walksPer9Inn", s, "walksPer9Inn");
            putIfExists(stats, "hitsPer9Inn", s, "hitsPer9Inn");
            putIfExists(stats, "strikeoutWalkRatio", s, "strikeoutWalkRatio");
            putIfExists(stats, "groundOutsToAirouts", s, "groundOutsToAirouts");
            putIfExists(stats, "battersFaced", s, "battersFaced");
            putIfExists(stats, "outs", s, "outs");
            putIfExists(stats, "pitchesPerInning", s, "pitchesPerInning");

        } catch (Exception e) {
            System.err.println(
                    "Error fetching traditional stats: " + e.getMessage());
        }
    }

    /**
     * Private Helper Method that fetches Statcast stats from Baseball Savant of
     * a specific player and season.
     *
     * @param playerId
     * @param year
     * @param stats
     */
    public static void fetchStatcastStats(int playerId, int year,
            Map<String, Double> stats) {
        try {
            String url = "https://baseballsavant.mlb.com/statcast_search/csv"
                    + "?player_id=" + playerId + "&player_type=pitcher"
                    + "&game_year=" + year + "&group_by=name" + "&hfSea=" + year
                    + "%7C" + "&min_pitches=0" + "&type=details";

            String csv = fetchRawCsv(url);

            Reader reader = new StringReader(csv);
            @SuppressWarnings("deprecation")
            Iterable<CSVRecord> records = CSVFormat.RFC4180
                    .withFirstRecordAsHeader().withTrim().withQuote('"')
                    .withEscape('\\').withIgnoreSurroundingSpaces()
                    .parse(reader);

            // Accumulate totals and counts for averaging
            java.util.Map<String, Double> totals = new java.util.HashMap<>();
            java.util.Map<String, Integer> counts = new java.util.HashMap<>();

            String[] statcastFields = { "release_speed", "effective_speed",
                    "release_spin_rate", "release_extension", "launch_speed",
                    "launch_angle", "estimated_woba_using_speedangle",
                    "woba_value", "pfx_x", "pfx_z" };

            java.util.Iterator<CSVRecord> iterator = records.iterator();
            while (iterator.hasNext()) {
                try {
                    CSVRecord record = iterator.next();
                    for (String field : statcastFields) {
                        accumulateStat(totals, counts, record, field);
                    }
                } catch (Exception e) {
                    // skip malformed row and continue
                }
            }

            // Average and store in stats map
            String[] statKeys = { "avgFastballVelo", "avgEffectiveVelo",
                    "avgSpinRate", "avgExtension", "avgExitVeloAllowed",
                    "avgLaunchAngleAllowed", "xwOBAAllowed", "wOBAAllowed",
                    "avgHorizBreak", "avgVertBreak" };

            for (int i = 0; i < statcastFields.length; i++) {
                String field = statcastFields[i];
                if (counts.containsKey(field) && counts.get(field) > 0) {
                    stats.add(statKeys[i],
                            totals.get(field) / counts.get(field));
                }
            }

        } catch (Exception e) {
            System.err.println(
                    "Error fetching Statcast stats: " + e.getMessage());
        }
    }

    // HELPERS

    /**
     * Helper for searching for traditional stats and putting them into map.
     *
     * @param stats
     * @param key
     * @param node
     * @param field
     */
    public static void putIfExists(Map<String, Double> stats, String key,
            JsonNode node, String field) {
        JsonNode val = node.path(field);
        if (!val.isMissingNode() && !val.isNull()) {
            try {
                stats.add(key, Double.parseDouble(val.asText()));
            } catch (NumberFormatException e) {

            }
        }
    }

    /**
     * Helper for searching for Statcast stats and putting them into map.
     *
     * @param totals
     * @param counts
     * @param record
     * @param field
     */
    public static void accumulateStat(java.util.Map<String, Double> totals,
            java.util.Map<String, Integer> counts, CSVRecord record,
            String field) {
        try {
            String val = record.get(field);
            if (val != null && !val.isEmpty() && !val.equals("null")) {
                double d = Double.parseDouble(val);
                totals.put(field, totals.getOrDefault(field, 0.0) + d);
                counts.put(field, counts.getOrDefault(field, 0) + 1);
            }
        } catch (IllegalArgumentException e) {

        }
    }

    /**
     * Returns a map of pitch type to usage percentage. e.g. {"FF": 0.45, "SL":
     * 0.30, "CH": 0.25}
     *
     * @param playerId
     * @param year
     * @return Map of pitch type abbreviation to usage fraction
     */
    public static Map<String, Double> fetchPitchMix(int playerId, int year) {
        Map<String, Double> mix = new Map1L<>();
        try {
            String url = "https://baseballsavant.mlb.com/statcast_search/csv"
                    + "?player_id=" + playerId + "&player_type=pitcher"
                    + "&game_year=" + year + "&group_by=name_pitch_type"
                    + "&hfSea=" + year + "%7C" + "&min_pitches=0&type=details";

            String csv = fetchRawCsv(url);
            Reader reader = new StringReader(csv);
            @SuppressWarnings("deprecation")
            Iterable<CSVRecord> records = CSVFormat.RFC4180
                    .withFirstRecordAsHeader().withTrim()
                    .withIgnoreSurroundingSpaces().parse(reader);

            java.util.Map<String, Integer> pitchCounts = new java.util.HashMap<>();
            int total = 0;

            java.util.Iterator<CSVRecord> iterator = records.iterator();
            while (iterator.hasNext()) {
                try {
                    CSVRecord record = iterator.next();
                    String pitchType = record.get("pitch_type");
                    if (pitchType != null && !pitchType.isEmpty()
                            && !pitchType.equals("null")) {
                        pitchCounts.put(pitchType,
                                pitchCounts.getOrDefault(pitchType, 0) + 1);
                        total++;
                    }
                } catch (Exception e) {
                    // skip malformed row
                }
            }

            for (java.util.Map.Entry<String, Integer> entry : pitchCounts
                    .entrySet()) {
                mix.add(entry.getKey(), (double) entry.getValue() / total);
            }

        } catch (Exception e) {
            System.err.println("Error fetching pitch mix: " + e.getMessage());
        }
        return mix;
    }

    /**
     * Returns average plate_x and plate_z grouped by pitch type. e.g. {"FF_x":
     * -0.12, "FF_z": 2.45, "SL_x": 0.34, "SL_z": 1.80}
     *
     * @param playerId
     * @param year
     * @return Map of "PITCHTYPE_x" and "PITCHTYPE_z" to average location
     */
    public static Map<String, Double> fetchPitchLocations(int playerId,
            int year) {
        Map<String, Double> locations = new Map1L<>();
        try {
            String url = "https://baseballsavant.mlb.com/statcast_search/csv"
                    + "?player_id=" + playerId + "&player_type=pitcher"
                    + "&game_year=" + year + "&group_by=name_pitch_type"
                    + "&hfSea=" + year + "%7C" + "&min_pitches=0&type=details";

            String csv = fetchRawCsv(url);
            Reader reader = new StringReader(csv);
            @SuppressWarnings("deprecation")
            Iterable<CSVRecord> records = CSVFormat.RFC4180
                    .withFirstRecordAsHeader().withTrim()
                    .withIgnoreSurroundingSpaces().parse(reader);

            java.util.Map<String, Double> xTotals = new java.util.HashMap<>();
            java.util.Map<String, Double> zTotals = new java.util.HashMap<>();
            java.util.Map<String, Integer> counts = new java.util.HashMap<>();

            java.util.Iterator<CSVRecord> iterator = records.iterator();
            while (iterator.hasNext()) {
                try {
                    CSVRecord record = iterator.next();
                    String pitchType = record.get("pitch_type");
                    String xVal = record.get("plate_x");
                    String zVal = record.get("plate_z");

                    if (pitchType != null && !pitchType.isEmpty()
                            && !pitchType.equals("null") && xVal != null
                            && !xVal.isEmpty() && zVal != null
                            && !zVal.isEmpty()) {
                        double x = Double.parseDouble(xVal);
                        double z = Double.parseDouble(zVal);
                        xTotals.put(pitchType,
                                xTotals.getOrDefault(pitchType, 0.0) + x);
                        zTotals.put(pitchType,
                                zTotals.getOrDefault(pitchType, 0.0) + z);
                        counts.put(pitchType,
                                counts.getOrDefault(pitchType, 0) + 1);
                    }
                } catch (Exception e) {
                    // skip malformed row
                }
            }

            for (String pt : counts.keySet()) {
                int n = counts.get(pt);
                locations.add(pt + "_x", xTotals.get(pt) / n);
                locations.add(pt + "_z", zTotals.get(pt) / n);
            }

        } catch (Exception e) {
            System.err.println(
                    "Error fetching pitch locations: " + e.getMessage());
        }
        return locations;
    }

    /**
     * Fetches raw CSV string from a URL.
     *
     * @param url
     * @return raw CSV as a String
     * @throws Exception
     */
    private static String fetchRawCsv(String url) throws Exception {
        @SuppressWarnings("deprecation")
        java.net.HttpURLConnection conn = (java.net.HttpURLConnection) new java.net.URL(
                url).openConnection();
        conn.setRequestProperty("User-Agent", "Mozilla/5.0");
        conn.setRequestProperty("Accept", "text/csv");
        conn.setConnectTimeout(15000);
        conn.setReadTimeout(15000);

        java.util.Scanner scanner = new java.util.Scanner(conn.getInputStream(),
                "UTF-8");
        String csv = scanner.useDelimiter("\\A").next().replace("\uFEFF", "")
                .replace("\r\n", "\n").replace("\r", "\n");
        scanner.close();
        return csv;
    }
}
