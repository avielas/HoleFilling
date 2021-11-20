package hole_filling;

public enum Direction {
    UP(0), RIGHT(1), DOWN(2), LEFT(3);
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
                return Direction.UP;
            case 1:
                return Direction.RIGHT;
            case 2:
                return Direction.DOWN;
            case 3:
                return Direction.LEFT;
            default:
                return null;
        }
    }
}
