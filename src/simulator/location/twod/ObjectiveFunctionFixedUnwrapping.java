/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package simulator.location.twod;

import distributions.GaussianNoise;
import distributions.UniformNoise;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javax.imageio.ImageIO;
import lattices.Zn;
import simulator.Point2;

/**
 * Computes the value of the objective function for
 * 2D location estimation.  This is what we wish to
 * maximise to find the location estimate.
 * You can fix the value of the unwrapping variable
 * here.  This is usefull for the purpose of
 * Newton's method.
 * This computes the negative sum of squares error.
 * @author Robby McKilliam
 */
public class ObjectiveFunctionFixedUnwrapping extends ObjectiveFunction{

    double[] u;

    /**
     * Constructor takes array of Transmitters
     * and any array of measured phases to each
     * transmitter.
     * @param phi phase measured by each transmitter
     * @param u integer unwrapping variables
     */
    public ObjectiveFunctionFixedUnwrapping(Transmitter[] trans, double[] phi, double[] u){
        super(trans, phi);
        this.u = u;
        if(u.length != N || phi.length != N){
            throw new ArrayIndexOutOfBoundsException("trans, phi and u arrays must be the same length.");
        }
    }

    @Override
    public double value(Point2 x){
        double ret = 0.0;
        for(int n = 0; n < N; n++){
            Point2 p = trans[n].point();
            double T = trans[n].wavelength();
            double dist = p.minus(x).normF();
            double err = T*u[n] + phi[n] - dist;
            ret += err*err;
        }
        //System.out.println(-ret);
        return -ret;
    }

    /** Draws a PNG image of a randomly generated objective function */
    public static void main(String[] args) throws Exception{

        double xmin = -4.0;
        double ymin = -4.0;
        double xmax = 4.0;
        double ymax = 4.0;
        double stepx = 0.01;
        double stepy = 0.01;
        
        int imwidth = (int)((xmax - xmin)/stepx) + 1;
        int imheight = (int)((ymax - ymin)/stepy) + 1;

        UniformNoise pnoise = new UniformNoise(0, 4);
        UniformNoise fnoise = new UniformNoise(3, 0.0);
        fnoise.setRange(0.6);
        Point2 loc = new Point2(0,0);
        int N = 4;

        //NoisyPhaseSignals sig =  new NoisyPhaseSignals(loc, Transmitter.getRandomArray(N, pnoise, fnoise));
        NoisyPhaseSignals sig = new NoisyPhaseSignals(loc,Transmitter.getLatticeArray(new Zn(2), 2.0, new Point2(0.5,0.5), fnoise));
        sig.setNoiseGenerator(new GaussianNoise(0,0.001));

        double[] d = sig.generateReceivedSignal();
        double[] u = new double[N];
        ObjectiveFunction ofunc = new ObjectiveFunction(sig.getTransmitters(), d);
        //ObjectiveFunctionFixedUnwrapping ofunc = new ObjectiveFunctionFixedUnwrapping(sig.getTransmitters(), d, u);

        BufferedImage im = new BufferedImage(imwidth, imheight, BufferedImage.TYPE_BYTE_GRAY);
        File file = new File("picfile.csv");
        BufferedWriter writer =  new BufferedWriter(new FileWriter(file));
        int i = 0;
        int j = 0;
        for(double y = ymin; y <ymax;  y += stepy){
            i = 0;
            for(double x = xmin; x <xmax;  x += stepx){
                double val = ofunc.value(new Point2(x, y));
                writer.write(val + ", ");
                im.setRGB(i, j, (int)(1000*Math.abs(val)));
                i++;
            }
            writer.newLine();
            j++;
        }
        writer.close();

        try {
            File outputfile = new File("saved.png");
            ImageIO.write(im, "png", outputfile);
        } catch (IOException e) {
            System.err.println("writting file failed");
        }


    }

}
