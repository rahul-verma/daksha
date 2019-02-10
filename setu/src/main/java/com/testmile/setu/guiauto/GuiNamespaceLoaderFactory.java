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
package com.testmile.setu.guiauto;

import org.apache.log4j.Logger;

import com.testmile.daksha.core.guiauto.namestore.NamespaceFileLoader;
import com.testmile.daksha.core.problem.ErrorType;
import com.testmile.daksha.core.problem.Problem;
import com.testmile.daksha.tpi.TestContext;
import com.testmile.daksha.tpi.enums.DakshaOption;
import com.testmile.daksha.tpi.enums.FileFormat;
import com.testmile.daksha.tpi.guiauto.namestore.GuiNamespaceLoader;
import com.testmile.daksha.tpi.sysauto.utils.FileSystemUtils;

public class GuiNamespaceLoaderFactory {
	private static Logger sLogger = Logger.getLogger("daksha");
	
	public static GuiNamespaceLoader createNamespaceLoader(TestContext testContext, String defPath) throws Exception{
		String ext = FileSystemUtils.getExtension(defPath).toUpperCase();
		FileFormat format = null;
		String consideredPath = defPath;
		try{
			format = FileFormat.valueOf(ext);
		} catch (Exception e){
			throw new Problem(
					"UI Automator", 
					"Namespace Loader", 
					"createNamespaceLoader", 
					ErrorType.GUI_NAMESPACE_FILE_UNSUPPORTED_FORMAT, 
					String.format(ErrorType.GUI_NAMESPACE_FILE_UNSUPPORTED_FORMAT, ext)
				);			
		}
		
		if (!FileSystemUtils.isFile(consideredPath)){
			consideredPath = FileSystemUtils.getCanonicalPath(testContext.getConfig().value(DakshaOption.GUIAUTO_NAMESPACE_DIR).asString() + "/" + consideredPath);
			if (FileSystemUtils.isDir(consideredPath)){
				throw new Problem(
						"UI Automator", 
						"Namespace Loader", 
						"createNamespaceLoader",  
						ErrorType.GUI_NAMESPACE_FILE_NOTAFILE, 
						String.format(ErrorType.GUI_NAMESPACE_FILE_NOTAFILE, consideredPath)
					);				
			} else if (!FileSystemUtils.isFile(consideredPath)){
				throw new Problem(
						"UI Automator", 
						"Namespace Loader", 
						"createNamespaceLoader", 
						ErrorType.GUI_NAMESPACE_FILE_NOT_FOUND, 
						String.format(ErrorType.GUI_NAMESPACE_FILE_NOT_FOUND, consideredPath)
					);				
			}
		}

		switch(format){
		case GNS : return new NamespaceFileLoader(consideredPath);
		default: return null;
		}
	}
}
