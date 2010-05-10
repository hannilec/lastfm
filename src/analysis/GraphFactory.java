/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package analysis;

import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.SparseMultigraph;
import edu.uci.ics.jung.graph.util.EdgeType;
import hiberex.User;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.collections15.Factory;

/**
 *
 * @author wrozka
 */
public class GraphFactory {

    public static Graph<Number, Number> CreateFriendsGraph() {
        return CreateFriendsGraph(10000000);
    }

    public static Graph<Number, Number> CreateFriendsGraph(int max) {
            	Factory<Number> vertexFactory = new Factory<Number>() {
            int n = 0;
            public Number create() { return n++; }
        };
        Factory<Number> edgeFactory = new Factory<Number>()  {
            int n = 0;
            public Number create() { return n++; }
        };

        Graph<Number,Number> graph = new SparseMultigraph<Number, Number>();

        Map<User, Number> vertices = new HashMap<User, Number>();


        List<User> users = User.getUsers();
        max = (users.size() > max) ? max : users.size();

        for(int i = 0; i < max; i++) {
            vertices.put(users.get(i), vertexFactory.create());
            graph.addVertex(vertices.get(users.get(i)));

        }


        for(User u: vertices.keySet()) {
            for(User f: u.getFriends()) {
                if (vertices.containsKey(f)) {
                    graph.addEdge(edgeFactory.create(),
                            vertices.get(u),
                            vertices.get(f), EdgeType.UNDIRECTED);
                }
            }
        }
        return graph;
    }
}
