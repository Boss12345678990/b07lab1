public class Polynomial{
    double[] coefficients;
    
    public Polynomial(){
        coefficients = new double[]{0};
    }
    
    public Polynomial(double[] arr){
        coefficients = new double[arr.length];
        System.arraycopy(arr, 0, coefficients, 0, arr.length);
    }
    
    public Polynomial add(Polynomial argument){
        int maxlength = Math.max(coefficients.length, argument.coefficients.length);
        double[] result = new double[maxlength];
        for (int i = 0; i < maxlength; i++){
            double thisCoeff = (i >= coefficients.length) ? 0 : coefficients[i];
            double argCoeff = (i >= argument.coefficients.length) ? 0 : argument.coefficients[i];
            result[i] = thisCoeff + argCoeff;
        }

        return new Polynomial(result);
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
}