package ru.bstu.dss.method;

import org.springframework.stereotype.Component;
import ru.bstu.dss.dto.DisplacedIdealDto;
import ru.bstu.dss.model.Alternative;

import java.util.ArrayList;
import java.util.List;

@Component
public class DisplacedIdealMethodSolver {

    public void run(DisplacedIdealDto data) {
        var result = data.getAlternatives();

        while (result.size() != 1) {
            List<Double> ideal = getIdealObject(data);
            List<Double> imperfect = getImperfectObject(data);
            double[][] relative = getRelativeUnits(result, ideal, imperfect);
            double[][] normalized = getNormalizedMatrix(relative);
            double[] entropyMatrix = getEntropy(normalized);
            double[] invertEntopyMatrix = getInvertEntropy(entropyMatrix);

        }
    }

    private  double[] getInvertEntropy(double[] entropy){
        double[] result = new double[entropy.length];
        for (int i = 0; i < entropy.length; i++) {
            result[i] = 1 - entropy[i];
        }
        return result;
    }

    private double[] getEntropy(double[][] normalized){
        int rowSize = normalized.length;
        int columnSize = normalized[0].length;
        double[] result = new double[columnSize];
        double k = 1 / Math.log(rowSize);

        for (int j = 0; j < columnSize; j++) {
            double sum = 0;
            for (int i = 0; i < rowSize; i++) {
                sum += normalized[i][j] * Math.log(normalized[i][j]);
            }
            result[j] = -k * sum;
        }

        return result;
    }

    private double[][] getNormalizedMatrix(double[][] relative) {
        int rowSize = relative.length;
        int columnSize = relative[0].length;
        double[][] result = new double[columnSize][rowSize];

        for (int i = 0; i < columnSize; i++) {
            double sum = sumColumn(relative, i);

            for (int j = 0; j < rowSize; j++) {
                if (sum != 0) {
                    result[i][j] = relative[i][j] / sum;
                }
                else {
                    result[i][j] = 0;
                }
            }
        }

        return result;
    }

    private double sumColumn(double[][] matrix, int index) {
        double sum = 0;

        for (int i = 0; i < matrix.length; i++) {
            sum += matrix[i][index];
        }

        return sum;
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


    private List<Double> getImperfectObject(DisplacedIdealDto data) {
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
