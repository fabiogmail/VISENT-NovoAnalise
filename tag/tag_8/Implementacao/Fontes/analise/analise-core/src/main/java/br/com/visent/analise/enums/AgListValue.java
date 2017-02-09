package br.com.visent.analise.enums;

import java.util.ArrayList;
import java.util.List;

public class AgListValue<E> {

	private List<E> values = new ArrayList<>();

	public List<E> getValues() {
		return values;
	}

	public void setValues(List<E> values) {
		this.values = values;
	}
	
	public void add(E value) {
		values.add(value);
	}
	
	public boolean isEmpty() {
		return values.isEmpty();
	}

	@Override
	public String toString() {
		return "AgSetValue [values=" + values + "]";
	}
	
}
