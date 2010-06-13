/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package analysis;

import edu.uci.ics.jung.algorithms.cluster.EdgeBetweennessClusterer;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.MultiGraph;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;

/**
 *
 * @author wrozka
 */
public class TextModeAnalyzer {
    private static String Now() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");

        return sdf.format(new Date());
    }
    public static void main(String[] args) {
        Set<Set<Number>> a = clusterAndReport("Friends", 100, 100);
        Set<Set<Number>> b = clusterAndReport("Loved", 100, 100);

        System.out.println(AnalysisHelper.ExtractSolidCommunitiesFactor(a, b));
    }

    public static Set<Set<Number>> clusterAndReport(String mode, int count, int edgesToRemove) {
        GraphFactory graphFactory= new GraphFactory();

        System.out.println("creating graph " + Now());
        Graph<Number, Number> graph = graphFactory.CreateUsersGraph(count, mode);
        System.out.println("graph created " + Now());

        EdgeBetweennessClusterer<Number, Number> clusterer = new EdgeBetweennessClusterer<Number, Number>(edgesToRemove);
        Set<Set<Number>> clusters = clusterer.transform(graph);
        System.out.println("graph clustered " + Now());

        graphFactory.SaveReport(count + mode + edgesToRemove + ".txt", clusters, false);
        System.out.println("report saved " + Now());
        return clusters;
    }
}
