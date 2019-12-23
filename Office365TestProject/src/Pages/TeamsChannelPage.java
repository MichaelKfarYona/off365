package Pages;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.Locatable;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import Pages.OneDrive.LeftMenuItem;

public class TeamsChannelPage {
	public enum KindOfTeam {PRIVATE, PUBLIC}
	public enum LeftMenuTeams{ACTIVITY, CHAT, TEAMS, CALENDAR, CALLS, FILES}
	public enum MessageTypeInConversationsTeamsTab{EMOJI, GIPHY, STICKER}
	public enum UserStatus{AVAILABLE,BUSY, DONT_DESTURB, BE_RIGHT_BACK, APPEAR_AWAY}
	public enum TeamsMenuItemPopUp{HIDE, MANAGE_TEAM, ADD_CHANNEL, ADD_MEMBER, LEAVE_THE_TEAM,EDIT_TEAM, GETLINK_TO_TEAM, DELETE_THE_TEAM}
	public enum FlowNewMenuItem{CREATE_FROM_TEMPLATE, AUTOMATED_FROM_BLANK, INSTANT_FROM_BLANK, SCHEDULED_FROM_BLANK, BUSINESS_PROCESS_FROM_BLANK}
	WebDriver driver;
	private WebElement gearButton, sendButton = null;
	private WebElement activityTab = null;
	private WebElement chatTab = null;
	private WebElement teamsTab = null;
	private WebElement calendarTab = null;
	private WebElement callsTab = null;
	private WebElement filesTab = null;
	private WebElement chatField, txtSearchOrTypeACommand = null;
	private WebElement upperTabElement = null;
	private WebElement emojiPicker, addAttachment, formatMessage, giphyPicker, clickElementTypeToSend = null;
	private WebElement joinOrCreateATeamLink = null;
	private WebElement createTeamButton = null;
	private WebElement buildTeamFromScratch = null;
	private WebElement privateKind, publicKind = null;
	private WebElement teamName = null;
	private WebElement createButton = null;
	private WebElement filterByTeamOrChannel = null;
	private WebElement skipButton = null;
	private WebElement filterButton = null;
	private WebElement flow_iFrame, btnAddNewFlow = null;
	private WebElement txtNameSharedFormViaTeams, btnSaveFormViaTeams, tabName = null;
	private WebElement txtNameYourPlan,btnSavePlan, NewFrame = null;
	private WebElement addATabButton, userMailAddress, membersAndGuest = null;
	private WebElement webElementInAddTab, linkNewCreatedTeamInTheListTeam = null;
	private WebElement webSiteName, webSiteURL , btnSaveWebSite, linkToMemberSettings, btnPosts = null;
	private WebElement btnSaveFlowViaTeamsAboutWindow = null; //button[@id='tabRemoveBtn' and contains(text(),'Save')]
	public TeamsChannelPage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

	public class FlowFrame{
		WebDriver driver;
		public FlowFrame(WebDriver driver){
	        this.driver = driver;
	        PageFactory.initElements(driver, this);
	    }
		
		public void newClick() {
			WebElement fileElem = driver.findElement(By.xpath("//button[@name='New']"));
			fileElem.click();
		}
		
	}
	public void buildATeamFromScratch(KindOfTeam kind, String newTeamName, int randTeamNumber) throws Exception {
		buildTeamFromScratch = driver.findElement(By.xpath("//*[@id='WizardBtnfromScratch']"));
		buildTeamFromScratch.click();Thread.sleep(3000);
		switch(kind) { case PRIVATE: privateKind = driver.findElement(By.xpath("//*[@id='WizardBtnPrivate']"));
		privateKind.click();break; case PUBLIC: publicKind = driver.findElement(By.xpath("//*[@id='WizardBtnPublic']"));
		publicKind.click();	break;}Thread.sleep(1000);
		teamName = driver.findElement(By.xpath("//input[@id='detailsTeamName']"));
		teamName.sendKeys(newTeamName+randTeamNumber);
		createButton = driver.findElement(By.xpath("//button[@track-summary='Create Team' and contains (@role,'button')]"));
		createButton.click();Thread.sleep(7000);
		Actions builder = new Actions(driver);
        builder.sendKeys(Keys.chord(Keys.ESCAPE)).perform();
			skipButton = driver.findElement(By.xpath("//button[@role='button']"));
			 skipButton.click();
		
	}
	/*************************
	 * Create Plan via Teams 
	 * @throws InterruptedException *
	 *************************/
	public void fillPlanerName(String plannerName) throws InterruptedException {
		Thread.sleep(4000);
		WebElement iFrame= driver.findElement(By.xpath("//iframe[@title='Planner Tab Settings']"));
		driver.switchTo().frame(iFrame);
		//Actions actions = new Actions(driver);
        //actions.moveToElement(iFrame).click().perform();
		txtNameYourPlan = driver.findElement(By.xpath("//input[@id='TextField2']"));
		txtNameYourPlan.sendKeys(plannerName);
		driver.switchTo().parentFrame();
		btnSavePlan = driver.findElement(By.xpath("//*[@id='ngdialog2']/div[2]/div/div/div/div/div[2]/div/div[2]/button"));
		btnSavePlan.click();
		Thread.sleep(5000);
	}
	public boolean checkNewPlannerViaTeams(String plannerName) {
		return searchTabByName(plannerName);
	}

