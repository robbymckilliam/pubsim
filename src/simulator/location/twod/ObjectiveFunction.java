/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package simulator.location.twod;

import Jama.Matrix;
import distributions.UniformNoise;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import optimisation.AutoDerivativeFunction;
import simulator.Point2;

/**
 * Computes the value of the objective function for
 * 2D location estimation.  This is what we wish to
 * maximise to find the location estimate.
 * This computes the negative sum of squares error.
 * @author Robby McKilliam
 */
public class ObjectiveFunction extends AutoDerivativeFunction{

    protected Transmitter[] trans;
    protected double[] phi;
    protected int N;

    /**
     * Constructor takes array of Transmitters
     * and any array of measured phases to each
     * transmitter.
     */
    public ObjectiveFunction(Transmitter[] trans, double[] phi){
        this.trans = trans;
        this.phi = phi;
        N = trans.length;
    }

    public double value(Point2 x){
        double ret = 0.0;
        for(int n = 0; n < N; n++){
            Point2 p = trans[n].point();
            double T = trans[n].wavelength();
            double dist = p.minus(x).normF();
            double err = T*(dist/T - Math.rint(dist/T)) - phi[n];
            ret += err*err;
        }
        //System.out.println(-ret);
        return -ret;
    }

    public double value(Matrix x) {
        return value(new Point2(x));
    }



    /** Draws a PNG image of a randomly generated objective function */
    public static void main(String[] args) throws Exception{

        double xmin = -3.0;
        double ymin = -3.0;
        double xmax = 3.0;
        double ymax = 3.0;
        double stepx = 0.005;
        double stepy = 0.005;
        
        int imwidth = (int)((xmax - xmin)/stepx) + 1;
        int imheight = (int)((ymax - ymin)/stepy) + 1;

        UniformNoise pnoise = new UniformNoise(0, 4);
        UniformNoise fnoise = new UniformNoise(3, 0.0);
        fnoise.setRange(0.6);
        Point2 loc = new Point2(0,0);
        int N = 4;

        NoisyPhaseSignals sig = new NoisyPhaseSignals(loc, N, pnoise, fnoise);
        sig.setNoiseGenerator(new UniformNoise(0,0));

        double[] d = sig.generateReceivedSignal();
        ObjectiveFunction ofunc = new ObjectiveFunction(sig.getTransmitters(), d);

        BufferedImage im = new BufferedImage(imwidth, imheight, BufferedImage.TYPE_USHORT_GRAY);
        int i = 0;
        int j = 0;
        for(double x = xmin; x <xmax;  x += stepx){
            j = 0;
            for(double y = ymin; y <ymax;  y += stepy){
                double val = ofunc.value(new Point2(x, y));
                im.setRGB(i, j, (int)(10000*val*val));
                j++;
            }
            i++;
        }

        try {
            File outputfile = new File("saved.png");
            ImageIO.write(im, "png", outputfile);
        } catch (IOException e) {
            System.err.println("writting file failed");
        }


    }

}
