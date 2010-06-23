package analysis;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

//package src;

import edu.uci.ics.jung.algorithms.cluster.EdgeBetweennessClusterer;
import edu.uci.ics.jung.algorithms.scoring.BetweennessCentrality;
import edu.uci.ics.jung.algorithms.scoring.PageRank;
import edu.uci.ics.jung.algorithms.scoring.VertexScorer;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import edu.uci.ics.jung.graph.util.EdgeType;
import hiberex.Pair;
import hiberex.Track;
import hiberex.User;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.collections15.Factory;
import hiberex.AdditionalFunc;
import java.io.File;
import java.sql.Date;
import java.util.Calendar;
//import java.util.Collection;
import java.util.HashSet;
import org.apache.commons.io.FileUtils;
/**
 *
 * @author wrozka
 */
public class GraphFactory {

    /*
     *
     *  - budowanie każdego rodzaju grafu dla N tych samych użytkowników.*
        - wybór N losowych użytkowników lub wybór N użytkowników którzy byli na koncertach.
     *
     *
     * 
     *
     */








    public Graph<Number, Number> CreateUsersGraph(String type) {
        return CreateUsersGraph(10000000,type,null);
    }

    protected Graph<Number,Number> graph;
    protected Graph<Number,Number> clusteredGraph;
    protected List<Integer> usersid;
    protected Map<Number, Number> vertices;
    protected List<Number> edgesRemoved;

    public Set<Set<Number>> cluster(int numEdges) {
        EdgeBetweennessClusterer<Number, Number> clusterer = new EdgeBetweennessClusterer<Number, Number>(numEdges);
        Set<Set<Number>> clusters = clusterer.transform(graph);
        edgesRemoved = clusterer.getEdgesRemoved();
        clusteredGraph = copyGraph(graph, edgesRemoved);
        
        return clusters;
    }

    List<Number> getEdgesRemoved() {
        return edgesRemoved;
    }

    Graph<Number, Number> getClusteredGraph() {
        return clusteredGraph;
    }

    public Graph<Number, Number> copyGraph(Graph<Number, Number> graph, List<Number> edgesToRemove) {
        Graph<Number, Number> result = new UndirectedSparseGraph<Number, Number>();
        for(Number n : graph.getVertices())
            result.addVertex(n);
        for(Number e: graph.getEdges())
            result.addEdge(e, graph.getEndpoints(e).getFirst(), graph.getEndpoints(e).getSecond(), EdgeType.UNDIRECTED);
        for(Number e: edgesToRemove)
            result.removeEdge(e);
        return result;
    }
    public Graph<Number, Number> CreateUsersGraph(int max,String type,List<Integer> users) {

        Factory<Number> vertexFactory = new Factory<Number>() {
            int n = 0;
            public Number create() { return n++; }
        };
       

        graph = new UndirectedSparseGraph<Number, Number>();
        vertices = new HashMap<Number, Number>();

        if(type.equals("Friends")){
            if(users!=null){

                usersid=users;
                max=usersid.size();
            }
            else
                usersid = User.getUserIds();

            max = (usersid.size() > max) ? max : usersid.size();
            System.out.println("max"+max);
            for(int i = 0; i < max; i++) {
                vertices.put(usersid.get(i), vertexFactory.create());
                //System.out.println("user "+usersid.get(i));
                graph.addVertex(vertices.get(usersid.get(i)));
            }
         //    if(users!=null)
           //      return CreateFriendsGraph(vertices,graph,users);
            // else
                return CreateFriendsGraph(vertices,graph);
        }else if(type.equals("Loved")){
            if(users!=null){
                usersid=users;
                max = usersid.size();
                for(int i = 0; i < max; i++) {
                    vertices.put(usersid.get(i), vertexFactory.create());
                    graph.addVertex(vertices.get(usersid.get(i)));
                }
                return CreateLovedGraph(vertices,graph,users);
            }
            return CreateLovedGraph(vertices,graph,max);
        }


        return null;
    }


