package motherteresa;

public class Coordinates
{
   public int x;
   public int y;

   public Coordinates()
   {
      this(0, 0);
   }

   public Coordinates(Coordinates coord)
   {
      this(coord.x, coord.y);
   }

   public Coordinates(int x, int y)
   {
      this.x = x;
      this.y = y;
   }
   
   public int getX()
   {
       return x; 
   }

   public int getY()
   {
       return y; 
   }
}
