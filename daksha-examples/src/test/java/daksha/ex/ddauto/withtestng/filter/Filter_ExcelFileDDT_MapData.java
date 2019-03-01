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

package daksha.ex.ddauto.withtestng.filter;

import static org.testng.Assert.assertEquals;

import java.util.Iterator;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.testmile.daksha.core.ddauto.DataRecord;
import com.testmile.daksha.tpi.ddauto.DataSourceFactory;
import com.testmile.daksha.tpi.testng.TestNGBaseTest;
import com.testmile.trishanku.tpi.enums.SetuOption;

public class Filter_ExcelFileDDT_MapData extends TestNGBaseTest {
	
	@DataProvider(name="dp")
	public Iterator<Object[]> linkDataSource() throws Exception {
		String fPath = this.getContext().getConfig().value(SetuOption.DATA_SOURCES_DIR) + "input_exclude_ex.xls";
		return DataSourceFactory.createDataSource(fPath).iterMapRecords();
	}
	
	@Test(dataProvider="dp")
	public void repeat(DataRecord record) throws Exception {
		int left = record.value("Left").asInt();
		int right = record.value("Right").asInt();
		int expectedSum = record.value("Sum").asInt();
		assertEquals(expectedSum, left+right);
	}
	
}

