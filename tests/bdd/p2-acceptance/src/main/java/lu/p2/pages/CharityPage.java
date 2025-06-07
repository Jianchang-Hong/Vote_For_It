package lu.p2.pages;

import lu.p2.factories.CharityFactory;
import lu.p2.io.Helper;
import lu.p2.models.Charity;
import lu.p2.models.DonateRecord;
import lu.p2.selenium.Bys;
import lu.p2.selenium.Finders;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Component;
import shiver.me.timbers.waiting.Wait;

import static java.lang.Boolean.TRUE;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static shiver.me.timbers.data.random.RandomIntegers.someIntegerBetween;
import static shiver.me.timbers.data.random.RandomStrings.someNumericString;
import static shiver.me.timbers.data.random.RandomStrings.someString;
import static shiver.me.timbers.data.random.RandomThings.someThing;
import static shiver.me.timbers.waiting.Decision.YES;

@Component
public class CharityPage {
    private final Finders finders;
    private final CharityFactory charityFactory;
    private final Helper helper;

    public CharityPage(final Finders finders, final CharityFactory charityFactory, final Helper helper) {
        this.finders = finders;
        this.charityFactory = charityFactory;
        this.helper = helper;
    }

    public Charity createACharity() {
        final Charity charity = charityFactory.create();
        finders.waitByText("a", "Start a\n" + "            Donation Drive");
        finders.clickByText("a", "Start a\n" + "            Donation Drive");
        finders.waitByText("button", "Submit your application");
        finders.setTextById("charity_name", charity.getName());
        finders.setTextById("charity_description", charity.getDescription());
//        finders.setTextById("charity_description", "");
        finders.setTextById("charity_image", charity.getImage());
        finders.setTextById("reg_num", charity.getRegNumber());
        finders.setTextById("bank_acc", charity.getBankAccount());
        finders.setTextById("ird_num", charity.getIrdNumber());
        finders.clickByText("button", "Submit your application");
        return charity;
    }

    public void actOnTheApplication(final Charity charity, final String approve) {
        finders.clickByText("a", "Charities");
        finders.waitByText("h2", "Charity and Donation Application Management");
        final WebElement button = finders.findByText("td", charity.getRegNumber())
                .findElement(Bys.parentClassName("tr", "table-custom-row"))
                .findElement(Bys.text("button", approve));
        button.click();
        finders.waitByClassName("modal-content");
        final WebElement modalBtn = finders.findByClassName("modal-content").findElement(Bys.text("button", approve));
        modalBtn.click();
        finders.waitByText("div", "Charity approved successfully");
    }

    @Wait(waitForTrue = YES)
    public boolean charityIsCreatedSuccessfully(final Charity charity) {
        finders.waitByText("div", "Charity created successfully");
        finders.findByText("strong", charity.getName());
        finders.findByText("span", "Status: Pending");
        assertThat(finders.findByClassName("card-text").getText().contains(charity.getDescription()), is(TRUE));
        finders.findByText("li", charity.getRegNumber());
        finders.findByText("li", charity.getFormattedBankAccount());
        finders.findByText("li", charity.getFormattedIrdNumber());
        return true;
    }

    public DonateRecord donate() {
        finders.waitByClassName("events");
        final WebElement eventWE = helper.getRandomElement(finders.findAllByText("a", " Donate")).findElement(Bys.parentClassName("div", "event-card"));
        final String eventName = eventWE.findElement(Bys.tagName("div")).getText().split("\\n")[0];
        final String themeName = eventWE.findElement(Bys.tagName("a")).getText();
        final DonateRecord donateRecord = new DonateRecord();
        donateRecord.setCharityName(eventName);
        donateRecord.setThemeName(themeName);
        eventWE.findElement(Bys.text("a", "Donate")).click();
        finders.waitByText("h1", "Make a Donation");
        final String amount = someIntegerBetween(1, 1000).toString();
        finders.setTextById("amount", amount);
        finders.setTextById("card_num", someNumericString(16));
        finders.setTextById("expiration_date", someThing("1126", "0526"));
        finders.setTextById("cvv", someNumericString(3));
        final String message = someThing("", someString(0, 255));
        finders.setTextById("message", message);
        finders.clickByText("button", "Donate");
        finders.waitByText("div", "You have donated successfully");
        donateRecord.setAmount(amount);
        donateRecord.setMessage(message);
//        finders.findByText("strong","Receipt date:");
        return donateRecord;
    }

    public void donateSuccessfully(final DonateRecord donateRecord) {
        finders.waitByText("div", "You have donated successfully");
        finders.findByText("div", "You have donated successfully");
    }
}
