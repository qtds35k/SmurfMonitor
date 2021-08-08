# SmurfMonitor
Aims to monitor when in a week are smurfs most active

# Design
- Main
    - Get json from aoe2.net every hour
    - Parse json and keep only ranked 1v1 data
    - Analyze stats for each player

- ApiCaller
    - getCurrentMatches
        Provided current time in epoch, get json from Matches api
            e.g., https://aoe2.net/api/matches?game=aoe2de&count=1000&since=1628352000
        Return whole json text

    - getPlayer1v1Stats
        Provided steam_id, get rating and winrate from Rank api
            e.g., https://aoe2.net/api/nightbot/rank?leaderboard_id=3&steam_id=76561198189368841        老宋 50%
            e.g., https://aoe2.net/api/nightbot/rank?leaderboard_id=3&steam_id=76561198350566117        妙禪 50%
            sample text: 🇹🇼 妙禪seafood (1309) Rank #6,766, has played 4,803 games with a 50% winrate, -1 streak, and 32 drops
        regex: (dddd) Rank #
        regex: with a with a dd% winrate
        Return int[2] -> dddd, dd

- Parser
    - parseRanked1v1Players
        "leaderboard_id": 3

    - parseRatingAndWinrate
    

- Analyzer
    Get steam_id list of with Filter
        "rating" < 1450
        "rating" > 1350

    Get rating & winrate lists of from steam_id list
    Output Median for rating & winrate list for each hour