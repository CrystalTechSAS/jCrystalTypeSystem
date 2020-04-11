package jcrystal.types;

import java.lang.reflect.Modifier;

public interface JIVariable extends JIAnnotable {

	IJType type();
	
	/**
	* The var name. Eg: color
	* @return
	*/
	String name();

	default int modifiers() {
		return 0;
	}

	default boolean isPublic() {
		return Modifier.isPublic(modifiers());
	}
	default boolean isStatic() {
		return Modifier.isStatic(modifiers());
	}
	default boolean isFinal() {
		return Modifier.isFinal(modifiers());
	}
	default boolean isPrivate() {
		return Modifier.isPrivate(modifiers());
	}
	default boolean isProtected() {
		return Modifier.isProtected(modifiers());
	}
}