package com.team3.project.Tests.LogicTests;

import com.team3.project.Classes.Profile;
import com.team3.project.Tests.BaseClassesForTests.BaseLogicTest;
import com.team3.project.service.AccountService;
import com.team3.project.service.WebSessionService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class ProfileTests extends BaseLogicTest {
    private static PrintWriter pw;
    private static Date date;
    private static SimpleDateFormat formatter;
    private boolean pass = true;

    @Spy
    private AccountService accountService;

    @Spy
    private WebSessionService webSessionService;

    @BeforeAll
    public static void setupTest() {
        setup();
        pw = printWriter;
        formatter = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
        date = new Date();
    }

    @BeforeEach
    @AfterEach
    public void emptyDB() {
        BaseLogicTest.wipeDb(true);
    }

    @AfterAll
    public static void closeWriter() {
        pw.close();
        tearDown();
    }

    @Test
        /*  Test ID: Logic.T3
         *  Author: Henry Lewis Freyschmidt
         *  Revisited: Henry van Rooyen, 27.6
         *  Zweck: Profile erstellen P1.B1
         */
    void createProfile() throws Exception {
        // Arrange
        pw.append("Logik-Test-createProfile\nTest ID: Logic.T3\n" + "Date: ").append(formatter.format(date)).append(String.valueOf('\n'));
        Profile profile = new Profile(0, "Dave", "dave@gmail.com", "Manager", "slow thinker", null, 1);
        Profile profileFail = new Profile(0, "Exception", "fail@gmail.com", "Manager", "slow thinker", null, 1);

        // Act
        accountService.register("Exception", "fail@gmail.com", "123");
        String sessionIDFail = webSessionService.getSessionID("fail@gmail.com");

        // Functional case
        accountService.register("Dave", "dave@gmail.com", "123");
        String sessionID = webSessionService.getSessionID("dave@gmail.com");
        accountService.savePublicData(sessionID, profile);

        // Assert
        assertThrows(Exception.class, () -> accountService.getProfileByID(null));
        assertThrows(Exception.class, () -> accountService.getProfileByID("-1"));
        assertThrows(Exception.class, () -> accountService.savePublicData(null, profileFail));
        assertThrows(Exception.class, () -> accountService.savePublicData("Fake-sessionID", profileFail));

        profileFail.setUname(null);
        assertThrows(Exception.class, () -> accountService.savePublicData(sessionIDFail, profileFail));

        profileFail.setUname("Dave");
        profileFail.setWorkDesc(null);
        assertThrows(Exception.class, () -> accountService.savePublicData(sessionIDFail, profileFail));

        profileFail.setWorkDesc("working");
        profileFail.setPrivatDesc(null);
        assertThrows(Exception.class, () -> accountService.savePublicData(sessionIDFail, profileFail));

        try {
        assertDoesNotThrow(() -> assertNotNull(accountService.getProfileByID(sessionID)));
        }catch(AssertionError | Exception e){
            pw.append("Fail: existent User-Profile not found\n");
            pass = false;
            throw new AssertionError(e);
        }

        pw.append(String.format("pass: %b", pass));
    }

    @Test
        /*  Test ID: Logic.T4
         *  Author: Henry Lewis Freyschmidt
         *  Revisited: Henry van Rooyen, 27.6
         *  Zweck: Profile aktualisieren P2.B1
         */
    void updateProfile() throws Exception{
        // Arrange
        pw.append("Logik-Test-updateUserStory\nTest ID: Logic.T4\n" + "Date: ").append(formatter.format(date)).append(String.valueOf('\n'));
        Profile profile = new Profile(0, "Dave", "dave@gmx.de", "netter Dave", "arbeitender Dave", null, 1);
        final String newDescription = "böse";

        // Act
        accountService.register("Dave", "dave@gmx.de", "passw");
        String sessionID = webSessionService.getSessionID("dave@gmx.de");
        accountService.savePublicData(sessionID, profile);
        profile.setPrivatDesc(newDescription);
        accountService.savePublicData(sessionID, profile);

        // Assert
        try {
        assertDoesNotThrow(() -> assertEquals(newDescription, accountService.getProfileByEmail("dave@gmx.de").getPrivatDesc()));
        }catch (AssertionError | Exception e){
            pw.append("Fail: User-Profile was not updated\n");
            pass = false;
            throw new AssertionError(e);
        }
        pw.append(String.format("pass = %b", pass));
    }

    @Test
        /*  Test ID: Logic.T18
         *  Author: Henry Lewis Freyschmidt
         *  Revisited: Henry van Rooyen, 27.6
         *  Zweck: alle Profile ausgeben P4.B1
         */
    void getAllProfiles() throws Exception{
        // Arrange
        pw.append("Logik-Test-getAllProfiles\nTest ID: Logic.T18\n" + "Date: ").append(formatter.format(date)).append(String.valueOf('\n'));

        // Act
        accountService.register("DaveT18", "daveT18@gmail.com", "T18");
        accountService.register("Dave1T18", "dave1T18@gmail.com", "T18");
        accountService.register("Dave2T18", "dave2T18@gmail.com", "T18");

        // Assert
        try {
            List<Profile> profiles = accountService.getAllProfiles();
            assertAll(
                    () -> assertEquals("DaveT18", profiles.get(0).getUname()),
                    () -> assertEquals("Dave1T18", profiles.get(1).getUname()),
                    () -> assertEquals("Dave2T18", profiles.get(2).getUname())
            );
        }catch (AssertionError e){
        pass = false;
        pw.append("Fail: did not return all Users\n");
        throw new AssertionError(e);
    }
        pw.append(String.format("pass = %b", pass));
    }
}