package dmit2015.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class VideoGameStreamQueryTest {
    static final List<VideoGame> gameList = Arrays.asList(
            new VideoGame("Diablo III Eternal Collection", GamingPlatform.NINTENDO, 34.99, 12919269L),
            new VideoGame("NBA 2K20", GamingPlatform.PLAYSTATION, 49.99, 13720461L),
            new VideoGame("NBA 2K20", GamingPlatform.NINTENDO, 49.99, 13720465L),
            new VideoGame("NBA 2K20", GamingPlatform.XBOX, 49.99, 13720462L),
            new VideoGame("Forza Horizon 4 (Xbox One)", GamingPlatform.XBOX, 39.99, 12612447L),
            new VideoGame("Final Fantasy X/X-2 HD Remaster (Switch)", GamingPlatform.NINTENDO, 34.99, 13208397L),
            new VideoGame("The Outer Worlds (PS4)", GamingPlatform.PLAYSTATION, 49.99, 13642197L),
            new VideoGame("Kingdom Hearts 3 (PS4)", GamingPlatform.PLAYSTATION, 19.99, 10255421L),
            new VideoGame("Overwatch Legendary Edition (Switch)", GamingPlatform.NINTENDO, 34.99, 13899355L),
            new VideoGame("WWE 2K20 (PS4)", GamingPlatform.PLAYSTATION, 39.99, 13836134L),
            new VideoGame("Kingdom Hearts 3 (Xbox One)", GamingPlatform.XBOX, 19.99, 10255666L),
            new VideoGame("Dragon Quest Builders 2 (PS4)", GamingPlatform.PLAYSTATION, 29.99, 13414143L),
            new VideoGame("Battlefield 2042 (PC)", GamingPlatform.PC_GAMING, 54.99, 15547074L),
            new VideoGame("Star Wars Jedi: Fallen Order (PC)", GamingPlatform.PC_GAMING, 19.97, 13508834L)
    );

    static VideoGameStreamQuery _gameRepository;

    @BeforeAll
    static void beforeAll() {
        _gameRepository = new VideoGameStreamQuery(gameList);
    }

    @BeforeEach
    void setUp() {

    }

    @AfterEach
    void tearDown() {

    }
    @Test
    void findOptionalById() {
        Optional<VideoGame> optionalVideoGame = _gameRepository.findOptionalById(13208397L);
        assertTrue(optionalVideoGame.isPresent());
        VideoGame existingVideoGame = optionalVideoGame.orElseThrow();
        assertNotNull(existingVideoGame);
        assertEquals("Final Fantasy X/X-2 HD Remaster (Switch)", existingVideoGame.getTitle());
        assertEquals(GamingPlatform.NINTENDO, existingVideoGame.getPlatform());
        assertEquals(34.99, existingVideoGame.getPrice());
    }

    @Test
    void findById() {
        VideoGame existingVideoGame = _gameRepository.findById(13208397L);
        assertNotNull(existingVideoGame);
        assertEquals("Final Fantasy X/X-2 HD Remaster (Switch)", existingVideoGame.getTitle());
        assertEquals(GamingPlatform.NINTENDO, existingVideoGame.getPlatform());
        assertEquals(34.99, existingVideoGame.getPrice());
    }

    @Test
    void findAll() {
        List<VideoGame> games = _gameRepository.findAll();
        assertEquals(14, games.size());
    }

    @Test
    void findByGamingPlatform() {
        // There should be 4 nintendo games
        assertEquals(4, _gameRepository.findByGamingPlatform(GamingPlatform.NINTENDO).size());
        // There should be 2 PC games
        assertEquals(2, _gameRepository.findByGamingPlatform(GamingPlatform.PC_GAMING).size());
    }

    @Test
    void mapToTitle() {
        List<String> titles = _gameRepository.mapToTitle();
        // There should be 14 titles
        assertEquals(14, titles.size());
        assertEquals("Battlefield 2042 (PC)", titles.get(0) );
        assertEquals("WWE 2K20 (PS4)", titles.get(titles.size() - 1) );
    }

    @Test
    void mapToDistinctPrices() {
        List<Double> prices = _gameRepository.mapToDistinctPrices();
        // There should be 7 prices
        assertEquals(7, prices.size());
        // The highest price should be 54.99
        assertEquals(54.99, prices.get(0));
        // THe lowest price should be 19.97
        assertEquals(19.97, prices.get(prices.size() - 1));
    }

    @Test
    void mapToPriceSet() {
        Set<Double> prices = _gameRepository.mapToPriceSet();
        // There should be 7 prices
        assertEquals(7, prices.size());
        // The highest price should be 54.99
        assertEquals(54.99, prices.toArray()[0]);
        // THe lowest price should be 19.97
        assertEquals(19.97, prices.toArray()[prices.size() - 1]);
    }

    @Test
    void sortByPlatformThenTitleThenPriceDescending() {
        List<VideoGame> resultList = _gameRepository.sortByPlatformThenTitleThenPriceDescending();
        resultList.forEach(System.out::println);
        // There first game should be Diablo III Eternal Collection for NINTENDO
        assertEquals("Diablo III Eternal Collection", resultList.get(0).getTitle());
        assertEquals(GamingPlatform.NINTENDO, resultList.get(0).getPlatform());
        // THe last game should be NBA 2K20 for XBOX
        assertEquals("NBA 2K20", resultList.get(resultList.size() - 1).getTitle());
        assertEquals(GamingPlatform.XBOX, resultList.get(resultList.size() - 1).getPlatform());
    }

    @Test
    void findFirstByTitleAndPlatform() {
        assertFalse(_gameRepository.findFirstByTitleAndPlatform("NBA 2K20",GamingPlatform.PC_GAMING).isPresent());
        assertTrue(_gameRepository.findFirstByTitleAndPlatform("NBA 2K20",GamingPlatform.PLAYSTATION).isPresent());
//        Optional<VideoGame> singleResult = _gameRepository.findFirstByTitleAndPlatform("NBA 2K20",GamingPlatform.PC_GAMING);

    }

    @Test
    void count() {
        // The sum of all prices should be 529.84
        assertEquals(14, _gameRepository.count());
    }
    @Test
    void sumAllPrices() {
        // The sum of all prices should be 529.84
        assertEquals(529.84, _gameRepository.sumAllPrices());
    }

    @Test
    void highestPriceByPlatform() {
        // The highest price Nintendo game should be 49.99
        assertEquals(49.99, _gameRepository.highestPriceByPlatform(GamingPlatform.NINTENDO));
        // The highest price PC Gaming game should be 54.99
        assertEquals(54.99, _gameRepository.highestPriceByPlatform(GamingPlatform.PC_GAMING));
    }

    @Test
    void lowestPriceByPlatform() {
        // The lowest price Nintendo game should be 34.99
        assertEquals(34.99, _gameRepository.lowestPriceByPlatform(GamingPlatform.NINTENDO));
        // The lowest price PC Gaming game should be 19.97
        assertEquals(19.97, _gameRepository.lowestPriceByPlatform(GamingPlatform.PC_GAMING));
    }

    @Test
    void averagePriceByPlatform() {
        // The average price Nintendo game should be 38.74
        assertEquals(38.74, _gameRepository.averagePriceByPlatform(GamingPlatform.NINTENDO));
        // The average price PC Gaming game should be 37.99
        assertEquals(37.99, _gameRepository.averagePriceByPlatform(GamingPlatform.PLAYSTATION));
    }

    @Test
    void highestPriceGame() {
        Optional<VideoGame> optionalGame = _gameRepository.highestPriceGame();
        assertTrue(optionalGame.isPresent());
        VideoGame highestPriceGme = optionalGame.orElseThrow();
        assertEquals(54.99, highestPriceGme.getPrice());
    }

    @Test
    void lowestPriceGame() {
        Optional<VideoGame> optionalGame = _gameRepository.lowestPriceGame();
        assertTrue(optionalGame.isPresent());
        VideoGame lowestPriceGme = optionalGame.orElseThrow();
        assertEquals(19.97, lowestPriceGme.getPrice());
    }

    @Test
    void groupByPlatform() {
        // There should be 4 groups of platform keys
        assertEquals(4, _gameRepository.groupByPlatform().keySet().size());
        // There should be 2 games in the PC Gaming platform key
        assertEquals(2,_gameRepository.groupByPlatform().get(GamingPlatform.PC_GAMING).size());
        // There should be 5 games in the Playstation platform key
        assertEquals(5,_gameRepository.groupByPlatform().get(GamingPlatform.PLAYSTATION).size());
    }

}