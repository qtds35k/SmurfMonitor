package data;

import java.util.ArrayList;
import java.util.List;
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
        Pattern RANKED_1V1_PATTERN = Pattern.compile("\\((\\d+)\\).*Rank.*with a (\\d+)");
        Matcher m = RANKED_1V1_PATTERN.matcher(player1v1Info);
        if (m.find()) {
            result[0] = Integer.parseInt(m.group(1)); 
            result[1] = Integer.parseInt(m.group(2)); 
        }
        return result;
    }
}
