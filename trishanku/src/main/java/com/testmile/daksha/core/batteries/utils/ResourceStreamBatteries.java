package com.testmile.daksha.core.batteries.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.testmile.daksha.tpi.sysauto.utils.SystemUtils;

public class ResourceStreamBatteries {

	public static String streamToString(InputStream stream) throws Exception{
		StringBuilder builder = new StringBuilder();
		BufferedReader txtReader = new BufferedReader(new InputStreamReader(stream));
		String line = null;
		while ((line = txtReader.readLine()) != null) {
			builder.append(line);
			builder.append(SystemUtils.getLineSeparator());
		}
		txtReader.close();
		return builder.toString();
	}
}
