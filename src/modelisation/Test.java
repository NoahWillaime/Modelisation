package modelisation;

import java.time.Clock;
import java.util.ArrayList;
import java.util.Timer;

class Test
{
   static boolean visite[];
   public static void dfs(Graph g, int u)
	 {
		visite[u] = true;
		System.out.println("Je visite " + u);
		for (Edge e: g.next(u))
		  if (!visite[e.to])
			dfs(g,e.to);
	 }

   public static void testHeap()
	 {
		// Crée ue file de priorité contenant les entiers de 0 à 9, tous avec priorité +infty
		Heap h = new Heap(10);
		h.decreaseKey(3,1664);
		h.decreaseKey(4,5);
		h.decreaseKey(3,8);
		h.decreaseKey(2,3);
		// A ce moment, la priorité des différents éléments est:
		// 2 -> 3
		// 3 -> 8
		// 4 -> 5
		// tout le reste -> +infini
		int x=  h.pop();
		System.out.println("On a enlevé "+x+" de la file, dont la priorité était " + h.priority(x));
		x=  h.pop();
		System.out.println("On a enlevé "+x+" de la file, dont la priorité était " + h.priority(x));
		x=  h.pop();
		System.out.println("On a enlevé "+x+" de la file, dont la priorité était " + h.priority(x));
		// La file contient maintenant uniquement les éléments 0,1,5,6,7,8,9 avec priorité +infini
	 }
   
   public static void testGraph()
	 {
		int n = 5;
		int i,j;
		Graph g = new Graph(n*n+2);
		
		for (i = 0; i < n-1; i++)
		  for (j = 0; j < n ; j++)
			g.addEdge(new Edge(n*i+j, n*(i+1)+j, 1664 - (i+j)));

		for (j = 0; j < n ; j++)		  
		  g.addEdge(new Edge(n*(n-1)+j, n*n, 666));
		
		for (j = 0; j < n ; j++)					
		  g.addEdge(new Edge(n*n+1, j, 0));
		
		g.addEdge(new Edge(13,17,1337));
		g.writeFile("test.dot");
		// dfs à partir du sommet 3
		visite = new boolean[n*n+2];
		dfs(g, 3);
	 }
   
   public static void main(String[] args)
	 {
		int[][] image = SeamCarving.readpgm("ex3.pgm");
		//SeamCarving.writepgm(image, "image_test.pgm");
		 int[][] tab = new int[3][4];
		 tab[0][0] = 3;
		 tab[0][1] = 11;
		 tab[0][2] = 24;
		 tab[0][3] = 39;
		 tab[1][0] = 8;
		 tab[1][1] = 21;
		 tab[1][2] = 29;
		 tab[1][3] = 39;
		 tab[2][0] = 200;
		 tab[2][1] = 60;
		 tab[2][2] = 25;
		 tab[2][3] = 0;
		 int[][] interet = SeamCarving.interest(image);
		 Graph g;
		// g.writeFile("test");
         ArrayList<Edge> test;
		 long time = Clock.systemUTC().millis();
         for (int i = 0; i < 50; i++) {
             g = SeamCarving.tograph(interet);
             test = SeamCarving.Dijkstra(g, 0, g.vertices() - 1);
             image = SeamCarving.toTab(test, image);
             interet = SeamCarving.interest(image);
         }
         SeamCarving.writepgm(image, "TEST.pgm");
		 /*for (Edge e : test){
		 	System.out.println("FROM : "+e.from+" / TO : "+e.to);
		 }*/
		 System.out.println("TIME : "+(Clock.systemUTC().millis()-time));
		 //testHeap();
		//testGraph();
	 }
}
