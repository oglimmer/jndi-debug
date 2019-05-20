
# What is does

This tools dumps the JNDI tree. You can either call it explicitly (e.g. in a ServletContextListener) or you can add it to Tomcat as a Tomcat LifecycleListener.

Tomcat maintains JNDI trees per web application. So if you dump the JNDI tree inside a web application you see the JNDI for this web application. If you dump the JNDI tree from outside a web application you see the Tomcat's "global" JNDI tree.

# How to use this

## Build and copy the jar

Start with building this jar:

```
$ mvn package
```

Then copy the `target/jndi-debug.jar` to your Tomcat's lib folder and/or your project.

## Global

Add this line to Tomcat's server.xml:

```
<Listener className="de.oglimmer.catalina.DebugListener" />
```

## Web application

Add a ServletContextListener like this one:

```
@WebListener
public class JndiDebugListener implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		try {
			InitialContext ctx = new InitialContext();
			Context c1 = (Context) ctx.lookup("java:");
			de.oglimmer.jndi.JndiDebug.print("", c1);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
```

