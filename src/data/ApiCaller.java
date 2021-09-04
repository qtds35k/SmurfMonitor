package data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;

import org.json.JSONArray;
import org.json.JSONObject;

public class ApiCaller {

    private String readJsonFromApiResponse(Reader reader) throws IOException {
        StringBuilder sb = new StringBuilder();
        int charCode;
        while ((charCode = reader.read()) != -1) {
            sb.append((char) charCode);
        }
        return sb.toString();
    }

    public JSONArray getJsonFromAoe2Net(String url) throws IOException {
        InputStream inStream = new URL(url).openStream();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inStream, Charset.forName("UTF-8")));
            JSONArray lastHourMatches = new JSONArray(readJsonFromApiResponse(reader));
            return lastHourMatches;
        } finally {
            inStream.close();
        }
    }

    public String getPlayers1v1Stats(JSONObject playerInfo) throws MalformedURLException, IOException {
        String queryKey = null;
        if (!playerInfo.isNull("steam_id")) {
            queryKey = "steam_id=" + playerInfo.getString("steam_id");
        } else if (!playerInfo.isNull("profile_id")) {
            queryKey = "profile_id=" + playerInfo.getInt("profile_id");
        } else {
            return null;
        }

        String urlRank = "https://aoe2.net/api/nightbot/rank?leaderboard_id=3&" + queryKey;
        InputStream inStream = new URL(urlRank).openStream();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inStream, Charset.forName("UTF-8")));
            String player1v1Info = readJsonFromApiResponse(reader);
            return player1v1Info;
        } finally {
            inStream.close();
        }
    }

    public String getPlayerName(String profile_id) throws MalformedURLException, IOException {
        String urlLastMatch = "https://aoe2.net/api/player/lastmatch?game=aoe2de&profile_id=" + profile_id;
        InputStream inStream = new URL(urlLastMatch).openStream();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inStream, Charset.forName("UTF-8")));
            JSONObject playerLastMatch = new JSONObject(readJsonFromApiResponse(reader));
            return playerLastMatch.getString("name");
        } finally {
            inStream.close();
        }
    }

    public JSONArray getPlayerMatchHistory(long profileId) throws IOException {
        int matches = 1000;
        String urlMatchHistory = "https://aoe2.net/api/player/matches?game=aoe2de&count=" + matches + "&profile_id="
                + profileId;
        JSONArray playerMatchHistory = getJsonFromAoe2Net(urlMatchHistory);
        return playerMatchHistory;
    }
}
