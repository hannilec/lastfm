/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package analysis;

import java.util.Collection;

/**
 *
 * @author wrozka
 */
public class MathHelper {
    public static double Avg(double[] numbers) {
        double sum = 0;
        for(double n : numbers)
            sum += n;

        return sum / numbers.length;
    }

    public static double StdDev(double[] numbers) {
        double avg = Avg(numbers);
        double stddev = 0;
        
        for(double n : numbers)
            stddev += Math.pow(n - avg, 2.0);

        return Math.sqrt(1.0/(numbers.length - 1) * stddev);
    }
}
