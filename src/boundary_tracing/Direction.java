package boundary_tracing;

public enum Direction {
    NORTH(0), EAST(1), SOUTH(2), WEST(3);
    private int value;

    Direction(int v){ value = v; }

    public Direction rotateRight(){
        return getDirectionByValue((value+1)%4);
    }

    public Direction rotateLeft(){
        int val = value == 0 ? 3 : value - 1;
        return getDirectionByValue(val);
    }

    public Direction getDirectionByValue(int v){
        switch (v) {
            case 0:
                return Direction.NORTH;
            case 1:
                return Direction.EAST;
            case 2:
                return Direction.SOUTH;
            case 3:
                return Direction.WEST;
            default:
                return null;
        }
    }
}
