package com.testmile.daksha.core.batteries.container;

import java.util.Map;

import com.testmile.daksha.tpi.batteries.container.Value;

public interface ValueContainer<T> extends ReadWriteContainer<T, Value>, Cloneable {

	void cloneAdd(Map<T, Value> map);

	void cloneAdd(T k, Value v);

	void cloneAdd(Container<T, Value> container) throws Exception;

	void addAsStringValue(Map<T, String> map);

	void addAsStringValue(T k, String str);
}
