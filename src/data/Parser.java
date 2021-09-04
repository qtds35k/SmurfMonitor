package data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONObject;

public class Parser {

    public List<JSONObject> parseRanked1v1Players(JSONArray lastHourMatches) {
        List<JSONObject> filteredMatches = new ArrayList<>();
        for (int i = 0; i < lastHourMatches.length(); i++) {
            JSONObject match = lastHourMatches.getJSONObject(i);
            if (!match.isNull("leaderboard_id") && match.getInt("leaderboard_id") == 3) {
                filteredMatches.add(match);
            }
        }
        return filteredMatches;
    }

    public int[] parseRatingAndWinrate(String player1v1Info) {
        int[] result = new int[2];
        Pattern RANKED_1V1_PATTERN = Pattern.compile("\\((\\d+)\\).*Rank.*with a (\\d+)"); // Check out
                                                                                           // https://regex101.com
        Matcher m = RANKED_1V1_PATTERN.matcher(player1v1Info);
        if (m.find()) {
            result[0] = Integer.parseInt(m.group(1));
            result[1] = Integer.parseInt(m.group(2));
        }
        return result;
    }

    public double calculateWinrateForCiv(long profileId, int civCode, List<JSONObject> filteredMatches,
            boolean strictStats) {

        int matchCnt = 0;
        int winCnt = 0;
        int strictStatsThreshold = 10;

        for (JSONObject matchInfo : filteredMatches) {
            JSONArray players = matchInfo.getJSONArray("players");
            for (int i = 0; i < players.length(); i++) {

                JSONObject playerInfo = players.getJSONObject(i);

                if (playerInfo.isNull("profile_id") || playerInfo.getLong("profile_id") != profileId
                        || playerInfo.isNull("civ") || playerInfo.getInt("civ") != civCode
                        || playerInfo.isNull("won")) {
                    continue;
                }

                matchCnt++;
                if (playerInfo.getBoolean("won")) {
                    winCnt++;
                }
            }
        }

        if (!strictStats) {
            System.out.println("matches: " + matchCnt + " / wins: " + winCnt);
        }

        if (strictStats && matchCnt < strictStatsThreshold) {
            return 0;
        }

        return (matchCnt == 0) ? 0 : 100 * (double) winCnt / matchCnt;
    }

    public double calculateWinrateForCiv(long profileId, int civCode, List<JSONObject> filteredMatches) {
        return calculateWinrateForCiv(profileId, civCode, filteredMatches, false);
    }

    public Map<String, Double> calculateWinrateForAllCivs(long profileId, Map<Integer, String> civs,
            List<JSONObject> filteredMatches) {
        Map<String, Double> result = new HashMap<>();
        for (int civCode : civs.keySet()) {
            result.put(civs.get(civCode), calculateWinrateForCiv(profileId, civCode, filteredMatches, true));
        }
        return result;
    }
}
