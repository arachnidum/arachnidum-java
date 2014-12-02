package com.github.arachnidium.tutorial.simple.mobile;

import io.appium.java_client.pagefactory.AndroidFindBy;

import org.openqa.selenium.WebElement;
import com.github.arachnidium.core.Handle;
import com.github.arachnidium.model.common.FunctionalPart;

public class UserScreen<S extends Handle> extends FunctionalPart<S> {
   
	@AndroidFindBy(uiAutomator="new UiSelector().resourceId(\"android:id/home\")")
	private WebElement home; 
	@AndroidFindBy(uiAutomator="new UiSelector().text(\"Videos\")")
	private WebElement videos;
	
	protected UserScreen(S handle) {
		super(handle);
	}
	
	@InteractiveMethod
	public void goHome(){
		home.click();
	}
	
	@InteractiveMethod
	public void selectVideos(){
		videos.click();
	}

}
