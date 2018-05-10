package ee.bcs.valiit.model;

import ee.bcs.valiit.services.OmniMeterService;

import java.sql.ResultSet;
import java.sql.SQLException;

public class VisitCounter {

    private static final String SITE_CODE = "VALIIT";

    private static int pageVisitCount = 0;

    public static String addVisit() {
        pageVisitCount++;
        return String.valueOf(pageVisitCount);
    }

    private static boolean visitRecorExists () throws SQLException {
        String sql = "SELECT * from visits where site_code = '" + SITE_CODE +"'";
        ResultSet result = OmniMeterService.executeSql(sql);

        if (result == null || !result.next()) {
            return false;
        }
        return true;
    }

    private static void addVisitRecord () throws SQLException {

        String sql = String.format("INSERT INTO visits(site_code, count) VALUES (%s, 1)", SITE_CODE);

        if (visitRecorExists()) {
            updateVisitCounter();
        } else {
            addVisitRecord();
        }
    }

    private static void updateVisitCounter() {

        String sql =String.format("UPDATE visits SET count = count+1 WHERE site_code ='%s'", SITE_CODE);
        OmniMeterService.executeSql(sql);
    }

    private static void getVisitCount () throws SQLException {
        String sql = String.format ("SELECT * FROM visits WHERE site_code = '%s'", SITE_CODE);
        ResultSet result = OmniMeterService.executeSql(sql);
    }
}
