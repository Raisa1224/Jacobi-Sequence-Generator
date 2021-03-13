import java.math.BigInteger;
import java.util.Random;

public class mainClass {
    static public void main(String[] args){
        BigInteger p, q;

        BigInteger maxLimit = new BigInteger("5000000000000");
        BigInteger minLimit = new BigInteger("25000000000");
        BigInteger bigInteger = maxLimit.subtract(minLimit);

        int len = maxLimit.bitLength();
        Random randNum = new Random();
        p = java.math.BigInteger.probablePrime(len, randNum);

        if (p.compareTo(minLimit) < 0)
            p = p.add(minLimit);
        if (p.compareTo(bigInteger) >= 0)
            p = p.mod(bigInteger).add(minLimit);

        q = java.math.BigInteger.probablePrime(len, randNum);
        if (q.compareTo(minLimit) < 0)
            q = q.add(minLimit);
        if (q.compareTo(bigInteger) >= 0)
            q = q.mod(bigInteger).add(minLimit);

        System.out.println("Jacobi Generator with given p and q:");
        JacobiGenerator jacobiGenerator = new JacobiGenerator(p,q);
        System.out.println(jacobiGenerator);

        System.out.println("Jacobi generator with p and q randomly generated in constructor:");
        JacobiGenerator randomJacobiGenerator = new JacobiGenerator();
        System.out.println(randomJacobiGenerator);

        System.out.println("***************************************");
        System.out.println("The random numbers:");
        jacobiGenerator.generateRandomNumbers(100);

        System.out.println();
        System.out.println("The random bytes:");
        jacobiGenerator.generateRandomBytes(100);

        System.out.println();
        jacobiGenerator.testing();
        //System.out.println("Jacobi: " + jacobiGenerator.JacobiSymbol(BigInteger.valueOf(1239),BigInteger.valueOf(20003)));
    }
}
