# Smurf Monitor
Aims to monitor when in a week are smurfs most active

# Design
- Main
    - Get json from aoe2.net every hour
    - Parse json and keep only ranked 1v1 data
    - Analyze stats for each player

- ApiCaller
    - getCurrentMatches
        - Provided current time in epoch, get json from Matches api
          `e.g., https://aoe2.net/api/matches?game=aoe2de&count=1000&since=1628352000`
        - Return whole json text

    - getPlayer1v1Stats
        - Provided steam_id or profile_id, get player's ranked 1v1 info from Rank api
            - E.g., `https://aoe2.net/api/nightbot/rank?leaderboard_id=3&steam_id=76561198189368841  老宋 50%`
            - E.g., `https://aoe2.net/api/nightbot/rank?leaderboard_id=3&steam_id=76561198350566117  妙禪 50%`
        - Return text: `🇹🇼 妙禪seafood (1309) Rank #6,766, has played 4,803 games with a 50% winrate, -1 streak, and 32 drops`

- Parser
    - parseRanked1v1Players
        - Filter only 1v1 Ranked games by specifying key/value: `"leaderboard_id": 3`

    - parseRatingAndWinrate
        - From the returned text of Rank api, parse out rating and winrate of player
        - Sample input text: `🇹🇼 妙禪seafood (1309) Rank #6,766, has played 4,803 games with a 50% winrate, -1 streak, and 32 drops`
            - regex: `(dddd) Rank #`
            - regex: `with a with a dd% winrate`
        - Return int[2] -> dddd, dd

- Analyzer
    - Get steam_id list with Filter
        `"rating" < 1500`
        `"rating" > 1200`
    - Get rating & winrate lists of from steam_id list
    - Output Median for rating & winrate list for each hour
