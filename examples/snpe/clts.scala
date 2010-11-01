/**
* Generate the central limit theorem curves for the sparse noisy period estimation problem.
*/
import robbysim.pes.bounds.CLT
import robbysim.distributions.GaussianNoise

val N = 32 //set the number of observations
val T = 1.0 //set the value of the true period
val uY = 4 //mean of the discrete sparse signal

//construct an array of noise distributions with differing variance.
val noises = Range.Double(-25.0, -7, 0.5).map( db => scala.math.pow(10, db/10.0) ).map( v => new GaussianNoise(0,v/T/T) )

println("stdev \t var(period) \t var(phase)")
for( noise <- noises ) { 
	val clt = new CLT( noise, uY , T) //compute the CLT
	val stddevstr = scala.math.sqrt(T*T*noise.getVariance).toString.replace('E', 'e')
	println(stddevstr + "\t" + clt.periodVar(N).toString.replace('E', 'e') + "\t" + clt.phaseVar(N).toString.replace('E', 'e'))
}
