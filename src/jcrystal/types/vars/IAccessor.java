package jcrystal.types.vars;

import java.util.Collections;
import java.util.Map;

import jcrystal.types.IJType;
import jcrystal.types.JAnnotation;
import jcrystal.types.JIVariable;

public interface IAccessor extends JIVariable{
	/**
	* The needed string to access the represented property. Eg: this.color or this.color()
	* @return
	*/
	public String read();
	
	public String write(String value);
	
	default public IAccessor subProperty(IJType type, String subName) {
		return new SubPropertyAccessor(this, type, subName);
	}
	
	@Override
	default JAnnotation getJAnnotationWithAncestorCheck(String name) {
		return null;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	default Map<String, JAnnotation> getAnnotations() {
		return Collections.EMPTY_MAP;
	}
}
class SubPropertyAccessor implements IAccessor{
	final IAccessor parent;
	final IJType type;
	final String name;
	public SubPropertyAccessor(IAccessor parent, IJType type, String name) {
		this.parent = parent;
		this.type = type;
		this.name = name;
	}
	@Override
	public IJType type() {
		return type;
	}
	@Override
	public String name() {
		return parent.name();
	}
	@Override
	public String read() {
		return parent.read() + " == null ? null : " + parent.read() + "." + name;
	}
	@Override
	public String write(String value) {
		throw new NullPointerException("Not implemented");
	}
	
}
