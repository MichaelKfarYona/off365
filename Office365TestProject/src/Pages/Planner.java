package Pages;

import java.util.NoSuchElementException;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Planner {
	public enum Privacy {
		PUBLIC,
		PRIVATE
		}
	private WebElement newPlan, plannerMainPage, plannerHub, myTasks, planFromTheList = null;
	private WebElement planName, btnPublic, btnPrivate, btnCreatePlan, createdPlansName = null;
	private WebElement txtEnterATaskName, assignTask, txtAssignField, chooseMember, confirmAssignAndAdd = null;
	WebDriver driver;
	String newPlanNameValue = null;
	public Planner(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
    } 
	/* 1 */
	public String createANewPlan(String newPlanName) {
		newPlan = driver.findElement(By.xpath("//li[@class='createplan_teachingCallout']"));
		newPlan.click();
		newPlanNameValue = newPlanName+getRandom(); 
		planName = driver.findElement(By.xpath("//input[@type='text']"));
		planName.click();
		planName.sendKeys(newPlanNameValue);
		return newPlanNameValue;
	}

	/*******************************************
	 * Choose type of privacy : optional method*
	 *******************************************/
	/* 2 Public by default */
	public void privacyOption(Privacy value) {
		switch(value) { case PUBLIC: btnPublic = driver.findElement(By.xpath("//span[@class='ms-ChoiceFieldLabel' and contains(text(), 'Public')]")); btnPublic.click(); break;
		case PRIVATE: btnPrivate = driver.findElement(By.xpath("//span[@class='ms-ChoiceFieldLabel' and contains(text(), 'Private')]")); btnPrivate.click(); break;
		}
	}
	/* 3 */
	public void clickCreatePlan() throws Exception {
		Thread.sleep(2000);
		btnCreatePlan = driver.findElement(By.xpath("//button[@class='createPlanButton']"));
		btnCreatePlan.click();
		Thread.sleep(8000);
	}
	public void createNewTaskByPlanName(String taskName, String planName) throws InterruptedException {
		Thread.sleep(5000);
		
		WebElement existentPlanName = (new WebDriverWait(driver, 15)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[@class='tileName' and contains(text(),'"+planName+"')]")));
		existentPlanName.click();
		boolean isPresent;
		if(!(driver.findElements(By.xpath("//input[@type='text' and contains(text(),'')]")).size()>0)) {
			WebElement btnAddTask = (new WebDriverWait(driver, 15)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[@class='addTaskText']")));
			btnAddTask.click();}
		
		
			specifyTaskNameAndEnter(taskName);
	}
	
	
	
	
	
	
	/* 4 */
	/* verification of the existence of the plan */
	public boolean verificationOfTheExistenceOfThePlan(String planName) throws InterruptedException {
		Thread.sleep(5000);
		createdPlansName = driver.findElement(By.xpath("//span[@class='tileName' and contains(text(), '"+planName+"')]"));
		return createdPlansName.isDisplayed();
	}
	// Add new task in the Planner and click ENTER
	public void specifyTaskNameAndEnter(String taskName) throws InterruptedException {
		txtEnterATaskName = (new WebDriverWait(driver, 10)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@type='text' and contains(text(),'')]")));
		txtEnterATaskName.click();
		txtEnterATaskName.sendKeys(taskName, Keys.ENTER);
		Thread.sleep(1000);
		
	}
	// Add new task in the Planner
	public void specifyTaskName(String taskName) throws InterruptedException {
		txtEnterATaskName = (new WebDriverWait(driver, 10)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@type='text' and contains(text(),'')]")));
		txtEnterATaskName.click();
		txtEnterATaskName.sendKeys(taskName);
		
	}
	//Task assignment
	public void specifyAssignName(String assignNameOrMail) throws InterruptedException {
		assignTask = driver.findElement(By.xpath("//span[@class='assignLabel']"));
		assignTask.click();
		txtAssignField = driver.findElement(By.xpath("//body/div[2]/div/div/div/div/div/div[2]/div/div/input"));
		txtAssignField.click();
		txtAssignField.sendKeys(assignNameOrMail);
		Thread.sleep(1000);
		chooseMember = driver.findElement(By.xpath("//body/div[2]/div/div/div/div/div/div[3]/div/div[2]/div/button/div/div[2]/div[1]/div"));
		chooseMember.click();
		Thread.sleep(2000);
		confirmAssignAndAdd = driver.findElement(By.xpath("//div[@class and contains(text(),'Assign and add')]"));
		confirmAssignAndAdd.click();
	}
	//Choose created plan
	public String choosePlanFromTheList(String name) throws InterruptedException {
		Thread.sleep(2000);
		planFromTheList = driver.findElement(By.xpath("//span[@class='tileName' and contains(text(), '"+name+"')]"));
		String res = planFromTheList.getText();
		planFromTheList.click();
		return res;
	}
	
	public void returnToTheMainPlannerPage() {
		plannerMainPage = driver.findElement(By.xpath("//span[@class='_1sMs4HtnnjNiwUlomBf_ia']"));
		plannerMainPage.click();
	}
	public int getRandom() {Random rand = new Random(); 
	int value = rand.nextInt(1000000);
	return value;}
}
