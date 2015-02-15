package game;

import info.gridworld.actor.*;
import info.gridworld.grid.BoundedGrid;
import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;
import info.gridworld.world.World;

public class World_2048 extends World {

    public static void main(String[] args) {
        World_2048 w = new World_2048();
        w.show();
    }
    
    private static Grid<Integer> grid = new BoundedGrid<>(4, 4);

    public World_2048() {
        super(grid);
        addTile();
        addTile();
        setMessage("2048");
    }

    @Override
    public boolean keyPressed(String description, Location loc) {
        Location newLoc = new Location(0, 0);
        switch (description) {
            case "DOWN":
                moveTilesC(Location.SOUTH);
                break;
            case "UP":
                moveTilesC(Location.NORTH);
                break;
            case "RIGHT":
                moveTilesC(Location.EAST);
                break;
            case "LEFT":
                moveTilesC(Location.WEST);
                break;
        }
        addTile();
        return true;
    }

    private void addTile() {
        int rand = (int) (Math.random() * 4);
        if (randLoc() != null) {
            if (rand == 0) {
                grid.put(randLoc(), new Integer(4));
            } else {
                grid.put(randLoc(), new Integer(2));
            }
        }
        if (win()) {
            setMessage("WIN!");
        }
        if (lose()) {
            setMessage("LOSE...");
        }
    }

    private Location randLoc() {
        Location loc = null;
        if (grid.getOccupiedLocations().size() < 16) {
            while (loc == null || (loc != null && (grid.get(loc) != null || !grid.isValid(loc)))) {
                int r = (int) (Math.random() * grid.getNumRows());
                int c = (int) (Math.random() * grid.getNumCols());
                loc = new Location(r, c);
            }
            return loc;
        }
        return null;
    }

    private void moveTilesC(int dir) {
        moveTiles(dir);
        combine(dir);
    }

    private void moveTiles(int dir) {
        if (dir == 0) {
            for (int i = 0; i < 4; i++) {
                for (int r = 1; r < grid.getNumRows(); r++) {
                    for (int c = 0; c < grid.getNumCols(); c++) {
                        if (grid.get(new Location(r, c)) != null && grid.get(new Location(r - 1, c)) == null) {
                            int tile = grid.get(new Location(r, c));
                            super.remove(new Location(r, c));
                            grid.put(new Location(r - 1, c), tile);
                        }
                    }
                }
            }
        }
        if (dir == 90) {
            for (int i = 0; i < 4; i++) {
                for (int c = grid.getNumCols() - 2; c >= 0; c--) {
                    for (int r = 0; r < grid.getNumRows(); r++) {
                        if (grid.get(new Location(r, c)) != null && grid.get(new Location(r, c + 1)) == null) {
                            int tile = grid.get(new Location(r, c));
                            super.remove(new Location(r, c));
                            grid.put(new Location(r, c + 1), tile);
                        }
                    }
                }
            }
        }
        if (dir == 180) {
            for (int i = 0; i < 4; i++) {
                for (int r = grid.getNumRows() - 2; r >= 0; r--) {
                    for (int c = 0; c < grid.getNumCols(); c++) {
                        if (grid.get(new Location(r, c)) != null && grid.get(new Location(r + 1, c)) == null) {
                            int tile = grid.get(new Location(r, c));
                            super.remove(new Location(r, c));
                            grid.put(new Location(r + 1, c), tile);
                        }
                    }
                }
            }
        }
        if (dir == 270) {
            for (int i = 0; i < 4; i++) {
                for (int c = 1; c < grid.getNumCols(); c++) {
                    for (int r = 0; r < grid.getNumRows(); r++) {
                        if (grid.get(new Location(r, c)) != null && grid.get(new Location(r, c - 1)) == null) {
                            int tile = grid.get(new Location(r, c));
                            super.remove(new Location(r, c));
                            grid.put(new Location(r, c - 1), tile);
                        }
                    }
                }
            }
        }
    }

