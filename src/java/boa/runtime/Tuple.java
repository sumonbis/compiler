/*
 * Copyright 2018, Robert Dyer
 *                 and Bowling Green State University
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
package boa.runtime;

import boa.output.Output.Row;


/**
 * Boa tuple base class
 *
 * @author rdyer
 */
public abstract class Tuple {
	public boolean def = true;


	public void fromRow(final Row r, final int offset) {
	}

	public void columnFromRow(final Row r, final int col) {
	}
}