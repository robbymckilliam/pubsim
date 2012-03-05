/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pubsim.snpe;

import static pubsim.Util.fracpart;

/**
 * Utilities for the sparse noisy period estimators.
 * @author Robby McKilliam
 */
public class Util {

        /**
         * Returns square error between estimated period That and
         * true period T.
         */
        public static double periodError(double That, double T){
            return (That - T)*(That - T);
        }

        /**
         * Returns square error of the fraction part difference between
         * estimated phase phat and true phase p. Requires the
         * true period T.
         */
        public static double phaseError(double phat, double p, double T){
            double fdiff = fracpart( (phat - p) / T )*T;
            return fdiff*fdiff;
        }

}