     private Graph<Number, Number> CreateLovedGraph(Map<Number, Number> vertices,Graph<Number,Number> graph,List<Integer> users){

        Factory<Number> edgeFactory = new Factory<Number>()  {
            int n = 0;
            public Number create() { return n++; }
        };


        List<Pair> ltr=AdditionalFunc.getPairs("User_LovedTrack","track_id","user_id");//Track.getLoved();//track,user

        for(int user:users){
            List<Integer> tracks=this.getLovedTracks(user, ltr);
            for(int track:tracks){
                List<Integer> fans=this.getFans(track, ltr);

                for(Integer fan:fans){
                    if(user!=fan){

                      
                       if(vertices.containsKey(fan) && !graph.isNeighbor(vertices.get(fan), vertices.get(user))){
                           graph.addEdge(edgeFactory.create(),
                                    vertices.get(user),
                                    vertices.get(fan), EdgeType.UNDIRECTED);
                       }
                    }


                }
            }
        }




         return graph;
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
        List<Pair> ltr=AdditionalFunc.getPairs("User_LovedTrack","track_id","user_id");//Track.getLoved();
        for(int trid:trs){
            int size=getSize(trid,ltr);
            set.add(new Pair(trid,size));
        }

        System.out.println("set builded");

       Collections.sort(set, new MC());

       int usc=0;

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

       // System.out.println("friendstab"+friends.size());
        //System.out.println("vertices"+vertices.size());

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




    public Graph<Number, Number> CreateEventsGraph(List<Integer> users){


        usersid=users;

        Factory<Number> vertexFactory = new Factory<Number>() {
            int n = 0;
            public Number create() { return n++; }
        };

        Factory<Number> edgeFactory = new Factory<Number>()  {
            int n = 0;
            public Number create() { return n++; }
        };

        graph = new UndirectedSparseGraph<Number, Number>();
        vertices = new HashMap<Number, Number>();

        //max = usersid.size();
                for(int i = 0; i < usersid.size(); i++) {
                    vertices.put(usersid.get(i), vertexFactory.create());
                    graph.addVertex(vertices.get(usersid.get(i)));
                }

        List<Pair> eventAtt=AdditionalFunc.getPairs("User_Event","user_id","event_id");

        for(Integer user:users){
            List<Integer> events=this.getUsersEvents(user, eventAtt);
            for(Integer event:events){
                List<Integer> atts=this.getAttendees(event, eventAtt);

                for(Integer att:atts){
                    if(!user.equals(att)){


                       if(vertices.containsKey(att) && !graph.isNeighbor(vertices.get(att), vertices.get(user))){
                          // System.out.println(" edge: "+user+" "+vertices.get(user)+" "+att+" "+vertices.get(att));
                           graph.addEdge(edgeFactory.create(),
                                    vertices.get(user),
                                    vertices.get(att), EdgeType.UNDIRECTED);
                       }
                    }
                }
            }
        }


        return graph;
    }

     public Graph<Number, Number> CreateEventsGraph(EvParams params){

        //Date from=params.getFrom();
        Factory<Number> vertexFactory = new Factory<Number>() {
            int n = 0;
            public Number create() { return n++; }
        };

        Factory<Number> edgeFactory = new Factory<Number>()  {
            int n = 0;
            public Number create() { return n++; }
        };

        graph = new UndirectedSparseGraph<Number, Number>();
        vertices = new HashMap<Number, Number>();


        List<Pair> eventAtt=AdditionalFunc.getPairs("User_Event","user_id","event_id");

       /* for(Pair p:eventAtt){
            System.out.println(p.trid+" "+p.nr);
        }*/
        List<Integer> eventIds;
        List<Integer> atts;

        //System.out.println("getEvents");
        if(params.getTo()!=null){
            eventIds=getEvents(params.getFrom(),params.getTo());
        }else{
            eventIds=getEvents(params.getFrom(),params.getMax());
        }

       /* for(Integer i:eventIds){
            System.out.println(i);
        }*/
       // System.out.println("creating");
        for(Integer ev:eventIds){
            atts=getAttendees(ev,eventAtt);
            //System.out.println("--------------------");
            for(Integer u:atts){

               // System.out.println("att: "+u);

                if(!vertices.containsKey(u)){
                    vertices.put(u, vertexFactory.create());
                    graph.addVertex(vertices.get(u));
                }
                for(Integer f:atts){
                    if(!f.equals(u)){
                        if(!vertices.containsKey(f)){
                            vertices.put(f, vertexFactory.create());
                            graph.addVertex(vertices.get(f));

                            graph.addEdge(edgeFactory.create(),
                                    vertices.get(u),
                                    vertices.get(f), EdgeType.UNDIRECTED);

                        }else if(!graph.isNeighbor(vertices.get(f), vertices.get(u))){
                            graph.addEdge(edgeFactory.create(),
                                    vertices.get(u),
                                    vertices.get(f), EdgeType.UNDIRECTED);
                        }



                    }
                }
            }
        }


       /* for(Number a:vertices.keySet()){
            System.out.println("vert:"+a+" "+vertices.get(a));
        }*/


        return graph;
    }



  /*   public Map<String, Graph<Number,Number>> CreateAllGraphs(int max){

         //List<Integer> users=getUsersForGraph(max);
         Map<String, Graph<Number,Number>> res=new HashMap<String, Graph<Number,Number>>();
         List<Integer> users=this.getMostConnUsers(max);
          //graph = new UndirectedSparseGraph<Number, Number>();
          //vertices = new HashMap<Number, Number>();
          res.put("Friends", CreateUsersGraph(0,"Friends",users));
          res.put("Loved", CreateUsersGraph(0,"Loved",users));
          res.put("Events", CreateEventsGraph(users));


         return res;
     }

  */





    public List<Integer> getMostConnUsers(int max){

        

        return AdditionalFunc.getMostConnUsr(max);
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
    
    public void SaveReport(String filename, Set<Set<Number>> clusters, boolean printUsers, int removed) {
        try {
            FileUtils.writeStringToFile(new File(filename), Report(clusters, printUsers, removed));
        } catch (IOException ex) {
            Logger.getLogger(GraphFactory.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String Report(Set<Set<Number>> clusters, boolean printUsers, int removed) {
        BetweennessCentrality centralityRanker = new BetweennessCentrality(clusteredGraph);

        PageRank pageRanker = new PageRank(clusteredGraph, 0.15);
        pageRanker.acceptDisconnectedGraph(true);
        pageRanker.evaluate();

        String res = "Edges: " + graph.getEdgeCount() + "\n";
        res += "EdgesRemoved: " + edgesRemoved.size() + "\n";
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
                    return (double)clusteredGraph.getNeighborCount(v);
                }
            };

            res += "Avg Friends: " + MathHelper.Avg(getRanks(s, friendScorer)) + "\n";
            res += "StdDev Friends: " + MathHelper.StdDev(getRanks(s, friendScorer)) + "\n";
            res += "Avg PR: " + MathHelper.Avg(getRanks(s, pageRanker)) + "\n";
            res += "StdDev PR: " + MathHelper.StdDev(getRanks(s, pageRanker)) + "\n";
            res += "Avg BC: " + MathHelper.Avg(getRanks(s, centralityRanker)) + "\n";
            res += "StdDev BC: " + MathHelper.StdDev(getRanks(s, centralityRanker)) + "\n";

            if (printUsers) {
                res += "Content: \n";
                for(Number n : s) {
                    //System.out.println("user"+n);
                    res += " " + users.get(n.intValue()).getName() + "\n";
                    res += "  - Friends: " + clusteredGraph.getNeighborCount(n) + "\n";
                    res += "  - PR: " + pageRanker.getVertexScore(n) + "\n";
                    res += "  - BC: " + centralityRanker.getVertexScore(n) + "\n";
                }
            }
            i++;
        }
        res = "Number of clusters: " + i + "\n" + res;
        res = "Total Avg number of members: " + clusteredGraph.getVertexCount() / i + "\n" + res;
        res = "Total Avg number of friends: " + totalFriendsAvg + "\n" + res;
        res = "Total StdDev friends: " + totalFriendsStdDev + "\n" + res;
        res = "Total Avg PR: " + totalPrAvg + "\n" + res;
        res = "Total StdDev PR: " + totalPrStdDev + "\n" + res;
        res = "Total Avg BC: " + totalBcAvg + "\n" + res;
        res = "Total StdDev BC: " + totalBcStdDev + "\n" + res;

        i = 0;
        res += "\n\n";
        for(Set<Number> s : clusters) {
            res += "Cluster: " + i + "\n";

            for(Number n : s) {
                res += users.get(n.intValue()).getName() + "\t";
                res += clusteredGraph.getNeighborCount(n) + "\t";
                res += pageRanker.getVertexScore(n) + "\t";
                res += centralityRanker.getVertexScore(n) + "\t\n";
            }
            
            i++;
        }
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



    private List<Integer> getLovedTracks(int user,List<Pair> lst){
        List<Integer> res=new ArrayList();

        for(Pair a:lst){
            if(user==a.nr) res.add(a.trid);
        }
        return res;
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
    public Map<Number, User> getUsers() {
        return getUsers(vertices);
    }
    
    public Map<Number,User> getUsers(Map<Number,Number> vertices){//key-id.val-int++
        List<User> us=AdditionalFunc.getUsersById(vertices.keySet());
                //.getUsersById(vertices.keySet());
        Map<Number,User> res=new HashMap<Number,User>();
        int i=0;
        for(User u:us){
            res.put(i++, u);
        }
        //System.out.println("ressize"+res.size());
        return res;
    }


     private List<Integer> getEvents(Calendar from, int max) {
        //System.out.println("getting");
        String dep= "(startDate > date(\'"+new Date(from.getTimeInMillis())+"\')) FETCH FIRST "+max+" ROWS ONLY";
        //String dep= "(startDate > "+from.getTime()+" FETCH FIRST "+max+" ROWS ONLY";

        //System.out.println(dep);
        return AdditionalFunc.getIds("Event",dep);
        //throw new UnsupportedOperationException("Not yet implemented");
    }

    private List<Integer> getEvents(Calendar from, Calendar to) {

        List<Integer> ls=AdditionalFunc.getIds("Event", "startDate between  date(\'"+new Date(from.getTimeInMillis())+ "\') and  date(\'"+new Date(to.getTimeInMillis())+"\')");
        return ls;
        //throw new UnsupportedOperationException("Not yet implemented");
    }

    public List<Integer> getUsersByEvents(EvParams params){

        List<Integer> eventIds;

        if(params.getTo()!=null){
            eventIds=getEvents(params.getFrom(),params.getTo());
        }else{
            //System.out.println("with max");
            eventIds=getEvents(params.getFrom(),params.getMax());
        }

        
       // System.out.println(" got events ");


        List<Pair> eventAtt=AdditionalFunc.getPairs("User_Event","user_id","event_id");

        //System.out.println(" got table ");
        Set<Integer> usSet=new HashSet();
        List<Integer> users=new ArrayList();
        for(Integer event:eventIds){

            usSet.addAll(getAttendees(event,eventAtt));
          // users.addAll(getAttendees(event,eventAtt));
           //System.out.println("uslen"+users.size());
        }

        users.addAll(usSet);
        System.out.println(" got users ");
        return users;

    }

    private List<Integer> getUsersEvents(Integer user,List<Pair> usrEv){
        List<Integer> res=new ArrayList<Integer>();
        for(Pair p:usrEv){
            if(p.trid==user) res.add(p.nr);
        }

        return res;
    }

    private List<Integer> getAttendees(Integer ev,List<Pair> eventAtt) {

        List<Integer> res=new ArrayList<Integer>();

        for(Pair p:eventAtt){
            if(p.nr==ev) res.add(p.trid);
        }

        return res;
        //throw new UnsupportedOperationException("Not yet implemented");
    }


}
