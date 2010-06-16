/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package analysis;

import edu.uci.ics.jung.algorithms.cluster.EdgeBetweennessClusterer;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.MultiGraph;
import hiberex.AdditionalFunc;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
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

        Calendar from=Calendar.getInstance();
        from.set(2009,01,03);
        EvParams params=new EvParams(from,10);

        Set<Set<Number>> a = clusterAndReport("Loved", 200, 10,null);

        Calendar to=Calendar.getInstance();
        to.set(2010, 01, 03);
        EvParams params2=new EvParams(from,10);
        
        Set<Set<Number>> b = clusterAndReport("Events", 200, 10,null);

        System.out.println(AnalysisHelper.ExtractSolidCommunitiesFactor(a, b));
    }

    public static Set<Set<Number>> clusterAndReport(String mode, int count, int edgesToRemove,EvParams params) {
        GraphFactory graphFactory= new GraphFactory();

        System.out.println("creating graph " + Now());
        Graph<Number, Number> graph;
        if(mode.equals("Events")){
            List<Integer> users=AdditionalFunc.getIds("User", "fetch first "+200+" rows only");
            graph=graphFactory.CreateEventsGraph(users);
        }
        else {
            graph = graphFactory.CreateUsersGraph(count, mode, null);
        }
        int edges=graph.getEdgeCount();
        edges=edges/3;
        System.out.println("graph created " + Now());

        EdgeBetweennessClusterer<Number, Number> clusterer = new EdgeBetweennessClusterer<Number, Number>(edges);
        Set<Set<Number>> clusters = clusterer.transform(graph);
        System.out.println("graph clustered " + Now());

        graphFactory.SaveReport(count + mode + edgesToRemove + ".txt", clusters, false, edgesToRemove);
        System.out.println("report saved " + Now());
        return clusters;
    }
}
