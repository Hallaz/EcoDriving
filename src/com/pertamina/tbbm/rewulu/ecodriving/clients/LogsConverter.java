package com.pertamina.tbbm.rewulu.ecodriving.clients;

import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.List;

import retrofit.converter.ConversionException;
import retrofit.converter.Converter;
import retrofit.mime.FormUrlEncodedTypedOutput;
import retrofit.mime.MimeUtil;
import retrofit.mime.TypedInput;
import retrofit.mime.TypedOutput;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.pertamina.tbbm.rewulu.ecodriving.pojos.DataLog;

public class LogsConverter implements Converter {

	@SuppressWarnings("deprecation")
	@Override
	public Object fromBody(TypedInput arg0, Type arg1)
			throws ConversionException {
		// TODO Auto-generated method stub
		String charset = "UTF-8";
		final Gson gson = new Gson();
		if (arg0.mimeType() != null) {
			charset = MimeUtil.parseCharset(arg0.mimeType());
		}
		InputStreamReader isr = null;
		try {
			isr = new InputStreamReader(arg0.in(), charset);
			return gson.fromJson(isr, arg1);
		} catch (IOException e) {
			throw new ConversionException(e);
		} catch (JsonParseException e) {
			throw new ConversionException(e);
		} finally {
			if (isr != null) {
				try {
					isr.close();
				} catch (IOException ignored) {
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public TypedOutput toBody(Object arg0) {
		// TODO Auto-generated method stub
		if (arg0 instanceof List<?>) {
			// TODO Maybe implement this?
			try {
				FormUrlEncodedTypedOutput form = new FormUrlEncodedTypedOutput();
				List<DataLog> logs = (List<DataLog>) arg0;
				for (int i = 0, size = logs.size(); i < size; i++) {
					Object item = logs.get(i);
					for (Field field : item.getClass().getFields()) {
						String value = String.valueOf(field.get(item));
						form.addField("logs[" + i + "][" + field.getName()
								+ "]", value);
					}
				}
				return form;
			} catch (IllegalAccessException e) {
				throw new RuntimeException(e);
			}
		} else {
			throw new UnsupportedOperationException();
		}
	}

}
