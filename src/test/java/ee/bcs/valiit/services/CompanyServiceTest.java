package ee.bcs.valiit.services;

import ee.bcs.valiit.model.SendEmail;
import org.junit.Test;

public class CompanyServiceTest {
/*
    @Test
    public void test() throws SQLException {
        ResultSet result = CompanyService.executeSql("select * from company");
        assertNotNull(result);
        assertTrue(result.next());
        int id = result.getInt("id");
        assertTrue(id > 0);
        String name = result.getString("name");
        assertTrue(name.length() > 0);

    }*/

    @Test
    public void testEmailSending() {
        SendEmail sm = new SendEmail();
        sm.sendMail();
    }

}
