public class Test1559
{
    private int x;
    private String Mike;
    public Test1559()
    {
        x = 1559;
        Mike = "Furry";
    }

    public Test1559(int x, String Mike)
    {
        this.x = x;
        this.Mike = Mike;
    }

    public int getX()
    {
        return x;
    }

    public String getMike()
    {
        return Mike;
    }

    public void setX(int x)
    {
        this.x = x;
    }

    public void setMike(String Mike)
    {
        this.Mike = Mike;
    }

    public String toString()
    {
        return Mike + " is number " + x;
    }
}