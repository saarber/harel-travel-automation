package com.harel.tests;

import com.harel.core.BaseTest;
import com.harel.pages.ContinentPage;
import com.harel.pages.LandingPage;
import com.harel.pages.PassengersPage;
import com.harel.pages.TravelDatesPage;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

public class TravelPolicyE2ETest extends BaseTest {

    @Test
    public void purchaseFlow_shouldOpenPassengersPage_afterSelectingContinentAndDates() {
        // 1. Open
        LandingPage landing = new LandingPage(driver, wait);
        landing.open();

        // 2. Click first purchase
        landing.clickFirstTimePurchase();

        // 3. Select a continent (example: Europe)
        ContinentPage continentPage = new ContinentPage(driver, wait);
        continentPage.chooseContinent("אירופה");

        // 4. Next to date selection
        continentPage.clickNextToTravelDates();

        // 5. Select departure = today+7, return = departure+30
        TravelDatesPage datesPage = new TravelDatesPage(driver, wait);

        LocalDate departure = LocalDate.now(ZoneId.of("Asia/Jerusalem")).plusDays(7);
        LocalDate back = departure.plusDays(30);

        datesPage.setDepartureDate(departure);
        datesPage.setReturnDate(back);

        // 6. Verify total days
        int uiTotalDays = datesPage.getTotalDays();

        int expectedDiff = (int) ChronoUnit.DAYS.between(departure, back); // usually 30
        int expectedInclusive = expectedDiff + 1; // sometimes 31 (inclusive)

        Assert.assertTrue(
                uiTotalDays == expectedDiff || uiTotalDays == expectedInclusive,
                String.format("Total days mismatch. UI=%d expected=%d (diff) or %d (inclusive)", uiTotalDays, expectedDiff, expectedInclusive)
        );

        // 7. Next to passengers
        datesPage.clickNextToPassengers();

        // 8. Verify passengers page opened
        PassengersPage passengersPage = new PassengersPage(driver, wait);
        Assert.assertTrue(passengersPage.isOpen(), "Passengers page did not open");
    }
}
