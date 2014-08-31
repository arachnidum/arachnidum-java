package org.arachnidium.model.mobile;

import org.arachnidium.core.ScreenManager;
import org.arachnidium.core.HowToGetMobileScreen;
import org.arachnidium.core.MobileScreen;
import org.arachnidium.model.common.Application;

/**
 * Representation of a mobile application
 */
public abstract class MobileApplication extends Application<MobileScreen, HowToGetMobileScreen> {

	protected MobileApplication(MobileScreen context) {
		super(context);
	}

	@SuppressWarnings("unchecked")
	@Override
	public ScreenManager getManager() {
		return (ScreenManager) super.getManager();
	}

}