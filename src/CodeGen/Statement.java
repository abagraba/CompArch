package CodeGen;

import java.util.LinkedList;



public abstract class Statement {

	public abstract LinkedList<QUAD> resolve(Method method);

}
