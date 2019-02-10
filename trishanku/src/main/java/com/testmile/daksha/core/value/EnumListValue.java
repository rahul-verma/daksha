package com.testmile.daksha.core.value;

import java.util.ArrayList;
import java.util.List;

import com.testmile.daksha.tpi.batteries.container.Value;
import com.testmile.daksha.tpi.enums.ValueType;

public class EnumListValue<T extends Enum<T>> extends AbstractValue {
	Class<T> actualEnumClass = null;
	boolean empty = false;

	public EnumListValue(List<T> enumListObject) {
		super(ValueType.ENUM_LIST, enumListObject);
		if (enumListObject.size() == 0) {
			empty = true;
			this.actualEnumClass = null;
		} else {
			this.actualEnumClass = (Class<T>) enumListObject.get(0).getClass();
		}
	}

	private EnumListValue(Class<T> klass, Object obj) {
		super(ValueType.ENUM_LIST, obj);
		this.actualEnumClass = klass;
	}

	@Override
	public Value clone() {
		return new EnumListValue<T>(actualEnumClass, this.object());
	}

	@Override
	public List<String> asStringList() throws Exception {
		List<String> retVal = new ArrayList<String>();
		for (T obj : this.asEnumList(this.actualEnumClass)) {
			retVal.add(obj.toString());
		}
		return retVal;
	}

	@Override
	public <T2 extends Enum<T2>> List<T2> asEnumList(Class<T2> enumClass) throws Exception {
		if (this.empty) {
			return new ArrayList<T2>();
		}
		if (actualEnumClass == enumClass) {
			return (List<T2>) this.object();
		} else {
			this.throwUnsupportedEnumForEnumException(actualEnumClass.getSimpleName(), ValueType.ENUM_LIST, enumClass.getSimpleName(), "asEnumList");
			return null;
		}
	}

	public Class<T> getEnumClass() {
		return actualEnumClass;
	}
}
