package com.pertamina.tbbm.rewulu.ecodriving.helpers;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Type;

import retrofit.converter.ConversionException;
import retrofit.converter.Converter;
import retrofit.mime.FormUrlEncodedTypedOutput;
import retrofit.mime.TypedInput;
import retrofit.mime.TypedOutput;

public class LogConverter implements Converter {

	@Override
	public Object fromBody(TypedInput arg0, Type arg1)
			throws ConversionException {
		// TODO Auto-generated method stub

		return null;
	}

	@Override
	public TypedOutput toBody(Object arg0) {
		// TODO Auto-generated method stub
		if (!arg0.getClass().isArray()) {
			// TODO Maybe implement this?
			throw new UnsupportedOperationException();
		}
		try {
			FormUrlEncodedTypedOutput form = new FormUrlEncodedTypedOutput();
			for (int i = 0, size = Array.getLength(arg0); i < size; i++) {
				Object item = Array.get(arg0, i);
				for (Field field : item.getClass().getFields()) {
					String value = String.valueOf(field.get(item));
					form.addField("logdatas[" + i + "][" + field.getName() + "]",
							value);
				}
			}
			return form;
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

}
