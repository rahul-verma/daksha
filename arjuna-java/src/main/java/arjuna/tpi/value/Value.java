package arjuna.tpi.value;

import java.util.List;

public interface Value{

	Object object();

	boolean isNotSet();
	
	boolean isNull();
	
	boolean isNA();

	boolean asBoolean() throws Exception;

	Number asNumber() throws Exception;

	int asInt() throws Exception;

	long asLong() throws Exception;

	double asDouble() throws Exception;

	String asString() throws Exception;

	<T extends Enum<T>> T asEnum(Class<T> enumClass) throws Exception;

	<T extends Enum<T>> List<T> asEnumList(Class<T> klass) throws Exception;

	List<Number> asNumberList() throws Exception;

	List<Integer> asIntList() throws Exception;

	List<String> asStringList() throws Exception;

	float asFloat() throws Exception;

}