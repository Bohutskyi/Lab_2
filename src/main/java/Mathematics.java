import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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

    public static BigInteger[] linearComparison(int a, int b, int n) {
        return linearComparison(new BigInteger(String.valueOf(a)), new BigInteger(String.valueOf(b)), new BigInteger(String.valueOf(n)));
    }

    public static BigInteger[] linearComparison(BigInteger a, BigInteger b, BigInteger n) {
        Mathematics math = Mathematics.gcd(a, n);
        BigInteger[] result = null;
        if (math.r.equals(BigInteger.ONE)) {
            BigInteger temp = getReverse(a, n);
            temp = temp.multiply(b);
            result = new BigInteger[1];
            result[0] = temp;
//            System.out.println("here");
            return result;
        } else {
            BigInteger[] temp = b.divideAndRemainder(math.r);
            if (!temp[1].equals(BigInteger.ZERO)) {
                return null;
            } else {
                result = new BigInteger[math.r.intValue()];
                BigInteger[] x0 = linearComparison(a.divide(math.r), b.divide(math.r), n.divide(math.r));
                result[0] = x0[0];
                BigInteger n1 = n.divide(math.r);
                for (int i = 1; i < math.r.intValue(); i++) {
                    result[i] = result[i - 1].add(n1);
                }
                return result;
            }
        }
    }

    public static Map<Integer, Integer> calculateSystemComparisons(int x1, int x2, int y1, int y2, int m) {
        Map<Integer, Integer> result = new HashMap<Integer, Integer>();
        while (x2 > x1) {
            x2 -= m;
        }
        while (y2 > y1) {
            y2 -= m;
        }
        try {
            for (BigInteger i : linearComparison(x1 - x2, y1 - y2, m)) {
                int b = (y1 - i.intValue() * x1) % m;
                while (b < 0) {
                    b += m;
                }
                result.put(i.intValue() % m, b);
            }
        } catch (NullPointerException e) {

        }
        return result;
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

        System.out.println("-----------------------------");
        System.out.println(Mathematics.linearComparison(5, 13, 21));
        for (BigInteger i : Mathematics.linearComparison(5, 13, 21)) {
            System.out.println(i);
        }
        System.out.println(Mathematics.linearComparison(256, 179, 337));
        for (BigInteger i : Mathematics.linearComparison(256, 179, 337)) {
            System.out.println(i);
        }
        System.out.println(Mathematics.linearComparison(243, 179, 337));
        for (BigInteger i : Mathematics.linearComparison(243, 179, 337)) {
            System.out.println(i);
        }

        System.out.println(Mathematics.linearComparison(1215, 560, 2755));
        for (BigInteger i : Mathematics.linearComparison(1215, 560, 2755)) {
            System.out.println(i);
        }

        System.out.println("*****************************************");
        for (Map.Entry<Integer, Integer> entry : calculateSystemComparisons(1, 5, 6, 4, 49).entrySet()) {
            System.out.println("a = " + entry.getKey() + " b = " + entry.getValue());
        }

        System.out.println("*****************************************");
        for (Map.Entry<Integer, Integer> entry : calculateSystemComparisons(11, 35, 26, 14, 81).entrySet()) {
            System.out.println("a = " + entry.getKey() + " b = " + entry.getValue());
        }

        System.out.println("*****************************************");
        for (Map.Entry<Integer, Integer> entry : calculateSystemComparisons(1, 5, 6, 4, 49).entrySet()) {
            System.out.println("a = " + entry.getKey() + " b = " + entry.getValue());
        }

        System.out.println("*****************************************");
        for (Map.Entry<Integer, Integer> entry : calculateSystemComparisons(1, 5, 6, 4, 49).entrySet()) {
            System.out.println("a = " + entry.getKey() + " b = " + entry.getValue());
        }

    }
}
