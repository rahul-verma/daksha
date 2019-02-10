package com.testmile.daksha.core.value;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.testmile.daksha.tpi.batteries.container.Value;
import com.testmile.daksha.tpi.enums.ValueType;

public class StringValue extends AbstractValue {
	private static Set<String> trues = new HashSet<String>(Arrays.asList("YES", "TRUE", "ON", "1"));
	private static Set<String> falses = new HashSet<String>(Arrays.asList("NO", "FALSE", "OFF", "0"));

	public StringValue(String strObject) {
		super(ValueType.STRING, strObject);
	}

	@Override
	public Value clone() {
		return new StringValue(this.asString());
	}
	
	public static boolean isSet(String value) {
		return !value.toUpperCase().trim().equals("NOT_SET");
	}
	
	@Override
	public boolean isNotSet() {
		return this.asString().toUpperCase().trim().equals("NOT_SET");
	}

	@Override
	public String asString() {
		if (this.object() != null) {
			return this.object().toString();
		} else {
			return "null";
		}
	}

	public void process(Object formatterObject, Method formatter) throws Exception {
		this.setObject((String) formatter.invoke(formatterObject, this.asString()));
	}

	@Override
	public <T2 extends Enum<T2>> T2 asEnum(Class<T2> enumClass) throws Exception {
		try {
			return Enum.valueOf(enumClass, this.asString().toUpperCase());
		} catch (Exception e) {
			this.throwUnsupportedForEnumException(ValueType.ENUM, enumClass.getSimpleName(), "asEnum");
			return null;
		}
	}
	
	@Override
	public boolean asBoolean() throws Exception {
		String uStr = this.asString().toUpperCase().trim();
		if (trues.contains(uStr)){
			return true;
		} else if (falses.contains(uStr)){
			return false;
		}
		throw new Exception(String.format(">>%s<< can not be represented as a boolean.", this.asString()));
	}

	@Override
	public Number asNumber() throws Exception {
		try{
			if (this.asString().matches("(\\-)?[0-9\\.]+")){
				try{
					return this.asFloat();
				} catch (Exception e){
					return this.asDouble();
				}
			} else if (this.asString().matches("(\\-)?[0-9]+")){
				try{
					return this.asInt();
				} catch (Exception e){
					return this.asLong();
				}				
			}
		} catch (Exception e){
			// do nothing. The subsequent exception is alright.
		}
		throw new Exception(String.format("Not supported for %s", this.getClass().getSimpleName()));
	}

	@Override
	public int asInt() throws Exception {
		try{
			return Integer.valueOf(this.asString());
		} catch (Exception e){
			throw new Exception(String.format(">>%s<< can not be represented as an integer.", this.asString()));
		}
	}

	@Override
	public long asLong() throws Exception {
		try{
			return Long.valueOf(this.asString());
		} catch (Exception e){
			throw new Exception(String.format(">>%s<< can not be represented as a long int.", this.asString()));
		}
	}

	@Override
	public double asDouble() throws Exception {
		try{
			return Double.valueOf(this.asString());
		} catch (Exception e){
			throw new Exception(String.format(">>%s<< can not be represented as a double.", this.asString()));
		}
	}

	@Override
	public float asFloat() throws Exception {
		try{
			return Float.valueOf(this.asString());
		} catch (Exception e){
			throw new Exception(String.format(">>%s<< can not be represented as a float.", this.asString()));
		}
	}

	@Override
	public <T extends Enum<T>> List<T> asEnumList(Class<T> klass) throws Exception {
		try{
			return Arrays.asList(this.asEnum(klass));
		} catch (Exception e){
			throw new Exception(String.format(">>%s<< can not be represented as a list of enum of type %s.", this.asString(), klass.getName()));
		}
	}

	@Override
	public List<Number> asNumberList() throws Exception {
		try{
			return Arrays.asList(this.asNumber());
		} catch (Exception e){
			throw new Exception(String.format(">>%s<< can not be represented as a list of numbers.", this.asString()));
		}
	}

	@Override
	public List<Integer> asIntList() throws Exception {
		try{
			return Arrays.asList(this.asInt());
		} catch (Exception e){
			throw new Exception(String.format(">>%s<< can not be represented as a list of integers.", this.asString()));
		}
	}

	@Override
	public List<String> asStringList() throws Exception {
		try{
			return Arrays.asList(this.asString());
		} catch (Exception e){
			throw new Exception(String.format(">>%s<< can not be represented as a list of strings.", this.asString()));
		}
	}

	public List<?> asList() throws Exception {
		return this.asStringList();
	}
}
