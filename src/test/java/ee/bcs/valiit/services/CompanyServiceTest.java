package ee.bcs.valiit.services;

import ee.bcs.valiit.model.User;
import org.junit.Test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static junit.framework.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class CompanyServiceTest {

    @Test
    public void test() throws SQLException {
        ResultSet result = CompanyService.executeSql("select * from company");
        assertNotNull(result);
        assertTrue(result.next());
        int id = result.getInt("id");
        assertTrue(id > 0);
        String name = result.getString("name");
        assertTrue(name.length() > 0);

    }

}
