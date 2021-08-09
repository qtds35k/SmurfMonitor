package data;

import java.util.ArrayList;
import java.util.List;

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

    public int[] parseRatingAndWinrate(String player1v1Stats) {
        // TODO Auto-generated method stub
        return null;
    }
    
    

//    // if a player's info is incomplete, this entry will be ignored
//    public int[] getPlayer1v1Stats(JSONObject matchInfo) {
//        int[] eloAndWinrate = new int[2];
//        
//        return eloAndWinrate;
//    }
}
