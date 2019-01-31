package com.testmile.daksha.core.value;

import com.testmile.daksha.tpi.batteries.container.Value;
import com.testmile.daksha.tpi.enums.ValueType;

public class LongValue extends NumberValue {

	public LongValue(long number) {
		super(ValueType.LONG, number);
	}

	@Override
	public Value clone() {
		return new LongValue(this.getRawObject());
	}

	@SuppressWarnings({ "unchecked", "unused" })
	private long getRawObject() {
		return (Long) this.object();
	}

	@Override
	public long asLong() {
		return (Long) this.object();
	}
}
