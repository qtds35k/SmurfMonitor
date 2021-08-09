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
                String players1v1Stats = caller.getPlayers1v1Stats(playerInfo);
                if (players1v1Stats == null) {
                    continue;
                }
                int[] eloAndWinrate = parser.parseRatingAndWinrate(players1v1Stats);
                if (eloAndWinrate == null) {
                    continue;
                }
                lastHourPlayerStats.add(eloAndWinrate);
            }
        }
        
        
    }
}
