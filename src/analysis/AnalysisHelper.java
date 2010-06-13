/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package analysis;

import java.util.HashSet;
import java.util.Map;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author wrozka
 */
public class AnalysisHelper {
    public static Set<Set<Number>> ExtractSolidCommunities(Set<Set<Number>> commA, Set<Set<Number>> commB) {
        Set<Set<Number>> result = new HashSet<Set<Number>>();
        Set<Set<Number>> bigger = commA.size() >= commB.size() ? commA : commB;
        Set<Set<Number>> smaller = commA.size() < commB.size() ? commA : commB;

        for(Set<Number> setA : bigger) {
            Set<Number> max = new HashSet<Number>();
            for(Set<Number> setB : smaller) {
                Set<Number> temp = new HashSet<Number>(setA);
                temp.retainAll(setB);
                if (temp.size() > max.size())
                    max = temp;
            }
            result.add(max);
        }
        return result;
    }

    public static double ExtractSolidCommunitiesFactor(Set<Set<Number>> commA, Set<Set<Number>> commB) {
        double result = 0;
        Set<Set<Number>> bigger = commA.size() >= commB.size() ? commA : commB;
        Set<Set<Number>> smaller = commA.size() < commB.size() ? commA : commB;

        for(Set<Number> setA : bigger) {
            Set<Number> max = new HashSet<Number>();
            for(Set<Number> setB : smaller) {
                Set<Number> temp = new HashSet<Number>(setA);
                temp.retainAll(setB);
                if (temp.size() > max.size())
                    max = temp;
            }
            result += max.size() / setA.size();
        }
        return result / bigger.size();
    }

    public static Map<Number, CommunityRole> AssignRoles(Set<Set<Number>> clusters) {
        return null;
    }
}
