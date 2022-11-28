import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FishGame {
//    10 male and 10 female fishes are added to an infinite pond.
//    Fishes randomly meet other fish in the pond according to the
//    rules.
//1) If two male fishes meet they kill each other
//2) If two female fishes meet one of them dies at random
//3) If male and female fish meet they spawn two new fishes of
//    random gender
//4) Fishes which are in meeting cannot be used by other
//    threads
//    Simulate the infinite pond using 5 threads. Print the
//    number of fishes remaining after each meeting. If all the fishes
//    are dead, terminate the program.
    static ArrayList<Fish> fishPool = new ArrayList<>();
    static ExecutorService conference = Executors.newFixedThreadPool(5);
    public static void main(String[] args) {
        // add fishes
        for (int i = 0; i < 1; i++) {
            Fish fish=new Fish(i,"male","alive");
            new Thread(fish).start();
            fishPool.add(fish);
        }
        for (int i = 1; i < 2; i++) {
            Fish fish=new Fish(i,"female","alive");
            new Thread(fish).start();
            fishPool.add(fish);
        }


    }
    public synchronized static void meet(int id1,int id2){
        Fish fish1 = fishPool.get(id1);
        Fish fish2 = fishPool.get(id2);
        if (fish1.id==fish2.id){
            return;
        }
        if (fish1.status.equals("dead")||fish1.status.equals("meeting")){
            return;
        }
        if (fish2.status.equals("dead")||fish2.status.equals("meeting")){
            return;
        }
        fish1.status="meeting";
        fish2.status="meeting";
        conference.submit(new Meeting(fish1,fish2));

    }

    public synchronized static void addNewFish(Fish fish){
        fish.id=fishPool.size();
        new Thread(fish).start();
        fishPool.add(fish);

    }
}
class Meeting implements Runnable{
    Fish fish1;
    Fish fish2;
    public Meeting(Fish fish1, Fish fish2) {
        this.fish1 = fish1;
        this.fish2 = fish2;
    }
    @Override
    public void run() {
        if (fish1.gender.equals("male")&&fish2.gender.equals("male")){
            fish1.status="dead";
            fish2.status="dead";
        }else if (fish1.gender.equals("female")&&fish2.gender.equals("female")){
            int choice = (int) (Math.random()*2);
            if (choice==0){
                fish1.status="dead";
                fish2.status="alive";
                
            }else{
                fish2.status="dead";
                fish1.status="alive";
            }
        } else if ((fish1.gender.equals("male")&&fish2.gender.equals("female"))||(fish1.gender.equals("female")&&fish2.gender.equals("male"))) {
            // spawn two new fish
            spawn();
            spawn();
            fish1.status="alive";
            fish2.status="alive";
        }
        int liveFish=0;
        for (int i = 0; i < FishGame.fishPool.size(); i++) {
            if (FishGame.fishPool.get(i).isAlive()){
                liveFish++;
            }
        }
        if (liveFish==0||liveFish==1){
            FishGame.conference.shutdown();
            for (Fish fish : FishGame.fishPool) {
                fish.status="dead";
            }
        }
        System.out.println("Live Fish:"+liveFish);

    }

    public void spawn(){
        int gender=(int)(Math.random()*2) ;
        Fish fish;
        if (gender==0){
            fish = new Fish("male","alive");
        }else {
            fish=new Fish("female","alive");
        }


        FishGame.addNewFish(fish);
    }


}


class Fish implements Runnable {
    int id;
    String gender;
    String status;

    public Fish(String gender, String status) {
        this.gender = gender;
        this.status = status;
    }

    public Fish(int id, String gender, String status) {
        this.id = id;
        this.gender = gender;
        this.status = status;
    }

    @Override
    public void run() {
        while (!status.equals("dead")){
                // searching for a fish
                int targetFish = (int) (Math.random()*FishGame.fishPool.size());
                FishGame.meet(this.id,targetFish);
        }
    }

    public boolean isAlive(){
        if (status.equals("dead")){
            return false;
        }
        return true;
    }


}
