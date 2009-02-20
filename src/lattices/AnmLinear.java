/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lattices;

import simulator.FastSelection;
import simulator.IndexedDouble;
import simulator.Util;
import simulator.VectorFunctions;


/**
 * Linear nearest point algorithm for the coxeter lattices.  Implementation
 * mimics the version of the code that ended up in the final paper.  Otherwise
 * this is very similar to AnmBucket.
 *
 * UNDER CONSTRUCTION.
 *
 * @author Robby McKilliam
 */
public class AnmLinear extends AnmBucket {

    private IndexedDouble[] w;

    /** Constructor can set the m part of A_{n/m}. */
    public AnmLinear(int M){
        super(M);
    }

    /** {@inheritDoc} */
    @Override
    public void setDimension(int n){
        this.n = n;
        u = new double[n+1];
        v = new double[n+1];
        z = new double[n+1];

        //setup the buckets.
        numBuckets = (n+1)/M;

        buckets = new IndexedDoubleList[numBuckets];
        for(int i = 0; i < numBuckets; i++)
            buckets[i] = new IndexedDoubleList();

        bes = new ListElem[n+1];
        for(int i = 0; i < n + 1; i++){
            bes[i] = new ListElem();
            bes[i].elem = new IndexedDouble(0.0, i);
        }

        w = new IndexedDouble[n+1];

        fselect = new FastSelection(n+1);

    }

    /** {@inheritDoc} */
    @Override
    public void nearestPoint(double[] y){
        if (n != y.length-1)
	    setDimension(y.length-1);

        //make sure that the buckets are empty!
        for(int i = 0; i < numBuckets; i++)
            buckets[i].clear();

        double a = 0, b = 0;
        int k = 0;
        for(int i = 0; i < n+1; i++){
            z[i] = y[i] - Math.round(y[i]);
            k += Math.round(y[i]);
            bes[i].elem.value = -z[i];
            bes[i].elem.index = i;
            int bi = numBuckets - 1 - (int)(Math.floor(numBuckets*(z[i]+0.5)));
            buckets[bi].add(bes[i]);
            a += z[i];
            b += z[i] * z[i];
        }

        //move all the buckets into one array
        //note this just moves pointers, it's not a copy
        int wi = 0;
        for(int i = 0; i < numBuckets; i++){
            IndexedDoubleListIterator itr = buckets[i].iterator();
            while(itr.hasNext()){
                w[wi] = itr.next();
                wi++;
            }
        }

        //System.out.println(VectorFunctions.print(w));

        double D = Double.POSITIVE_INFINITY;
        //System.out.println("dist = " + D);
        int m = 0, L = 0, R = 0;
        for(int i = 0; i < numBuckets; i++){

            R = L + buckets[i].size() - 1;

            //get the first modularly admissble index in the bucket
            //int j = nearestMultM(k) - k;
            //int j = M*(int)Math.ceil(((double)k)/M) - k;
            int g = Util.mod(M - Util.mod(k, M), M);
            int p = buckets[i].size() - Util.mod(buckets[i].size() + k, M);

            //System.out.println("g = " + g + ", p = " + p);
            //System.out.println("bucketsize = " + buckets[i].size());


            if(g >= 0 && g < buckets[i].size())
                FastSelection.FloydRivestSelect(L, R, L+g, w);
            if(p > g && p < buckets[i].size())
                FastSelection.FloydRivestSelect(L+g+1, R, L+p, w);

            //System.out.println(VectorFunctions.print(w));

            //
            for(int t = 0; t < buckets[i].size(); t++){
                if(t == g || t == p){
                    double dist = b - a*a/(n+1);
                    //System.out.println("dist = " + dist);
                    if(dist < D){
                        D = dist;
                        m = L+t;
                    }
                }
                a -= 1;
                b += 2*w[t + L].value + 1;
                k++;
            }

            L += buckets[i].size();

        }

        //get the first element in the Bresenham set
        for(int i = 0; i < n + 1; i++)
            u[i] = Math.round(y[i]);

        //System.out.println("m = " + m);

        //add all the buckets before the best on
        for(int t = 0; t < m; t++){
            //System.out.print(w[t].index + ", ");
            u[w[t].index]++;
        }

        //System.out.println(VectorFunctions.print(w));

        //project index to nearest lattice point
        AnstarVaughan.project(u, v);

        //System.out.println();

    }
    
}
