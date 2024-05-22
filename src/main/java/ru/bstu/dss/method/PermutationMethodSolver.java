//package ru.bstu.dss.method;
//
//import org.springframework.stereotype.Component;
//import ru.bstu.dss.dto.DisplacedIdealDto;
//import ru.bstu.dss.model.Alternative;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Map;
//import java.util.Set;
//
//@Component
//public class PermutationMethodSolver {
//
//    private final int P = 5;
//    private final int[] pValues = {1, 2, 3, 4, 5};
//
//    public String run(DisplacedIdealDto data) {
//        var alternatives = data.getAlternatives();
//
//        List<Double> ideal = getIdealObject(data);
//        List<Double> imperfect = getImperfectObject(data);
//        double[][] relative = getRelativeUnits(alternatives, ideal, imperfect);
//        double[][] normalized = getNormalizedMatrix(relative);
//        double[] entropyMatrix = getEntropy(normalized);
//        double[] invertEntropyMatrix = getInvertEntropy(entropyMatrix);
//        double[] complexImportance = getComplexImportance(invertEntropyMatrix, getNormalizedAssessment(data.getCriteriaSet().getImportance()));
//
//
//
//
//        return alternatives.get(0).getName();
//    }
//
////    private void getBestAlternative(List<Alternative> alternatives, double[] complexImportance){
////        int countAlternatives = alternatives.size();
////
////        List<List<Integer>> permutations;
////
////        for (List<Integer> permutation : permutations){
////            int[] C = new int[complexImportance.length];
////            int[] H = new int[complexImportance.length];
////
////            for (var i = 0; i < countAlternatives - 1; i++){
////                List<Double> left = alternatives.get(i).getCriteriaValuesList();
////                for (var j = i + 1; j < countAlternatives; j++){
////                    List<Double> right = alternatives.get(j).getCriteriaValuesList();
////                    int size = left.size();
////                    for(var k = 0; k < size; k++){
////                        if(left.get(k) > right.get(k)){
////                            C[k] = C[k] + 1;
////                        } else{
////                            H[k] = H[k] + 1;
////                        }
////                    }
////
////                }
////            }
////
////
////        }
////    }
//
//    private String getBestAlternative(List<Alternative> alternatives, double[] complexImportance) {
//        int countAlternatives = alternatives.size();
//
//        List<List<Integer>> permutations = generatePermutations(countAlternatives);
//
//        while (alternatives.size() > 1) {
//            for (List<Integer> permutation : permutations) {
//                int[] C = new int[complexImportance.length];
//                int[] H = new int[complexImportance.length];
//
//                for (var i = 0; i < countAlternatives - 1; i++) {
//                    List<Double> left = alternatives.get(permutation.get(i)).getCriteriaValuesList();
//                    for (var j = i + 1; j < countAlternatives; j++) {
//                        List<Double> right = alternatives.get(permutation.get(j)).getCriteriaValuesList();
//                        int size = left.size();
//                        for (var k = 0; k < size; k++) {
//                            if (left.get(k) > right.get(k)) {
//                                C[k] = C[k] + 1;
//                            } else {
//                                H[k] = H[k] + 1;
//                            }
//                        }
//                    }
//                }
//
//                double[][] distanceMatrix = getDistanceMatrix(normalized, complexImportance);
//                int worse = getWorse(distanceMatrix);
//                deleteAlternative(alternatives, worse);
//            }
//        }
//        return alternatives.get(0).getName();
//    }
//
//    private List<List<Integer>> generatePermutations(int n) {
//        List<List<Integer>> result = new ArrayList<>();
//        generatePermutationsHelper(new ArrayList<>(), new boolean[n], n, result);
//        return result;
//    }
//
//    private void generatePermutationsHelper(List<Integer> current, boolean[] used, int n, List<List<Integer>> result) {
//        if (current.size() == n) {
//            result.add(new ArrayList<>(current));
//            return;
//        }
//
//        for (int i = 0; i < n; i++) {
//            if (!used[i]) {
//                used[i] = true;
//                current.add(i);
//                generatePermutationsHelper(current, used, n, result);
//                current.remove(current.size() - 1);
//                used[i] = false;
//            }
//        }
//    }
//
//
//    private void deleteAlternative(List<Alternative> alternatives, int worse){
//        alternatives.remove(worse);
//    }
//
//    private int findMostFrequentNumber(int[] numbers) {
//        // Создаем HashMap для хранения чисел и их частоты
//        Map<Integer, Integer> frequencyMap = new HashMap<>();
//
//        // Заполняем HashMap и считаем частоту встречаемости
//        for (int num : numbers) {
//            frequencyMap.put(num, frequencyMap.getOrDefault(num, 0) + 1);
//        }
//
//        // Находим число с максимальной частотой
//        int maxFrequency = 0;
//        int mostFrequentNumber = 0;
//        for (Map.Entry<Integer, Integer> entry : frequencyMap.entrySet()) {
//            if (entry.getValue() > maxFrequency) {
//                maxFrequency = entry.getValue();
//                mostFrequentNumber = entry.getKey();
//            }
//        }
//
//        return mostFrequentNumber;
//    }
//
//    private int getWorse(double[][] matrix){
//        int[] countArray = new int[P];
//
//        for (int i = 0; i < P; i++){
//            countArray[i] = 0;
//            double currentMin = Double.MAX_VALUE;
//            for (int j = 0; j < matrix.length; j++){
//                if (matrix[j][i] < currentMin){
//                    currentMin = matrix[j][i];
//                    countArray[i] = j;
//                }
//            }
//        }
//        return findMostFrequentNumber(countArray);
//    }
//
//    private double[][] getDistanceMatrix(double[][] relative, double[] importance) {
//        int n = relative.length; // Количество объектов
//        int m = relative[0].length; // Количество характеристик (расстояний)
//
//        double[][] distances = new double[n][P]; // Матрица расстояний
//
//        for (int i = 0; i < n; i++) {
//            for (int j = 0; j < P; j++) {
//                double sum = 0.0;
//                for (int k = 0; k < m; k++) {
//                    sum += importance[k] * Math.pow(1 - relative[i][k], pValues[j]); // Формула с показателем p=2
//                }
//                distances[i][j] = Math.pow(sum, 1.0 / pValues[j]); // Применение корня к сумме
//            }
//        }
//
//        return distances;
//    }
//
//    private double[] getNormalizedAssessment(List<Double> assessments){
//        double sum = 0;
//        double[] result = new double[assessments.size()];
//
//        for(var assessment : assessments){
//            sum += assessment;
//        }
//
//        for(var i = 0; i < assessments.size(); i++){
//            result[i] = (assessments.get(i) / sum);
//        }
//
//        return  result;
//    }
//
//    private double[] getComplexImportance(double[] variability, double[] assessments){
//        int size = variability.length;
//        double[] result = new double[size];
//        double sum = 0;
//
//        for (int i = 0; i < size; i++) {
//            sum += variability[i] * assessments[i];
//        }
//
//        for (int i = 0; i < size; i++) {
//            result[i] = (variability[i] * assessments[i])/(sum);
//        }
//
//        return result;
//    }
//
//    private  double[] getInvertEntropy(double[] entropy){
//        double[] result = new double[entropy.length];
//        for (int i = 0; i < entropy.length; i++) {
//            result[i] = 1 - entropy[i];
//        }
//        return result;
//    }
//
//    private double[] getEntropy(double[][] normalized) {
//        int m = normalized[0].length; // Количество критериев
//        int n = normalized.length;    // Количество альтернатив
//
//        double k = 1.0 / Math.log(n); // Вычисляем k
//
//        double[] entropy = new double[m]; // Массив для хранения значений энтропии
//
//        for (int j = 0; j < m; j++) {
//            double sum = 0.0;
//            for (int i = 0; i < n; i++) {
//                double pij = normalized[i][j];
//                if (pij > 0) { // Проверяем, что pij не равно нулю для избежания ошибки логарифма
//                    sum += pij * Math.log(pij);
//                }
//            }
//            entropy[j] = -k * sum; // Вычисляем энтропию для каждого критерия j
//        }
//
//        return entropy;
//    }
//
//    private double[][] getNormalizedMatrix(double[][] relative) {
//        int n = relative.length;
//        int m = relative[0].length;
//        double[][] result = new double[n][m];
//
//        double[] sumValues = new double[m];
//        for (int j = 0; j < m; j++) {
//            double sum = 0.0;
//            for (int i = 0; i < n; i++) {
//                sum += relative[i][j];
//            }
//            sumValues[j] = sum;
//        }
//
//        // Вычисляем значения P_ij
//        for (int i = 0; i < n; i++) {
//            for (int j = 0; j < m; j++) {
//                result[i][j] = relative[i][j] / sumValues[j];
//            }
//        }
//
//        return result;
//    }
//
//    private double[][] getRelativeUnits(List<Alternative> alternatives, List<Double> ideal, List<Double> imperfect) {
//        var alternativeMatrix = generateMatrix(alternatives);
//        var alternativeNumber = alternatives.size();
//        var criteriaNumber = alternatives.get(0).getCriteriaValuesList().size();
//        double[][] result = new double[alternativeNumber][criteriaNumber];
//
//        for (var i = 0; i < alternativeNumber; i++) {
//            for (var j = 0; j < criteriaNumber; j++) {
//                result[i][j] = (ideal.get(j) - alternativeMatrix[i][j]) / (ideal.get(j) - imperfect.get(j));
//            }
//        }
//
//        return result;
//    }
//
//    private double[][] generateMatrix(List<Alternative> alternatives) {
//        int numRows = alternatives.size();
//        int numCols = alternatives.get(0).getCriteriaValuesList().size();
//        double[][] matrix = new double[numRows][numCols];
//
//        for (int i = 0; i < numRows; i++) {
//            Alternative alternative = alternatives.get(i);
//            List<Double> criteriaValuesList = alternative.getCriteriaValuesList();
//
//            for (int j = 0; j < numCols; j++) {
//                matrix[i][j] = criteriaValuesList.get(j);
//            }
//        }
//
//        return matrix;
//    }
//
//    private List<Double> getIdealObject(DisplacedIdealDto data) {
//        var criteriaSet = data.getCriteriaSet();
//        var usefulness = criteriaSet.getUsefulness();
//        var alternatives = data.getAlternatives();
//        var result = new ArrayList<Double>();
//
//        for (var i = 0; i < usefulness.size(); i++) {
//            var isUseful = usefulness.get(i);
//            var extremeValue = isUseful ? Double.MIN_VALUE : Double.MAX_VALUE;
//
//            result.add(extremeValue);
//
//            for (Alternative alternative : alternatives) {
//                var value = alternative.getCriteriaValuesList().get(i);
//                result.set(i, isUseful ? Math.max(result.get(i), value) : Math.min(result.get(i), value));
//            }
//        }
//
//        return result;
//    }
//
//
//    private List<Double> getImperfectObject(DisplacedIdealDto data) {
//        var criteriaSet = data.getCriteriaSet();
//        var usefulness = criteriaSet.getUsefulness();
//        var alternatives = data.getAlternatives();
//        var result = new ArrayList<Double>();
//
//        for (var i = 0; i < usefulness.size(); i++) {
//            var isUseful = usefulness.get(i);
//            var extremeValue = isUseful ? Double.MAX_VALUE : Double.MIN_VALUE;
//
//            result.add(extremeValue);
//
//            for (Alternative alternative : alternatives) {
//                var value = alternative.getCriteriaValuesList().get(i);
//                result.set(i, isUseful ? Math.min(result.get(i), value) : Math.max(result.get(i), value));
//            }
//        }
//
//        return result;
//    }
//
//}
package ru.bstu.dss.method;