	/*********************************
	 * Create new form via Teams tab *
	 * @throws InterruptedException  *
	 *********************************/
	public void fillTheFormViaTeams(String formName) throws InterruptedException {
		Thread.sleep(3000); 
		WebElement iFrame= driver.findElement(By.xpath("//iframe[@title='Forms Tab Settings']"));
		//WebElement iFrame= driver.findElement(By.xpath("//form[@role='form']"));

		driver.switchTo().frame(iFrame);
		txtNameSharedFormViaTeams = driver.findElement(By.xpath("//input[@class='newTitleInput']")); 
		txtNameSharedFormViaTeams.click();
		txtNameSharedFormViaTeams.sendKeys(formName);
		driver.switchTo().parentFrame();
		Thread.sleep(1000);
		btnSaveFormViaTeams = driver.findElement(By.xpath("//button[contains(text(),'Save')]"));
		btnSaveFormViaTeams.click();Thread.sleep(7000);
	}
	public boolean searchTabByName(String newFormName) {
		tabName = driver.findElement(By.xpath("//span[@class='tab-display-name' and contains (text(),'"+newFormName+"')]"));
		if(tabName.isDisplayed()) {
		tabName.click(); return true;}
		else return false;
	} 
	/*******************************************
	 * Top Teams Tabs : Return name of the Tab *
	 *******************************************/
	public boolean checkNewTabAddedViaContent(String url) {
		WebElement getUrl = (new WebDriverWait(driver, 10)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='tab-content']//embedded-page-container[@url='"+url+"']"))); 
//driver.findElement(By.xpath("//div[@class='tab-content']//embedded-page-container[@url='"+url+"']"));
		if(getUrl.isDisplayed()) {return true;}
		return false;
	}
	public String checkNewTabAdded(String tabElementName) {
		upperTabElement = driver.findElement(By.xpath("//div//span/span[contains(text(), '"+tabElementName+"')]"));
		return upperTabElement.getText();
	}
	//***************************
	//Open Team Members settings
	//***************************
	public void openTeamMembersSettings() {
		linkToMemberSettings = driver.findElement(By.xpath("//a[@class='team-href']"));
		linkToMemberSettings.click();
	}
	
