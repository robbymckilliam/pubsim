/**
* Script for running frequency estimation simulations.
*/
import pubsim.distributions.circular.CircularRandomVariable
import pubsim.distributions.circular.WrappedUniform
import pubsim.distributions.circular.WrappedGaussian
import pubsim.distributions.GaussianNoise
import pubsim.fes.NoisyComplexSinusoid
import pubsim.fes.SamplingLatticeEstimator
import pubsim.fes.ZnLLS
import pubsim.fes.PeriodogramFFTEstimator
import pubsim.fes.PSCFDEstimator
import pubsim.fes.KaysEstimator
import pubsim.fes.QuinnFernades
import pubsim.fes.CircularNoiseSingleFrequencySignal
import pubsim.Util._

val N = 64 //number of observations
val iters = 1000 //number of iterations to run for each variance

//construct an array of noise distributions with a logarithmic scale
val noises = Range.Double(-25.0, -7, 1).map( db => scala.math.pow(10, db/10.0) ).map( v => new WrappedGaussian(0,v) ) 
//val noises = Range.Double(-25.0, -7, 1).map( db => scala.math.pow(10, db/10.0) ).map( v => new GaussianNoise(0,v) )

//function returns a tuple with random frequency in the interval [-0.5,0.5]^2 
def randparams = {
  val rand = new scala.util.Random
  (rand.nextDouble - 0.5, rand.nextDouble - 0.5)
}

val siggen =  new CircularNoiseSingleFrequencySignal(N) //noisy single frequency signal generate with phase noise
//val siggen =  new NoisyComplexSinusoid(N) //noisy single frequency signal generate with complex noise

//set the estimator you want to use
//val est = new SamplingLatticeEstimator(N, 10*N)
//val est = new ZnLLS(N)
val est = new PeriodogramFFTEstimator(N)
//val est = new PSCFDEstimator(N) 
//val est = new KaysEstimator(N)
//val est = new QuinnFernades(N)

println("var \t mse")
val starttime = (new java.util.Date).getTime
for(noise <- noises ){
  siggen.setNoiseGenerator(noise)
  
  //compute the mses
  val msetotal = (1 to iters).map{ i => 
      val (f, p) = randparams
      siggen.setFrequency(f)
      siggen.setPhase(p)
      siggen.generateReceivedSignal
      val fhat:Double = est.estimateFreq(siggen.getReal, siggen.getImag)
      fracpart(fhat - f)*fracpart(fhat - f)
    }.foldLeft(0.0)( _ + _)
    
  val mse = msetotal/iters
  val variance = scala.math.sqrt(noise.unwrappedVariance).toString.replace('E', 'e')
  //val variance = scala.math.sqrt(noise.getVariance.toString.replace('E', 'e'))
  println(variance  + "\t" + mse.toString.replace('E', 'e'))
}
val runtime = (new java.util.Date).getTime - starttime

println(est.getClass + " finished in " + (runtime/1000.0) + " seconds.\n") 