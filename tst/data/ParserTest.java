package data;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class ParserTest {
    Parser p;

    @Before
    public void init() {
        this.p = new Parser();
    }

    @Test
    public void parseRatingAndWinrate_withValidPlayer1v1Info_returnsExpectedRatingAndWinrate() {
        // Given
        String info = "🇹🇼 我最爛別快攻我 (1403) Rank #4,522, has played 2,181 games with a 50% winrate, +1 streak, and 6 drops";
        int[] expected =  new int[] {1403, 50};
        
        // When
        int[] actual = p.parseRatingAndWinrate(info);
        
        // Then
        assertEquals(expected[0], actual[0]);
        assertEquals(expected[1], actual[1]);
        }
}
