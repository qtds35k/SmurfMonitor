package data;

import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class Driver {
    public static void main(String[] args) throws IOException {
        long lastHourTimestamp = Instant.now().toEpochMilli() / 1000 - 3600;
        String urlMatches = "https://aoe2.net/api/matches?game=aoe2de&count=50&since=" + lastHourTimestamp;

        ApiCaller caller = new ApiCaller();
        JSONArray lastHourMatches = caller.getLastHourMatches(urlMatches);

        Parser parser = new Parser();
        List<JSONObject> filteredMatches = parser.parseRanked1v1Players(lastHourMatches);
         System.out.println(filteredMatches);

        List<int[]> lastHourPlayerStats = new ArrayList<>();
        for (JSONObject matchInfo : filteredMatches) {
            JSONArray players = matchInfo.getJSONArray("players");
            for (int i = 0; i < players.length(); i++) {
                JSONObject playerInfo = players.getJSONObject(i);
                String player1v1Info = caller.getPlayers1v1Stats(playerInfo);
                if (player1v1Info == null || player1v1Info.equalsIgnoreCase("Player not found")) {
                    continue;
                }
                int[] ratingAndWinrate = parser.parseRatingAndWinrate(player1v1Info);
                if (ratingAndWinrate == null) {
                    continue;
                }
                lastHourPlayerStats.add(ratingAndWinrate);
                System.out.println(player1v1Info);
                System.out.println(ratingAndWinrate[0] + " " + ratingAndWinrate[1]);
            }
        }
    }
}
