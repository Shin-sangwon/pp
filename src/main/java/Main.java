import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

public class Main {

    static int people, answer, end;
    static boolean[] visited;
    static ArrayList<ArrayList<Integer>> graph;
    static class Node{
        int num;
        int dist;
        public Node(int num, int dist) {
            this.num = num;
            this.dist = dist;
        }
    }
    private static void init() {

        graph = new ArrayList<>();

        for(int i = 0; i <= people; i++) {
            graph.add(new ArrayList<>());
        }
    }

    private static void dfs(Node node) {

        if(node.num == end) {
            answer = node.dist;
            return;
        }
        visited[node.num] = true;

        for(int x : graph.get(node.num)) {
            if(visited[x]) continue;
            dfs(new Node(x, node.dist + 1));
        }

    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        people = Integer.parseInt(br.readLine());
        st = new StringTokenizer(br.readLine());

        int start = Integer.parseInt(st.nextToken());
        end = Integer.parseInt(st.nextToken());

        int m = Integer.parseInt(br.readLine());

        init();

        for(int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());

            int parents = Integer.parseInt(st.nextToken());
            int child = Integer.parseInt(st.nextToken());
            //양방향
            graph.get(parents).add(child);
            graph.get(child).add(parents);
        }
        answer = -1;
        visited = new boolean[people+1];
        dfs(new Node(start, 0));

        System.out.println(answer);
    }
}
