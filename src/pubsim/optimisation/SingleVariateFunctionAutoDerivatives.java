package pubsim.optimisation;

import Jama.Matrix;

public abstract class SingleVariateFunctionAutoDerivatives 
	extends AutoDerivativeFunction implements SingleVariateFunction{

	@Override
	public double value(Matrix x) {
		return value(x.get(0, 0));
	}

}
