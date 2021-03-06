/*
 * Copyright 2014, Anthony Urso, Hridesh Rajan, Robert Dyer, 
 *                 and Iowa State University of Science and Technology
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
package boa.types;

/**
 * @author anthonyu
 */
public class BoaName extends BoaScalar {
	private final BoaType type;
	private final String id;

	public BoaName(final BoaType type, final String id) {
		this.type = type;
		this.id = id;
	}

	public BoaName(final BoaType type) {
		this(type, type.toString());
	}

	public BoaType getType() {
		return this.type;
	}

	public String getId() {
		return this.id;
	}

	/** {@inheritDoc} */
	@Override
	public boolean assigns(final BoaType that) {
		if (that instanceof BoaName)
			return this.type.assigns(((BoaName) that).getType());
		return this.type.assigns(that);
	}

	/** {@inheritDoc} */
	@Override
	public boolean accepts(final BoaType that) {
		return this.type.accepts(that);
	}

	@Override
	public String toString() {
		return this.id + ":" + this.type;
	}

	@Override
	public String toJavaType() {
		return this.type.toJavaType();
	}

	@Override
	public String toBoxedJavaType() {
		return this.type.toBoxedJavaType();
	}

	/** {@inheritDoc} */
	@Override
	public String defaultValue() {
		return this.type.defaultValue();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + (this.id == null ? 0 : this.id.hashCode());
		result = prime * result + (this.type == null ? 0 : this.type.hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (this.getClass() != obj.getClass())
			return false;
		final BoaName other = (BoaName) obj;
		if (this.id == null) {
			if (other.id != null)
				return false;
		} else if (!this.id.equals(other.id))
			return false;
		if (this.type == null) {
			if (other.type != null)
				return false;
		} else if (!this.type.equals(other.type))
			return false;
		return true;
	}
}
