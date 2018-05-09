package ee.bcs.valiit.rest;

public class VisitCounter {

    private static int pageVisitCount = 0;

    public static String addVisit() {
        pageVisitCount++;

        return String.valueOf(pageVisitCount);
    }
}
