package dmit2015.model;

import java.util.*;
import java.util.stream.Collectors;

public class VideoGameStreamQuery {

    private List<VideoGame> _gameList;


    public VideoGameStreamQuery(List<VideoGame> gameList) {
        _gameList = gameList;
    }

    public Optional<VideoGame> findOptionalById(Long webCode) {
        return _gameList
                .stream()
                .filter(currentGame -> Objects.equals(currentGame.getWebCode(), webCode))
                .findFirst();
    }

    public VideoGame findById(Long webCode) {
        return _gameList
                .stream()
                .filter(currentGame -> Objects.equals(currentGame.getWebCode(), webCode))
                .findFirst()
                .orElseThrow();
    }

    public List<VideoGame> findAll() {
        return _gameList
                .stream()
                .sorted(Comparator.comparing(VideoGame::getTitle))
                .toList();
    }

    public List<VideoGame> findByGamingPlatform(GamingPlatform platform) {
        return _gameList
                .stream()
                .filter(currentGame -> currentGame.getPlatform().equals(platform))
                .sorted(Comparator.comparing(VideoGame::getTitle))
                .toList();
    }

    public List<String> mapToTitle() {
        return _gameList
                .stream()
                .map(VideoGame::getTitle)
                .sorted()
                .toList();
    }

    public List<Double> mapToDistinctPrices() {
        return _gameList
                .stream()
                .map(VideoGame::getPrice)
                .distinct()
                .sorted(Comparator.reverseOrder())
                .toList();
    }

    public Set<Double> mapToPriceSet() {
        return _gameList
                .stream()
                .map(VideoGame::getPrice)
                .sorted(Comparator.reverseOrder())
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    public List<VideoGame> sortByPlatformThenTitleThenPriceDescending() {
        Comparator<VideoGame> gamingPlatformComparator = Comparator.comparing(lhs -> lhs.getPlatform().name);
        Comparator<VideoGame> titleComparator = Comparator.comparing(VideoGame::getTitle);
        Comparator<VideoGame> priceReversedComparator = Comparator.comparingDouble(VideoGame::getPrice).reversed();

        return _gameList
                .stream()
                .sorted(gamingPlatformComparator.thenComparing(titleComparator).thenComparing(priceReversedComparator))
                .toList();
    }

    public Optional<VideoGame> findFirstByTitleAndPlatform(String title, GamingPlatform platform) {
        return _gameList
                .stream()
                .filter(currentGame -> currentGame.getTitle().equalsIgnoreCase(title))
                .filter(currentGame -> currentGame.getPlatform().equals(platform))
                .findFirst();
    }

    public long count() {
        return _gameList
                .size();
    }

    public double sumAllPrices() {
        return _gameList
                .stream()
                .mapToDouble(VideoGame::getPrice)
                .sum();
    }

    public double highestPriceByPlatform(GamingPlatform platform) {
        return _gameList
                .stream()
                .filter(currentGame -> currentGame.getPlatform().equals(platform))
                .mapToDouble(VideoGame::getPrice)
                .max()
                .orElseThrow();
    }

    public double lowestPriceByPlatform(GamingPlatform platform) {
        return _gameList
                .stream()
                .filter(currentGame -> currentGame.getPlatform().equals(platform))
                .mapToDouble(VideoGame::getPrice)
                .min()
                .orElseThrow();
    }

    public double averagePriceByPlatform(GamingPlatform platform) {
        return _gameList
                .stream()
                .filter(currentGame -> currentGame.getPlatform().equals(platform))
                .mapToDouble(VideoGame::getPrice)
                .average()
                .orElseThrow();
    }

    public Optional<VideoGame> highestPriceGame() {
        double maxPrice = _gameList
                .stream()
                .mapToDouble(VideoGame::getPrice)
                .max()
                .orElseThrow();
        return _gameList
                .stream()
                .filter(currentGame -> currentGame.getPrice() >= maxPrice)
                .findFirst();
    }

    public Optional<VideoGame> lowestPriceGame() {
        double minPrice = _gameList
                .stream()
                .mapToDouble(VideoGame::getPrice)
                .min()
                .orElseThrow();
        return _gameList
                .stream()
                .filter(currentGame -> currentGame.getPrice() <= minPrice)
                .findFirst();
    }

    public Map<GamingPlatform, List<VideoGame>> groupByPlatform() {
        return _gameList
                .stream()
                .sorted(Comparator.comparing(VideoGame::getPlatform))
                .collect(Collectors.groupingBy(VideoGame::getPlatform));
    }

}