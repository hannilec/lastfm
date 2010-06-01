package analysis;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

//package src;

import edu.uci.ics.jung.algorithms.scoring.BetweennessCentrality;
import edu.uci.ics.jung.algorithms.scoring.PageRank;
import edu.uci.ics.jung.algorithms.scoring.VertexScorer;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.SparseMultigraph;
import edu.uci.ics.jung.graph.util.EdgeType;
import hiberex.Pair;
import hiberex.Track;
import hiberex.User;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.collections15.Factory;
import hiberex.AdditionalFunc;
import java.util.Collection;
/**
 *
 * @author wrozka
 */
public class GraphFactory {

    public Graph<Number, Number> CreateUsersGraph(String type) {
        return CreateUsersGraph(10000000,type);
    }

    protected Graph<Number,Number> graph;
    protected List<Integer> usersid;
    protected Map<Number, Number> vertices;
    
    public Graph<Number, Number> CreateUsersGraph(int max,String type) {

        Factory<Number> vertexFactory = new Factory<Number>() {
            int n = 0;
            public Number create() { return n++; }
        };
       

        graph = new SparseMultigraph<Number, Number>();

        


        /*users = User.getUsers();
        max = (users.size() > max) ? max : users.size();

        for(int i = 0; i < max; i++) {
            vertices.put(users.get(i), vertexFactory.create());
            graph.addVertex(vertices.get(users.get(i)));

        }*/

        vertices = new HashMap<Number, Number>();

        if(type.equals("Friends")){
           // = new HashMap<Number, Number>();
            usersid = User.getUserIds();
            max = (usersid.size() > max) ? max : usersid.size();

            for(int i = 0; i < max; i++) {
                vertices.put(usersid.get(i), vertexFactory.create());
                graph.addVertex(vertices.get(usersid.get(i)));

            }


            return CreateFriendsGraph(vertices,graph);
        }else if(type.equals("Loved")){
            //Map<Number, Number> vertices = new HashMap<Number, Number>();
            return CreateLovedGraph(vertices,graph,max);
        }


        return null;
    }



    private Graph<Number, Number> CreateLovedGraph(Map<Number, Number> vertices,Graph<Number,Number> graph,int max){


        Factory<Number> edgeFactory = new Factory<Number>()  {
            int n = 0;
            public Number create() { return n++; }
        };
        Factory<Number> vertexFactory = new Factory<Number>() {
            int n = 0;
            public Number create() { return n++; }
        };
        
        class MC implements Comparator{
            public int compare(Object o1,Object o2){
                Pair a=(Pair)o1;
                Pair b=(Pair)o2;
                if(a.nr==b.nr) return 0;
                if(a.nr<b.nr) return -1;
                else return 1;

            }

        }
        
        List set=new ArrayList();
        List<Integer> trs=Track.getTrackIds();
        System.out.println("got ids");
        List<Pair> ltr=Track.getLoved();
        for(int trid:trs){
            int size=getSize(trid,ltr);
            set.add(new Pair(trid,size));
        }
        //System.out.println("SIZE:"+trs.size());
        
       /* for(Pair a:ltr){
            //int size=getSize(ltr,a);
            System.out.println("track "+a.trid+" user "+a.nr);
        }*/


        /*for(int tr:trs){
            int size=Track.getFansSize(tr);
            if(size>1){
                //System.out.println("adding: "+tr.getName()+" "+size);
                set.add(new Pair(tr,size));
            }
            //if(set.size()>2443) break;
        }*/
        System.out.println("set builded");
       //set.sort(set,new MC());
       Collections.sort(set, new MC());
       //Iterator it=set.iterator();
       /*while(it.hasNext()){
           Pair c=(Pair)it.next();
           System.out.println(c.tr.getName()+"  "+c.nr);
       }*/
       int usc=0;
       //users=User.getUsers();
       for(int i=set.size()-1;i>0;i--){
           int act=((Pair)set.get(i)).trid;
           List<Integer> fans=getFans(act,ltr);
           for(Integer u:fans){
               for(Integer v:fans){
                   if(u!=v){

                       if(!vertices.containsKey(u) && usc<max){
                           vertices.put(u, vertexFactory.create());
                           usc++;
                           graph.addVertex(vertices.get(u));
                       }else if(usc>=max) break;



                       if(!vertices.containsKey(v) && usc<max){
                           vertices.put(v, vertexFactory.create());
                           usc++;
                           graph.addVertex(vertices.get(v));
                       }else if(usc>=max) break;


                         if(!graph.isNeighbor(vertices.get(v), vertices.get(u))){


                               graph.addEdge(edgeFactory.create(),
                                    vertices.get(u),
                                    vertices.get(v), EdgeType.UNDIRECTED);
                         }

                   }
               }
           }
       }



        return graph;
    }


    public Graph<Number, Number> CreateFriendsGraph(Map<Number, Number> vertices,Graph<Number,Number> graph){


        Factory<Number> edgeFactory = new Factory<Number>()  {
            int n = 0;
            public Number create() { return n++; }
        };


        List<Pair> friends=User.getFriendsTab();

        System.out.println("friendstab"+friends.size());
         for(Number u: vertices.keySet()) {
            for(Integer f:getFriends(u,friends)) {
                if (vertices.containsKey(f)) {
                    graph.addEdge(edgeFactory.create(),
                            vertices.get(u),
                            vertices.get(f), EdgeType.UNDIRECTED);
                }
            }
        }
        return graph;
    }

