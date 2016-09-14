package com.remindme.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.MenuItem;

import com.remindme.Activator;

/**
 *  RemindMeSettingsHandler extends AbstractHandler, an IHandler base class.
 * 
 * @see org.eclipse.core.commands.IHandler
 * @see org.eclipse.core.commands.AbstractHandler
 */
public class RemindMeSettingsHandler extends AbstractHandler {

	

	/**
	 * The constructor.
	 */
	public RemindMeSettingsHandler() {
	}

	/**
	 * the command has been executed, so extract extract the needed information
	 * from the application context.
	 */
	public Object execute(ExecutionEvent event) throws ExecutionException {
	    Event selEvent = (Event) event.getTrigger();
		if(selEvent.widget instanceof MenuItem){
			MenuItem item = (MenuItem) selEvent.widget;
			IContributionItem itemDetails=(IContributionItem)item.getData();
			Activator.timing=Integer.parseInt(itemDetails.getId());
			System.out.println(Activator.timing);
		 }
		return null;
	}

}
