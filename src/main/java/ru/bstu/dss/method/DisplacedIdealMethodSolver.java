package ru.bstu.dss.method;

import org.springframework.stereotype.Component;
import ru.bstu.dss.dto.inputDataDto;
import ru.bstu.dss.model.Alternative;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class DisplacedIdealMethodSolver {

    private final int P = 5;
    private final int[] pValues = {1, 2, 3, 4, 5};

    public String run23(inputDataDto data) {
        var result = data.getAlternatives();

        while (result.size() != 1) {
            List<Double> ideal = getIdealObject(data);
            List<Double> imperfect = getImperfectObject(data);
            double[][] relative = getRelativeUnits(result, ideal, imperfect);
            double[][] normalized = getNormalizedMatrix(relative);
            double[] entropyMatrix = getEntropy(normalized);
            double[] invertEntropyMatrix = getInvertEntropy(entropyMatrix);
            double[] complexImportance = getComplexImportance(invertEntropyMatrix, getNormalizedAssessment(data.getCriteriaSet().getImportance()));

        }

        return result.get(0).getName();
    }


    public String run(inputDataDto data) {
        var result = data.getAlternatives();

        while (result.size() != 1) {
            List<Double> ideal = getIdealObject(data);
            List<Double> imperfect = getImperfectObject(data);
            double[][] relative = getRelativeUnits(result, ideal, imperfect);
            double[][] normalized = getNormalizedMatrix(relative);
            double[] entropyMatrix = getEntropy(normalized);
            double[] invertEntropyMatrix = getInvertEntropy(entropyMatrix);
            double[] complexImportance = getComplexImportance(invertEntropyMatrix, getNormalizedAssessment(data.getCriteriaSet().getImportance()));
            double[][] distanceMatrix = getDistanceMatrix(relative, complexImportance);
            int worse = getWorse(distanceMatrix);
            deleteAlternative(result,worse);
        }

        return result.get(0).getName();
    }

    private void deleteAlternative(List<Alternative> alternatives, int worse){
        alternatives.remove(worse);
    }

    private int findMostFrequentNumber(int[] numbers) {
        // Создаем HashMap для хранения чисел и их частоты
        Map<Integer, Integer> frequencyMap = new HashMap<>();

        // Заполняем HashMap и считаем частоту встречаемости
        for (int num : numbers) {
            frequencyMap.put(num, frequencyMap.getOrDefault(num, 0) + 1);
        }

        // Находим число с максимальной частотой
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

    private int getWorse(double[][] matrix){
        int[] countArray = new int[P];

        for (int i = 0; i < P; i++){
            countArray[i] = 0;
            double currentMin = Double.MAX_VALUE;
            for (int j = 0; j < matrix.length; j++){
                if (matrix[j][i] < currentMin){
                    currentMin = matrix[j][i];
                    countArray[i] = j;
                }
            }
        }
        return findMostFrequentNumber(countArray);
    }

    private double[][] getDistanceMatrix(double[][] relative, double[] importance) {
        int n = relative.length; // Количество объектов
        int m = relative[0].length; // Количество характеристик (расстояний)

        double[][] distances = new double[n][P]; // Матрица расстояний

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < P; j++) {
                double sum = 0.0;
                for (int k = 0; k < m; k++) {
                    sum += importance[k] * Math.pow(1 - relative[i][k], pValues[j]); // Формула с показателем p=2
                }
                distances[i][j] = Math.pow(sum, 1.0 / pValues[j]); // Применение корня к сумме
            }
        }

        return distances;
    }

    private double[] getNormalizedAssessment(List<Double> assessments){
        double sum = 0;
        double[] result = new double[assessments.size()];

        for(var assessment : assessments){
            sum += assessment;
        }

        for(var i = 0; i < assessments.size(); i++){
            result[i] = (assessments.get(i) / sum);
        }

        return  result;
    }

    private double[] getComplexImportance(double[] variability, double[] assessments){
        int size = variability.length;
        double[] result = new double[size];
        double sum = 0;

        for (int i = 0; i < size; i++) {
            sum += variability[i] * assessments[i];
        }

        for (int i = 0; i < size; i++) {
            result[i] = (variability[i] * assessments[i])/(sum);
        }

        return result;
    }

    private  double[] getInvertEntropy(double[] entropy){
        double[] result = new double[entropy.length];
        for (int i = 0; i < entropy.length; i++) {
            result[i] = 1 - entropy[i];
        }
        return result;
    }

    private double[] getEntropy(double[][] normalized) {
        int m = normalized[0].length; // Количество критериев
        int n = normalized.length;    // Количество альтернатив

        double k = 1.0 / Math.log(n); // Вычисляем k

        double[] entropy = new double[m]; // Массив для хранения значений энтропии

        for (int j = 0; j < m; j++) {
            double sum = 0.0;
            for (int i = 0; i < n; i++) {
                double pij = normalized[i][j];
                if (pij > 0) { // Проверяем, что pij не равно нулю для избежания ошибки логарифма
                    sum += pij * Math.log(pij);
                }
            }
            entropy[j] = -k * sum; // Вычисляем энтропию для каждого критерия j
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

        // Вычисляем значения P_ij
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

    private List<Double> getIdealObject(inputDataDto data) {
        var criteriaSet = data.getCriteriaSet();
        var usefulness = criteriaSet.getUsefulness();
        var alternatives = data.getAlternatives();
        var result = new ArrayList<Double>();

        for (var i = 0; i < usefulness.size(); i++) {
            var isUseful = usefulness.get(i);
            var extremeValue = isUseful ? Double.MIN_VALUE : Double.MAX_VALUE;

            result.add(extremeValue);

            for (Alternative alternative : alternatives) {
                var value = alternative.getCriteriaValuesList().get(i);
                result.set(i, isUseful ? Math.max(result.get(i), value) : Math.min(result.get(i), value));
            }
        }

        return result;
    }


    private List<Double> getImperfectObject(inputDataDto data) {
        var criteriaSet = data.getCriteriaSet();
        var usefulness = criteriaSet.getUsefulness();
        var alternatives = data.getAlternatives();
        var result = new ArrayList<Double>();

        for (var i = 0; i < usefulness.size(); i++) {
            var isUseful = usefulness.get(i);
            var extremeValue = isUseful ? Double.MAX_VALUE : Double.MIN_VALUE;

            result.add(extremeValue);

            for (Alternative alternative : alternatives) {
                var value = alternative.getCriteriaValuesList().get(i);
                result.set(i, isUseful ? Math.min(result.get(i), value) : Math.max(result.get(i), value));
            }
        }

        return result;
    }


}
