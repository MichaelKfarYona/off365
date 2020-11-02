package pages;

import java.util.List;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;

public class Forms {
	public enum FormType {
		CHOICE,
		TEXT,
		RATING,
		DATE
		} 
	WebDriver driver;
	private WebElement btnCreateANewForm, linkFormsMainFormsPage = null;
	private WebElement btnAddNew,newFormTitle,searchBoxField = null;
	private WebElement btnChoice, btnText, btnRating, btnDate;
	boolean warningTitle;
	private WebElement txtQuestion, txtOption, btnPachAshpaa, addNewOption, addNewQuestion, newFormName= null;
	
	public int getRandom() {Random rand = new Random(); 
	int value = rand.nextInt(100000);
	return value;}
	
	public Forms(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
	public void createNewFormEnteranceWindow(String formName) {
		try {
			List<WebElement> firstPageList = driver.findElements(By.xpath("//div[@class='button-content' and contains(text(), 'Create a new form')]"));
			firstPageList.get(0).click();
	/*
		btnCreateANewForm = driver.findElement(By.xpath("//div[@class='button-content' and contains(text(), 'Create a new form')]"));
		btnCreateANewForm.click();
	*/
		}
		catch(Exception exception) {System.out.println("There is no 'Create a new form' window . Check createNewFormEnteranceWindow method!");}
		
		getFormTitle(formName);
	}
	
	public void closeNewFormEnteranceWindow() {
		try {
			List<WebElement> closeSign = driver.findElements(By.xpath("//div[@class='button-content']/i"));
			closeSign.get(0).click();
	/*
		btnCreateANewForm = driver.findElement(By.xpath("//div[@class='button-content' and contains(text(), 'Create a new form')]"));
		btnCreateANewForm.click();
	*/
		}
		catch(Exception exception) {System.out.println("There is no 'Create a new form' window . Check createNewFormEnteranceWindow method!");}
		
		
	}
	
	public void clickByNewQuiz() {
		WebElement btnNewQuiz = driver.findElement(By.xpath("//button[@title='New Quiz']"));
		btnNewQuiz.click();
	}
	
	private void getFormTitle(String formTitle) {
		newFormName = driver.findElement(By.xpath("//div[@class='office-form-title heading-1']"));
		newFormName.click(); 
		newFormTitle = driver.findElement(By.xpath("//textarea[@aria-label='Form title']"));
		newFormTitle.click();
		newFormTitle.clear();
		newFormTitle.sendKeys(formTitle+getRandom());
	}

	/* 
	 * Searching by forms name
	 * */
	public boolean searchFormByName(String formName) {
		searchBoxField = driver.findElement(By.xpath("//input[@aria-label='Search box']"));
		searchBoxField.click();
		searchBoxField.sendKeys(formName);
		// vozmoznaya oshibka v xpath!!!
		warningTitle = driver.findElement(By.xpath("//div[@class='fl-title searchable middle-size']")).isDisplayed();
																
		if(warningTitle) {return true;}
		return false;
	}

	public void addNewForm(FormType type) {
		btnAddNew = driver.findElement(By.xpath("//*[@id='form-designer']//div[2]/*/button/*[@class='button-content']"));
		btnAddNew.click();
		switch(type) { case CHOICE: btnChoice = driver.findElement(By.xpath("//button[@aria-label='Choice']")); btnChoice.click(); break;
		case TEXT: btnChoice = driver.findElement(By.xpath("//button[@aria-label='Text']")); btnText.click(); break;
		case RATING: btnChoice = driver.findElement(By.xpath("//button[@aria-label='Rating']")); btnRating.click(); break;
		case DATE: btnChoice = driver.findElement(By.xpath("//button[@aria-label='Date']")); btnDate.click(); break;
		}
	}
/***************************************************************************************************************************
**************************************************** Filling out the form **************************************************
****************************************************************************************************************************/
	public void fillTheChoiceForm(String question, int addOption) throws InterruptedException {
		txtQuestion = driver.findElement(By.xpath("//textarea[@aria-label='Question title']"));
		txtQuestion.clear();
		txtQuestion.sendKeys(question);
		int myValue = 0;
		for(myValue = 0; myValue < addOption; myValue++) {clickAddNewOption();}
		List<WebElement> optionList = driver.findElements(By.xpath("//input[@aria-label='Choice Option Text']"));
		for(WebElement elementInList: optionList){
			elementInList.clear();
			elementInList.sendKeys("Answer_"+getRandom());
		}
		//goToFormsMainPage();
	}
	public void addNewQuestion() {addNewQuestion = driver.findElement(By.xpath("//span[@class='button-text']"));
	addNewQuestion.click();}
	
	public void goToFormsMainPage() {
		linkFormsMainFormsPage = driver.findElement(By.xpath("//div[@id='Control_2_container']"));
		linkFormsMainFormsPage.click();
		}

	public void clickAddNewOption() {
		addNewOption = driver.findElement(By.xpath("//span[@class='design-question-addonemore']"));
		addNewOption.click();
	}
	
	// Nuzno podumat'
	public void deleteOptions() throws InterruptedException {
		//btnPachAshpaa = driver.findElement(By.xpath("//i[@class='ms-Icon ms-Icon--Delete option-edit-button-img forms-icon-size16x16']"));
		Actions actions = new Actions(driver);
		actions.moveToElement(driver.findElement(By.xpath("//i[@class='ms-Icon ms-Icon--Delete option-edit-button-img forms-icon-size16x16']"))).perform();
		Thread.sleep(3000);
	}
	
}
