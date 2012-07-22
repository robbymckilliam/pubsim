/**
* Compute Prob Error data
*/
import pubsim.lattices.BarnesWall
import pubsim.lattices.GeneralLatticeAndNearestPointAlgorithm
import pubsim.distributions.GaussianNoise
import pubsim.VectorFunctions.sum2
import pubsim.VectorFunctions.randomGaussian

val starttime = (new java.util.Date).getTime

//generate some data conjecturing about kissing numbers
println("Computing probability of error")

val SNRs = -10.0 to -8.0 by 0.1
val vars = SNRs.map( snr => scala.math.pow(snr/10.0,10.0)  )
val toerrs = 10
val m = 4
val n = scala.math.pow(2,m+1).toInt


val pelist = vars.par.map { v =>
  val lattice = new GeneralLatticeAndNearestPointAlgorithm((new BarnesWall(m)).getGeneratorMatrix)
  var numerrs = 0.0
  var numitrs = 0.0
//println(pubsim.VectorFunctions.print(randomGaussian(n,0,v)))
  while(numerrs < toerrs){
    lattice.nearestPoint(randomGaussian(n,0,v))
//println(pubsim.VectorFunctions.print(lattice.getLatticePoint))
    if( sum2(lattice.getLatticePoint) > 0.001 ) numerrs = numerrs + 1   
    numitrs = numitrs + 1
  }
  val pe = numerrs/numitrs
  println(v + ", " + pe)
  pe
}.toList

val file = new java.io.FileWriter("pem" + m)
for( i <- SNRs.indices ){
  file.write(SNRs(i).toString.replace('E', 'e') + "\t" + pelist(i).toString.replace('E', 'e') + "\n")
}
file.close

println
val runtime = (new java.util.Date).getTime - starttime
println("Finished in " + (runtime/1000.0) + " seconds.\n") 





