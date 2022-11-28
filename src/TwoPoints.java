public class TwoPoints {

    public static void main(String[] args) {
        int result = computePointOfInterception(2, 4);

    }

    public static int computePointOfInterception(int x, int y) {
    //TODO Add your implementation here
        int distance=0;
        if (x<=y){
            distance=y-x;
        }else {
            distance=12-(x-y);
        }
        int point=y+distance;
        if (point>12){
            point-=12;
        }
        return point;
    }
}


