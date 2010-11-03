/**
* Script for running frequency estimation simulations.
*/
import robbysim.distributions.circular.CircularRandomVariable
import robbysim.distributions.circular.WrappedUniform
import robbysim.distributions.circular.WrappedGaussian
import robbysim.distributions.GaussianNoise
import robbysim.fes.NoisyComplexSinusoid
import robbysim.fes.SamplingLatticeEstimator
import robbysim.fes.ZnLLS
import robbysim.fes.PeriodogramFFTEstimator
import robbysim.fes.PSCFDEstimator
import robbysim.fes.KaysEstimator
import robbysim.fes.QuinnFernades
import robbysim.fes.CircularNoiseSingleFrequencySignal
import robbysim.Util._

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