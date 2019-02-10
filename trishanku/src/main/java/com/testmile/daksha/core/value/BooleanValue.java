package com.testmile.daksha.core.value;

import com.testmile.daksha.tpi.batteries.container.Value;
import com.testmile.daksha.tpi.enums.ValueType;

public class BooleanValue extends AbstractValue {

	public BooleanValue(boolean boolObject) {
		super(ValueType.BOOLEAN, boolObject);
	}

	@Override
	public Value clone() {
		return new BooleanValue(this.asBoolean());

	}

	@Override
	public boolean asBoolean() {
		return (boolean) this.object();
	}

}
