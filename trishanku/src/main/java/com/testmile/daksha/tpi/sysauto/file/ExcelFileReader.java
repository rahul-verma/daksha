/*******************************************************************************
 * Copyright 2015-18 Test Mile Software Testing Pvt Ltd
 * 
 * Website: www.TestMile.com
 * Email: support [at] testmile.com
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package com.testmile.daksha.tpi.sysauto.file;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.DataFormatter;

import com.testmile.daksha.tpi.sysauto.utils.DataUtils;

public class ExcelFileReader {

	private FileInputStream fileIn = null;
	private POIFSFileSystem fs = null;
	private HSSFWorkbook wb = null;
	private HSSFSheet sheet = null;
	private int rowCount;
	private int colCount;
	private int currentRowIndex = 1;
	private List<String> headers = new ArrayList<String>();

	public List<String> getHeaders() {
		return this.headers;
	}

	public ExcelFileReader(String path) throws Exception {
		setFileIn(new FileInputStream(path));
		setFs(new POIFSFileSystem(getFileIn()));
		setWb(new HSSFWorkbook(getFs()));
		setSheet(getWb().getSheetAt(0));
		populateHeaders();
	}

	protected void populateHeaders() throws Exception {
		setRowCount(getSheet().getLastRowNum() + 1); //.getPhysicalNumberOfRows());
		if (getRowCount() == 0) {
			throw new Exception("Empty or wrongly formattted Excel file. Is first line empty?");
		}
		HSSFRow headerRow = getSheet().getRow(0);
		if (headerRow == null) {
			throw new Exception("Empty or wrongly formattted Excel file. Is first line empty?");
		} else {
			setColCount(headerRow.getPhysicalNumberOfCells());
		}
		for (Object o : getRowAsArrayList(0)) {
			getHeaders().add(o.toString());
		}
	}

	protected List<Object> getRowAsArrayList(int rowIndex) {
		List<Object> cellArray = new ArrayList<Object>();
		DataFormatter fmt = new DataFormatter();
		HSSFRow row = getSheet().getRow(rowIndex);
		if (row == null){
			return null;
		}
		for (int c = 0; c < getColCount(); c++) {
			HSSFCell cell = row.getCell(c);
			if (cell != null) {
				cellArray.add(fmt.formatCellValue(cell));
			} else {
				cellArray.add("NOT_PROVIDED");
			}
		}
		return cellArray;
	}

	protected Map<String, Object> zip(List<Object> values) {
		return DataUtils.zipObjectValues(this.getHeaders(), values);
	}

	public void close() throws IOException {
		if (getFileIn() != null) {
			getFileIn().close();
		}
	}

	protected int getRowCount() {
		return rowCount;
	}

	protected void setRowCount(int rowCount) {
		this.rowCount = rowCount;
	}

	protected int getColCount() {
		return colCount;
	}

	protected void setColCount(int colCount) {
		this.colCount = colCount;
	}

	protected int getCurrentRowIndex() {
		return currentRowIndex;
	}

	protected void setCurrentRowIndex(int currentRowIndex) {
		this.currentRowIndex = currentRowIndex;
	}

	protected FileInputStream getFileIn() {
		return fileIn;
	}

	protected void setFileIn(FileInputStream fileIn) {
		this.fileIn = fileIn;
	}

	protected POIFSFileSystem getFs() {
		return fs;
	}

	protected void setFs(POIFSFileSystem fs) {
		this.fs = fs;
	}

	protected HSSFWorkbook getWb() {
		return wb;
	}

	protected void setWb(HSSFWorkbook wb) {
		this.wb = wb;
	}

	protected HSSFSheet getSheet() {
		return sheet;
	}

	protected void setSheet(HSSFSheet sheet) {
		this.sheet = sheet;
	}

	protected void setHeaders(List<String> headers) {
		this.headers = headers;
	}
}
