/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package analysis;

import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.SparseMultigraph;
import edu.uci.ics.jung.graph.util.EdgeType;
import hiberex.Track;
import hiberex.User;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.collections15.Factory;

/**
 *
 * @author wrozka
 */
public class GraphFactory {

    public Graph<Number, Number> CreateUsersGraph(String type) {
        return CreateUsersGraph(10000000,type);
    }

    protected Graph<Number,Number> graph;
    protected List<User> users;

    
    public Graph<Number, Number> CreateUsersGraph(int max,String type) {

        Factory<Number> vertexFactory = new Factory<Number>() {
            int n = 0;
            public Number create() { return n++; }
        };
       

        graph = new SparseMultigraph<Number, Number>();

        Map<User, Number> vertices = new HashMap<User, Number>();


        users = User.getUsers();
        max = (users.size() > max) ? max : users.size();

        for(int i = 0; i < max; i++) {
            vertices.put(users.get(i), vertexFactory.create());
            graph.addVertex(vertices.get(users.get(i)));

        }



        if(type.equals("Friends")){
            return CreateFriendsGraph(vertices,graph);
        }else if(type.equals("Loved")){
            return CreateLovedGraph(vertices,graph);
        }


        return null;
    }



    public Graph<Number, Number> CreateLovedGraph(Map<User, Number> vertices,Graph<Number,Number> graph){


        Factory<Number> edgeFactory = new Factory<Number>()  {
            int n = 0;
            public Number create() { return n++; }
        };


        List<Track> tracks=Track.getTracks();
        for(Track t:tracks){


            for(User u:t.getFans()){
                if (vertices.containsKey(u)) {
                    for(User f:t.getFans()){
                        if (f!=u && vertices.containsKey(f) && !graph.isNeighbor(vertices.get(u), vertices.get(f))) {
                            graph.addEdge(edgeFactory.create(),
                                vertices.get(u),
                                vertices.get(f), EdgeType.UNDIRECTED);
                        }
                    }

                }
            }

        }

        
        return graph;
    }


    public Graph<Number, Number> CreateFriendsGraph(Map<User, Number> vertices,Graph<Number,Number> graph){


        Factory<Number> edgeFactory = new Factory<Number>()  {
            int n = 0;
            public Number create() { return n++; }
        };


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

        public String Report(Set<Set<Number>> clusters) {
        String res = "";
        int i = 0;
        int sum = 0;
        int friendstotal = 0;
        for(Set<Number> s : clusters) {
            res += "= Cluster: " + i + " =\n";
            res += "Count: " + s.size() + "\n";
            res += "Content: \n";
            int friendssum = 0;
            for(Number n : s) {
                res += "- " + users.get(n.intValue()).getName() + "\n";
                friendssum += users.get(n.intValue()).getFriends().size();
            }
            res += "Avg number of friends: " + friendssum / s.size() + "\n";
            sum += s.size();
            friendstotal += friendssum;
            i++;
        }
        res = "Number of clusters: " + i + "\n" + res;
        res = "Avg number of members: " + sum/i + "\n" + res;
        res = "Avg number of friends: " + friendstotal/sum + "\n" + res;

        return res;
    }
}
