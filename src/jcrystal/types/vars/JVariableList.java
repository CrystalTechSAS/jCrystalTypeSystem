package jcrystal.types.vars;

import java.util.ArrayList;
import java.util.function.Function;
import java.util.stream.Collectors;

import jcrystal.types.JVariable;
import jcrystal.utils.langAndPlats.AbsCodeBlock.PL;

public class JVariableList {
	public final ArrayList<JVariable> list;
    public static final PL EMPTY = new PL();
    public JVariableList(){
        this.list = new ArrayList<>();
    }
    public JVariableList(java.util.List<JVariable> list){
        this.list = new ArrayList<>(list);
    }
    public JVariableList(JVariable...list){
        this.list = new ArrayList<>(list.length);
        for(JVariable v : list)
        	this.list.add(v);
    }
    protected JVariableList(java.util.List<JVariable> list, JVariable...list2){
          this.list = new ArrayList<>(list.size() + list2.length);
          this.list.addAll(list);
          for(JVariable v : list)
          	this.list.add(v);
      }
    public final void add(JVariable p){
    	list.add(p);
    }
    public String collect(Function<JVariable, String> mapper) {
    	return list.stream().map(mapper).collect(Collectors.joining(", "));
    }
    public String collect(String prefix, Function<JVariable, String> mapper) {
    	String ret = list.stream().map(mapper).collect(Collectors.joining(", "));
    	if(ret.isEmpty())
    		return prefix;
    	return prefix+", "+ret;
    }
    public String collect(Function<JVariable, String> mapper, String suffix) {
    	String ret = list.stream().map(mapper).collect(Collectors.joining(", "));
    	if(ret.isEmpty())
    		return suffix;
    	return ret + ", "+suffix;
    }
    public void adding(JVariable p,Runnable r) {
    	list.add(p);
    	r.run();
    	list.remove(list.size()-1);
    }
    public void pop() {
    	list.remove(list.size()-1);
    }
}
