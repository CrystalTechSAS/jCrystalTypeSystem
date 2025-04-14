package jcrystal.types.vars;

public abstract class AbsJAccessor implements IAccessor{
	protected String prefix;
	protected boolean property;
	public AbsJAccessor prefix(String prefix) {
		this.prefix = prefix;
		return this;
	}
	public AbsJAccessor $this() {
		this.prefix = "this.";
		return this;
	}
	public AbsJAccessor asField() {
		this.property = false;
		return this;
	}
	public AbsJAccessor asProperty() {
		this.property = true;
		return this;
	}
	@Override
	public String write(String value) {
		String ret = "";
		if(prefix != null)
			ret = prefix;
		ret += name();
		if(property)
			ret += "(" + value + ")";
		else
			ret += " = " + value;
		return ret;
	}
	
	@Override
	public String read() {
		String ret = "";
		if(prefix != null)
			ret = prefix;
		ret += name();
		if(property)
			ret += "()";
		return ret;
	}
}
