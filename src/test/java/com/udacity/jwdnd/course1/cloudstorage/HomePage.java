package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage {
    @FindBy(id="logout-button")
    private WebElement logoutButton;

    @FindBy(id="nav-notes-tab")
    private WebElement noteTabButton;
    @FindBy(id="add-note-button")
    private WebElement addNoteButton;
    @FindBy(id="note-title")
    private WebElement noteTitleField;
    @FindBy(id="note-description")
    private WebElement noteDescriptionField;
    @FindBy(id="save-note-button")
    private WebElement saveNoteButton;
    @FindBy(id="submitted-note-title")
    private WebElement submittedNoteTitle;
    @FindBy(id="submitted-note-description")
    private WebElement submittedNoteDescription;
    @FindBy(className = "note-row")
    private WebElement noteRow;

    @FindBy(id="nav-credentials-tab")
    private WebElement credentialTabButton;
    @FindBy(id="add-credential-button")
    private WebElement addCredentialButton;
    @FindBy(id="credential-url")
    private WebElement credentialUrlField;
    @FindBy(id="credential-username")
    private WebElement credentialUsernameField;
    @FindBy(id="credential-password")
    private WebElement credentialPasswordField;
    @FindBy(id="save-credential-button")
    private WebElement saveCredentialButton;
    @FindBy(id="submitted-credential-url")
    private WebElement submittedCredentialUrl;
    @FindBy(id="submitted-credential-username")
    private WebElement submittedCredentialUsername;
    @FindBy(id="submitted-credential-password")
    private WebElement submittedCredentialPassword;
    @FindBy(className="credential-row")
    private WebElement credentialRow;

    @FindBy(className="btn-success")
    private WebElement editButton;
    @FindBy(className = "btn-danger")
    private WebElement deleteButton;

    public HomePage(WebDriver webDriver){
        PageFactory.initElements(webDriver, this);
    }

    private void waitForElement(WebDriver driver, WebElement element) throws Error{
        new WebDriverWait(driver, 20).until(ExpectedConditions.visibilityOf(element));
    }

    public void createNote(WebDriver driver, String noteTitle, String noteDescription){
        viewNote(driver);
        waitForElement(driver, addNoteButton);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", addNoteButton);
        saveNote(driver, noteTitle, noteDescription);
    }

    public Note getFirstNote(WebDriver driver){
        viewNote(driver);
        waitForElement(driver, submittedNoteTitle);
        Note result = new Note();
        result.setNoteTitle(submittedNoteTitle.getText());
        result.setNoteDescription(submittedNoteDescription.getText());
        return result;
    }
    public void saveNote(WebDriver driver, String noteTitle, String noteDescription){
        waitForElement(driver, noteTitleField);
        ((JavascriptExecutor) driver).executeScript("arguments[0].value='" + noteTitle + "';", noteTitleField);
        waitForElement(driver, noteDescriptionField);
        ((JavascriptExecutor) driver).executeScript("arguments[0].value='" + noteDescription + "';", noteDescriptionField);
        waitForElement(driver, saveNoteButton);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", saveNoteButton);
    }
    public void viewNote(WebDriver driver){
        waitForElement(driver, noteTabButton);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", noteTabButton);
    }

    public void editNote(WebDriver driver, String noteTitle, String noteDescription){
        viewNote(driver);
        waitForElement(driver, editButton);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", editButton);
        saveNote(driver, noteTitle, noteDescription);
    }

    public boolean deleteNote(WebDriver driver) throws Exception{
        viewNote(driver);
        waitForElement(driver, deleteButton);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", deleteButton);
        viewNote(driver);
        try {
            waitForElement(driver, noteRow);
        }catch(Exception e){
            return true;
        }
        return false;
    }

    public void openCredentialTab(WebDriver driver){
        waitForElement(driver, credentialTabButton);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", credentialTabButton);
    }

    public void createCredential(WebDriver driver, String url, String username, String password){
        openCredentialTab(driver);
        waitForElement(driver, addCredentialButton);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", addCredentialButton);
        saveCredential(driver, url, username, password);
    }

    public void saveCredential(WebDriver driver, String url, String username, String password){
        waitForElement(driver, credentialUrlField);
        ((JavascriptExecutor) driver).executeScript("arguments[0].value='" + url + "';", credentialUrlField);
        waitForElement(driver, credentialUsernameField);
        ((JavascriptExecutor) driver).executeScript("arguments[0].value='" + username + "';", credentialUsernameField);
        waitForElement(driver, credentialPasswordField);
        ((JavascriptExecutor) driver).executeScript("arguments[0].value='" + password + "';", credentialPasswordField);
        waitForElement(driver, saveCredentialButton);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", saveCredentialButton);
    }

    public Credential getFirstCredential(WebDriver driver){
        openCredentialTab(driver);
        waitForElement(driver, submittedCredentialUrl);
        Credential result = new Credential();
        result.setUrl(submittedCredentialUrl.getText());
        result.setUsername(submittedCredentialUsername.getText());
        result.setPassword(submittedCredentialPassword.getText());
        return result;
    }

    public void viewCredential(WebDriver driver){
        openCredentialTab(driver);
        waitForElement(driver, editButton);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", editButton);
    }

    public boolean deleteCredential(WebDriver driver) throws Exception{
        openCredentialTab(driver);
        waitForElement(driver, deleteButton);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", deleteButton);
        openCredentialTab(driver);
        try{
            waitForElement(driver, credentialRow);
        }catch (Exception e){
            return true;
        }
        return false;
    }

    public void logout(){
        logoutButton.click();
    }

}
