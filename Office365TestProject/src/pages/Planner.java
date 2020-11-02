package pages;

import java.util.List;
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
		newPlan = (new WebDriverWait(driver, 10)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//li[@class='createplan_teachingCallout']")));
		//newPlan = driver.findElement(By.xpath("//li[@class='createplan_teachingCallout']"));
		newPlan.click();
		newPlanNameValue = newPlanName+getRandom(); 
		planName = (new WebDriverWait(driver, 10)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@type='text']")));
		// planName = driver.findElement(By.xpath("//input[@type='text']"));
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
	public void checkAdminGroupCreation() {
		List<WebElement> warningMsg = driver.findElements(By.xpath("//div[contains(text(),'Your admin has turned off new group creation')]"));
		if (warningMsg.size()>0) {
			WebElement chooseGroups = driver.findElement(By.xpath("//span[@class='selectedGroupPlaceholder']"));
			chooseGroups.click();
			WebElement teamsTestGroup = (new WebDriverWait(driver, 10)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='groupName' and contains(text(),'Teams Testing Group')]")));
			teamsTestGroup.click();
		}
	}
	/* 3 */
	public void clickCreatePlan() throws Exception {
		Thread.sleep(3000);
		btnCreatePlan = (new WebDriverWait(driver, 12)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[@class='createPlanButton']")));
		//btnCreatePlan = driver.findElement(By.xpath("//button[@class='createPlanButton']"));
		btnCreatePlan.click();
		Thread.sleep(10000);
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
		assignTask =(new WebDriverWait(driver, 20))
		  .until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[@class='assignLabel']")));
		//assignTask = driver.findElement(By.xpath("//span[@class='assignLabel']"));
		assignTask.click();
		
		
		txtAssignField = driver.findElement(By.xpath("//input[@placeholder='Type a name or email address']"));
		//txtAssignField = driver.findElement(By.xpath("//body/div[2]/div/div/div/div/div/div[2]/div/div/input"));
		txtAssignField.click();
		txtAssignField.sendKeys(assignNameOrMail);
		Thread.sleep(1000);
		
		chooseMember = driver.findElement(By.xpath("//button/div/div[@role='presentation']"));
		//chooseMember = driver.findElement(By.xpath("//body/div[2]/div/div/div/div/div/div[3]/div/div[2]/div/button/div/div[2]/div[1]/div"));
		chooseMember.click();
		
		WebElement createdFieldAssign = driver.findElement(By.xpath("//input[@aria-label='Task name']"));
		createdFieldAssign.click();
		
		
		Thread.sleep(1000);
		//confirmAssignAndAdd = driver.findElement(By.xpath("//span[@class and contains(text(),'Assign and add')]"));
		confirmAssignAndAdd = driver.findElement(By.xpath("//button[@class='addTaskButton']"));
		
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
		plannerMainPage = (new WebDriverWait(driver, 20))
		  .until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a/span[contains(text(),'Planner')]")));
		// plannerMainPage = driver.findElement(By.xpath("//a/span[contains(text(),'Planner')]"));
		
		// plannerMainPage = driver.findElement(By.xpath("//span[@class='_1sMs4HtnnjNiwUlomBf_ia']"));
		plannerMainPage.click();
	}
	public int getRandom() {Random rand = new Random(); 
	int value = rand.nextInt(1000000);
	return value;}
}
