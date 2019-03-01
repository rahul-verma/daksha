/*******************************************************************************
 * Copyright 2015-19 Test Mile Software Testing Pvt Ltd
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

package com.testmile.daksha.core.ddauto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.testmile.daksha.tpi.ddauto.DataRecordContainer;
import com.testmile.daksha.tpi.ddauto.DataSourceFinishedException;
import com.testmile.daksha.tpi.ddauto.ListDataRecord;
import com.testmile.daksha.tpi.ddauto.MapDataRecord;
import com.testmile.trishanku.tpi.enums.DataRecordOrder;

public abstract class BaseDataRecordContainer extends BaseDataSource implements DataRecordContainer {
	private DataRecordOrder order = DataRecordOrder.ORDERED;
	private List<Object[]> queue = new ArrayList<Object[]>();
	private String[] headers = null;
	private boolean headersDone = false;
	private int refLen = -1;

	public BaseDataRecordContainer(DataRecordOrder order) {
		this.order = order;
	}

	public BaseDataRecordContainer() {
		this(DataRecordOrder.ORDERED);
	}

	public BaseDataRecordContainer(Object[][] records) throws Exception {
		this.addAll(records);
	}
	
	protected String[] getHeaders(){
		return this.headers;
	}
	
	@Override
	public void setHeaders(String[] names) throws Exception {
		if (!headersDone){
			if (refLen != -1){
				if (names.length != refLen){
					throw new Exception(String.format("Length of headers should match the length first record in the container: %d. Headers Provided: [%s]", refLen, Arrays.toString(names)));
				}
			} else {
				this.refLen = names.length;
			}

			this.headers = names;
			headersDone = true;
			
		} else {
			throw new Exception("You can setup headers only once for a Data Record Container.");
		}
	}

	@Override
	public void add(Object[] record) throws Exception {
		if (refLen != -1){
			if (record.length != refLen){
				if (headersDone){
					throw new Exception(String.format("All records must match the length of headers: %d. Current Record Length: %d. Record: [%s]", refLen, record.length, Arrays.toString(record)));					
				} else {
					throw new Exception(String.format("All records must match the length of first record provided by you: %d. Current Record Length: %d. Record: [%s]", refLen, record.length, Arrays.toString(record)));
				}
			}
		} else {
			this.refLen = record.length;
		}
		this.queue.add(record);
	}

	@Override
	public void addAll(Object[][] records) throws Exception {
		for (Object[] record : records) {
			this.add(record);
		}
	}
	
	private synchronized Object[] nextRaw() throws DataSourceFinishedException, Exception{
		if ((isTerminated()) || (this.queue.size() == 0)){
			throw new DataSourceFinishedException();
		} else {
			return this.queue.remove(0);
		}		
	}

	@Override
	public DataRecord next() throws DataSourceFinishedException, Exception {
		Object[] n = nextRaw();
		if (this.headersDone){
			return new MapDataRecord(headers, n);
		} else {
			return new ListDataRecord(n);
		}
	}
	
	public boolean hasNext(){
		if (isTerminated()){
			return false;
		}
		
		if (this.queue.size() != 0){
			return true;
		}
		
		return false;
	}
	
	protected synchronized DataRecord nextListRecord() throws DataSourceFinishedException, Exception{
		Object[] n = nextRaw();
		return new ListDataRecord(n);
	}
	
	protected synchronized DataRecord nextMapRecord() throws DataSourceFinishedException, Exception{
		Object[] n = nextRaw();
		if (this.headersDone){
			return new MapDataRecord(headers, n);
		} else {
			throw new Exception("This data container is without header. So, it does not support map record iteration.");
		}
	}
}
