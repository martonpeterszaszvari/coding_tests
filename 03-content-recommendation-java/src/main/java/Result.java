import java.util.*;

public class Result {

    //Solution
    public static List<String> getRecommendations(
            List<String> watchedCategories,
            List<Integer> watchedCategoryRelevance,
            List<String> availableVideoTitles,
            List<String> availableVideoCategories) {
        Map<Integer, String> relevanceMap = new HashMap<>();
        Map<String, List<String>> categoriesMap = new HashMap<>();
        List<String> rec = new ArrayList<>();
        for (int i = 0; i < watchedCategoryRelevance.size(); i++) {
            relevanceMap.put(watchedCategoryRelevance.get(i), watchedCategories.get(i));
        }
        for (int i = 0; i < availableVideoTitles.size(); i++) {
            String currentCategory = availableVideoCategories.get(i);
            List<String> currentTitles = categoriesMap.get(currentCategory);
            if (currentTitles == null) {
                currentTitles = new ArrayList<>();
            }
            currentTitles.add(availableVideoTitles.get(i));
            categoriesMap.put(currentCategory, currentTitles);
        }
        List<Integer> sortedRelevance = watchedCategoryRelevance.stream().sorted(Comparator.reverseOrder()).toList();
        for (Integer relevance : sortedRelevance) {
            String currentCategory = relevanceMap.get(relevance);
            List<String> currentTitles = categoriesMap.remove(currentCategory);
            if (currentTitles != null)
                currentTitles.stream().sorted().forEach(rec::add);

        }
        List<String> categories = categoriesMap.keySet().stream().sorted().toList();
        for (String category : categories) {
            List<String> titles = categoriesMap.get(category);
            titles.stream().sorted().forEach(rec::add);
        }
        return rec;
    }
}
