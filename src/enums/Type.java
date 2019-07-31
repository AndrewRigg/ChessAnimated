package enums;

public enum Type {
	
	KNIGHT(2, 2, 1, 3), BISHOP(2, 3, 1, 3),  ROOK(2, 1, 1, 5), QUEEN(1, 4, 1, 9), KING(1, 5, 1, 1000000), PAWN(8, 1, 2, 1);
	
	  private final int quantity;
	  private final int positionX, positionY;
	  private double points;
	  
      Type(int quantity, int positionX, int positionY, double points) {
    	  this.quantity = quantity;
          this.positionX = positionX;
          this.positionY = positionY;
          this.points = points;
      }
      
      public int getQuantity() { 
    	  return quantity; 
      }
      
      public int getPositionX() { 
    	  return positionX; 
      }
      
      public int getPositionY() {
    	  return positionY;
      }
      
      public double getPoints() { 
    	  return points; 
      }
      
      /**
       * Set a custom amount of points 
       * Literals.java defines multiple commonly used points systems
       * @param customPoints
       */
      public void setPoints(double [] customPoints) {
    	  this.points = customPoints[this.ordinal()];
      }
}
