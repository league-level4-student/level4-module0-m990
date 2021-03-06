package _04_Maze_Maker;

import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

import _03_Conways_Game_of_Life.WorldPanel;


public class MazeMaker{
	
	private static int width;
	private static int height;
	
	private static Maze maze;
	
	private static Random randGen = new Random();
	private static Stack<Cell> uncheckedCells = new Stack<Cell>();
	
	
	public static Maze generateMaze(int w, int h){
		width = w;
		height = h;
		maze = new Maze(width, height);
		
		//4. select a random cell to start
		int row = randGen.nextInt(maze.getWidth());
		int col = randGen.nextInt(maze.getHeight());
		
		//5. call selectNextPath method with the randomly selected cell
		selectNextPath(maze.getCell(row, col));
		
		return maze;
	}

	//6. Complete the selectNextPathMethod
	private static void selectNextPath(Cell currentCell) {
		System.out.println(currentCell.getX() + " " + currentCell.getY());
		//System.out.println("select next path");
		//System.out.println(currentCell.getX() + " " + currentCell.getY());
		//A. mark cell as visited
		currentCell.setBeenVisited(true);
		//B. check for unvisited neighbors using the cell
		ArrayList<Cell> neighbors = getUnvisitedNeighbors(currentCell);
		//System.out.println(neighbors);
		if (neighbors != null && neighbors.size() != 0) {
			//System.out.println("not null");
			int randomCell = randGen.nextInt(neighbors.size());
			Cell ranCell = neighbors.get(randomCell);
			uncheckedCells.push(currentCell);
			removeWalls(currentCell, ranCell);
			selectNextPath(ranCell);
		} else if (!uncheckedCells.isEmpty()) {
			selectNextPath(uncheckedCells.pop());
		}else {
			System.out.println("Complete");
		}

		//C. if has unvisited neighbors,
		
			//C1. select one at random.
			
			//C2. push it to the stack
		
			//C3. remove the wall between the two cells

			//C4. make the new cell the current cell and mark it as visited
			
			
		//D. if all neighbors are visited
		
			//D1. if the stack is not empty
			
				// D1a. pop a cell from the stack
		
				// D1b. make that the current cell
				
	}

	//7. Complete the remove walls method.
	//   This method will check if c1 and c2 are adjacent.
	//   If they are, the walls between them are removed.
	private static void removeWalls(Cell c1, Cell c2) {
		if (c1.getY() == c2.getY()) {
			if (c1.getX() > c2.getX()) {
				c1.setWestWall(false);
				c2.setEastWall(false);
			}
			else if (c1.getX() < c2.getX()) {
				c1.setEastWall(false);
				c2.setWestWall(false);
			}
			else {
				System.out.println("error in x comparison");
			}
		}
		else if (c1.getX() == c2.getX()) {
			if (c1.getY() > c2.getY()) {
				c1.setNorthWall(false);
				c2.setSouthWall(false);
			}
			else if (c1.getY() < c2.getY()) {
				c1.setSouthWall(false);
				c2.setNorthWall(false);
			}
			else {
				System.out.println("error in y comparison");
			}
		}
		else {
			System.out.println("try to remove walls between walls that are not the same");
		}
	}
	
	//8. Complete the getUnvisitedNeighbors method
	//   Any unvisited neighbor of the passed in cell gets added
	//   to the ArrayList
	private static ArrayList<Cell> getUnvisitedNeighbors(Cell c) {
		//System.out.println("in get unvisited cells");
		ArrayList<Cell> cellList = new ArrayList<>();
		if (c.getX() != width-1 && !maze.getCell(c.getX()+1, c.getY()).hasBeenVisited()) {
			cellList.add(maze.getCell(c.getX()+1, c.getY()));
		}
		if (c.getX() != 0 && !maze.getCell(c.getX()-1, c.getY()).hasBeenVisited()) {
			cellList.add(maze.getCell(c.getX()-1, c.getY()));
		}
		if (c.getY() != height-1 && !maze.getCell(c.getX(), c.getY()+1).hasBeenVisited()) {
			cellList.add(maze.getCell(c.getX(), c.getY()+1));
		}
		if (c.getY() != 0 && !maze.getCell(c.getX(), c.getY()-1).hasBeenVisited()) {
			cellList.add(maze.getCell(c.getX(), c.getY()-1));
		}
		if (cellList.isEmpty()) {
			//System.out.println("returning null");
			return null;
		}
		//for (Cell c1: cellList) System.out.println(c1.getX() + " " + c1.getY());
		return cellList;
	}
}