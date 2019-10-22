
public class Test {
    public static void main(String[] args) {
        int rando = (int)(10*Math.random()+1);
        System.out.println(rando);
        if(rando < 3)
        {
            System.out.println("Boi");
        }
        else if(rando >= 3 && rando < 7)
        {
            System.out.println("Yeet");
        }
        else
        {
            System.out.println("You FOOL!!!!");
        }
        int ohNo = yeeter(rando);
        System.out.println(ohNo);
        yeeter2(rando);
    }

    public static int yeeter(int yeet)
    {
        int doneGoofed = yeet + 10;
        return doneGoofed;
    }

    public static void yeeter2(int yeet)
    {
        int doneGoofed = yeet + 10;
        System.out.println(doneGoofed);
        

    }
}