import org.springframework.stereotype.Component;
import ru.bstu.dss.dto.DisplacedIdealDto;
import ru.bstu.dss.model.Alternative;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class PermutationMethodSolver {

    private final int P = 5;
    private final int[] pValues = {1, 2, 3, 4, 5};

    public String run(DisplacedIdealDto data) {
        var alternatives = data.getAlternatives();

        List<Double> ideal = getIdealObject(data);
        List<Double> imperfect = getImperfectObject(data);
        double[][] relative = getRelativeUnits(alternatives, ideal, imperfect);
        double[][] normalized = getNormalizedMatrix(relative);
        double[] entropyMatrix = getEntropy(normalized);
        double[] invertEntropyMatrix = getInvertEntropy(entropyMatrix);
        double[] complexImportance = getComplexImportance(invertEntropyMatrix, getNormalizedAssessment(data.getCriteriaSet().getImportance()));

        List<List<Integer>> permutations = generatePermutations(alternatives.size());
        Map<List<Integer>, Double> permutationWeights = getPermutationWeights(permutations, alternatives, complexImportance);

        // Найти лучшую перестановку с максимальным весом
        List<Integer> bestPermutation = null;
        double maxWeight = Double.NEGATIVE_INFINITY;
        for (Map.Entry<List<Integer>, Double> entry : permutationWeights.entrySet()) {
            if (entry.getValue() > maxWeight) {
                maxWeight = entry.getValue();
                bestPermutation = entry.getKey();
            }
        }

        // Вернуть имя лучшей альтернативы
        return alternatives.get(bestPermutation.get(0)).getName();
    }

    private Map<List<Integer>, Double> getPermutationWeights(List<List<Integer>> permutations, List<Alternative> alternatives, double[] complexImportance) {
        Map<List<Integer>, Double> weights = new HashMap<>();

        for (List<Integer> permutation : permutations) {
            int n = alternatives.size();
            double weight = 0.0;

            for (int k = 0; k < n - 1; k++) {
                List<Double> left = alternatives.get(permutation.get(k)).getCriteriaValuesList();
                for (int l = k + 1; l < n; l++) {
                    List<Double> right = alternatives.get(permutation.get(l)).getCriteriaValuesList();
                    for (int j = 0; j < left.size(); j++) {
                        if (left.get(j) > right.get(j)) {
                            weight += complexImportance[j];
                        } else {
                            weight -= complexImportance[j];
                        }
                    }
                }
            }

            weights.put(permutation, weight);
        }

        return weights;
    }

    private List<List<Integer>> generatePermutations(int n) {
        List<List<Integer>> result = new ArrayList<>();
        generatePermutationsHelper(new ArrayList<>(), new boolean[n], n, result);
        return result;
    }

    private void generatePermutationsHelper(List<Integer> current, boolean[] used, int n, List<List<Integer>> result) {
        if (current.size() == n) {
            result.add(new ArrayList<>(current));
            return;
        }

        for (int i = 0; i < n; i++) {
            if (!used[i]) {
                used[i] = true;
                current.add(i);
                generatePermutationsHelper(current, used, n, result);
                current.remove(current.size() - 1);
                used[i] = false;
            }
        }
    }

    private void deleteAlternative(List<Alternative> alternatives, int worse) {
        alternatives.remove(worse);
    }

    private int findMostFrequentNumber(int[] numbers) {
        Map<Integer, Integer> frequencyMap = new HashMap<>();
        for (int num : numbers) {
            frequencyMap.put(num, frequencyMap.getOrDefault(num, 0) + 1);
        }

        int maxFrequency = 0;
        int mostFrequentNumber = 0;
        for (Map.Entry<Integer, Integer> entry : frequencyMap.entrySet()) {
            if (entry.getValue() > maxFrequency) {
                maxFrequency = entry.getValue();
                mostFrequentNumber = entry.getKey();
            }
        }

        return mostFrequentNumber;
    }

    private int getWorse(double[][] matrix) {
        int[] countArray = new int[P];

        for (int i = 0; i < P; i++) {
            countArray[i] = 0;
            double currentMin = Double.MAX_VALUE;
            for (int j = 0; j < matrix.length; j++) {
                if (matrix[j][i] < currentMin) {
                    currentMin = matrix[j][i];
                    countArray[i] = j;
                }
            }
        }
        return findMostFrequentNumber(countArray);
    }

    private double[][] getDistanceMatrix(double[][] relative, double[] importance) {
        int n = relative.length;
        int m = relative[0].length;

        double[][] distances = new double[n][P];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < P; j++) {
                double sum = 0.0;
                for (int k = 0; k < m; k++) {
                    sum += importance[k] * Math.pow(1 - relative[i][k], pValues[j]);
                }
                distances[i][j] = Math.pow(sum, 1.0 / pValues[j]);
            }
        }

        return distances;
    }

    private double[] getNormalizedAssessment(List<Double> assessments) {
        double sum = 0;
        double[] result = new double[assessments.size()];

        for (var assessment : assessments) {
            sum += assessment;
        }

        for (var i = 0; i < assessments.size(); i++) {
            result[i] = (assessments.get(i) / sum);
        }

        return result;
    }

    private double[] getComplexImportance(double[] variability, double[] assessments) {
        int size = variability.length;
        double[] result = new double[size];
        double sum = 0;

        for (int i = 0; i < size; i++) {
            sum += variability[i] * assessments[i];
        }

        for (int i = 0; i < size; i++) {
            result[i] = (variability[i] * assessments[i]) / sum;
        }

        return result;
    }

    private double[] getInvertEntropy(double[] entropy) {
        double[] result = new double[entropy.length];
        for (int i = 0; i < entropy.length; i++) {
            result[i] = 1 - entropy[i];
        }
        return result;
    }

    private double[] getEntropy(double[][] normalized) {
        int m = normalized[0].length;
        int n = normalized.length;

        double k = 1.0 / Math.log(n);

        double[] entropy = new double[m];

        for (int j = 0; j < m; j++) {
            double sum = 0.0;
            for (int i = 0; i < n; i++) {
                double pij = normalized[i][j];
                if (pij > 0) {
                    sum += pij * Math.log(pij);
                }
            }
            entropy[j] = -k * sum;
        }

        return entropy;
    }

    private double[][] getNormalizedMatrix(double[][] relative) {
        int n = relative.length;
        int m = relative[0].length;
        double[][] result = new double[n][m];

        double[] sumValues = new double[m];
        for (int j = 0; j < m; j++) {
            double sum = 0.0;
            for (int i = 0; i < n; i++) {
                sum += relative[i][j];
            }
            sumValues[j] = sum;
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                result[i][j] = relative[i][j] / sumValues[j];
            }
        }

        return result;
    }

    private double[][] getRelativeUnits(List<Alternative> alternatives, List<Double> ideal, List<Double> imperfect) {
        var alternativeMatrix = generateMatrix(alternatives);
        var alternativeNumber = alternatives.size();
        var criteriaNumber = alternatives.get(0).getCriteriaValuesList().size();
        double[][] result = new double[alternativeNumber][criteriaNumber];

        for (var i = 0; i < alternativeNumber; i++) {
            for (var j = 0; j < criteriaNumber; j++) {
                result[i][j] = (ideal.get(j) - alternativeMatrix[i][j]) / (ideal.get(j) - imperfect.get(j));
            }
        }

        return result;
    }

    private double[][] generateMatrix(List<Alternative> alternatives) {
        int numRows = alternatives.size();
        int numCols = alternatives.get(0).getCriteriaValuesList().size();
        double[][] matrix = new double[numRows][numCols];

        for (int i = 0; i < numRows; i++) {
            Alternative alternative = alternatives.get(i);
            List<Double> criteriaValuesList = alternative.getCriteriaValuesList();

            for (int j = 0; j < numCols; j++) {
                matrix[i][j] = criteriaValuesList.get(j);
            }
        }

        return matrix;
    }

    private List<Double> getIdealObject(DisplacedIdealDto data) {
        var criteriaSet = data.getCriteriaSet();
        List<Double> ideal = new ArrayList<>();

        for (var criterion : criteriaSet.getImportance()) {
            ideal.add(criterion);
        }

        return ideal;
    }

    private List<Double> getImperfectObject(DisplacedIdealDto data) {
        var criteriaSet = data.getCriteriaSet();
        List<Double> imperfect = new ArrayList<>();

        for (var criterion : criteriaSet.getImportance()) {
            imperfect.add(criterion);
        }

        return imperfect;
    }
}


