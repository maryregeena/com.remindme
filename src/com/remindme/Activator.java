package com.remindme;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends AbstractUIPlugin {


	// The plug-in ID
	public static final String PLUGIN_ID = "com.remindme"; 
	public static final List<String> reminders=new ArrayList<String>();
	public static Timer timer=null;
	public static int timing=30;
	
	// The shared instance
	private static Activator plugin;
	
	static{
		loadReminders();
	}
	
	/**
	 * Load the reminders
	 */
	private static void loadReminders(){
		
		URL fileURL = FileLocator.find(Platform.getBundle(PLUGIN_ID), new Path("files/reminders.dat"), null);
		URL filePath=null;
		try {
			filePath = FileLocator.toFileURL(fileURL);
		} catch (IOException e) {
			e.printStackTrace();
		}

		try(BufferedReader in = new BufferedReader(new FileReader(filePath.getFile()))){
	          String line;
	          while((line = in.readLine())!=null){
	              String[] remindMe_reminders = line.split("\r",-1);
	              reminders.addAll(new ArrayList<String>(Arrays.asList(remindMe_reminders)));
	          }
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		//System.out.println(reminders.size());
	}
	
	/**
	 * Get the reminders list
	 * @return
	 */
	public static List<String> getReminders() {
		return reminders;
	}
	
	/**
	 * The constructor
	 */
	public Activator() {
		
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
	    
		System.out.println("RemindMe Plugin Loaded");	
	}


	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
		if(timer!=null){
			timer.cancel();
			timer=null;
			System.out.println("eclipse closed");	
		}
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static Activator getDefault() {
		return plugin;
	}

	/**
	 * Returns an image descriptor for the image file at the given
	 * plug-in relative path
	 *
	 * @param path the path
	 * @return the image descriptor
	 */
	public static ImageDescriptor getImageDescriptor(String path) {
		return imageDescriptorFromPlugin(PLUGIN_ID, path);
	}

}
