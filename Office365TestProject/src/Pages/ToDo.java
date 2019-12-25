package Pages;

import java.util.List;
import java.util.Random;

import javax.naming.Context;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import com.gargoylesoftware.htmlunit.javascript.host.Console;

public class ToDo {
	public enum LeftMenuItems {
		MY_DAY,
		IMPORTANT,
		PLANNED,
		TASKS
		}
	WebDriver driver;
	private WebElement newList = null;
	private WebElement taskLine = null;
	private WebElement taskItemBody = null;
	private WebElement myDay, important, planned, tasks = null;
	
	public ToDo(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
	 
	public void chooseleftItem(LeftMenuItems leftMenuItem) {
		switch(leftMenuItem) { case MY_DAY: myDay = driver.findElement(By.xpath("//div[@class='todayToolbar-inner']"));myDay.click(); break;
		case IMPORTANT: important = driver.findElement(By.xpath("//div[@id='important']")); important.click(); break;
		case PLANNED: planned = driver.findElement(By.xpath("//div[@id='planned']")); planned.click(); break;
		case TASKS: tasks = driver.findElement(By.xpath("//div[@id='inbox']")); tasks.click(); break;
		default: System.out.println("Left menu item error!");
		}
	}
	public int getTaskItemBody() {
		List<WebElement> listItem = driver.findElements(By.xpath("//div[@class='taskItem-body']"));
		return listItem.size();
		
	}
	public void createNewToDoList(String listName) {
		newList = driver.findElement(By.xpath("//input[@id='baseAddInput-addList']"));
		newList.click();
		newList.sendKeys(listName, Keys.ENTER);
	}

	/************************************
	 * Zаполняем таск-лист всякой х**ней *
	 ************************************/
	public void addTaskToTheList(String taskName, int linesNumber) {
		int i=0;
		for(i=0; i < linesNumber; i++) {
		taskLine = driver.findElement(By.xpath("//input[@id='baseAddInput-addTask']"));
		taskLine.sendKeys(taskName+getRandom(), Keys.ENTER);}
	}

	
	public int getRandom() {Random rand = new Random(); 
	int value = rand.nextInt(1000000);
	return value;}
}
