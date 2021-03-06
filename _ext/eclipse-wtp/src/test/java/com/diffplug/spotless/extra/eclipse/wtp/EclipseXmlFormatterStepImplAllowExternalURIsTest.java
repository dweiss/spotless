/*
 * Copyright 2016 DiffPlug
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.diffplug.spotless.extra.eclipse.wtp;

import static org.eclipse.wst.xml.core.internal.preferences.XMLCorePreferenceNames.INDENTATION_CHAR;
import static org.eclipse.wst.xml.core.internal.preferences.XMLCorePreferenceNames.INDENTATION_SIZE;
import static org.eclipse.wst.xml.core.internal.preferences.XMLCorePreferenceNames.SPACE;
import static org.junit.Assert.*;

import java.util.Properties;

import org.junit.Before;
import org.junit.Test;

import com.diffplug.spotless.extra.eclipse.wtp.sse.PluginPreferences;

/** Test configuration allowExternalURI=false */
public class EclipseXmlFormatterStepImplAllowExternalURIsTest {
	private TestData testData = null;
	private EclipseXmlFormatterStepImpl formatter;

	@Before
	public void initializeStatic() throws Exception {
		testData = TestData.getTestDataOnFileSystem("xml");
		/*
		 * The instantiation can be repeated for each step, but only with the same configuration
		 * All formatter configuration is stored in
		 * org.eclipse.core.runtime/.settings/org.eclipse.wst.xml.core.prefs.
		 * So a simple test of one configuration item change is considered sufficient.
		 */
		Properties properties = new Properties();
		properties.put(PluginPreferences.RESOLVE_EXTERNAL_URI, "TRUE");
		properties.put(INDENTATION_SIZE, "2");
		properties.put(INDENTATION_CHAR, SPACE); //Default is TAB
		formatter = new EclipseXmlFormatterStepImpl(properties);
	}

	@Test
	public void dtdExternalPath() throws Throwable {
		String[] input = testData.input("dtd_external.test");
		String output = formatter.format(input[0], input[1]);
		assertEquals("External DTD not resolved. Restrictions are not applied by formatter.",
				testData.expected("dtd_external.test"), output);
	}

	@Test
	public void xsdExternalPath() throws Throwable {
		String[] input = testData.input("xsd_external.test");
		String output = formatter.format(input[0], input[1]);
		assertEquals("External XSD not resolved. Restrictions are not applied by formatter.",
				testData.expected("xsd_external.test"), output);
	}
}
