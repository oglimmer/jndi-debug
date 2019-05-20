package de.oglimmer.jndi;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NameClassPair;
import javax.naming.NamingEnumeration;

public class JndiDebug {

	public static void print(String indent, Context ctx) throws Exception {
		NamingEnumeration<NameClassPair> ne = ctx.list("");
		while (ne.hasMore()) {
			NameClassPair b = ne.next();

			Class<?> clazz = Class.forName(b.getClassName());
			if (Class.forName("javax.naming.Context").isAssignableFrom(clazz)) {
				System.out.println(indent + b.getName() + " (Context) / " + b.getClassName());
				Context subCtx = (Context) ctx.lookup(b.getName());
				print(indent + "   ", subCtx);
			} else {
				boolean isRef = false;
				String description;
				if (Class.forName("javax.naming.Reference").isAssignableFrom(clazz)) {
					isRef = true;
				}
				try {
					Object obj = ctx.lookup(b.getName());
					description = obj.toString();
				} catch (Throwable e) {
					description = e.getMessage();
				}
				System.out.println(indent + b.getName() + " (" + (isRef ? "Reference" : "Object") + ") / "
						+ b.getClassName() + " / " + description);
			}
		}
	}

}
