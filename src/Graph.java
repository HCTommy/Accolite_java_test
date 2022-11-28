import java.util.ArrayDeque;

public class Graph {
    public static void main(String[] args) {
        Graph graph = new Graph();
        int result = graph.calculateAverageDistanceBetweenTwoPoints("A", "c");
        System.out.println(result);

    }
    public int calculateAverageDistanceBetweenTwoPoints(String x, String y) {
    //TODO Add your implementation here
        int max=Integer.MAX_VALUE;
        int[][] graph = new int[][]{
                {0,12,13,11,8},
                {max,0,3,max,max},
                {max,max,0,max,max},
                {max,max,max,0,7},
                {max,max,4,max,0}

        };
        int routes=0;
        int totalDistance=0;
        ArrayDeque<int[]> queue = new ArrayDeque<>();
        int start = x.toUpperCase().charAt(0)-'A';
        int end = y.toUpperCase().charAt(0)-'A';
        for (int i = 0; i < 5; i++) {
            if (graph[start][i]<max&&graph[start][i]>0){
                queue.add(new int[]{i,graph[start][i]});
            }
        }
        while (queue.size()>0){
            int[] node = queue.poll();
            if (node[0]==end){
                routes++;
                totalDistance+=node[1];
            }else if (node[0]==2){
                continue;
            }else {
                for (int i = 0; i < 5; i++) {
                    if (graph[node[0]][i]<max&&graph[node[0]][i]>0){
                        queue.add(new int[]{i,node[1]+graph[node[0]][i]});
                    }
                }
            }
        }
        return totalDistance/routes;
    }




}
