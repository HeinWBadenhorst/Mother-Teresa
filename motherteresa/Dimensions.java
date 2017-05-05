package motherteresa;


public class Dimensions
{
   public int w;
   public int h;

   public Dimensions()
   {
      this(0, 0);
   }

   public Dimensions(Dimensions dim)
   {
      this(dim.w, dim.h);
   }

   public Dimensions(int w, int h)
   {
      this.w = w;
      this.h = h;
   }
   
   public int getWidth()
   {
       return w;
   }

   public int getHeight()
   {
       return h;
   }
}
