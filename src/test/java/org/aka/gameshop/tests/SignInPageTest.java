package org.aka.gameshop.tests;

import org.aka.gameshop.appData.AppData;
import org.aka.gameshop.apppages.SignInPage;
import org.aka.gameshop.base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SignInPageTest extends BaseTest {

    @Test( testName="Verify if Sign-In link is working",priority = 1)
    public void userNavigationToSignInPageTest(){
        if (homePage==null)
            setupBrowser();
        signInPage= homePage.gotoSignInPage();
        if (signInPage.isSignInPageVisible())
            System.out.println("Navigated to Sign-In page successfully...");
        else
            System.out.println("Navigation to Sign-In failed...");
        Assert.assertTrue(signInPage.isSignInPageVisible());
    }

    @Test(testName="Verify that user see error when clicking on Sign-In after only entering email",
            priority = 2)
    public void userSignInOnlyEmailTest(){
        //signInPage= homePage.gotoSignInPage();
        signInPage.enterEmail(prop.getProperty("username"));
        signInPage.clickSignIn();
        Assert.assertTrue(signInPage.isPassErrorVisible());
    }

    @Test(testName="Verify that user see error when clicking on Sign-In after only entering password",
            priority = 3)
    public void userSignInOnlyPasswordTest(){
        //signInPage= homePage.gotoSignInPage();
            signInPage.enterEmail("");
            signInPage.enterPassword(prop.getProperty("password"));
            signInPage.clickSignIn();
            Assert.assertTrue(signInPage.isEmailErrorVisible());

    }

    @Test(testName="Verify that user see error when trying to Sign-In with short password",
            priority = 4)
    public void userSignInShortPasswordTest(){
        //homePage.gotoSignInPage();
        signInPage.enterEmail(prop.getProperty("username"));
        signInPage.enterPassword("Test1");
        signInPage.clickSignIn();
            Assert.assertTrue(signInPage.isPassLengthMSGVisible());

    }

    @Test(testName="Verify that user see Sign-Up message when trying to login with email which doesn't exist",
            priority = 5)
    public void userSignInUnregisteredEmailTest(){
        //homePage.gotoSignInPage();
        signInPage.enterEmail("exampleshop@bxm.com");
        signInPage.enterPassword("Testdf1");
        signInPage.clickSignIn();
        boolean isMSGVisible = signInPage.isEmailNotExistMSGVisible();
        signInPage.clickPopupOK();
        Assert.assertTrue(isMSGVisible);

    }

    @Test(testName="Verify that user see Login Failed message when trying to login using wrong password",
            priority = 6)
    public void userSignInWrongPasswordTest(){
        //homePage.gotoSignInPage();
        signInPage.enterEmail(prop.getProperty("username"));
        signInPage.enterPassword("Testdf1");
        signInPage.clickSignIn();
        boolean isMSGVisible = signInPage.isLoginFailedMSGVisible();
        signInPage.clickPopupOK();
        Assert.assertTrue(isMSGVisible);

    }

    @Test(testName="Verify that user is logged in successfully with valid credentials",
            priority = 7)
    public void userSignInCorrectEmailAndPasswordTest(){
        //homePage.gotoSignInPage();
        homePage = signInPage.userSignIn(prop.getProperty("username"),prop.getProperty("password"));
        if (homePage.isUserSignedIn())
            System.out.println("User Sign-In successful...");
        else
            System.out.println("User Sign-In failed...");
        Assert.assertTrue(homePage.isUserSignedIn());
    }




}
