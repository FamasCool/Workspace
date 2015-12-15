package com.qty.nhwa;

import java.awt.EventQueue;

import javax.swing.JApplet;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

/*
<applet code="NotHelloWorldApplet.class" width="300" height="300">
</applet>
 */
public class NotHelloWorldApplet extends JApplet {

	public void init() {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				JLabel label = new JLabel("Not a Hello, World applet", SwingConstants.CENTER);
				add(label);
			}
		});
	}
}