    private void combine(int dir) {
        if (dir == 0) {
            for (int r = 0; r < grid.getNumRows() - 1; r++) {
                for (int c = 0; c < grid.getNumCols(); c++) {
                    if (grid.get(new Location(r, c)) != null && ((Integer) (grid.get(new Location(r, c)))).equals((Integer) (grid.get(new Location(r + 1, c))))) {
                        int num = grid.get(new Location(r, c));
                        super.remove(new Location(r, c));
                        super.remove(new Location(r + 1, c));
                        grid.put(new Location(r, c), num * 2);
                    }
                }
            }
        }
        if (dir == 90) {
            for (int c = grid.getNumCols() - 1; c >= 1; c--) {
                for (int r = 0; r < grid.getNumRows(); r++) {
                    if (grid.get(new Location(r, c)) != null && ((Integer) (grid.get(new Location(r, c)))).equals((Integer) (grid.get(new Location(r, c - 1))))) {
                        int num = grid.get(new Location(r, c));
                        super.remove(new Location(r, c));
                        super.remove(new Location(r, c - 1));
                        grid.put(new Location(r, c), new Integer(num * 2));
                    }
                }
            }
        }
        if (dir == 180) {
            for (int r = grid.getNumRows() - 1; r >= 1; r--) {
                for (int c = 0; c < grid.getNumCols(); c++) {
                    if (grid.get(new Location(r, c)) != null && ((Integer) (grid.get(new Location(r, c)))).equals((Integer) (grid.get(new Location(r - 1, c))))) {
                        int num = grid.get(new Location(r, c));
                        super.remove(new Location(r, c));
                        super.remove(new Location(r - 1, c));
                        grid.put(new Location(r, c), num * 2);
                    }
                }
            }
        }
        if (dir == 270) {
            for (int c = 0; c < grid.getNumCols() - 1; c++) {
                for (int r = 0; r < grid.getNumRows(); r++) {
                    if (grid.get(new Location(r, c)) != null && ((Integer) (grid.get(new Location(r, c)))).equals((Integer) (grid.get(new Location(r, c + 1))))) {
                        int num = grid.get(new Location(r, c));
                        super.remove(new Location(r, c));
                        super.remove(new Location(r, c + 1));
                        grid.put(new Location(r, c), num * 2);
                    }
                }
            }
        }
        moveTiles(dir);
    }

    private boolean win() {
        boolean twentyFortyEight = false;
        for (int r = 0; r < grid.getNumRows(); r++) {
            for (int c = 0; c < grid.getNumCols(); c++) {
                if (grid.get(new Location(r, c)) != null && grid.get(new Location(r, c)) == 2048) {
                    twentyFortyEight = true;
                }
            }
        }
        return twentyFortyEight;
    }

    private boolean lose() {
        boolean moves = false;
        for (int r = 0; r < grid.getNumRows() - 1; r++) {
            for (int c = 0; c < grid.getNumCols(); c++) {
                if (grid.get(new Location(r, c)) != null && ((Integer) (grid.get(new Location(r, c)))).equals((Integer) (grid.get(new Location(r + 1, c))))) {
                    moves = true;
                }
            }
        }
        for (int c = grid.getNumCols() - 1; c >= 1; c--) {
            for (int r = 0; r < grid.getNumRows(); r++) {
                if (grid.get(new Location(r, c)) != null && ((Integer) (grid.get(new Location(r, c)))).equals((Integer) (grid.get(new Location(r, c - 1))))) {
                    moves = true;
                }
            }
        }

        for (int r = grid.getNumRows() - 1; r >= 1; r--) {
            for (int c = 0; c < grid.getNumCols(); c++) {
                if (grid.get(new Location(r, c)) != null && ((Integer) (grid.get(new Location(r, c)))).equals((Integer) (grid.get(new Location(r - 1, c))))) {
                    moves = true;
                }
            }
        }
        for (int c = 0; c < grid.getNumCols() - 1; c++) {
            for (int r = 0; r < grid.getNumRows(); r++) {
                if (grid.get(new Location(r, c)) != null && ((Integer) (grid.get(new Location(r, c)))).equals((Integer) (grid.get(new Location(r, c + 1))))) {
                    moves = true;
                }
            }
        }
        return !win() && !moves && grid.getOccupiedLocations().size() == 16;
    }
}
