package _03_Conways_Game_of_Life;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JPanel;
import javax.swing.Timer;

public class WorldPanel extends JPanel implements MouseListener, ActionListener {
	private static final long serialVersionUID = 1L;
	private int cellsPerRow;
	private int cellSize;

	private Timer timer;

	// 1. Create a 2D array of Cells. Do not initialize it.
	Cell[][] cellArr;

	public WorldPanel(int w, int h, int cpr) {
		setPreferredSize(new Dimension(w, h));
		addMouseListener(this);
		timer = new Timer(500, this);
		this.cellsPerRow = cpr;

		// 2. Calculate the cell size.
		cellSize = w / cellsPerRow;
		// int cellSizeH = h/cellsPerRow;
		// 3. Initialize the cell array to the appropriate size.
		cellArr = new Cell[cellsPerRow][cellsPerRow];
		// 3. Iterate through the array and initialize each cell.
		// Don't forget to consider the cell's dimensions when
		// passing in the location.
		for (int i = 0; i < cellArr.length; i++)
			for (int j = 0; j < cellArr[i].length; j++)
				cellArr[i][j] = new Cell(i * cellSize, j * cellSize, cellSize);
	}

	public void randomizeCells() {
		// 4. Iterate through each cell and randomly set each
		// cell's isAlive memeber to true of false
		Random r = new Random();
		for (int i = 0; i < cellArr.length; i++)
			for (int j = 0; j < cellArr[i].length; j++) {
				int random = r.nextInt(2);
				if (random == 0) {
					cellArr[i][j].isAlive = false;
				} else {
					cellArr[i][j].isAlive = true;
				}
			}
		repaint();
	}

	public void clearCells() {
		// 5. Iterated through the cells and set them all to dead.
		for (int i = 0; i < cellArr.length; i++)
			for (int j = 0; j < cellArr[i].length; j++)
				cellArr[i][j].isAlive = false;

		repaint();
	}

	public void startAnimation() {
		timer.start();
	}

	public void stopAnimation() {
		timer.stop();
	}

	public void setAnimationDelay(int sp) {
		timer.setDelay(sp);
	}

	@Override
	public void paintComponent(Graphics g) {
		// 6. Iterate through the cells and draw them all
		for (int i = 0; i < cellArr.length; i++)
			for (int j = 0; j < cellArr[i].length; j++)
				cellArr[i][j].draw(g);

		// draws grid
		g.setColor(Color.BLACK);
		g.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
	}

	public ArrayList<Cell> allNeighbors(int w, int h) {
		ArrayList<Cell> neighbors = new ArrayList<>();
		try {
			if (cellArr[w + 1][h] != null)
				neighbors.add(cellArr[w + 1][h]);
			if (cellArr[w - 1][h] != null)
				neighbors.add(cellArr[w - 1][h]);
			if (cellArr[w][h + 1] != null)
				neighbors.add(cellArr[w][h + 1]);
			if (cellArr[w][h - 1] != null)
				neighbors.add(cellArr[w][h - 1]);
			if (cellArr[w + 1][h + 1] != null)
				neighbors.add(cellArr[w + 1][h + 1]);
			if (cellArr[w + 1][h - 1] != null)
				neighbors.add(cellArr[w + 1][h - 1]);
			if (cellArr[w - 1][h + 1] != null)
				neighbors.add(cellArr[w - 1][h + 1]);
			if (cellArr[w - 1][h - 1] != null)
				neighbors.add(cellArr[w - 1][h - 1]);
		} catch (ArrayIndexOutOfBoundsException e) {

		}

		return neighbors;
	}

	// advances world one step
	public void step() {
		// 7. iterate through cells and get their neighbors
		int[][] neighbors = new int[cellArr.length][cellArr.length];
		for (int i = 0; i < cellArr.length; i++) {
			for (int j = 0; j < cellArr[i].length; j++) {
				neighbors[i][j] = getLivingNeighbors(i, j);
			}
		}

		// 8. check if each cell should live or die
		for (int i = 0; i < cellArr.length; i++) {
			for (int j = 0; j < cellArr[i].length; j++) {
				cellArr[i][j].liveOrDie(neighbors[i][j]);
			}
		}

		repaint();
	}

	// 9. Complete the method.
	// It returns an int list of the 8 or less neighbors of the
	// cell identified by x and y
	public int getLivingNeighbors(int x, int y) {
		ArrayList<Cell> neighbors = allNeighbors(x, y);
		int livingNeighbors = 0;

		for (int i = 0; i < neighbors.size(); i++) {
			if (neighbors.get(i).isAlive) {
				livingNeighbors++;
			}
		}

		return livingNeighbors;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// 10. Use e.getX() and e.getY() to determine
		// which cell is clicked. Then toggle
		// the isAlive variable for that cell.
		System.out.println("Cell size: " + cellSize);
		System.out.println(e.getX() + " " + e.getY());

		int x = 0;
		int y = 0;
		if (e.getX() != 0)
			x = e.getX() / cellSize;
		if (e.getY() != 0)
			y = e.getY() / cellSize;

		System.out.println(x + " " + y);

		if (cellArr[x][y].isAlive)
			cellArr[x][y].isAlive = false;
		if (!cellArr[x][y].isAlive)
			cellArr[x][y].isAlive = true;

		// System.out.println(e.getX() + " " + e.getY());

		repaint();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		step();
	}
}