	public void openTeamMembersSettingsDot(String searchTeam) throws InterruptedException {
		clickByFilterButton();
		setFilterByTeamOrChannel(searchTeam);
		Thread.sleep(2000);
		WebElement moreOptions = driver.findElement(By.xpath("//button[@title='More options']"));
		moreOptions.click();Thread.sleep(2000);
		linkToMemberSettings = (new WebDriverWait(driver, 10)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[@track-summary='View Team']")));
		linkToMemberSettings.click();
	}
	
	
	public void openTeamSettingsDot(String searchTeam, TeamsMenuItemPopUp menuItem) throws InterruptedException {
		clickByFilterButton();
		setFilterByTeamOrChannel(searchTeam);
		Thread.sleep(2000);
		WebElement moreOptions = driver.findElement(By.xpath("//button[@title='More options']"));
		moreOptions.click();Thread.sleep(2000);
		String menuItemChoose= null;
		switch(menuItem) { 
		case HIDE: menuItemChoose = "Hide"; 		break;
		case MANAGE_TEAM: menuItemChoose = "Manage team"; 		break;
		case ADD_CHANNEL: menuItemChoose = "Add channel"; 		break;
		case ADD_MEMBER: menuItemChoose = "Add member"; 		break;
		case LEAVE_THE_TEAM: menuItemChoose = "Leave the team"; 		break;
		case EDIT_TEAM: menuItemChoose = "Edit team"; 		break;
		case GETLINK_TO_TEAM: menuItemChoose = "Get link"; 		break;
		case DELETE_THE_TEAM: menuItemChoose = "Delete the team"; 		break;
		}
		linkToMemberSettings = (new WebDriverWait(driver, 10)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[@class='ts-popover-label' and contains(text(),'"+menuItemChoose+"')]")));
		linkToMemberSettings.click();
		if(menuItemChoose.equalsIgnoreCase("Delete the team")) {
			WebElement btnRoleChecbox = (new WebDriverWait(driver, 5)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[@role='checkbox']")));
			btnRoleChecbox.click();
			WebElement btnDeleteTeam = (new WebDriverWait(driver, 5)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[@id='confirmButton']")));
			btnDeleteTeam.click();
		}
	}
	
	
	
	public void chooseCreatedTeam(String teamName) throws InterruptedException {
		Thread.sleep(2000);
		linkNewCreatedTeamInTheListTeam = driver.findElement(By.xpath("//span[contains(text(),'"+teamName+"')]"));
		linkNewCreatedTeamInTheListTeam.click();
	}

	/*****************************
	 * Create Map of all members *
	 *****************************/
	public HashMap<String, String> checkOwnerOrMember() throws InterruptedException {
		Thread.sleep(2000);
		List<WebElement> listOfOwners = driver.findElements(By.xpath("//div[@class='section-container-0']//div[@class='td-member']"));
		int iterator;
		
		HashMap<String, String> mapOfAllMembers = new HashMap<String, String>();
		for (iterator=0; iterator < listOfOwners.size();iterator++) {
		mapOfAllMembers.put(listOfOwners.get(iterator).findElement(By.xpath("//div[@class='td-member-display-name']"))
				.getText(), listOfOwners.get(iterator).findElement(By.xpath("//span[@data-tid='selectedText']")).getText());}
		
		return mapOfAllMembers;
	}
	public HashMap<String, String> checkGuestUsers() throws InterruptedException {
		Thread.sleep(1000);
		List<WebElement> listOfGuests = driver.findElements(By.xpath("//div[@class='section-container-1']//div[@class='td-member']"));
		int iterator;
		HashMap<String, String> mapOfAllGuests = new HashMap<String, String>();
			for(iterator = 0; iterator < listOfGuests.size(); iterator++) {
				  mapOfAllGuests.put(driver.findElement(By.
				  xpath("//div[@class='section-container-1']//div[@class='td-member']//span[@class='person-card-hover' and contains(text(), '')]")).getText(), driver.findElement(By.
				  xpath("//div[@class='section-container-1']//div[@class='td-member']//div[@class='td-member-role role-extra-padding']/span[@ng-bind and contains(text(),'')]")).getText());}
				  return mapOfAllGuests;}
	
	/***********************************
	 * Click by collapse/expand option *
	 ***********************************/
	public void collapseExpandMembersAndGuests() throws InterruptedException {
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		String numberOfGuests = driver.findElement(By.xpath("//*[@id=\"page-content-wrapper\"]//div/messages-header/div//td-members-tab/div/div[2]/div/div[1]/div[2]/div/button/span[2]")).getText();
		if(numberFromString(numberOfGuests)>0)
		{membersAndGuest = driver.findElement(By.xpath("//span[@class='section-header-title' and contains (text(),'guest')]"));
			membersAndGuest.click();};driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
	}
	
	public int numberFromString(String data) {
		int i=0;
		int result = 0;
	    int[] digits = findDigits(data);
	    int length = digits.length;
	    for (i = 0; i < length; ++i) {
	        System.out.print(digits[i]);
	    }
	    result = digits[0]; return result;	    
		}
	    private static int[] findDigits(String str) {
	    int length = str.length(), count = 0;
	    char[] data = str.toCharArray();
	    int[] result = new int[length];
	    for (int i = 0; i < data.length; ++i) {
	        if (Character.isDigit(data[i])) {
	        result[count++] = Integer.parseInt(Character.toString(data[i]));
	        }
	    }
	    return Arrays.copyOfRange(result, 0, count);
	}
	    
	public void hoverUserNameToGetMail() throws InterruptedException {
		/*
		 * Actions actions = new Actions(driver); Thread.sleep(2000); //WebElement
		 * menuOption =
		 * driver.findElement(By.xpath("//div[@class = 'td-member-display-name']"));
		 * actions.moveToElement(driver.findElement(By.
		 * xpath("//div[@class = 'td-member-display-name']"))).perform();
		 * //menuOption.click(); Thread.sleep(3000);
		 * 
		 * 
		 * 
		 * userMailAddress =
		 * driver.findElement(By.xpath("//div[@ng-if = 'rpc.person.email']"));
		 * System.out.println(userMailAddress.getText());
		 */
	}

	/**********************************
	 * Choose status via user picture *
	 * @throws InterruptedException   *
	 **********************************/
	public String chooseStatus(UserStatus status) throws InterruptedException {
		String setStatus=null;
		WebElement userPicture = driver.findElement(By.xpath("//*[@id='personDropdown']//profile-picture"));
		userPicture.click();
		WebElement userMenuElement = (new WebDriverWait(driver, 10)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[@class='ts-sym']")));
		Actions act = new Actions(driver);
		act.moveToElement(userMenuElement).perform();
		switch(status) { 
		case AVAILABLE: setStatus = "Available"; 		break;
		case BUSY: setStatus = "Busy";  				break;
		case DONT_DESTURB: setStatus = "Do not disturb";break;
		case BE_RIGHT_BACK: setStatus = "Be right back";break;
		case APPEAR_AWAY: setStatus = "Appear away"; 	break;
		default: setStatus="Available"; 				break;
		}
		WebElement ststusElement = (new WebDriverWait(driver, 10)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[@class='status-text' and contains(text(),'"+setStatus+"')]")));
		ststusElement.click();
		return setStatus;
	}
	//not*
	public String checkUserStatus() {
		String setStatus=null;
		WebElement userPicture = driver.findElement(By.xpath("//*[@id='personDropdown']//profile-picture"));
		userPicture.click();
		WebElement statusNow = driver.findElement(By.xpath("//span[@id='personal-skype-status-text']"));
		setStatus = statusNow.getAttribute("innerHTML");
		System.out.println("Status _ "+statusNow);
		return setStatus;
	}
	
	// Change status by command
	public void setUserStatus(UserStatus status) {
		String setStatus = null;
		txtSearchOrTypeACommand = (new WebDriverWait(driver, 10)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@id='searchInputField']")));
		switch(status) { 
		case AVAILABLE: setStatus="/available"; break;
		case BUSY: setStatus="/busy";  			break;
		case DONT_DESTURB: setStatus="/dnd"; 	break;
		case BE_RIGHT_BACK: setStatus="/brb";  	break;
		case APPEAR_AWAY: setStatus="/away"; 	break;
		default: setStatus="/"; 				break;
		}
		System.out.println("STATUS - "+setStatus);
		txtSearchOrTypeACommand.sendKeys(setStatus, Keys.ENTER);
	}
	
	public void chooseElementFromAddATab(String element) {

		webElementInAddTab = (new WebDriverWait(driver, 10))
		.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//li/*[contains(text(),'"+element+"')]")));
		webElementInAddTab.click();
	}
	//TopMenuTeams
	public void chooseTopMenuItem(String item) {
		WebElement topMenuElement = driver.findElement(By.xpath("//span[@class='tab-display-name' and contains(text(),'"+item+"')]"));
		topMenuElement.click();
	}
	//********** Flow via Teams **********
	public void chooseNewFlowType(FlowNewMenuItem flowType) {
		String chooseType = null;
		switch(flowType) { 
		case CREATE_FROM_TEMPLATE: chooseType ="Create"; 		break;
		case AUTOMATED_FROM_BLANK: chooseType ="Automated";  	break;
		case INSTANT_FROM_BLANK: chooseType="Instant"; 			break;
		case SCHEDULED_FROM_BLANK: chooseType="Scheduled";  	break;
		case BUSINESS_PROCESS_FROM_BLANK: chooseType="Business"; 	break;
		default: chooseType="Create"; 				break;
		}
		 WebElement flowMenuItem = (new WebDriverWait(driver, 10)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//ul/li[@role='presentation']//div/span[contains(text(),'"+chooseType+"')]")));
		 flowMenuItem.click();
		
	}
	public void closeFlowMainWindow() throws InterruptedException {
		Thread.sleep(2000);
	btnSaveFlowViaTeamsAboutWindow = (new WebDriverWait(driver, 10)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[@id='tabRemoveBtn' and contains(text(),'Save')]"))); 
	btnSaveFlowViaTeamsAboutWindow.click();
	Thread.sleep(10000);
	}
	public void clickAddNewFlow() throws InterruptedException {
		
		 WebElement myNewEl = (new WebDriverWait(driver, 15)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[@class='tab-display-name' and contains(text(),'Flow')]")));
		 myNewEl.click();
		 Thread.sleep(8000);
		System.out.println("Popitka naity element dropDownListNew");
		WebElement dropDownListNew = (new WebDriverWait(driver, 15)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//iframe[@title='Flow Tab View']")));
		driver.switchTo().frame(dropDownListNew);
		driver.switchTo().frame("widgetIFrame");
		WebElement el = driver.findElement(By.name("New"));
		el.click();
		
	}
	public void clickContinueButton() throws InterruptedException {
		
		Thread.sleep(3000);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
		Thread.sleep(3000);
		WebElement countinueButton = (new WebDriverWait(driver, 15)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("/html/body/div[1]/div/div/connection-wizard/section/div/div/div/div[3]/button/div")));
		countinueButton.click();
	}
	
	//vozvrashaet List(1- Plan_id, 2 - Team. Channel === General)
	public List<String> chooseElementFromDropDownList_ByIndex(int index) {
		WebElement dropdown = (new WebDriverWait(driver, 15)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div//button[@title='Show options']")));
		dropdown.click();
		WebElement elementFromTheList = (new WebDriverWait(driver, 15)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//ul/li/a/span[contains(text(),'')]")));
		
		System.out.println("+++++ "+elementFromTheList.getText());
		elementFromTheList.click();
		
		WebElement teamName = (new WebDriverWait(driver, 15)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='msla-combobox-input']//div//span/span"))); 
		
		String setElements[];
		List<String> res = new ArrayList<String>();
		res.add(elementFromTheList.getText());
		res.add(teamName.getText());
		System.out.println("PLAN_ID = "+elementFromTheList.getText()+ "    ***   TEAM = "+teamName.getText());
		//clickSaveBtnFlow();
		return res;
	}
	//Long wait. min value = 240
	public boolean checkMessageByText(String someText) {
		if ((new WebDriverWait(driver, 300)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[contains(text(),'"+someText+"')]"))).isDisplayed()) {return true;}
		else {return false;}
	}
	
	public void clickSaveBtnFlow() {WebElement btnSaveFlow = driver.findElement(By.xpath("//button[contains(text(),'Save')]"));
	btnSaveFlow.click();}
	

	//Template List
	public void chooseFlowTemplateByDescription(String description) {
		WebElement templateElement = (new WebDriverWait(driver, 15)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='content']/h3[contains(text(),'"+description+"')]")));
		templateElement.click();
		
	}
	//***** FLOW ***** 
	public void flowFirstScreenSave() throws InterruptedException {
		WebElement btnSaveFlow = (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[@type='submit']"))); 
		btnSaveFlow.click(); Thread.sleep(3000);
	}
	public void specifyFlowInfo(String flow) throws InterruptedException {
		
		
		
				  WebElement flowTab = (new WebDriverWait(driver, 10)).until(ExpectedConditions.presenceOfElementLocated(By.
		  xpath("//span[@class='tab-display-name' and contains(text(),'"+flow+"')]")));
		  flowTab.click();
		  FlowFrame flowFr = new FlowFrame(driver);
		flowFr.newClick();
		  
		  //driver.switchTo().frame(driver.findElement(By.xpath("//html//body//iframe[@name='widgetIFrame']")));  
		/*
		 * WebElement fileElem = driver.findElement(By.xpath("//button[@name='New']"));
		 * fileElem.click();
		 */
		Thread.sleep(3000);
		/*
		 * btnAddNewFlow = (new WebDriverWait(driver,
		 * 10)).until(ExpectedConditions.presenceOfElementLocated(By.xpath(
		 * "//button[@name='New']"))); btnAddNewFlow.click();
		 */  
		/*
		 * WebElement wewewe = driver.findElement(By.xpath("//button[@name='New']"));
		 * wewewe.click();
		 */
		
		/*
		WebElement lineNumberOfTheFlow = (new WebDriverWait(driver, 5)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//li[@role='presentation']//span[@class='ms-ContextualMenu-itemText label-302' and contains(text(),'Create')]")));
		lineNumberOfTheFlow.click();
		*/
	}
	/********************************
	 * Specify WebSite name and URL * 
	 * @throws InterruptedException *
	 ********************************/
	public void specifyWebSiteNameAndURL(String name, String URL) throws InterruptedException {
		webSiteName = (new WebDriverWait(driver, 10))
		.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@name='tabName']")));
		webSiteURL = (new WebDriverWait(driver, 10))
		.until(ExpectedConditions.presenceOfElementLocated(By.xpath("/html/body/div[5]/div[2]/div/div/div/div/div/div/form/section[3]/div/div/div/div[2]/input")));
		webSiteName.sendKeys(name);
		webSiteURL.sendKeys(URL);Thread.sleep(3000);
		btnSaveWebSite = (new WebDriverWait(driver, 10))
		.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='ngdialog2']/div[2]/div/div/div/div/div/div/form/section[4]/div[2]/div[2]/button")));
		btnSaveWebSite.click();
	}
	public void clickAddATabButton() throws InterruptedException {
		addATabButton = (new WebDriverWait(driver, 10))
    	.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[@id='add-tab-button-v2']")));
		addATabButton.click();
	}
	public void clickForPlannerViaTeams() {
		WebElement cross = driver.findElement(By.xpath("//button[@id='add-tab-button-v2']"));
		cross.click();
	}
	public void clickByFlow() {}
	
	public void teamCheck(String teamName, int teamNumber) {
		// Actions builder = new Actions(driver);
		// builder.sendKeys(Keys.chord(Keys.SHIFT,Keys.CONTROL,"F")).perform();	
		String searchTeam = teamName+teamNumber;
		clickByFilterButton();
		setFilterByTeamOrChannel(searchTeam);
	}
	
	public void clickByFilterButton() {
		filterButton = driver.findElement(By.xpath("//*[@class='ts-sym app-icons-fill-hover left-rail-header-filter-button left-rail-header-button']"));
		filterButton.click();
	}
	public void setFilterByTeamOrChannel(String filter) {
		filterByTeamOrChannel = driver.findElement(By.xpath("//input[@id='left-rail-header-filter-input']"));
		filterByTeamOrChannel.sendKeys(filter);
	}
	public boolean elementIsNotPresent(String xpath){
	       return driver.findElements(By.xpath(xpath)).isEmpty();

	}
	/***************** 
	 * Random method *
	 *****************/
	/*
	 * public int getRandom() {Random rand = new Random(); int value =
	 * rand.nextInt(100000); return value;}
	 */
	
	/***************************
	 * Open Create Team window *
	 ***************************/
	public void createTeam() {
		clickJoinOrCreateATeam();
		clickCreateTeamButton();
	}
	public void clickJoinOrCreateATeam() {
		joinOrCreateATeamLink = driver.findElement(By.xpath("//span[@id='create_join_team_text']"));
		joinOrCreateATeamLink.click();
	} 
	
	public void clickCreateTeamButton() {
		createTeamButton = driver.findElement(By.xpath("//*[@id='discover-suggested-tile-suggested']//button"));
		createTeamButton.click();
	}

	/**********************************************
	 * Test Format message in Teams Conversations * 
	 **********************************************/
	public void formatTextMessage() {
		formatMessage = driver.findElement(By.xpath("//button[@track-summary='Expand compose box']"));
		formatMessage.click();

	}
	/**************** 
	 * Random Range *
	 ****************/
	public int randomNumInRange(int min, int max) {
		int randomNum = ThreadLocalRandom.current().nextInt(min, max + 1);
		return randomNum;
	}

	/*************************
	 * Add attachment : file * 
	 * @throws InterruptedException 
	 *************************/
	public void chooseAttachment_OneDrive_Chat() throws InterruptedException {
		addAttachment = driver.findElement(By.xpath("//button[@track-summary='Add attachment']"));
		addAttachment.click();
		WebElement linkOneDriveUpload = (new WebDriverWait(driver, 10)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[@class='element-text' and contains(text(),'OneDrive')]")));
		linkOneDriveUpload.click(); Thread.sleep(5000);
		List<WebElement> filesList = driver.findElements(By.xpath("//a[@track-summary='Opens a file in document stage']")); 
		int listSize =  filesList.size();
		if (listSize<1) {System.out.println("Empty list!"); WebElement btnCancel = driver.findElement(By.xpath("//button[@translate-once='cancel']")); btnCancel.click();}
		else {int randomFile = randomNumInRange(0, listSize-1); WebElement randomFileElement = filesList.get(randomFile); Thread.sleep(3000);
		randomFileElement.click();
		WebElement btnShareFile = (new WebDriverWait(driver, 10)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[@data-tid='submit-file-selected']}")));
		btnShareFile.click();
		sendMsg();}

	}
	public void sendMessageInTeamsConversations(MessageTypeInConversationsTeamsTab typeOfMessage) throws InterruptedException {
		String bottomLine = "//button[@track-summary=";
		switch(typeOfMessage) { 
		case EMOJI: clickElementTypeToSend = driver.findElement(By.xpath(bottomLine+"'Emoji picker']"));	clickElementTypeToSend.click();  break;
		case GIPHY: clickElementTypeToSend = driver.findElement(By.xpath(bottomLine+"'Fun stuff picker']")); clickElementTypeToSend.click();  break;
		case STICKER: clickElementTypeToSend = driver.findElement(By.xpath(bottomLine+"'Fun stuff picker']")); clickElementTypeToSend.click(); break;
		}
		Thread.sleep(1000);
		List<WebElement> stickerList = clickElementTypeToSend.findElements(By.xpath("//a[@class='link']"));
		int mojoSize = stickerList.size();
		stickerList.get(randomNumInRange(0, mojoSize)).click();
		WebElement elementForSendinf = driver.findElement(By.xpath("//div[@role='textbox']"));
		elementForSendinf.sendKeys(Keys.ENTER);
		//sendMsg();
		
	}
	/****************
	 * Choose Emoji *
	 ****************/ 
	public void chooseEmoji() {
		emojiPicker = driver.findElement(By.xpath("//button[@track-summary='Emoji picker']"));
		emojiPicker.click();
		
		List<WebElement> emojiList = emojiPicker.findElements(By.xpath("//a[@class='link']"));
		int mojoSize = emojiList.size();
		emojiList.get(randomNumInRange(0, mojoSize)).click();
		sendMsg();
	}
	public void chooseRandomGiphy() {
		giphyPicker = driver.findElement(By.xpath("//button[@track-summary='Fun stuff picker']"));
		giphyPicker.click();
		List<WebElement> giphyList = giphyPicker.findElements(By.xpath("//a[@class='link']"));
		int giphySize = giphyList.size();
		giphyList.get(randomNumInRange(0, giphySize)).click();
		sendMsg();
		
	}
	//button[@track-summary='Stickers input Extension picker']
	
	/***************************
	 * Sending message in Chat *
	 ***************************/
	public void sendMsg() {
		sendButton = driver.findElement(By.xpath("//*[@id='send-message-button']/ng-include"));
	}
    public void specifyingMessage(String msg) {
    	chatField = driver.findElement(By.xpath("//div[@role='textbox']"));
    	chatField.sendKeys(msg);
    	sendMsg();
    	
    }
    public void clickByLinkWeb() {
    	WebElement myLinkNew = (new WebDriverWait(driver, 10)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[@class='use-app-lnk']")));
    	//WebElement myLink = driver.findElement(By.xpath("//a[@class='use-app-lnk']"));
    	myLinkNew.click();
        }
    // Add new meeting (Teams -> Calendar)
    public String createNewMeetingInCalendar(String meetingName_Title, String[] Attendes) throws InterruptedException {
    	WebElement btnNewMeeting = (new WebDriverWait(driver, 10)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[contains(text(),'New meeting')]")));
    	btnNewMeeting.click();
    	WebElement txtTitle = (new WebDriverWait(driver, 10)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@name='subject']")));
    	txtTitle.sendKeys(meetingName_Title);
    	for(String user : Attendes) {
    	WebElement txtInviteSomeone = (new WebDriverWait(driver, 10)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='ts-people-picker']//input")));
    	txtInviteSomeone.sendKeys(user); Thread.sleep(1000);
    	txtInviteSomeone.sendKeys(Keys.ENTER); Thread.sleep(1000);}
    	
    	WebElement btnSchedule = (new WebDriverWait(driver, 10)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[@class='ts-btn ts-btn-fluent ts-btn-fluent-primary']")));
    	btnSchedule.click(); Thread.sleep(2000);
    	WebElement titleToCheck = (new WebDriverWait(driver, 10)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//h1[@class='app-font-family-base ts-modal-dialog-title']")));
    	String meetingTitleAsAResult = titleToCheck.getText();
    	return meetingTitleAsAResult;
    }
    /****************************************************************************************************************
	 ********************************************** Left side elements **********************************************
	 ****************************************************************************************************************/
    public void clickByLeftMenuItem(LeftMenuTeams item) {
		String parametrListItem = null;
		switch(item) { 
		case ACTIVITY: parametrListItem = "app-bar-14d6962d-6eeb-4f48-8890-de55454bb136";  break;
		case CHAT: parametrListItem = "app-bar-86fcd49b-61a2-4701-b771-54728cd291fb";  break;
		case TEAMS: parametrListItem = "app-bar-2a84919f-59d8-4441-a975-2a8c2643b741";  break;
		case CALENDAR: parametrListItem = "app-bar-ef56c0de-36fc-4ef8-b417-3d82ba9d073c";  break;
		case CALLS: parametrListItem = "app-bar-20c3440d-c67e-4420-9f80-0e50c39693df";  break;
		case FILES: parametrListItem = "app-bar-5af6a76b-40fc-4ba1-af29-8f49b08e44fd";  break;
		}
		WebElement leftMenuItemElement = driver.findElement(By.xpath("//*[@id='"+parametrListItem+"']"));
		leftMenuItemElement.click();
	}
    // the main idea is to compare lists (Files -> OneDrive) not finished
    public void compareOneDriveLists() {
    	clickByLeftMenuItem(LeftMenuTeams.FILES);
    	
    }
    
    public void clickActivityTab() {
    	activityTab = driver.findElement(By.xpath("//*[@id='app-bar-14d6962d-6eeb-4f48-8890-de55454bb136']"));
    	activityTab.click();
    }
    
    public void clickChatTab() {
    	chatTab = driver.findElement(By.xpath("//*[@id='app-bar-86fcd49b-61a2-4701-b771-54728cd291fb']"));
    	chatTab.click();
    }
    public void clickTeamsTab() throws Exception {
    	Thread.sleep(5000);
    	teamsTab = (new WebDriverWait(driver, 10))
    	.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='app-bar-2a84919f-59d8-4441-a975-2a8c2643b741']")));
    	teamsTab.click();
    }
    
    public void clickCalendarTab() {
    	calendarTab = driver.findElement(By.xpath("//*[@id='app-bar-ef56c0de-36fc-4ef8-b417-3d82ba9d073c']"));
    	calendarTab.click();
    }
    
    public void clickCallsTab() {
    	callsTab = driver.findElement(By.xpath("//*[@id='app-bar-20c3440d-c67e-4420-9f80-0e50c39693df']"));
    	callsTab.click();
    }
    
    public void clickFilesTab() {
    	filesTab = driver.findElement(By.xpath("//*[@id='app-bar-5af6a76b-40fc-4ba1-af29-8f49b08e44fd']"));
    	filesTab.click();
    }
	/*
	 * public void clickByLinkWeb() { WebElement myLink =
	 * driver.findElement(By.xpath("//a[@class='use-app-lnk']"));
	 * if(myLink.isDisplayed()) myLink.click(); else {Thread.sleep(1000);} }
	 */
    
  
	/*
	 * private WebElement myLink =
	 * fluentWait(By.xpath("//a[@class='use-app-lnk']")); // wait link public
	 * WebElement fluentWait(final By locator) { Wait<WebDriver> wait = new
	 * FluentWait<WebDriver>(driver) .withTimeout(30,
	 * TimeUnit.SECONDS).pollingEvery(5,
	 * TimeUnit.SECONDS).ignoring(NoSuchElementException.class); WebElement el =
	 * wait.until(new Function<WebDriver, WebElement>() { public WebElement
	 * apply(WebDriver driver) { return driver.findElement((locator)); } });
	 * 
	 * return el; };
	 */
    
}
    
