package lu.p2.steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lu.p2.factories.UserFactory;
import lu.p2.holders.CharityHolder;
import lu.p2.holders.DonateRecordHolder;
import lu.p2.holders.ThemeApplicationHolder;
import lu.p2.holders.UserHolder;
import lu.p2.pages.CharityPage;
import lu.p2.pages.DashboardPage;
import lu.p2.pages.HomePage;
import lu.p2.pages.LoginPage;
import lu.p2.pages.ThemePage;

public class CharitySteps {
    private final CharityPage charityPage;
    private final HomePage homePage;
    private final LoginPage loginPage;
    private final ThemePage themePage;
    private final DashboardPage dashboardPage;
    private final UserFactory userFactory;
    private final UserHolder userHolder;
    private final ThemeApplicationHolder themeApplicationHolder;
    private final CharityHolder charityHolder;
    private final DonateRecordHolder donateRecordHolder;

    public CharitySteps(final CharityPage charityPage, final HomePage homePage, final LoginPage loginPage,
                        final ThemePage themePage, final DashboardPage dashboardPage, final UserFactory userFactory, final UserHolder userHolder,
                        final ThemeApplicationHolder themeApplicationHolder, final CharityHolder charityHolder, final DonateRecordHolder donateRecordHolder) {
        this.charityPage = charityPage;
        this.homePage = homePage;
        this.loginPage = loginPage;
        this.themePage = themePage;
        this.dashboardPage = dashboardPage;
        this.userFactory = userFactory;
        this.userHolder = userHolder;
        this.themeApplicationHolder = themeApplicationHolder;
        this.charityHolder = charityHolder;
        this.donateRecordHolder = donateRecordHolder;
    }

    @When("I create a charity")
    public void iCreateACharity() {
        themePage.gotoMyTheme(themeApplicationHolder.get());
        charityHolder.set(charityPage.createACharity());
    }

    @And("the charity is approved by other admins")
    public void theCharityIsApprovedByOtherAdmins() {
        homePage.logout();
        loginPage.login(userFactory.getAnAdminUser());
        charityPage.actOnTheApplication(charityHolder.get(), "Approve");
        homePage.logout();
        loginPage.login(userHolder.get());
    }


    @Then("I can see donation button on homepage")
    public void iCanSeeDonationButtonOnHomepage() {
        homePage.clickDonation(charityHolder.get());
    }

    @And("the charity is created successfully")
    public void theCharityIsCreatedSuccessfully() {
        charityPage.charityIsCreatedSuccessfully(charityHolder.get());
    }

    @When("I donate")
    public void iDonate() {
        donateRecordHolder.set(charityPage.donate());
    }

    @Then("my donation is succeed")
    public void myDonationIsSucceed() {
        charityPage.donateSuccessfully(donateRecordHolder.get());
    }

    @And("I can see my record under dashboard")
    public void iCanSeeMyRecordUnderDashboard() {
        homePage.goToMyDashboardPage();
        dashboardPage.checkDonation(donateRecordHolder.get());
    }
}

