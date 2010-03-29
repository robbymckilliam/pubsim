/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lattices.decoder;

import java.util.TreeMap;
import java.util.Vector;
import lattices.Lattice;
import lattices.reduction.LLL;
import simulator.VectorFunctions;

/**
 * 
 * @author Robby McKilliam
 */
public class Mbest extends Babai {

    /** Current sphere radius squared */
    protected double D;

    //temporary variable for ut
    protected double[] ut, ubest;

    protected double[] xr;

    //small number to avoid numerical errors in branches.
    protected double DELTA = 0.000001;

    protected int M;

    /**
     * Contructor sets the M parameter for the M best method.
     * This is the maximum number of points that can be kept at
     * each iteration of the decoder.
     */
    public Mbest(int M){
        super();
        this.M = M;
    }

    public Mbest(Lattice L, int M){
        super(L);
        this.M = M;
    }

    @Override
    public void setLattice(Lattice L) {
        G = L.getGeneratorMatrix().copy();
        m = G.getRowDimension();
        n = G.getColumnDimension();
        u = new double[n];
        uh = new double[n];
        x = new double[m];
        yr = new double[n];
        ut = new double[n];
        ubest = new double[n];
        xr = new double[n];

        lll = new LLL();
        B = lll.reduce(G);
        U = lll.getUnimodularMatrix();

        //CAREFULL!  This version of the decoder requires R to
        //have positive diagonal entries.
        simulator.QRDecomposition QR = new simulator.QRDecomposition(B);
        R = QR.getR();
        Q = QR.getQ();

    }

    @Override
    public void nearestPoint(double[] y) {
        if(m != y.length)
            throw new RuntimeException("Point y and Generator matrix are of different dimension!");

        //this will store the nearest point in the variable x
        //computeBabaiPoint(y);

        //compute the radius squared of the sphere we are decoding in.
        //Add DELTA to avoid numerical error causing the
        //Babai point to be rejected.
        //D = VectorFunctions.distance_between2(y, x) + DELTA;

        //COMPUTE THE NEAREST POINT!
        //set least possible ut[k]
        TreeMap<Double, Vector<Integer>> prevmap = new TreeMap<Double, Vector<Integer>>();
        
        //ok, set up the initial tree map with the first M elements
        int k = n-1;
        int u = (int)Math.round(y[k]/R.get(k,k));
        double d = R.get(k, k)*u - yr[k];
        Vector<Integer> vec = new Vector<Integer>();
        vec.add(u);
        prevmap.put(d*d, vec);
        int m = 0;
        //this is being a bit lazy, it might use a little more that M.
        //no big problem though.
        while( m < (M+1)/2 ){
            //add u+m
            vec = new Vector<Integer>();
            vec.add(u+m);
            d = R.get(k, k)*(u+m) - yr[k];
            prevmap.put(d*d, vec);

            //add u-m
            vec = new Vector<Integer>();
            vec.add(u-m);
            d = R.get(k, k)*(u-m) - yr[k];
            prevmap.put(d*d, vec);

            m++;
        }

        //now run the algorithm
        for(k = n-2; k >= 0; k--){

            TreeMap<Double, Vector<Integer>> nextmap = new TreeMap<Double, Vector<Integer>>();
            
            for(int Mtimes = 0; Mtimes < M; Mtimes++){

                vec = prevmap.pollFirstEntry().getValue();
                //compute the sum of R[k][k+i]*uh[k+i]'s
                double rsum = 0.0;
                for(int i = k+1; i < n; i++ ){
                    int tu = vec.get(tovecIndex(i));
                    rsum += tu*R.get(k, i);
                }
                u = (int)Math.round((yr[k] - rsum)/R.get(k,k));
                d = R.get(k, k)*u + rsum - yr[k];
                Vector<Integer> veccopy = (Vector<Integer>)vec.clone();
                veccopy.add(u);
                nextmap.put(d*d, vec);

                m = 0;
                //this is being a bit lazy, it might use a little more that M.
                //no big problem though.
                while(m < (M+1)/2){
                    //add u+m
                    veccopy = (Vector<Integer>)vec.clone();
                    veccopy.add(u+m);
                    d = R.get(k, k)*(u+m) + rsum - yr[k];
                    nextmap.put(d*d, vec);

                    //add u-m
                    veccopy = (Vector<Integer>)vec.clone();
                    veccopy.add(u-m);
                    d = R.get(k, k)*(u-m) + rsum - yr[k];
                    nextmap.put(d*d, vec);

                    m++;
                }
            }
            prevmap = nextmap;
        }



        //compute index u = Uuh so that Gu is nearest point
        VectorFunctions.matrixMultVector(U, ubest, this.u);

        //compute nearest point
        VectorFunctions.matrixMultVector(G, this.u, x);

    }

    //just invert the indices for the Vector<Integers>
    private int tovecIndex(int i){ return n - 1 - i; }

    

}
