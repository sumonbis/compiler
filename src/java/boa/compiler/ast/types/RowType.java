/*
 * Copyright 2019, Robert Dyer, 
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
package boa.compiler.ast.types;

import java.util.ArrayList;
import java.util.List;

import boa.compiler.ast.Identifier;
import boa.compiler.ast.Index;
import boa.compiler.visitors.AbstractVisitor;
import boa.compiler.visitors.AbstractVisitorNoArgNoRet;
import boa.compiler.visitors.AbstractVisitorNoReturn;

/**
 * 
 * @author rdyer
 */
public class RowType extends AbstractType {
	protected Identifier id;
	protected final List<Index> indices = new ArrayList<Index>();

	public Identifier getId() {
		return id;
	}

	public List<Index> getIndices() {
		return indices;
	}

	public void addIndex(final Index idx) {
		indices.add(idx);
	}

	public RowType(final Identifier id) {
		this.id = id;
	}

	/** {@inheritDoc} */
	@Override
	public <T,A> T accept(final AbstractVisitor<T,A> v, A arg) {
		return v.visit(this, arg);
	}

	/** {@inheritDoc} */
	@Override
	public <A> void accept(final AbstractVisitorNoReturn<A> v, A arg) {
		v.visit(this, arg);
	}

	/** {@inheritDoc} */
	@Override
	public void accept(final AbstractVisitorNoArgNoRet v) {
		v.visit(this);
	}

	public RowType clone() {
		final RowType t = new RowType(id.clone());
		for (final Index i : indices)
			t.addIndex(i.clone());
		copyFieldsTo(t);
		return t;
	}
}