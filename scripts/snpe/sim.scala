/**
* Run simulations for the sparse noisy period estimation problem.
* Various estimators are available.
*/
import pubsim.snpe.SparseNoisyPeriodicSignal
import pubsim.distributions.GaussianNoise
import pubsim.distributions.discrete.GeometricRandomVariable
import pubsim.distributions.discrete.PoissonRandomVariable
import pubsim.snpe.PeriodogramEstimator
import pubsim.snpe.SamplingLLS
import pubsim.VectorFunctions
import pubsim.snpe.NormalisedPeriodogram
import pubsim.snpe.ZnLLS
import pubsim.snpe.PRIEstimator
import pubsim.snpe.NormalisedSamplingLLS
import pubsim.snpe.NormalisedZnLLS
import pubsim.snpe.SLS2all
import pubsim.snpe.SLS2new
import pubsim.snpe.SLS2novlp
import pubsim.snpe.Util

val iters = 1000 //number of trials run per simualtion.
val N = 100 //values of N we will generate curves for
val Tmin = 0.7 //minimum value of period
val Tmax = 1.4 //maximum value of period.
val T = 1.0 //value of the true period
val uY = 10 //mean of the discrete sparse signal

//construct an array of noise distributions with a logarithmic scale
val noises = Range.Double(-25.0, -7, 1).map( db => scala.math.pow(10, db/10.0) ).map( v => new GaussianNoise(0,v) ) 

def randphase = ( (new scala.util.Random).nextDouble() ) * (Tmax -Tmin) //function to randomise the phase
  
val siggen =  new SparseNoisyPeriodicSignal(N)  //construct our signal generator
//siggen.setSparseGenerator(new GeometricRandomVariable(0.25))
siggen.setSparseGenerator(new PoissonRandomVariable(uY)) //set the discrete sparse generator we are using.
siggen.setPeriod(T) //set the true period

//set the estimator you want to use
val est = new NormalisedSamplingLLS(N, 100) //second parameter here is the number of sample used in the approximation.
//val est = new SamplingLLS(N, 64*N),
//val est = new PeriodogramEstimator(N, 2*N) 
//val est = new NormalisedZnLLS(N)
//val est = new ZnLLS(N)

println("stdev \t mse(period) \t mse(phase)")
val starttime = (new java.util.Date).getTime
for(noise <- noises ){
  siggen.setNoiseGenerator(noise)
  
  //compute the mses
  val (mseTtotal, msePtotal) = (1 to iters).map{ i => 
      siggen.generateSparseSignal(N)
      val phase = randphase
      siggen.setPhase(phase)
      est.estimate(siggen.generateReceivedSignal(), Tmin, Tmax)
      ( Util.periodError(est.getPeriod, T) , Util.phaseError(est.getPhase, phase, T) )
    }.foldLeft( (0.0,0.0) )( (p, t)  => (p._1 + t._1, p._2 + t._2)  )
    
  val (mseT, mseP) =  (mseTtotal/iters, msePtotal/iters) 
  val stddevstr = scala.math.sqrt(noise.getVariance).toString.replace('E', 'e')
  println(stddevstr  + "\t" + mseT.toString.replace('E', 'e') + "\t" + mseP.toString.replace('E', 'e'))
}
val runtime = (new java.util.Date).getTime - starttime

println(est.getClass + " finished in " + (runtime/1000.0) + " seconds.\n") 
    
