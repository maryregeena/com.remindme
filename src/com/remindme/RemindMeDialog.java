package com.remindme;

import java.awt.Toolkit;
import java.util.Date;

import org.eclipse.jface.resource.FontDescriptor;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

public class RemindMeDialog extends Dialog {

	private String message="";

	/**
	 * Message Dialog constructor
	 * 
	 * @param parent
	 * @param message
	 */
	public RemindMeDialog(Shell parent, String message) {
		// Pass the default styles here
		this(parent, SWT.APPLICATION_MODAL | SWT.CLOSE, message);
		open(parent);
	}

	/**
	 * Message Dialog constructor
	 * 
	 * @param parent
	 * @param style
	 * @param message     
	 */
	public RemindMeDialog(Shell parent, int style, String message) {
		// Let users override the default styles
		super(parent,style);
		
		setMessage(message);
	}

	/**
	 * Gets the message
	 * 
	 * @return String
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Sets the message
	 * 
	 * @param message
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * Opens the dialog
	 */
	public void open(Shell shell)  {
		Toolkit tools=Toolkit.getDefaultToolkit();
		// Create the dialog window
		createContents(shell);
		shell.setSize(800, 250);
	
		shell.open();
		tools.beep();
		System.out.println("opened"+new Date().toString());
		
		shell.addDisposeListener(new DisposeListener() {
			@Override
			public void widgetDisposed(DisposeEvent e) {
				System.out.println("closed");
			}
		});
		
		int i=0;
	    Display display = shell.getDisplay();
	    Rectangle screenSize = display.getPrimaryMonitor().getBounds();
		shell.setLocation((screenSize.width - shell.getBounds().width) / 2, (screenSize.height - shell.getBounds().height) / 2);

	    while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				i++;
				if(i==700){
					shell.close();
				}
				display.sleep();
			}
		}
	}

	/**
	 * Creates the dialog's contents
	 * 
	 * @param shell
	 */
	private void createContents(final Shell shell) {
		shell.setLayout(new GridLayout());
		ImageDescriptor imageDesc=Activator.getImageDescriptor("images/remindmelogopic.png");
		Image image=imageDesc.createImage(shell.getDisplay());
		shell.setToolTipText("RemindMe");
		shell.setText("RemindMe");
		
		GridData data = new GridData(SWT.CENTER, SWT.CENTER, true, true, 1, 1);
		Label tip = new Label(shell, SWT.WRAP | SWT.CENTER);
		tip.setLayoutData(data);
		
		FontDescriptor boldDescriptor = FontDescriptor.createFrom(tip.getFont()).increaseHeight(9);
		Font boldFont = boldDescriptor.createFont(tip.getDisplay());
		tip.setFont( boldFont );
		tip.setText(getMessage());

		// Create the OK button and add a handler
		Button ok = new Button(shell, SWT.PUSH);
		ok.setText("OK");
		data = new GridData(SWT.CENTER, SWT.TOP, true, true, 1, 1);
		ok.setLayoutData(data);
		
		ok.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				shell.close();
			}
		});

		// Set the OK button as the default, so
		shell.setDefaultButton(ok);
		
		Label logo = new Label(shell,  SWT.RIGHT);
		data = new GridData(SWT.RIGHT, SWT.RIGHT, true, true, 1, 1);
		logo.setLayoutData(data);
		logo.setImage(image);
	}

	public static void main(String[] args) {
		final Shell shell = new Shell();
		new RemindMeDialog(shell, "Hold one foot off the floor with your leg straight. \n Flex your ankle pointing your toes up.\n Extend you ankle pointing your toes down.\n Do ten times and repeat with other leg.\n Next, draw a circle with your toes, moving one foot clockwise and then counter-clockwise. \nChange feet.");

	}
}