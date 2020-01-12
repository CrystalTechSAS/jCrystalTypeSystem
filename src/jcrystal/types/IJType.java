package jcrystal.types;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

import jcrystal.types.loaders.IJClassLoader;
import jcrystal.types.utils.GlobalTypes;

public interface IJType extends Comparable<IJType>, JIAnnotable{

	JClass resolve();

	boolean isEnum();

	boolean isArray();

	boolean isPrimitive();
	
	boolean nullable();

	/**
	 * Este método se sobreescribe porque los tipos no cuentan con anotaciones, las anotaciones en las en las JClass
	 */
	boolean isAnnotationPresent(Class<? extends Annotation> clase);

	<A extends Annotation> A getAnnotation(Class<A> annotationClass);

	boolean isSubclassOf(Class<?> clase);

	boolean isSubclassOf(IJType clase);
	
	default boolean isSubclassOf(Class<?>...clases) {
		for(Class<?> clase : clases)
			if(isSubclassOf(clase))
				return true;
		return false;
	}

	boolean is(Class<?>... classes);

	boolean is(IJType... classes);
	
	default boolean is(String name) {
		return getName().equals(name) || getSimpleName().equals(name);
	}

	String getName();

	String getSimpleName();

	JPackage getPackage();

	String getPackageName();
	
	List<IJType> getInnerTypes();
	
	IJClassLoader classLoader();

	public default IJType getObjectType(){
	        switch (getSimpleName()){
	            case "int": return GlobalTypes.OBJ_INT;
	            case "long": return GlobalTypes.OBJ_LONG;
	            case "double": return GlobalTypes.OBJ_DOUBLE;
	            case "float": return GlobalTypes.OBJ_FLOAT;
	            case "boolean": return GlobalTypes.OBJ_BOOLEAN;
	            case "char": return GlobalTypes.OBJ_CHAR;
	            case "byte": return GlobalTypes.OBJ_BYTE;
	            case "short": return GlobalTypes.OBJ_SHORT;
	        }
	        return this;
	}
	public default IJType getPrimitiveType(){
	        switch (getSimpleName()){
	      	case "Integer": return GlobalTypes.INT;
	            case "Long": return GlobalTypes.LONG;
	            case "Double": return GlobalTypes.DOUBLE;
	            case "Float": return GlobalTypes.FLOAT;
	            case "Boolean": return GlobalTypes.BOOLEAN;
	            case "Character": return GlobalTypes.CHAR;
	            case "Byte": return GlobalTypes.BYTE;
	            case "Short": return GlobalTypes.SHORT;
	        }
	        return this;
	}
	public default boolean anyMatch(Predicate<IJType> predicate) {
		return predicate.test(this) || getInnerTypes().stream().anyMatch(f->f.anyMatch(predicate));
	}
	public default boolean isPrimitiveObjectType(){
		return is(Integer.class, Long.class, Double.class, Float.class, Boolean.class, Character.class, Byte.class, Short.class);
	}
	
	public default IJType createListType(boolean nullable) {
		JType ret = new JType(classLoader(), List.class);
		ret.nullable = nullable;
		ret.innerTypes.add(this);
		return ret;
	}
	public default IJType createListType() {
		return createListType(true);
	}
	
	public default IJType createArrayType() {
		JType ret = new JType(classLoader());
		ret.isArray = true;
		ret.primitive = false;
		ret.nullable = true;
		ret.innerTypes.add(this);
		return ret;
	}
	
	@Override
	public default int compareTo(IJType o) {
		return getName().compareTo(o.getName());
	}
	public default boolean isIterable() {
		return isSubclassOf(Iterable.class);
	}
	public default boolean isTupla() {
		return getSimpleName().startsWith("Tupla");
	}
	public default void iterate(Consumer<IJType> consumer) {
		consumer.accept(this);
		for(IJType i : getInnerTypes())
			i.iterate(consumer);
	}
	public default String prefixName(String prefix) {
		return getPackageName()+"."+prefix+getSimpleName();
	}
	public default IJType toNullable() {
		return new IJType() {
			
			@Override
			public JClass resolve() {
				return IJType.this.resolve();
			}

			@Override
			public boolean isEnum() {
				return IJType.this.isEnum();
			}

			@Override
			public boolean isArray() {
				return IJType.this.isArray();
			}

			@Override
			public boolean isPrimitive() {
				return IJType.this.isPrimitive();
			}

			@Override
			public boolean nullable() {
				return true;
			}

			@Override
			public boolean isAnnotationPresent(Class<? extends Annotation> clase) {
				return IJType.this.isAnnotationPresent(clase);
			}

			@Override
			public <A extends Annotation> A getAnnotation(Class<A> annotationClass) {
				return IJType.this.getAnnotation(annotationClass);
			}

			@Override
			public boolean isSubclassOf(Class<?> clase) {
				return IJType.this.isSubclassOf(clase);
			}
			
			@Override
			public boolean isSubclassOf(Class<?>...clases) {
				return IJType.this.isSubclassOf(clases);
			}

			@Override
			public boolean isSubclassOf(IJType clase) {
				return IJType.this.isSubclassOf(clase);
			}

			@Override
			public boolean is(Class<?>... classes) {
				return IJType.this.is(classes);
			}

			@Override
			public boolean is(IJType... classes) {
				return IJType.this.is(classes);
			}

			@Override
			public String getName() {
				return IJType.this.getName();
			}

			@Override
			public String getSimpleName() {
				return IJType.this.getSimpleName();
			}

			@Override
			public JPackage getPackage() {
				return IJType.this.getPackage();
			}

			@Override
			public String getPackageName() {
				return IJType.this.getPackageName();
			}

			@Override
			public List<JAnnotation> getAnnotations() {
				return IJType.this.getAnnotations();
			}

			@Override
			public List<IJType> getInnerTypes() {
				return IJType.this.getInnerTypes();
			}

			@Override
			public JAnnotation getJAnnotationWithAncestorCheck(String name) {
				return IJType.this.getJAnnotationWithAncestorCheck(name);
			}

			@Override
			public IJClassLoader classLoader() {
				return IJType.this.classLoader();
			}
			
		};
	}
}