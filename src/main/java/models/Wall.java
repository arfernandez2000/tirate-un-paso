package models;

public enum Wall {
    TOP (null, 112),
    BOTTOM (null, 0),
    LEFT (0, null),
    RIGHT(224, null);

    private Integer x;
    private Integer y;

    Wall(Integer x, Integer y){
        this.x = x;
        this.y = y;
    }

    public boolean isRightWall(){
        return this == RIGHT;
    }

    public boolean isTopWall(){
        return this == TOP;
    }

    public Integer getX() {
        return x;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public Integer getY() {
        return y;
    }

    public void setY(Integer y) {
        this.y = y;
    }
}
