package cz.czechitas.automation;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class MujUzasnyTest extends TestRunner {


    @Test
    void UserRegistrationLoginProcess() {
        browser.loginSection.clickLoginMenuLink();
        browser.loginSection.clickRegisterButton();

        var jmeno = browser.generateRandomName(6);
        var prijmeni = browser.generateRandomName(8);
        var email = browser.generateRandomName(5) + "@gmail.com";

        browser.registerSection.insertFirstAndLastName(jmeno, prijmeni);
        browser.registerSection.insertEmail(email);
        browser.registerSection.insertPassword("123abc456D");
        browser.registerSection.insertPasswordConfirmation("123abc456D");
        browser.registerSection.clickRegisterButton();

        browser.loginSection.logout();

        browser.loginSection.clickLoginMenuLink();
        browser.loginSection.insertEmail(email);
        browser.loginSection.insertPassword("123abc456D");
        browser.loginSection.clickLoginButton();

        asserter.loginSection.checkUserIsLoggedIn(prijmeni);
    }

    @Test
    void CreateBookingAsATeacherCamp() {
        browser.headerMenu.goToKindergartenAndSchoolSection();
        browser.orderSection.insertICO("12345678");

        browser.waitFor(8);

        var odberatel = browser.generateRandomName(8);

        browser.orderDetailSection.insertClient(odberatel);
        browser.orderDetailSection.insertFullAddress("Za Obloukem 8");
        browser.orderDetailSection.insertSubstitute("Filip Omáčka");
        browser.orderDetailSection.insertContactPersonNameAndSurname("Abraham", "Lincoln");
        browser.orderDetailSection.insertContactPersonTelephone("777666555");
        browser.orderDetailSection.insertContactPersonEmail("abraham.lincoln@gmail.com");
        browser.orderDetailSection.insertStartDate("13.05.2026");
        browser.orderDetailSection.insertEndDate("20.05.2026");

        browser.waitFor(5);

        browser.orderSection.selectSuburbanCampOption();

        browser.orderDetailSection.selectAfternoonSuburbanCampVariant();
        browser.orderDetailSection.insertChildrenCountToSuburbanCamp(18);
        browser.orderDetailSection.insertInAgeToSuburbanCamp(8);
        browser.orderDetailSection.insertAdultsCountToSuburbanCamp(3);
        browser.orderDetailSection.saveSuburbanCampOrder();

        browser.waitFor(10);

        browser.loginSection.clickLoginMenuLink();
        browser.loginSection.insertEmail("da-app.master@czechitas.cz");
        browser.loginSection.insertPassword("AppRoot123");
        browser.loginSection.clickLoginButton();

        browser.internalMenu.goToOrdersSection();
        browser.orderSection.search(odberatel);

        asserter.orderSection.checkNumberOfOrders(1);
    }

    @Test
    void CreateBookingAsATeacherSchoolInNature() {
        browser.headerMenu.goToKindergartenAndSchoolSection();
        browser.orderSection.insertICO("87654321");

        browser.waitFor(8);

        var odberatel2 = browser.generateRandomName(8);

        browser.orderDetailSection.insertClient(odberatel2);
        browser.orderDetailSection.insertFullAddress("U Lomu 5");
        browser.orderDetailSection.insertSubstitute("Jana Oslnivá");
        browser.orderDetailSection.insertContactPersonNameAndSurname("Winston", "Churchill");
        browser.orderDetailSection.insertContactPersonTelephone("666444333");
        browser.orderDetailSection.insertContactPersonEmail("winston.churchill@gmail.com");
        browser.orderDetailSection.insertStartDate("13.06.2026");
        browser.orderDetailSection.insertEndDate("20.06.2026");

        browser.waitFor(5);

        browser.orderSection.selectSchoolInNatureOption();

        browser.orderDetailSection.insertChildrenCountToSchoolInNature(23);
        browser.orderDetailSection.insertInAgeToSchoolInNature(12);
        browser.orderDetailSection.insertAdultsCountToSchoolInNature(3);
        browser.orderDetailSection.insertStartTime("09:00");
        browser.orderDetailSection.selectBreakfastStartToSchoolInNature();
        browser.orderDetailSection.insertEndTime("18:00");
        browser.orderDetailSection.selectDinnerEndToSchoolInNature();
        browser.orderDetailSection.saveSchoolInNatureOrder();

        browser.waitFor(10);

        browser.loginSection.clickLoginMenuLink();
        browser.loginSection.insertEmail("da-app.master@czechitas.cz");
        browser.loginSection.insertPassword("AppRoot123");
        browser.loginSection.clickLoginButton();

        browser.internalMenu.goToOrdersSection();
        browser.orderSection.search(odberatel2);

        asserter.orderSection.checkNumberOfOrders(1);
    }

    @Test
    void CreateApplicationAsAParent() {
        browser.loginSection.clickLoginMenuLink();
        browser.loginSection.insertEmail("olivia.ospala@gmail.com");
        browser.loginSection.insertPassword("Rodic123");
        browser.loginSection.clickLoginButton();

        browser.headerMenu.goToApplicationsSection();
        browser.applicationSection.clickCreateNewApplicationButton();

        browser.applicationSection.selectProgrammingSection();
        browser.applicationSection.clickCreatePythonApplicationButton();

        browser.applicationDetailsSection.selectTerm("04.06. - 08.06.2025");
        browser.applicationDetailsSection.insertStudentFirstName("Olga");

        var OlgaPrijmeni = browser.generateRandomName(8);
        var OlgaPrijmeniCapitalized = OlgaPrijmeni.substring(0, 1).toUpperCase() + OlgaPrijmeni.substring(1);

        browser.applicationDetailsSection.insertStudentLastName(OlgaPrijmeni);
        browser.applicationDetailsSection.insertBirthdate("31.07.2017");

        browser.applicationDetailsSection.selectCashPaymentMethod();
        browser.applicationDetailsSection.clickHealthDisabilityCheckbox();
        browser.applicationDetailsSection.insertHealthDisabilityNote("Alergie na ryby");
        browser.applicationDetailsSection.insertNote("Dorazí o den později");
        browser.applicationDetailsSection.clickAcceptTermsCheckbox();
        browser.applicationDetailsSection.clickCreateApplicationButton();

        browser.waitFor(5);

        browser.headerMenu.goToApplicationsSection();
        browser.applicationSection.search(OlgaPrijmeni);
        asserter.applicationSection.checkNumberOfApplications(1);

        browser.applicationSection.openFirstApplicationDetailsPage();

        asserter.applicationDetailSection.checkPaymentMethod("Hotově");
        asserter.applicationDetailSection.checkFirstName("Olga");
        asserter.applicationDetailSection.checkLastName(OlgaPrijmeniCapitalized);
        asserter.applicationDetailSection.checkDateOfBirth("31.07.2017");
        asserter.applicationDetailSection.checkHealthDisabilityNote("Alergie na ryby");
        asserter.applicationDetailSection.checkNote("Dorazí o den později");
    }
}