    private double[] getNumberOfFriends(Map<Number, User> users) {
        double[] numbers = new double[users.size()];

        for(int i = 0; i < users.size(); i++)
            numbers[i] = graph.getNeighborCount(i);

        return numbers;
    }

    private double[] getRanks(Set<Number> indices, VertexScorer<Number, Double> scorer) {
        double[] numbers = new double[indices.size()];

        int i = 0;
        for(Number n : indices)
            numbers[i++] = scorer.getVertexScore(n);

        return numbers;
    }
    
    
    public String Report(Set<Set<Number>> clusters) {
        BetweennessCentrality centralityRanker = new BetweennessCentrality(graph);
        PageRank pageRanker = new PageRank(graph, 0.15);
        pageRanker.acceptDisconnectedGraph(true);
        pageRanker.evaluate();

        String res = "";
        int i = 0;

        final Map<Number,User> users=getUsers(vertices);

        double totalFriendsAvg = MathHelper.Avg(getNumberOfFriends(users));
        double totalFriendsStdDev = MathHelper.StdDev(getNumberOfFriends(users));

        double totalBcAvg = MathHelper.Avg(getRanks(users.keySet(), centralityRanker));
        double totalBcStdDev = MathHelper.StdDev(getRanks(users.keySet(), centralityRanker));

        double totalPrAvg = MathHelper.Avg(getRanks(users.keySet(), pageRanker));
        double totalPrStdDev = MathHelper.StdDev(getRanks(users.keySet(), pageRanker));
        
        for(Set<Number> s : clusters) {
            res += "= Cluster: " + i + " =\n";
            res += "Count: " + s.size() + "\n";

            VertexScorer<Number, Double> friendScorer = new VertexScorer<Number, Double>() {
                public Double getVertexScore(Number v) {
                    return (double)graph.getNeighborCount(v);
                }
            };

            res += "Avg Friends: " + MathHelper.Avg(getRanks(s, friendScorer)) + "\n";
            res += "StdDev Friends: " + MathHelper.StdDev(getRanks(s, friendScorer)) + "\n";
            res += "Avg PR: " + MathHelper.Avg(getRanks(s, pageRanker)) + "\n";
            res += "StdDev PR: " + MathHelper.StdDev(getRanks(s, pageRanker)) + "\n";
            res += "Avg BC: " + MathHelper.Avg(getRanks(s, centralityRanker)) + "\n";
            res += "StdDev BC: " + MathHelper.StdDev(getRanks(s, centralityRanker)) + "\n";

            res += "Content: \n";
            for(Number n : s) {
                res += " " + users.get(n.intValue()).getName() + "\n";
                res += "  - Friends: " + graph.getNeighborCount(n) + "\n";
                res += "  - PR: " + pageRanker.getVertexScore(n) + "\n";
                res += "  - BC: " + centralityRanker.getVertexScore(n) + "\n";
            }
            i++;
        }
        res = "Number of clusters: " + i + "\n" + res;
        res = "Total Avg number of members: " + graph.getVertexCount() / i + "\n" + res;
        res = "Total Avg number of friends: " + totalFriendsAvg + "\n" + res;
        res = "Total StdDev friends: " + totalFriendsStdDev + "\n" + res;
        res = "Total Avg PR: " + totalPrAvg + "\n" + res;
        res = "Total StdDev PR: " + totalPrStdDev + "\n" + res;
        res = "Total Avg BC: " + totalBcAvg + "\n" + res;
        res = "Total StdDev BC: " + totalBcStdDev + "\n" + res;

        return res;
    }

    private int getSize(int trid,List<Pair> lst) {

        int size=0;
        for(Pair a:lst){
            if(trid==a.trid) size++;
        }
        return size;
        //throw new UnsupportedOperationException("Not yet implemented");
    }

    private List<Integer> getFans(int act,List<Pair> lst) {

        List<Integer> res=new ArrayList();

        for(Pair a:lst){
            if(act==a.trid) res.add(a.nr);
        }
        return res;
        //throw new UnsupportedOperationException("Not yet implemented");
    }

    private List<Integer> getFriends(Number u, List<Pair> friends) {
        //System.out.println("looking for: "+u);
        //System.out.println(friends.size());
        List<Integer> res=new ArrayList();
        for(Pair a:friends){
            //System.out.println(a.trid+" "+a.nr);
            if(u.equals(a.trid)) res.add(a.nr);
        }
        return res;
        //throw new UnsupportedOperationException("Not yet implemented");
    }
    private Map<Number,User> getUsers(Map<Number,Number> vertices){//key-id.val-int++
        List<User> us=AdditionalFunc.getUsersById(vertices.keySet());
                //.getUsersById(vertices.keySet());
        Map<Number,User> res=new HashMap<Number,User>();
        int i=0;
        for(User u:us){
            res.put(i++, u);
        }
        return res;
    }

}
