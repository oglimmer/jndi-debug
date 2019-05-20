package de.oglimmer.catalina;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.apache.catalina.Lifecycle;
import org.apache.catalina.LifecycleEvent;
import org.apache.catalina.LifecycleListener;

import de.oglimmer.jndi.JndiDebug;

public class JndiDebugListener implements LifecycleListener {
	
	@Override
	public void lifecycleEvent(LifecycleEvent event) {
		if (event.getType() == Lifecycle.PERIODIC_EVENT || event.getType() == Lifecycle.AFTER_START_EVENT) {
			try {
				InitialContext ctx = new InitialContext();
				Context c1 = (Context) ctx.lookup("java:");
				JndiDebug.print("", c1);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
