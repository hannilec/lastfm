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
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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


        /*users = User.getUsers();
        max = (users.size() > max) ? max : users.size();

        for(int i = 0; i < max; i++) {
            vertices.put(users.get(i), vertexFactory.create());
            graph.addVertex(vertices.get(users.get(i)));

        }*/



        if(type.equals("Friends")){

            users = User.getUsers();
            max = (users.size() > max) ? max : users.size();

            for(int i = 0; i < max; i++) {
                vertices.put(users.get(i), vertexFactory.create());
                graph.addVertex(vertices.get(users.get(i)));

            }


            return CreateFriendsGraph(vertices,graph);
        }else if(type.equals("Loved")){
            return CreateLovedGraph(vertices,graph,max);
        }


        return null;
    }



    private Graph<Number, Number> CreateLovedGraph(Map<User, Number> vertices,Graph<Number,Number> graph,int max){


        Factory<Number> edgeFactory = new Factory<Number>()  {
            int n = 0;
            public Number create() { return n++; }
        };
        Factory<Number> vertexFactory = new Factory<Number>() {
            int n = 0;
            public Number create() { return n++; }
        };
        class Pair{
            Track tr;
            int nr;
            public Pair(Track tr,int nr){
                this.tr=tr;
                this.nr=nr;
            }
        }
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
        List<Track> trs=Track.getTracks();
        //System.out.println("SIZE:"+trs.size());
        for(Track tr:trs){
            int size=tr.getFansSize();
            if(size>1){
                //System.out.println("adding: "+tr.getName()+" "+size);
                set.add(new Pair(tr,size));
            }
            //if(set.size()>2443) break;
        }

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
           Track act=((Pair)set.get(i)).tr;
           List<User> fans=act.getFans();
           for(User u:fans){
               for(User v:fans){
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
