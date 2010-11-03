/**
* Script for running frequency estimation simulations.
*/
import robbysim.distributions.circular.CircularRandomVariable
import robbysim.distributions.circular.WrappedUniform
import robbysim.fes.NoisyComplexSinusoid
import robbysim.fes.CircularNoiseSingleFrequencySignal

val N = 64 //number of observations
val iters = 1000 //number of iterations to run for each variance

//construct an array of noise distributions with a logarithmic scale
val noises = Range.Double(-25.0, -7, 1).map( db => scala.math.pow(10, db/10.0) ).map( v => new WrappedUniform(0,v) ) 

//function returns a tuple with random frequency in the interval [-0.5,0.5]^2 
def randparams = {
  val rand = new scala.util.Random
  (rand.nextDouble - 0.5, rand.nextDouble - 0.5)
}

val siggen =  new CircularNoiseSingleFrequencySignal(N) //noisy single frequency signal generate with phase noise
//val siggen =  new NoisyComplexSinusoid(N) //noisy single frequency signal generate with complex noise


