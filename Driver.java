import java.io.File;
public class Driver {
    public static void main(String[] args) {
        Polynomial p = new Polynomial();
        System.out.println("Default Polynomial evaluate at x=3: " + p.evaluate(3)); 
        double[] c1 = {6, 0, 0, 5};
        Polynomial p1 = new Polynomial(c1, new int[]{0, 1, 2, 3});
        double[] c2 = {0, -2, 0, 0, -9};
        Polynomial p2 = new Polynomial(c2, new int[]{0, 1, 2, 3, 4});
        Polynomial s = p1.add(p2);
        System.out.println("s(x) = p1(x) + p2(x), s(0.1) = " + s.evaluate(0.1));
        if (s.hasRoot(1)) {
            System.out.println("1 is a root of s");
        } else {
            System.out.println("1 is not a root of s");
        }
        Polynomial product = p1.multiply(p2);
        System.out.println("Product of p1 and p2 evaluated at x=1: " + product.evaluate(1));
        try {
            File file = new File("test.txt");
            Polynomial pFromFile = new Polynomial(file);
            System.out.println("Polynomial from file evaluated at x=2: " + pFromFile.evaluate(2));
        } catch (Exception e) {
            System.out.println("Error reading polynomial from file: " + e.getMessage());
        }
        
    }
}
