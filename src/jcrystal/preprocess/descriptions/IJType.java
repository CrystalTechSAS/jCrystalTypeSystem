package jcrystal.preprocess.descriptions;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.stream.Collectors;

public interface IJType extends Comparable<IJType>{

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

	boolean is(Class<?>... classes);

	boolean is(IJType... classes);
	
	default boolean is(String name) {
		return getName().equals(name) || getSimpleName().equals(name);
	}

	String getName();

	String getSimpleName();

	JPackage getPackage();

	String getPackageName();

	List<JAnnotation> getAnnotations();
	
	List<IJType> getInnerTypes();

	public default IJType getObjectType(){
	        switch (getSimpleName()){
	            case "int": return JTypeSolver.OBJ_INT;
	            case "long": return JTypeSolver.OBJ_LONG;
	            case "double": return JTypeSolver.OBJ_DOUBLE;
	            case "float": return JTypeSolver.OBJ_FLOAT;
	            case "boolean": return JTypeSolver.OBJ_BOOLEAN;
	            case "char": return JTypeSolver.OBJ_CHAR;
	            case "byte": return JTypeSolver.OBJ_BYTE;
	            case "short": return JTypeSolver.OBJ_SHORT;
	        }
	        return this;
	}
	public default IJType getPrimitiveType(){
	        switch (getSimpleName()){
	      	case "Integer": return JTypeSolver.INT;
	            case "Long": return JTypeSolver.LONG;
	            case "Double": return JTypeSolver.DOUBLE;
	            case "Float": return JTypeSolver.FLOAT;
	            case "Boolean": return JTypeSolver.BOOLEAN;
	            case "Character": return JTypeSolver.CHAR;
	            case "Byte": return JTypeSolver.BYTE;
	            case "Short": return JTypeSolver.SHORT;
	        }
	        return this;
	}

	public default boolean isPrimitiveObjectType(){
		return is(Integer.class, Long.class, Double.class, Float.class, Boolean.class, Character.class, Byte.class, Short.class);
	}
	
	public default IJType createListType(boolean nullable) {
		JType ret = new JType(List.class);
		ret.nullable = nullable;
		ret.innerTypes.add(this);
		return ret;
	}
	public default IJType createListType() {
		return createListType(true);
	}
	
	public default IJType createArrayType() {
		JType ret = new JType();
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
			
		};
	}
}