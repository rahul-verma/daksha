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

package daksha.ex.ddauto.basic;

import java.util.Iterator;

import com.testmile.daksha.Daksha;
import com.testmile.setu.requester.databroker.ListDataRecord;
import com.testmile.setu.requester.databroker.ListDataSource;

public class ListDataRecordContainerExample{
	
	private static ListDataSource createListDataSource() throws Exception {
		ListDataSource source = 
				Daksha
				.createDataSourceBuilder()
				.listDataContainer()
				.record(1,2,3)
				.record(4,5,9)
				.record(7,8,12)
				.build();
		return source;
	}
	
	private static void printDataRecord(ListDataRecord record) throws Exception {
		System.out.println(record.valueAt(0).asInt());
		System.out.println(record.valueAt(1).asInt());
		System.out.println(record.valueAt(2).asInt());		
	}
	
	public static void main(String[] args) throws Exception {
		ListDataSource source = createListDataSource();
		
		// Using default Java Iterator
		Iterator<ListDataRecord> recordIter1 = source.iterRecords();
		System.out.println("With Java iterator.");
		while(recordIter1.hasNext()) {
			ListDataRecord record = recordIter1.next();
			printDataRecord(record);			
		}
	}
}

