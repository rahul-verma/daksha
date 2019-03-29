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
package arjuna.lib.core.value;

import arjuna.tpi.value.Value;

public class IncompatibleInputForValueException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 669734988933529307L;

	// Parameterless Constructor
	public <T extends Value> IncompatibleInputForValueException(String value, String actual, String valueType) {
		super(String.format("Incompatible input value >>%s<< (type: %s) supplied for creating %s.", value, actual, valueType));
	}

}
