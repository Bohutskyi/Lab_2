public class TextRecognizer {

    private static final double EPSILON = 0.005;

    public static boolean isRecognized(String text) {
        Index index = new Index(text);
        if (Math.abs(index.calculateI() - 0.055) < EPSILON) {
//            System.out.println("i: " + index.calculateI());
//            System.out.println(text);
            return true;
        } else {
            return false;
        }
    }

}
