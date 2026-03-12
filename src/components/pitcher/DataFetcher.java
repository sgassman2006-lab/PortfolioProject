package components.pitcher;

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
public class DataFetcher {

    /**
     * Mapper for statistics.
     */
    private static final ObjectMapper MAPPER = new ObjectMapper();

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
    public Map<String, Double> fetchAllStats(int playerId, int year) {
        Map<String, Double> stats = new Map1L<>();

        this.fetchTraditionalStats(playerId, year, stats);
        this.fetchStatcastStats(playerId, year, stats);

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
    private void fetchTraditionalStats(int playerId, int year,
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

            this.putIfExists(stats, "era", s, "era");
            this.putIfExists(stats, "whip", s, "whip");
            this.putIfExists(stats, "wins", s, "wins");
            this.putIfExists(stats, "losses", s, "losses");
            this.putIfExists(stats, "saves", s, "saves");
            this.putIfExists(stats, "gamesPlayed", s, "gamesPlayed");
            this.putIfExists(stats, "gamesStarted", s, "gamesStarted");
            this.putIfExists(stats, "inningsPitched", s, "inningsPitched");
            this.putIfExists(stats, "strikeOuts", s, "strikeOuts");
            this.putIfExists(stats, "baseOnBalls", s, "baseOnBalls");
            this.putIfExists(stats, "hits", s, "hits");
            this.putIfExists(stats, "homeRuns", s, "homeRuns");
            this.putIfExists(stats, "earnedRuns", s, "earnedRuns");
            this.putIfExists(stats, "strikeoutsPer9Inn", s,
                    "strikeoutsPer9Inn");
            this.putIfExists(stats, "walksPer9Inn", s, "walksPer9Inn");
            this.putIfExists(stats, "hitsPer9Inn", s, "hitsPer9Inn");
            this.putIfExists(stats, "strikeoutWalkRatio", s,
                    "strikeoutWalkRatio");
            this.putIfExists(stats, "groundOutsToAirouts", s,
                    "groundOutsToAirouts");
            this.putIfExists(stats, "battersFaced", s, "battersFaced");
            this.putIfExists(stats, "outs", s, "outs");
            this.putIfExists(stats, "pitchesPerInning", s, "pitchesPerInning");

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
    private void fetchStatcastStats(int playerId, int year,
            Map<String, Double> stats) {
        try {
            String url = "https://baseballsavant.mlb.com/statcast_search/csv?player_id="
                    + playerId + "&player_type=pitcher&game_year=" + year
                    + "&group_by=name" + "&sort_col=pitches&sort_order=desc";

            Document doc = Jsoup.connect(url).userAgent("Mozilla/5.0")
                    .ignoreContentType(true).timeout(15000).get();

            String csv = doc.text();
            String[] lines = csv.split("\n");

            if (lines.length < 2) {
                System.out.println("No Statcast data found for player "
                        + playerId + " in " + year);
                return;
            }

            // Parse header row
            String[] headers = lines[0].split(",");
            // Parse first data row (aggregated season totals)
            String[] values = lines[1].split(",");

            Map<String, String> row = new Map1L<>();
            for (int i = 0; i < headers.length && i < values.length; i++) {
                row.add(headers[i].trim(), values[i].trim());
            }

            // Pull out useful Statcast pitching fields
            this.parseStatcast(stats, row, "release_speed", "avgFastballVelo");
            this.parseStatcast(stats, row, "effective_speed",
                    "avgEffectiveVelo");
            this.parseStatcast(stats, row, "release_spin_rate", "avgSpinRate");
            this.parseStatcast(stats, row, "release_extension", "avgExtension");
            this.parseStatcast(stats, row, "launch_speed",
                    "avgExitVeloAllowed");
            this.parseStatcast(stats, row, "launch_angle",
                    "avgLaunchAngleAllowed");
            this.parseStatcast(stats, row, "estimated_woba_using_speedangle",
                    "xwOBAAllowed");
            this.parseStatcast(stats, row, "woba_value", "wOBAAllowed");
            this.parseStatcast(stats, row, "pfx_x", "avgHorizBreak");
            this.parseStatcast(stats, row, "pfx_z", "avgVertBreak");

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
    private void putIfExists(Map<String, Double> stats, String key,
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
     * @param stats
     * @param row
     * @param csvKey
     * @param statKey
     */
    private void parseStatcast(Map<String, Double> stats,
            Map<String, String> row, String csvKey, String statKey) {
        String val = row.value(csvKey);
        if (val != null && !val.isEmpty() && !val.equals("null")) {
            try {
                stats.add(statKey, Double.parseDouble(val));
            } catch (NumberFormatException e) {

            }
        }
    }
}