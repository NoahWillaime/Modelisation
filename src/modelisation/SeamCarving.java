package modelisation;

import java.util.ArrayList;
import java.io.*;
import java.util.*;
public class SeamCarving
{

   public static int[][] readpgm(String fn)
	 {		
        try {
			InputStream f = ClassLoader.getSystemClassLoader().getResourceAsStream(fn);
			BufferedReader d = new BufferedReader(new InputStreamReader(f));
			String magic = d.readLine();
			String line = d.readLine();
			while (line.startsWith("#")) {
				line = d.readLine();
			}
			Scanner s = new Scanner(line);
			int width = s.nextInt();
			int height = s.nextInt();
			line = d.readLine();
			s = new Scanner(line);
			int maxVal = s.nextInt();
			int[][] im = new int[height][width];
			s = new Scanner(d);
			int count = 0;
			while (count < height * width) {
				im[count / width][count % width] = s.nextInt();
				count++;
			}
			return im;
		}
        catch(Throwable t) {
            t.printStackTrace(System.err) ;
            return null;
        }
    }

    public static void writepgm(int[][] image, String filename){
   		int hauteur = image.length;
   		int largeur = image[0].length;
   		FileWriter flot;
   		PrintWriter flotFiltre;
   		File fichier;
   		fichier = new File(filename);
		try {
			flot = new FileWriter(fichier);
			flotFiltre = new PrintWriter(new BufferedWriter(flot));
			flotFiltre.println("P2");
			flotFiltre.println(largeur+" "+hauteur);
			flotFiltre.println("255");
			for (int i = 0; i < hauteur; i++){
				for (int j = 0; j < largeur; j++){
					flotFiltre.print(image[i][j]);
					if (j+1 < largeur)
						flotFiltre.print(" ");
				}
				flotFiltre.print('\n');
			}
			flotFiltre.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static int[][] interest(int[][] image){
		int hauteur = image.length;
		int largeur = image[0].length;
		int[][] interet = new int[hauteur][largeur];
		int moyenne = 0;
		for (int i = 0; i < hauteur; i++){
			for (int j = 0; j < largeur; j++){
				if (j == 0){
					interet[i][j] = Math.abs(image[i][j] - image[i][j+1]);
				} else if (j+1 >= largeur){
					interet[i][j] = Math.abs(image[i][j] - image[i][j-1]);
				} else {
					moyenne = (image[i][j-1] + image[i][j+1])/2;
					interet[i][j] = Math.abs(image[i][j] - moyenne);
				}
			}
		}
		return interet;
	}

	public static Graph tograph(int[][] itr){
		int hauteur = itr.length;
		int largeur = itr[0].length;
		Graph g = new Graph(hauteur*largeur+2);
		int count = 0;
		int from;
		int next;
		for (int j = 0; j < largeur; j++){
			g.addEdge(new Edge(0, j+1, 0));
		}
		for (int i = 0; i < hauteur; i++){
			for (int j = 0; j < largeur; j++){
				if (i+1 >= hauteur) {
					g.addEdge(new Edge(j+count+1, hauteur*largeur+1, itr[i][j]));
				} else {
					from = j+count+1;
					next = count+largeur+1;
					if (j == 0){
						g.addEdge(new Edge(from, next+j, itr[i][j]));
						g.addEdge(new Edge(from, next+j+1, itr[i][j]));
					} else if (j+1 >= largeur){
						g.addEdge(new Edge(from, next+j-1, itr[i][j]));
						g.addEdge(new Edge(from, next+j, itr[i][j]));
					} else {
						g.addEdge(new Edge(from, next+j-1, itr[i][j]));
						g.addEdge(new Edge(from, next+j, itr[i][j]));
						g.addEdge(new Edge(from, next+j+1, itr[i][j]));
					}
				}
			}
			count += largeur;
		}
		return g;
	}

	public static void Dijkstra(Graph g, int s, int t){
		int key;
		int cost;
		boolean[] visited = new boolean[g.vertices()];
		for (int i = 0; i < visited.length; i++)
			visited[i] = false;
		Edge[] chemin = new Edge[g.vertices()];
		Heap heap = new Heap(g.vertices());
		heap.decreaseKey(0, 0);
		while (!heap.isEmpty()) {
			key = heap.pop();
			visited[key] = true;
			for (Edge e : g.next(key)){
				if (!visited[e.to]) {
					cost = heap.priority(key) + e.cost;
					if (cost < heap.priority(e.to)) {
						heap.decreaseKey(e.to, cost);
							chemin[e.to] = e;
					}
				}
			}
		}
		int cellule = t;
		int x;
		int size = 0;
		while (cellule != s){
			x = cellule;
			size++;
			System.out.print(chemin[x].from+" / "+chemin[x].to+" / "+chemin[x].cost + "\n");
			cellule = chemin[x].from;
		}
		System.out.println("TAILLE : "+size);
	}
}
