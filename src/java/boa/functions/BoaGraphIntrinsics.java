package boa.functions;

import boa.analysis.ds.Graph;
import boa.graphs.cfg.CFG;
import boa.types.Ast.Method;

/**
 * Boa functions for working with control flow graphs.
 * 
 * @author ganeshau
 *
 */
public class BoaGraphIntrinsics {

	public static Graph getcfg(final Method method, final String cls) {
		CFG cfg = new CFG(method, cls);
		cfg.astToCFG();
		return new Graph(method, cfg.newBuilder().build());
	}
}