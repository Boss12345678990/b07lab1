// public class Polynomial{
//     double[] coefficients;
//     int[] exponents; 
    
//     public Polynomial(){
//         coefficients = new double[]{0};
//         exponents = new int[]{0};
//     }
    
//     public Polynomial(double[] coArr){
//         coefficients = new double[coArr.length];
//         System.arraycopy(coArr, 0, exponents, 0 , coArr.length);
//     }
//     public Polynomial add(Polynomial argument){
//         int maxlength = Math.max(coefficients.length, argument.coefficients.length);
//         double[] result = new double[maxlength];
//         // double[] resultExp = new int[maxlength];
//         for (int i = 0; i < maxlength; i++){
//             double thisCoeff = (i >= coefficients.length) ? 0 : coefficients[i];
//             double argCoeff = (i >= argument.coefficients.length) ? 0 : argument.coefficients[i];
//             result[i] = thisCoeff + argCoeff;

//         }

//         return new Polynomial(result);
//     }

//     public double evaluate(double x){
//        	double total = 0;
//         for (int i = 0; i<coefficients.length; i++){
//             total = total + coefficients[i] * Math.pow(x,i);
//         }
//         return total;
//     }
//     public boolean hasRoot(double x){
//         return evaluate(x) == 0;
//     }
// }
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class Polynomial{
    double[] coefficients;
    int[] exponents;
    
    public Polynomial(){
        coefficients = new double[]{0};
        exponents = new int[]{0};
    }
    
    public Polynomial(double[] coArr, int[] expArr){
        ArrayList<Double> coeffList = new ArrayList<>();
        ArrayList<Integer> expList = new ArrayList<>();

        for (int i = 0; i < coArr.length; i++) {
            if (coArr[i] != 0) {
                coeffList.add(coArr[i]);
                expList.add(expArr[i]);
            }
        }

        coefficients = new double[coeffList.size()];
        exponents = new int[expList.size()];

        for (int i = 0; i < coeffList.size(); i++) {
            coefficients[i] = coeffList.get(i);
            exponents[i] = expList.get(i);
        }
    }
    
    public Polynomial add(Polynomial argument) {
        // Create arrays to hold the results, which may need to be larger than the inputs
        double[] resultCo = new double[coefficients.length + argument.coefficients.length];
        int[] resultExp = new int[coefficients.length + argument.coefficients.length];
    
        int i = 0, j = 0, c = 0; // Initialize indices for this polynomial, the argument, and the result
    
        // Loop through both polynomials
        while (i < coefficients.length && j < argument.coefficients.length) {
            if (exponents[i] < argument.exponents[j]) {
                resultCo[c] = coefficients[i];
                resultExp[c] = exponents[i];
                i++;
            } else if (exponents[i] > argument.exponents[j]) {
                resultCo[c] = argument.coefficients[j];
                resultExp[c] = argument.exponents[j];
                j++;
            } else { // exponents[i] == argument.exponents[j]
                resultCo[c] = coefficients[i] + argument.coefficients[j];
                resultExp[c] = exponents[i];
                i++;
                j++;
            }
            c++; // Increment the count of coefficients added to the result
        }
    
        // Add remaining terms from this polynomial, if any
        while (i < coefficients.length) {
            resultCo[c] = coefficients[i];
            resultExp[c] = exponents[i];
            i++;
            c++;
        }
    
        // Add remaining terms from the argument polynomial, if any
        while (j < argument.coefficients.length) {
            resultCo[c] = argument.coefficients[j];
            resultExp[c] = argument.exponents[j];
            j++;
            c++;
        }
    
        // Resize the result arrays to the actual size used
        double[] finalCo = new double[c];
        int[] finalExp = new int[c];
        System.arraycopy(resultCo, 0, finalCo, 0, c);
        System.arraycopy(resultExp, 0, finalExp, 0, c);
    
        return new Polynomial(finalCo, finalExp);
    }

    
    public Polynomial multiply(Polynomial argument){
        HashMap<Integer, Double> termMap = new HashMap<>();
        for (int i = 0; i < coefficients.length; i++) {
            for (int j = 0; j < argument.coefficients.length; j++) {
                double coValue = coefficients[i] * argument.coefficients[j];
                int expValue = exponents[i] + argument.exponents[j];
                termMap.put(expValue, termMap.getOrDefault(expValue, 0.0) + coValue);
            }
        }
        double[] resultCo = new double[termMap.size()];
        int[] resultExp = new int[termMap.size()];
        int index = 0;
        for (int exp : termMap.keySet()) {
            resultExp[index] = exp;
            resultCo[index] = termMap.get(exp);
            index++;
        }
        return  new Polynomial(resultCo, resultExp); 
    }
    public double evaluate(double x){
        double total = 0;
        for (int i = 0; i<coefficients.length; i++){
            total = total + coefficients[i] * Math.pow(x,i);
        }
        return total;
    }
    public boolean hasRoot(double x){
        return evaluate(x) == 0;
    }
    public Polynomial(File file) {
        List<Double> coeffList = new ArrayList<>();
        List<Integer> expList = new ArrayList<>();

        try (Scanner scanner = new Scanner(file)) {
            if (scanner.hasNextLine()) {
                String polynomialString = scanner.nextLine();
                parsePolynomial(polynomialString, coeffList, expList);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        coefficients = coeffList.stream().mapToDouble(Double::doubleValue).toArray();
        exponents = expList.stream().mapToInt(Integer::intValue).toArray();
    }

    private void parsePolynomial(String polynomial, List<Double> coeffList, List<Integer> expList) {
        String[] terms = polynomial.split("(?=[+-])");
        for (String term : terms) {
            term = term.trim();
            if (term.contains("x")) {
                String[] parts = term.split("x");
                double coefficient = parts[0].isEmpty() || parts[0].equals("+") ? 1 : parts[0].equals("-") ? -1 : Double.parseDouble(parts[0]);
                int exponent = parts.length > 1 && !parts[1].isEmpty() ? Integer.parseInt(parts[1].replaceAll("^\\^", "")) : 1;
                coeffList.add(coefficient);
                expList.add(exponent);
            } else {
                double constant = Double.parseDouble(term);
                coeffList.add(constant);
                expList.add(0);
            }
        }
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < coefficients.length; i++) {
            if (i > 0 && coefficients[i] > 0) {
                sb.append("+");
            }
            sb.append(coefficients[i]);
            if (exponents[i] > 0) {
                sb.append("x");
                if (exponents[i] > 1) {
                    sb.append("^").append(exponents[i]);
                }
            }
        }
        return sb.toString();
    }
}