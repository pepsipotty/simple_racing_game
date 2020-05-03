import java.util.ArrayList;

/**
 * This class represents a highway. A highway consists of multiple Lanes.
 * If h Is a reference to a highway object, the leftmost lane has number
 *   0 and the rightmost lane has number given by h.getNumLanes() - 1.
 * 
 * This class was originally written for the third programming assignment
 *   in CS 302 at UW-Madison in Spring 2009 by the current author.
 *
 * @author Dalibor Zeleny (dalibor@cs.wisc.edu)
 */
public class Highway {
	
	ArrayList<Lane> lanes; //in scope
   
   /**
    * Creates a highway with a specified number of Lanes. 
    * 
    * @param numLanes the number of lanes this highway should have
    * 
    */
	
   public Highway(int numLanes) {
	   this.lanes = new ArrayList<Lane>();
	 for (int i = 0; i < numLanes; i++) {
		 
		 Lane lane = new Lane();
		 lanes.add(lane);
		 
	 }
	 
	  
	 
   }
   
   
   /**
    * Determines whether the car in the parameter can go in the specified lane
    *   without crashing.
    * 
    * @param car The car that's trying to fit in the given lane
    * @param laneNumber The number of the lane where we are trying to fit the car
    * @return whether the car can fit in that lane
    */
   public boolean canFitCar(Car car, int laneNumber) {
	   
	   return lanes.get(laneNumber).canFitCar(car);
	   
	   
	
	 
   }
   
   
   /**
    * Adds car to the specified lane. This method does not
    *   attempt to verify that there is space for car in this lane.
    *   The user can verify there is room by calling the canFitCarInLane()
    *   method.
    *
    * @param car the car we're adding to the lane.
    * @param laneNumber The number of the lane where we are adding the car 
    */
   public void addCar(Car car, int laneNumber) {
	   
	   lanes.get(laneNumber).addCar(car);
      
   }
   
   
   /**
    * Adds the coin co to the specified lane.
    *
    * @param co the coin we're adding to the lane.
    * @param laneNumber The number of the lane where we are adding the coin 
    */
   public void addCoin(Coin co, int laneNumber) {
	   
	  
	   lanes.get(laneNumber).addCoin(co);
      
   }
      
   
   /**
    * Moves car from lane fromLane to lane toLane.
    * This method assumes that car is initially in lane number 
    * fromLane and makes no effort to check whether there is room
    * for car in lane number toLane.
    * 
    * @param car The car to move.
    * @param fromLane The number of the lane where the car is now.
    * @param toLane The number of the lane where the car should go.
    */
   public void moveCar(Car car, int fromLane, int toLane) {
      removeCar(car, fromLane); //removes car from lane
      addCar(car, toLane); //adds car to new lane
   }
   
   
   /**
    * Returns the number of lanes on this highway.
    * @return the number of lanes on this highway
    */
   public int getNumLanes() {
	  return lanes.size(); //returns number of lanes in arraylist lanes
	  
   }
   
   
   /**
    * Removes car from the specified lane, and returns
    * a reference to it. If the car isn't found in the lane, return null.
    * 
    * @param car The car to remove
    * @param laneNumber The number of the lane where the car should be
    * @return reference to the removed car or null if the car was not removed
    */
   public Car removeCar(Car car, int laneNumber) {
	   return lanes.get(laneNumber).removeCar(car); //removes car from lane
      
   }
   
   
   /**
    * Removes co from the specified lane, and returns
    * a reference to it. If the coin isn't found in this lane, return null.
    * 
    * @param co The coin to remove
    * @param laneNumber The number of the lane where the coin should be
    * @return reference to the removed object or null if it was not removed
    */
   public Coin removeCoin(Coin co, int laneNumber) {
      return lanes.get(laneNumber).removeCoin(co); //removes coin from lane
   }
   
   
   /**
    * Resets the iterators for all lanes. That is, after calling this method,
    * the following holds for every lane in the highway:
    * the next call to getNextCar() or lookAtNextCar() 
    * will return a reference to the first car in the specified lane. 
    * Similarly, the next call to getNextCoin() or 
    * lookAtNextCoin() will return a reference to the 
    * first coin for any lane.
    */
   public void rewind() {
	   
	   Lane laneRewindAll = new Lane();
	   laneRewindAll.rewind();
      
   }
   
   
   /**
    * Resets the iterators for the specified lane. That is, after calling this method,
    * the next call to getNextCar() or lookAtNextCar() 
    * will return a reference to the first car in the specified lane. 
    * Similarly, the next call to getNextCoin() or 
    * lookAtNextCoin() will return a reference to the 
    * first coin in the specified lane.
    * 
    * @param laneNumber The number of the lane whose iterators should be reset.
    */
   public void rewind(int laneNumber) {
	   lanes.get(laneNumber).rewind();
      
   }
   
   
   /**
    * Checks whether the iterator for cars for the current lane has reached the end
    *   of the list of cars for that lane.
    *   
    * @param laneNumber The lane to check
    * @return true if there are cars left, false otherwise
    */
   public boolean hasNextCar(int laneNumber) {
	   return lanes.get(laneNumber).hasNextCar();
      
   }
   
   
   /**
    * Returns a reference to the car pointed to by the iterator for cars for the given
    *   lane. This method does not advance the iterator.
    *   
    * @param laneNumber The lane to check
    * @return reference to the car pointed to by the lane's iterator for cars
    */
   public Car lookAtNextCar(int laneNumber) {
     return lanes.get(laneNumber).lookAtNextCar();
   }
   
   
   /**
    * Returns a reference to the car pointed to by the iterator for cars for the given 
    *   lane. This method advances the iterator after each call.
    *   
    * @param laneNumber The lane to check
    * @return reference to the car pointed to by the lane's iterator for cars
    */
   public Car getNextCar(int laneNumber) {
	   return lanes.get(laneNumber).getNextCar();
   }
   
   
   /**
    * Checks whether the iterator for coins for the current lane 
    *   has reached the end of the list of coins for that lane.
    *   
    * @param laneNumber The lane to check
    * @return true if there are coins left, false otherwise
    */
   public boolean hasNextCoin(int laneNumber) {
      return lanes.get(laneNumber).hasNextCoin();
   }
   
   
   /**
    * Returns a reference to the coin pointed to by the iterator 
    *   for coins for the given
    *   lane. This method does not advance the iterator.
    *   
    * @param laneNumber The lane to check
    * @return reference to the coin pointed to by the lane's 
    *   iterator for coins
    */
   public Coin lookAtNextCoin(int laneNumber) {
	   return lanes.get(laneNumber).lookAtNextCoin();
      
   }
   
   
   /**
    * Returns a reference to the coin pointed to by the iterator 
    *   for coins for the given 
    *   lane. This method advances the iterator after each call.
    *   
    * @param laneNumber The lane to check
    * @return reference to the coin pointed to by the lane's 
    *   iterator for coins
    */
   public Coin getNextCoin(int laneNumber) {
      return lanes.get(laneNumber).getNextCoin();
   }
   
   
   /**
    * Returns a string representation of the current state of the highway.
    * The string should list all cars on the highway, grouped by lane. Here
    * is an example...
    * 
    * <xmp>
    * Lane 0: 5 car(s), 1 coin(s)
CAR 0: ID: 41, distance: 1098.5299999999847, velocity: 33.0, length: 4.0; driven by Bob who prefers 33.0
CAR 1: ID: 35, distance: 1042.9299999999862, velocity: 43.0, length: 3.75; driven by Bob who prefers 43.0
CAR 2: ID: 31, distance: 1008.4099999999748, velocity: 56.0, length: 3.75; driven by Bob who prefers 56.0
CAR 3: ID: 4, distance: 845.9199999999934, velocity: 66.0, length: 3.5; driven by Bob who prefers 66.0
CAR 4: ID: 2, distance: 563.2399999999914, velocity: 52.0, length: 5.75; driven by Bob who prefers 52.0
COIN 0: ID: 3, at distance: 955.850000000004 with score bonus 796(-12 decrease), time bonus 6.300000000000006(-0.1 decrease) and decrease period 5. It has lived 88 out of 400 ticks

Lane 1: 9 car(s), 0 coin(s)
CAR 0: ID: 40, distance: 1277.4299999999923, velocity: 78.0, length: 4.5; driven by Bob who prefers 78.0
CAR 1: ID: 42, distance: 1184.5500000000104, velocity: 70.0, length: 4.0; driven by Bob who prefers 70.0
CAR 2: ID: 36, distance: 1126.8599999999908, velocity: 53.0, length: 4.5; driven by Bob who prefers 53.0
CAR 3: ID: 34, distance: 1119.609999999992, velocity: 52.0, length: 3.25; driven by Bob who prefers 52.0
CAR 4: ID: 39, distance: 993.3899999999787, velocity: 24.0, length: 4.5; driven by Bob who prefers 24.0
CAR 5: ID: 1, distance: 926.25, velocity: 100.0, length: 5.0; driven by Dalibor who prefers 10.0
CAR 6: ID: 15, distance: 906.5300000000148, velocity: 48.0, length: 4.75; driven by Bob who prefers 48.0
CAR 7: ID: 11, distance: 787.7300000000116, velocity: 36.0, length: 4.75; driven by Bob who prefers 36.0
CAR 8: ID: 3, distance: 768.1599999999852, velocity: 68.0, length: 3.0; driven by Bob who prefers 68.0

Lane 2: 9 car(s), 0 coin(s)
CAR 0: ID: 49, distance: 1351.3499999999992, velocity: 30.0, length: 4.25; driven by Bob who prefers 30.0
CAR 1: ID: 46, distance: 1246.3000000000086, velocity: 43.0, length: 4.5; driven by Bob who prefers 43.0
CAR 2: ID: 32, distance: 1102.5899999999992, velocity: 66.0, length: 5.25; driven by Bob who prefers 66.0
CAR 3: ID: 12, distance: 1082.0099999999923, velocity: 76.0, length: 3.75; driven by Bob who prefers 76.0
CAR 4: ID: 24, distance: 1017.5600000000327, velocity: 69.0, length: 5.5; driven by Bob who prefers 69.0
CAR 5: ID: 8, distance: 999.5899999999751, velocity: 78.0, length: 5.0; driven by Bob who prefers 78.0
CAR 6: ID: 7, distance: 720.7999999999781, velocity: 40.0, length: 5.0; driven by Bob who prefers 40.0
CAR 7: ID: 13, distance: 683.0499999999809, velocity: 40.0, length: 5.5; driven by Bob who prefers 40.0
CAR 8: ID: 5, distance: 618.3800000000087, velocity: 49.0, length: 4.25; driven by Bob who prefers 49.0

Lane 3: 10 car(s), 0 coin(s)
CAR 0: ID: 47, distance: 1275.75, velocity: 75.0, length: 3.25; driven by Bob who prefers 75.0
CAR 1: ID: 28, distance: 1205.6399999999908, velocity: 77.0, length: 3.5; driven by Bob who prefers 77.0
CAR 2: ID: 26, distance: 1139.090000000015, velocity: 72.0, length: 5.0; driven by Bob who prefers 72.0
CAR 3: ID: 9, distance: 1050.9299999999841, velocity: 65.0, length: 3.5; driven by Bob who prefers 65.0
CAR 4: ID: 25, distance: 1037.4000000000206, velocity: 71.0, length: 4.75; driven by Bob who prefers 71.0
CAR 5: ID: 23, distance: 977.1699999999805, velocity: 66.0, length: 4.0; driven by Bob who prefers 66.0
CAR 6: ID: 21, distance: 954.5500000000054, velocity: 59.0, length: 4.75; driven by Bob who prefers 59.0
CAR 7: ID: 14, distance: 930.2699999999837, velocity: 78.0, length: 3.75; driven by Bob who prefers 78.0
CAR 8: ID: 20, distance: 890.2200000000081, velocity: 47.0, length: 4.25; driven by Bob who prefers 47.0
CAR 9: ID: 17, distance: 800.0099999999715, velocity: 56.0, length: 5.0; driven by Bob who prefers 56.0

Lane 4: 17 car(s), 0 coin(s)
CAR 0: ID: 50, distance: 1344.25, velocity: 25.0, length: 4.0; driven by Bob who prefers 25.0
CAR 1: ID: 48, distance: 1270.1099999999974, velocity: 36.0, length: 4.5; driven by Bob who prefers 36.0
CAR 2: ID: 44, distance: 1221.8199999999824, velocity: 37.0, length: 5.75; driven by Bob who prefers 37.0
CAR 3: ID: 45, distance: 1188.4500000000062, velocity: 20.0, length: 4.0; driven by Bob who prefers 20.0
CAR 4: ID: 43, distance: 1178.0500000000147, velocity: 41.0, length: 3.75; driven by Bob who prefers 41.0
CAR 5: ID: 37, distance: 1090.7500000000048, velocity: 42.0, length: 3.25; driven by Bob who prefers 42.0
CAR 6: ID: 38, distance: 1000.0899999999892, velocity: 29.0, length: 4.5; driven by Bob who prefers 29.0
CAR 7: ID: 30, distance: 980.469999999988, velocity: 22.0, length: 3.5; driven by Bob who prefers 22.0
CAR 8: ID: 22, distance: 974.7300000000337, velocity: 44.0, length: 4.5; driven by Bob who prefers 44.0
CAR 9: ID: 27, distance: 941.9799999999896, velocity: 27.0, length: 5.5; driven by Bob who prefers 27.0
CAR 10: ID: 18, distance: 929.839999999977, velocity: 67.0, length: 3.0; driven by Bob who prefers 67.0
CAR 11: ID: 33, distance: 894.4700000000081, velocity: 22.0, length: 4.5; driven by Bob who prefers 22.0
CAR 12: ID: 29, distance: 847.9400000000202, velocity: 33.0, length: 4.0; driven by Bob who prefers 33.0
CAR 13: ID: 19, distance: 824.6199999999726, velocity: 31.0, length: 4.0; driven by Bob who prefers 31.0
CAR 14: ID: 16, distance: 682.4900000000073, velocity: 24.0, length: 3.75; driven by Bob who prefers 24.0
CAR 15: ID: 10, distance: 611.5900000000147, velocity: 22.0, length: 3.0; driven by Bob who prefers 22.0
CAR 16: ID: 6, distance: 562.6199999999928, velocity: 51.0, length: 5.75; driven by Bob who prefers 51.0
   </xmp>
    */
   public String toString() {
      return lanes.toString();
   }
}
