package org.aka.gameshop.tests;


import org.aka.gameshop.appData.AppData;
import org.aka.gameshop.base.BaseTest;
import org.aka.gameshop.factory.Utils;
import org.testng.Assert;
import org.aka.gameshop.factory.CustomAnnotations.*;
import org.testng.annotations.Test;

import static org.aka.gameshop.factory.Utils.printLogs;

public class SignInPageTest extends BaseTest {

    @TestName("Verify if Sign-In link is working")
    @Test(priority = 5)
    public void userNavigationToSignInPageTest() {
        signInPage = homePage.gotoSignInPage();
        boolean testPass = signInPage.isUserONSignInPage();
        if (testPass)
            printLogs("Navigate to Sign-In page successful...");
        else
            printLogs("Navigate to Sign-In page failed...",true);

        Assert.assertTrue(testPass);
    }

    @TestName("Verify that correct title is displayed for Home Page")
    @Test(priority = 10)
    public void signInTitleTest() {
        String signInPageTitle = signInPage.getSignInPageTitle();
        printLogs("Sign In page title is "+ signInPageTitle);

        boolean testPass = signInPageTitle.contains(AppData.SIGN_IN_PAGE_TITLE);

        if(testPass)
            printLogs("Home Page Title " + signInPageTitle+" is correct.");
        else
            printLogs("Sign-In Page title is not correct Actual Title: " + signInPageTitle
                    + "\nExpected to contain :" + AppData.SIGN_IN_PAGE_TITLE,true);

        Assert.assertTrue(testPass);
    }

    @TestName("Verify that user see error when clicking on Sign-In after only entering email")
    @Test(priority = 15)
    public void userSignInOnlyEmailTest() {
        //signInPage= homePage.gotoSignInPage();
        signInPage.enterEmail(prop.getProperty("username"));
        printLogs("Username : "+ prop.getProperty("username")+" entered..");
        signInPage.clickSignIn();
        printLogs("Clicked on Sign-In button successfully..");
        boolean testPass = signInPage.isPassErrorVisible();

        if(testPass)
            printLogs("Expected message displayed to enter password..");
        else
            printLogs("Message not displayed to enter password..");

        Assert.assertTrue(testPass);
    }

    @TestName("Verify that user see error when clicking on Sign-In after only entering password")
    @Test(priority = 20)
    public void userSignInOnlyPasswordTest() {
        //signInPage= homePage.gotoSignInPage();
        signInPage.enterEmail("");
        printLogs("Deleted username from field..");
        signInPage.enterPassword(prop.getProperty("password"));
        printLogs("Password : "+ prop.getProperty("password")+" entered..");
        signInPage.clickSignIn();
        printLogs("Clicked on Sign-In button successfully..");
        boolean testPass = signInPage.isEmailErrorVisible();
        if(testPass)
            printLogs("Expected message displayed to enter Username..");
        else
            printLogs("Message not displayed to enter Username..");

        Assert.assertTrue(testPass);

    }

    @TestName("Verify that user see error when trying to Sign-In with short password")
    @Test(priority = 25)
    public void userSignInShortPasswordTest() {
        //homePage.gotoSignInPage();
        signInPage.enterEmail(prop.getProperty("username"));
        printLogs("Username : "+ prop.getProperty("username")+" entered..");
        signInPage.enterPassword("Test1");
        printLogs("Short password 'Test1' entered..");
        signInPage.clickSignIn();
        printLogs("Clicked on Sign-In button successfully..");
        boolean testPass = signInPage.isPassLengthMSGVisible();
        if(testPass)
            printLogs("Expected message displayed to enter Username..");
        else
            printLogs("Message not displayed to enter Username..");

        Assert.assertTrue(testPass);

    }

    @TestName("Verify that user see Sign-Up message when trying to login with email which doesn't exist")
    @Test(priority = 30)
    public void userSignInUnregisteredEmailTest() {
        //homePage.gotoSignInPage();
        signInPage.enterEmail("exampleshop@bxm.com");
        printLogs("Email exampleshop@bxm.com entered..");
        signInPage.enterPassword("Testdf1");
        printLogs("Passwrod Testdf1 entered..");
        signInPage.clickSignIn();
        printLogs("Clicked on Sign-In button successfully..");
        boolean testPass = signInPage.isEmailNotExistMSGVisible();
        signInPage.clickPopupOK();
        if(testPass)
            printLogs("Sign Up message displayed to user..");
        else
            printLogs("Sign Up message not displayed to user..");
        Assert.assertTrue(testPass);

    }

    @TestName("Verify that user see Login Failed message when trying to login using wrong password")
    @Test( priority = 35)
    public void userSignInWrongPasswordTest() {
        //homePage.gotoSignInPage();
        signInPage.enterEmail(prop.getProperty("username"));
        printLogs("Email "+prop.getProperty("username")+" entered..");
        signInPage.enterPassword("Testdf1");
        printLogs("Passwrod Testdf1 entered..");
        signInPage.clickSignIn();
        printLogs("Clicked on Sign-In button successfully..");
        boolean testPass = signInPage.isLoginFailedMSGVisible();
        signInPage.clickPopupOK();
        if(testPass)
            printLogs("Login failed message displayed to user..");
        else
            printLogs("Login failed message not displayed to user..");
        Assert.assertTrue(testPass);
    }

    @TestName("Verify that user is logged in successfully with valid credentials")
    @Test(priority = 40)
    public void userSignInCorrectEmailAndPasswordTest() {
        printLogs("Signing in with Username: "+prop.getProperty("username")+ " and Password: "+prop.getProperty("password"));
        homePage = signInPage.userSignIn(prop.getProperty("username"), prop.getProperty("password"));
        boolean testPass = homePage.isUserSignedIn();
        if(testPass)
            printLogs("User Sign-In successful..");
        else
            printLogs("User Sign-In failed..");
        Assert.assertTrue(testPass);
    }
}
