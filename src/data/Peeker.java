package data;

import static java.util.Map.entry;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.json.JSONArray;
import org.json.JSONObject;

public class Peeker {
    public static void main(String[] args) throws IOException {
//        long profileId = 1937000; // Eric
//        long profileId = 3927220; // Marshal
//        long profileId = 2576025; // KanSu
//        long profileId = 1309251; // Sung
        long profileId = 2510223; // Monkie
        
        int civCode = 32;

        Map<Integer, String> civs = Map.ofEntries(entry(1, "Britons"), entry(2, "Franks"), entry(3, "Goths"),
                entry(4, "Teutons"), entry(5, "Japanese"), entry(6, "Chinese"), entry(7, "Byzantines"),
                entry(8, "Persians"), entry(9, "Saracens"), entry(10, "Turks"), entry(11, "Vikings"),
                entry(12, "Mongols"), entry(13, "Celts"), entry(14, "Spanish"), entry(15, "Aztecs"),
                entry(16, "Mayans"), entry(17, "Huns"), entry(18, "Koreans"), entry(19, "Italians"),
                entry(20, "Indians"), entry(21, "Incas"), entry(22, "Magyars"), entry(23, "Slavs"),
                entry(24, "Portuguese"), entry(25, "Ethiopians"), entry(26, "Malians"), entry(27, "Berbers"),
                entry(28, "Khmer"), entry(29, "Malay"), entry(30, "Burmese"), entry(31, "Vietnamese"),
                entry(32, "Bulgarians"), entry(33, "Tartars"), entry(34, "Cumans"), entry(35, "Lithuanians"),
                entry(36, "Burgundians"), entry(37, "Sicilians"), entry(38, "Poles"), entry(39, "Bohemians"));

        ApiCaller caller = new ApiCaller();
        JSONArray playerMatchHistory = caller.getPlayerMatchHistory(profileId);

        Parser parser = new Parser();
        List<JSONObject> filteredMatches = parser.parseRanked1v1Players(playerMatchHistory);

        double winrate = 0;
        winrate = parser.calculateWinrateForCiv(profileId, civCode, filteredMatches);
        System.out.printf("Player %s has a %.1f%% winrate playing %s.\n\n", caller.getPlayerName(Long.toString(profileId)),
                winrate, civs.get(civCode));

        Map<String, Double> civToWinrate = parser.calculateWinrateForAllCivs(profileId, civs, filteredMatches);
        List<String> sortedCivToWinrate = civToWinrate.entrySet().stream()
                .sorted(Comparator.comparing(Map.Entry::getValue, Comparator.reverseOrder()))
                .map(entry -> entry.getKey() + " " + String.format("%.1f", entry.getValue()) + "%")
                .collect(Collectors.toList());
        System.out.println("Top 10 civs:");
        for (int i = 0; i < 10; i++) {
            System.out.println(sortedCivToWinrate.get(i));
        }

    }
}
