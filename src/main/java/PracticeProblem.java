import java.util.*;

public class PracticeProblem {
    
    public static int searchMazeMoves(String[][] maze) {
        if (maze == null || maze.length == 0) return -1;
        
        int rows = maze.length;
        int cols = maze[0].length;
        
        // Find start position (bottom left)
        int startRow = -1, startCol = -1;
        int finishRow = -1, finishCol = -1;
        
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (maze[i][j].equals("S")) {
                    startRow = i;
                    startCol = j;
                } else if (maze[i][j].equals("F")) {
                    finishRow = i;
                    finishCol = j;
                }
            }
        }
        
        if (startRow == -1 || finishRow == -1) return -1;
        
        // BFS for shortest path
        Queue<int[]> queue = new LinkedList<>();
        boolean[][] visited = new boolean[rows][cols];
        int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
        
        queue.offer(new int[]{startRow, startCol, 0});
        visited[startRow][startCol] = true;
        
        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            int row = current[0];
            int col = current[1];
            int moves = current[2];
            
            if (row == finishRow && col == finishCol) {
                return moves;
            }
            
            for (int[] dir : directions) {
                int newRow = row + dir[0];
                int newCol = col + dir[1];
                
                if (newRow >= 0 && newRow < rows && newCol >= 0 && newCol < cols &&
                    !visited[newRow][newCol] && !maze[newRow][newCol].equals("*")) {
                    
                    visited[newRow][newCol] = true;
                    queue.offer(new int[]{newRow, newCol, moves + 1});
                }
            }
        }
        
        return -1; // No path found
    }
    
    public static int noOfPaths(String[][] maze) {
        if (maze == null || maze.length == 0) return 0;
        
        int rows = maze.length;
        int cols = maze[0].length;
        
        // Find positions
        int startRow = -1, startCol = -1;
        int finishRow = -1, finishCol = -1;
        
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (maze[i][j].equals("S")) {
                    startRow = i;
                    startCol = j;
                } else if (maze[i][j].equals("F")) {
                    finishRow = i;
                    finishCol = j;
                }
            }
        }
        
        if (startRow == -1 || finishRow == -1) return 0;
        
        boolean[][] visited = new boolean[rows][cols];
        return dfsCountPaths(maze, startRow, startCol, finishRow, finishCol, visited);
    }
    
    private static int dfsCountPaths(String[][] maze, int row, int col, 
                                   int finishRow, int finishCol, boolean[][] visited) {
        
        if (row < 0 || row >= maze.length || col < 0 || col >= maze[0].length ||
            visited[row][col] || maze[row][col].equals("*")) {
            return 0;
        }
        
        if (row == finishRow && col == finishCol) {
            return 1;
        }
        
        visited[row][col] = true;
        int pathCount = 0;
        
        pathCount += dfsCountPaths(maze, row + 1, col, finishRow, finishCol, visited);
        pathCount += dfsCountPaths(maze, row - 1, col, finishRow, finishCol, visited);
        pathCount += dfsCountPaths(maze, row, col + 1, finishRow, finishCol, visited);
        pathCount += dfsCountPaths(maze, row, col - 1, finishRow, finishCol, visited);
        
        visited[row][col] = false;
        
        return pathCount;
    }
    
    public static void printMaze(String[][] maze) {
        for (String[] row : maze) {
            for (String cell : row) {
                System.out.print(cell + " ");
            }
            System.out.println();
        }
    }
    
    public static void main(String[] args) {
        // Test with corrected mazes that actually have paths
        String[][] maze1 = {
            {"*", "*", "*", "*", "*"},
            {"*", " ", " ", " ", "*"},
            {"*", " ", "*", " ", "F"},
            {"*", " ", "*", " ", "*"},
            {"S", " ", " ", " ", "*"}
        };
        
        String[][] maze2 = {
            {"*", "*", "*", "*"},
            {"*", " ", " ", "F"},
            {"*", " ", "*", "*"},
            {"S", " ", " ", "*"}
        };
        
        System.out.println("Maze 1:");
        printMaze(maze1);
        System.out.println("Minimum moves: " + searchMazeMoves(maze1));
        System.out.println("Number of paths: " + noOfPaths(maze1));
        System.out.println();
        
        System.out.println("Maze 2:");
        printMaze(maze2);
        System.out.println("Minimum moves: " + searchMazeMoves(maze2));
        System.out.println("Number of paths: " + noOfPaths(maze2));
        
        // Test your original maze that has no path
        System.out.println("\nYour original Maze 2 (no path):");
        String[][] mazeNoPath = {
            {"*", "*", "*", "F"},
            {"*", " ", " ", "*"},
            {"*", " ", "*", "*"},
            {"S", " ", " ", "*"}
        };
        printMaze(mazeNoPath);
        System.out.println("Minimum moves: " + searchMazeMoves(mazeNoPath));
        System.out.println("Number of paths: " + noOfPaths(mazeNoPath));
    }
}