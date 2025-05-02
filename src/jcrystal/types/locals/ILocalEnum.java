package jcrystal.types.locals;

import java.util.List;
import java.util.Map;

public interface ILocalEnum {
    String getName();
	List<ILocalEnumValue> getValues();
	Map<String, ILocalType> getProperties();
}
