package org.aka.gameshop.tests;


import org.aka.gameshop.appData.AppData;
import org.aka.gameshop.base.BaseTest;
import org.aka.gameshop.factory.Utils;
import org.testng.Assert;
import org.aka.gameshop.factory.CustomAnnotations.*;
import org.testng.annotations.Test;

public class SignInPageTest extends BaseTest {

    @TestName("Verify if Sign-In link is working")
    @Test(priority = 5)
    public void userNavigationToSignInPageTest() {
//        if (homePage==null)
//            setupBrowser();
        signInPage = homePage.gotoSignInPage();
        boolean signInPageVisible = signInPage.isUserONSignInPage();
        Assert.assertTrue(signInPageVisible, "Navigate to Sign-In page failed...");
    }

    @TestName("Verify that correct title is displayed for Home Page")
    @Test(priority = 10)
    public void signInTitleTest() {
        String signInPageTitle = signInPage.getSignInPageTitle();
        System.out.printf("Home page title is %s", signInPageTitle);
        Utils.printLogs("Home title printed successfully...");
        Assert.assertTrue(signInPageTitle.contains(AppData.SIGN_IN_PAGE_TITLE), "Actual Title: " + signInPageTitle
                + "\nExpected to contain :" + AppData.SIGN_IN_PAGE_TITLE);

    }

    @TestName("Verify that user see error when clicking on Sign-In after only entering email")
    @Test(priority = 15)
    public void userSignInOnlyEmailTest() {
        //signInPage= homePage.gotoSignInPage();
        signInPage.enterEmail(prop.getProperty("username"));
        signInPage.clickSignIn();
        Assert.assertTrue(signInPage.isPassErrorVisible());
    }

    @TestName("Verify that user see error when clicking on Sign-In after only entering password")
    @Test(priority = 20)
    public void userSignInOnlyPasswordTest() {
        //signInPage= homePage.gotoSignInPage();
        signInPage.enterEmail("");
        signInPage.enterPassword(prop.getProperty("password"));
        signInPage.clickSignIn();
        Assert.assertTrue(signInPage.isEmailErrorVisible());

    }

    @TestName("Verify that user see error when trying to Sign-In with short password")
    @Test(priority = 25)
    public void userSignInShortPasswordTest() {
        //homePage.gotoSignInPage();
        signInPage.enterEmail(prop.getProperty("username"));
        signInPage.enterPassword("Test1");
        signInPage.clickSignIn();
        Assert.assertTrue(signInPage.isPassLengthMSGVisible());

    }

    @TestName("Verify that user see Sign-Up message when trying to login with email which doesn't exist")
    @Test(priority = 30)
    public void userSignInUnregisteredEmailTest() {
        //homePage.gotoSignInPage();
        signInPage.enterEmail("exampleshop@bxm.com");
        signInPage.enterPassword("Testdf1");
        signInPage.clickSignIn();
        boolean isMSGVisible = signInPage.isEmailNotExistMSGVisible();
        signInPage.clickPopupOK();
        Assert.assertTrue(isMSGVisible);

    }

    @TestName("Verify that user see Login Failed message when trying to login using wrong password")
    @Test( priority = 35)
    public void userSignInWrongPasswordTest() {
        //homePage.gotoSignInPage();
        signInPage.enterEmail(prop.getProperty("username"));
        signInPage.enterPassword("Testdf1");
        signInPage.clickSignIn();
        boolean isMSGVisible = signInPage.isLoginFailedMSGVisible();
        signInPage.clickPopupOK();
        Assert.assertTrue(isMSGVisible);

    }

    @TestName("Verify that user is logged in successfully with valid credentials")
    @Test(priority = 40)
    public void userSignInCorrectEmailAndPasswordTest() {
        homePage = signInPage.userSignIn(prop.getProperty("username"), prop.getProperty("password"));
        if (homePage.isUserSignedIn())
            System.out.println("User Sign-In successful...");
        else
            System.out.println("User Sign-In failed...");
        Assert.assertTrue(homePage.isUserSignedIn());
    }
}
