import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class JacobiGenerator {

    BigInteger p;
    BigInteger q;
    BigInteger n;
    BigInteger seed;
    List<BigInteger> randomNumbers;
    int[] randomBytes;

    public JacobiGenerator(){
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
        BigInteger aux = p;
        this.n = aux.multiply(q);
        do {
            bigInteger = maxLimit.subtract(minLimit);
            randNum = new Random();
            len = maxLimit.bitLength();
            BigInteger res = new BigInteger(len, randNum);
            if (res.compareTo(minLimit) < 0)
                res = res.add(minLimit);
            if (res.compareTo(bigInteger) >= 0)
                res = res.mod(bigInteger).add(minLimit);
            this.seed = res;
        } while (seed == p || seed == n || seed == q || seed.compareTo(BigInteger.ZERO)<0 || seed.compareTo(n)>=0);
    }

    public JacobiGenerator(BigInteger p, BigInteger q) {
        this.p = p;
        this.q = q;
        BigInteger copy =p;
        this.n = copy.multiply(q);
        BigInteger maxLimit = new BigInteger("5000000000000");
        BigInteger minLimit = new BigInteger("25000000000");
        do {
            BigInteger bigInteger = maxLimit.subtract(minLimit);
            Random randNum = new Random();
            int len = maxLimit.bitLength();
            BigInteger res = new BigInteger(len, randNum);
            if (res.compareTo(minLimit) < 0)
                res = res.add(minLimit);
            if (res.compareTo(bigInteger) >= 0)
                res = res.mod(bigInteger).add(minLimit);
            this.seed = res;
        } while (seed == p || seed == n || seed == q || seed.compareTo(BigInteger.ZERO)<0 || seed.compareTo(n)>=0);
    }

    public int JacobiSymbol(BigInteger number, BigInteger n){
        //BigInteger jacobiSymbol = BigInteger.ZERO;
        if(number.compareTo(BigInteger.valueOf(1))==0) return 1;
        if(number.compareTo(BigInteger.valueOf(0))==0) return 0;
        if(number.compareTo(n)>0){
            return JacobiSymbol(number.mod(n), n);
        }
        if(number.mod(BigInteger.valueOf(2)).compareTo(BigInteger.ZERO)==0){
            if(n.mod(BigInteger.valueOf(8)).compareTo(BigInteger.valueOf(-1))==0 || n.mod(BigInteger.valueOf(8)).compareTo(BigInteger.valueOf(1))==0 ){
                return JacobiSymbol(number.divide(BigInteger.valueOf(2)), n);
            }
        }

        if(n.mod(BigInteger.valueOf(4)).compareTo(BigInteger.valueOf(3))==0 && number.mod(BigInteger.valueOf(4)).compareTo(BigInteger.valueOf(3))==0)
            return -JacobiSymbol(n,number);
        else{
            return JacobiSymbol(n,number);
        }
    }
    public void generateRandomNumbers(int numberOfNumbers){
        //calculate the Jacobi Symbol of s, s+1, s+2, ..., s+numberOfNumbers-1
        randomNumbers = new ArrayList<>();
        BigInteger copyOfSeed = this.seed.add(BigInteger.ZERO);
        BigInteger copyOfP = this.p;
        BigInteger copyOfn = copyOfP.multiply(this.q);
        for(int number=0; number<numberOfNumbers; number++){
            copyOfSeed = copyOfSeed.add(BigInteger.valueOf(number));
            int symbol = JacobiSymbol(copyOfSeed,copyOfn);
            copyOfn = n;
            randomNumbers.add(BigInteger.valueOf(symbol));
            System.out.print(symbol + " ");
        }
    }

    public void generateRandomBytes(int numberOfBytes){
        //calculate the Jacobi Symbol of s, s+1, s+2, ..., s+numberOfNumbers-1
        //if the Jacobi symbol is -1 the bit is 0. Else the bit is 1
        randomBytes = new int[numberOfBytes];
        BigInteger copyOfSeed = this.seed.add(BigInteger.ZERO);
        BigInteger copyOfP = this.p;
        BigInteger copyOfn = copyOfP.multiply(this.q);
        for(int number=0; number<numberOfBytes; number++){
            copyOfSeed = copyOfSeed.add(BigInteger.valueOf(number));
            int symbol = JacobiSymbol(copyOfSeed,copyOfn);
            copyOfn = n;
            if(symbol==-1){
                randomBytes[number] = 0;
            }
            else{
                randomBytes[number] = 1;
            }
            System.out.print(randomBytes[number] + " ");
        }
    }

    void testing(){
        if(randomBytes == null) System.out.println("There have been no bytes generated so there are no tests to be done");
        else{
            int numberOf0 = 0;
            int numberOf1 = 0;
            for(int randByte : randomBytes){
                if(randByte==0) numberOf0++;
                else numberOf1 ++;
            }
            System.out.println("There are " + numberOf0 + " bytes of value 0 and " + numberOf1 + " bytes of value 1");
            if(numberOf0>numberOf1){
                System.out.println("There are " + (numberOf0-numberOf1) + " more values of 0 than 1");
            }
            else{
                if(numberOf0==numberOf1) System.out.println("There are equal numbers of 0's and 1's");
                else
                    System.out.println("There are " + (numberOf1-numberOf0) + " more values of 1 than 0");
            }
        }
    }

    @Override
    public String toString() {
        return "seed: " + this.seed + "\np: " + this.p + "\nq: " + this.q + "\nn: " + this.n +"\n";
    }

    public BigInteger getP() {
        return p;
    }

    public BigInteger getQ() {
        return q;
    }

    public BigInteger getN() {
        return n;
    }

    public BigInteger getSeed() {
        return seed;
    }

    public void setP(BigInteger p) {
        this.p = p;
    }

    public void setQ(BigInteger q) {
        this.q = q;
    }

    public void setN(BigInteger n) {
        this.n = n;
    }

    public void setSeed(BigInteger seed) {
        this.seed = seed;
    }
}
