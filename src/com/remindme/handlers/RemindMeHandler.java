package com.remindme.handlers;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ThreadLocalRandom;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;

import com.remindme.Activator;
import com.remindme.RemindMeDialog;

/**
 * RemindMeHandler extends AbstractHandler, an IHandler base class.
 * 
 * @see org.eclipse.core.commands.IHandler
 * @see org.eclipse.core.commands.AbstractHandler
 */
public class RemindMeHandler extends AbstractHandler {

	List<String> reminders = Activator.getReminders();

	/**
	 * The constructor.
	 */
	public RemindMeHandler() {
	}

	/**
	 * the command has been executed, so extract extract the needed information
	 * from the application context.
	 */
	public Object execute(ExecutionEvent event) throws ExecutionException {
	    Event selEvent = (Event) event.getTrigger();
		MenuItem item = (MenuItem) selEvent.widget;
		item.setEnabled(false);
	
		if(event.getCommand().getId().equals("remindMe.commands.remindMeStartStopCommand")){
			if(Activator.timer==null){
				Activator.timer=new Timer();
				startTimer();
			}else {
				stopTimer();
			}
		}
		return Activator.timer;
	}
	
	public void startTimer(){
		
		Activator.timer.schedule(new TimerTask() {
				
				@Override
				public void run() {
					Display.getDefault().asyncExec(new Runnable() {
						@Override
						public void run() {
							new RemindMeDialog(new Shell(),
									reminders.get(ThreadLocalRandom.current().nextInt(reminders.size())).toString());
						}
					});
				}
			}, 0, Activator.timing*60*1000);
		System.out.println("Started RemindMe");
	}
	
	public void stopTimer(){
		if(Activator.timer!=null){
			Activator.timer.cancel();
			Activator.timer=null;
			System.out.println("Stopped RemindMe");
		}
	}


	public List<String> getReminders() {
		return reminders;
	}

	public void setReminders(List<String> reminders) {
		this.reminders = reminders;
	}
	

}
