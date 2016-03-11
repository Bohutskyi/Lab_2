import java.math.BigInteger;

public class Mathematics {

    private BigInteger r, x, y;

    Mathematics(int one, int two, int three) {
        this(new BigInteger(Integer.toString(one)), new BigInteger(Integer.toString(two)), new BigInteger(Integer.toString(three)));
    }

    Mathematics(BigInteger one, BigInteger two, BigInteger three) {
        r = one;
        x = two;
        y = three;
    }

    Mathematics() {
    }

    public BigInteger getR() {
        return r;
    }

    public BigInteger getX() {
        return x;
    }

    public BigInteger getY() {
        return y;
    }

    static Mathematics gcd(int a, int b) {
        return gcd(new BigInteger(String.valueOf(a)), new BigInteger(String.valueOf(b)));
    }

    static Mathematics gcd(BigInteger a, BigInteger b) {
        Mathematics temp = new Mathematics(a, BigInteger.ONE, BigInteger.ZERO);
        Mathematics temp2 = new Mathematics();
        if (b == BigInteger.ZERO) {
            return temp;
        }
        temp2 = gcd(b, a.mod(b));
        temp = new Mathematics();
        temp.r = temp2.r;
        temp.x = temp2.y;
        temp.y = temp2.x.subtract(a.divide(b).multiply(temp2.y));
        return temp;
    }

    static BigInteger getReverse(int a, int mod) {
        return getReverse(new BigInteger(String.valueOf(a)), new BigInteger(String.valueOf(mod)));
    }

    static BigInteger getReverse(BigInteger a, BigInteger mod) {
        Mathematics temp = Mathematics.gcd(a, mod);
        if (temp.r.equals(BigInteger.ONE)) {
            if (temp.x.compareTo(BigInteger.ZERO) > 0) {
                return temp.x;
            }
            while (temp.x.compareTo(BigInteger.ZERO) < 0) {
                temp.x = temp.x.add(mod);
            }
            return temp.x;
        } else {
            return null;
        }
    }

    public static void main(String[] args) {
        Mathematics gcd1 = Mathematics.gcd(new BigInteger(Integer.toString(5)), new BigInteger(Integer.toString(9)));
        System.out.println(gcd1.x);
        System.out.println(gcd1.r);
        System.out.println(gcd1.y);
        System.out.println();

        gcd1 = Mathematics.gcd(new BigInteger(Integer.toString(45)), new BigInteger(Integer.toString(9)));
        System.out.println(gcd1.x);
        System.out.println(gcd1.r);
        System.out.println(gcd1.y);
        System.out.println();


        gcd1 = Mathematics.gcd(new BigInteger(Integer.toString(5248)), new BigInteger(Integer.toString(3248)));
        System.out.println(gcd1.x);
        System.out.println(gcd1.r);
        System.out.println(gcd1.y);
        System.out.println();


        gcd1 = Mathematics.gcd(5248, 3248);
        System.out.println(gcd1.x);
        System.out.println(gcd1.r);
        System.out.println(gcd1.y);
        System.out.println();


        System.out.println(BigInteger.ONE);

        System.out.println("----------");
        System.out.println(Mathematics.getReverse(5, 7));
        System.out.println(Mathematics.getReverse(11, 17));
        System.out.println(Mathematics.getReverse(13, 19));
        System.out.println(Mathematics.getReverse(15, 21));
        System.out.println(Mathematics.getReverse(11, 22));
        System.out.println(Mathematics.getReverse(22, 39));
        System.out.println(Mathematics.getReverse(41, 43));

    }
}
