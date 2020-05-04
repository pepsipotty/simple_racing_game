import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * This class represents the car game. The car game maintains information
 *   about the highway where the game is happening as well as about the
 *   player, the player's score, and how much time is left in the game.
 *   It also defines a random number generator that is
 *   used throughout the game and keeps track of the current tick number.
 * 
 * @author Dalibor Zeleny (dalibor@cs.wisc.edu)
 */
public class CarGame {
   // Do not change any of these variables and constants described here.
   private Random r = new Random();
   private boolean[] keysPressed = new boolean[4];
   /**
    * The number of ticks in each second.
    */
   public static long TICKS_PER_SECOND = 40;
   /**
    * If the distance of any car is more than this constant's value from the
    *   player's car, it will get removed from the highway.
    */
   public static double DROP_CARS_DISTANCE = 1000;
   private double addCarProbability;
   private double addCoinProbability;
   
   private Highway highway;
   private Driver player;
   private Car car;
   private int playerCarLane = 0;
   
   public int score = 0;
   private long tickNumber;
   private double timeLeft  = 60.0;
   private int gameDelay;
  
  
   private double tickLength = 0.01;
  
   
   
   /**
    * Returns a reference to the random number generator used by this game.
    * @return a reference to the random number generator used by this game.
    */
   public Random getRNG() {
      return r;
   }
   
   
   /**
    * Updates which keys were pressed before this tick. The keystrokes will
    *   be acknowledged by the game in its next tick. The array indices
    *   correspond to the constants KEY_UP, <TT>KEY_RIGHT,
    *   KEY_DOWN and KEY_LEFT in CarGameController.
    * You should not call this method from anywhere in your code.
    *
    * @param keystrokes An array indicating which keys were pressed. An array
    *   element is true if and only if its corresponding key was pressed.
    */
   public void updateKeystrokes(boolean[] keystrokes) {
      for (int i = 0; i < this.keysPressed.length; i++) {
         this.keysPressed[i] = keystrokes[i];
      }
   }
   
   
   
   
   /**
    * Creates a new CarGame object with the specified properties
    * 
    * @param numLanes Number of lanes in the highway
    * @param tickLength Length of a tick. Recommended: 0.01
    * @param playerName Name of the player
    * @param ticksBeforeStart Number of ticks before the start of
    *    the game. This allows other parts of the program to do some setup 
    *    before the game actually starts and also to display a nice countdown.
    */
   public CarGame(int numLanes, double tickLength, String playerName,
         int ticksBeforeStart) {
	   this.gameDelay = ticksBeforeStart;
	   this.player = new Driver(playerName, 12, true);
	   
//	   this.car = new Car(Color.BLUE, 5.0);
	   this.highway = new Highway(numLanes);
	   
//	   this.player.enterCar(this.car);
//	   this.car.setDriver(this.player);
//	   this.highway.addCar(this.car, 0);
	   
	   this.init();
	   
      // Here are some suggestions and remarks:
      // - It doesn't matter what you set the human driver's preferred
      //     velocity to. It won't be used by a human driver.
      // - Set the player's car color to blue using Color.BLUE as
      //     one of the arguments when calling the constructor that makes
      //     a new car for the player.
      // - Put the player's car in lane 0.
      // - The tick number should be the negative of ticksBeforeStart
   
   }

   /**
    * Sets up the initial state of the game (empties the highway,
    * resets the number of ticks to ticksBeforeStart, puts a new
    * player in the game, adds the car to a lane on the highway,
    * sets initial time and score, and so on.
    */
    private void init() {
    	this.tickNumber = 0 - this.gameDelay;
    	this.car = new Car(Color.BLUE, 5.0);
    	this.player.enterCar(this.car);
    	this.car.setDriver(player);
    	
    	this.car.setVelocity(50.0);
    	this.highway.addCar(car, 0);
    	this.playerCarLane = 0;
    	this.timeLeft = 60;
    	this.score = 0;

    }
   
   
   /** 
    * Returns a reference to an object that represents the player.
    * @return a reference to an object that represents the player.
    */
   public Driver getPlayer() {
	   return this.player;
      
   }
   
   
   /**
    * Returns the player's score.
    * @return the player's score.
    */
   public long getScore() {
      return this.score; 
   }
   
   
   /**
    * Returns how many seconds are remaining in the game.
    * @return how many seconds are remaining in the game.
    */
   public double getTimeLeft() {
      return this.timeLeft;
   }
   
   
   /**
    * Returns the current tick number.
    * @return the current tick number.
    */
   public long getTickNumber() {
      return this.tickNumber;
   }
   
   
   /**
    * Updates the probability with which another car should be added in each
    *   tick using the parameter as the new value.
    * @param addCarProbability A positive double value that is less than 1. 
    */
   public void setAddCarProbability(double addCarProbability) {
      this.addCarProbability = addCarProbability;
   }
   
   
   /**
    * Updates the probability with which another object should be added in each
    *   tick using the parameter as the new value.
    * @param addObjectProbability A positive double value that is less than 1.
    */
   public void setAddCoinProbability(double addObjectProbability) {
	   this.addCoinProbability = addObjectProbability;
   }

   
   /**
    * Runs one tick of the game. You should not modify this method's code.
    */
   public void tick() {      
      // Add a random cars with some probability.
      // We allow multiple cars and/or objects to be added.
	   while (this.getRNG().nextDouble() < addCarProbability) {
	         this.addRandomCar();
	      }
      // A negative tick number means the game is not ready to start yet.
      // This is used mostly so that we have some time to set up
      //   the game world and also to display a countdown before game starts.
      if (this.tickNumber < 0) {
         this.tickNumber++;
         return;
      }
      
      // We add random objects only after the game starts.
      while (this.getRNG().nextDouble() < addCoinProbability) {
         this.addRandomCoin();
      }
      
      // Drivers should now all be able to act
      this.highway.rewind();
      for (int i = 0; i < this.highway.getNumLanes(); i++) {
    	 this.highway.lanes.get(i).rewind(); //WHAT THE FUCK IS GOING ON?! WHY ARE YOU NOT RESETTING THE ITERATOR YOU NITWITjhfjk,yfikyrfyikurf
         while (this.highway.hasNextCar(i)) { 
            this.highway.getNextCar(i).getDriver().setCanAct(true);
         }
      }
      
      // Unlike computers that move cars around by using the makeDecision() method,
      //   human drivers change depending on keyboard input. Now is the time
      //   to process those key presses. 
      this.processKeysPressed();
      
      // We must check for crashes now because the player has switched lanes.
      // If the player did crash, that's it for this tick and the game is over.
      this.checkForCrashes();
      if (this.player.getDrivenCar().isCrashed()) {
         return;
      }
      
      
      // Looks like the driver has moved and hasn't run into anything,
      //   so let's see if he caught something.
      this.pickUpCoins();
      
      // Let all the other drivers adjust their cars' speeds and change lanes. 
      this.notifyDrivers();
      
      // Move everbody now
      this.moveEverything();
      
      // Check for crashes again in case our drivers did something silly.
      this.checkForCrashes();
      if (this.player.getDrivenCar().isCrashed()) {
         return;
      }
      
      // Maybe the driver caught some additional objects after everything has
      //   moved
      this.pickUpCoins();
      
      // Remove all expired coins.
      this.removeExpiredCoins();
      
      // Dump cars that are too far (on either side of the player's car).
      this.removeDistantCars();
      
      // Some bookkeeping at the end.
      this.tickNumber++;
      this.timeLeft -= 1.0 / CarGame.TICKS_PER_SECOND;
      
      

   }
   
   
   /**
    * Returns a reference to the highway where the game is happening.
    * @return a reference to the highway where the game is happening.
    */
   public Highway getHighway() {
      return this.highway;
   }
      
   
   /**
    * Indicates whether the game is over. The game is over when the player
    *   crashed his car or when time runs out.
    * @return whether the game is over.
    */
   public boolean isGameOver() {
      return (this.player.getDrivenCar().isCrashed() || timeLeft == 0);
   }

   
   /**
    * Restarts the game. This should restore the highway to the state it was
    *   in right after the constructor finished.
    */
   public void reset() {
      
   }
   
   
   /**
    * Returns a string description of the game's state. Here is an example of
    *   what the output could look like:
    * <xmp>Tick 962, score: 2172, time left: 53
Lane 0: 5 car(s), 1 coin(s)
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
    * </xmp>
    */
   public String toString() {
      return " ";
   }

   
   /**
    * For each car on the highway, tell its driver about nearby cars by calling
    *   the makeDecision() method. Once the makeDecision()
    *   method returns, put the car in the correct lane if the driver chose to 
    *   switch lanes. The makeDecision() method should not get 
    *   called twice for any driver, which you can guarantee by properly using
    *   the canAct() and setCanAct() methods.
    */
   private void notifyDrivers() {
	   
	   
	   //needs rework
	   double velocityOfCarAhead;
	   double distanceFromCarAhead;

       double velocityOfCarBehind;
       double distanceFromCarBehind;
       
       double velocityOfCarAheadLeft;
       double distanceFromCarAheadLeft;
       
       double velocityOfCarBehindLeft;
       double distanceFromCarBehindLeft;
       
       double velocityOfCarAheadRight;
       double distanceFromCarAheadRight;
       
       double velocityOfCarBehindRight;
       double distanceFromCarBehindRight;
       
       double tickLength = this.tickLength;

       for (int i = 0; i < this.highway.getNumLanes(); i++)
	   {
    	   
    	   
    	   Car currentCar = null;
    	   Car carAhead = null;
    	   Car carBehind = null;
    	   Car carLeftAhead = null;
    	   Car carLeftBehind = null;
    	   Car carRightAhead = null;
    	   Car carRightBehind = null;
    	   
    	  
    	   
    	   Lane currentLane = this.highway.lanes.get(i);
    	   currentLane.rewind();
    	   Lane leftLane = null;
    	   Lane rightLane = null;
    	   
    	   if (currentLane.hasNextCar() == true)
    	   {
    		   currentCar = currentLane.getNextCar(); 
    	   }
    	   //IDENTIFICATION of cars in relation to currentCar
    	   while (currentCar != null) //IDENTIFICATION of cars within currentLane
    	   {
    		   if (currentLane.hasNextCar() == true) //if the lane is populated, the carBehind is set to the first car in the ArrayList
    		   {
    			   carBehind = currentLane.getNextCar(); //the NEXT car becomes the carBehind
    		   }
    		   else if (currentLane.hasNextCar() == false)
    		   {
    			   carBehind = null;
    		   }
    		   
    		   if (currentCar.getDriver().isHuman() == true || currentCar.getDriver().canAct() == false)
    		   {
    			   carAhead = currentCar; 
    			   currentCar = carBehind;
    			   continue;
    		   }
    		   else //IDENTIFICATION of cars adjacent to currentLane
    		   {
    			   if (i != 0 && i != this.highway.getNumLanes()-1)  //CASE 1: NOT EXTREME RIGHT OR EXTREME LEFT
    			   {  
    				   leftLane = this.highway.lanes.get(i-1); //lane to the left
    				   leftLane.rewind();
    				   rightLane = this.highway.lanes.get(i+1); //lane to the right
    				   rightLane.rewind();
    				   //identify cars immediately ahead and behind LEFT lane
	    			   while (leftLane.hasNextCar())
	    			   {
	    				   carLeftAhead = leftLane.getNextCar();
	    				   
	    				   if (carLeftAhead.getPositionOfFront() < currentCar.getPositionOfFront()) //no cars in front of currentCar. carLeftAhead is definitely Behind  currentCar
	    				   {
	    					   carLeftBehind = carLeftAhead;
	    					   carLeftAhead = null;
	    					   break;
	    				   }
	    				   else if(leftLane.hasNextCar() == false) //if there are no other cars behind carLeftAhead, then carLeftBehind doesn't exist.
	    				   {
	    					   carLeftBehind = null;
	    					   break;
	    				   }
	    				   else if (leftLane.lookAtNextCar().getPositionOfFront() < currentCar.getPositionOfFront()) //if the NEXT car has a position behind currentCar,and carLeftAhead is definitely in front of currentCar(otherwise the if condition above would trigger) it is the carLeftBehind. Middle Case
	    				   {
	    					   carLeftBehind = leftLane.lookAtNextCar();
	    					   break;    					   //break out of the while loop. We found our leftCarAhead and rightCarAhead.
	    				   }
	
	    			   }
	    			
	    			   
	    			 //identify cars immediately ahead and behind RIGHT lane
	    			   while (rightLane.hasNextCar())
	    			   {
	    				   carRightAhead = rightLane.getNextCar();
	    				   
	    				   if (carRightAhead.getPositionOfFront() < currentCar.getPositionOfFront()) //no cars in front of currentCar. carRightAhead is definitely behind currentCar
	    				   {
	    					   carRightBehind = carRightAhead;
	    					   carRightAhead = null;
	    					   break;
	    				   }
	    				   else if(rightLane.hasNextCar() == false) //if there are no other cars behind carLeftAhead, then carLeftBehind doesn't exist.
	    				   {
	    					   carRightBehind = null;
	    					   break;
	    				   }
	  
	    				   else if (rightLane.lookAtNextCar().getPositionOfFront() < currentCar.getPositionOfFront()) //if the NEXT car has a position behind currentCar, it is the carRightBehind. Middle Case
	    				   {
	    					   carRightBehind = rightLane.lookAtNextCar();
	    					   break;    					   //break out of the while loop. We found our leftCarAhead and rightCarAhead.
	    					   
	    				   }
	    			   }
    			   }
    			   else if (i == 0) //if currentLane is the leftmost lane
    			   {
    				   rightLane = this.highway.lanes.get(i+1);
    				   rightLane.rewind();
    				   while (rightLane.hasNextCar())
	    			   {
	    				   carRightAhead = rightLane.getNextCar();
	    				   
	    				   if (carRightAhead.getPositionOfFront() < currentCar.getPositionOfFront()) //no cars in front of currentCar. carRightAhead is definitely in front of currentCar
	    				   {
	    					   carRightBehind = carRightAhead;
	    					   carRightAhead = null;
	    					   break;
	    				   }
	    				   else if(rightLane.hasNextCar() == false) //if there are no other cars behind carLeftAhead, then carLeftBehind doesn't exist.
	    				   {
	    					   carRightBehind = null;
	    					   break;
	    				   }
	  
	    				   else if (rightLane.lookAtNextCar().getPositionOfFront() < currentCar.getPositionOfFront()) //if the NEXT car has a position behind currentCar, it is the carRightBehind. Middle Case
	    				   {
	    					   carRightBehind = rightLane.lookAtNextCar();
	    					   break;    					   //break out of the while loop. We found our leftCarAhead and rightCarAhead.
	    					   
	    				   }
	    			   }
    			   }
    			   else //i=4
    			   {
    				   leftLane = this.highway.lanes.get(i-1);
    				   leftLane.rewind();
    				   while (leftLane.hasNextCar())
	    			   {
	    				   carLeftAhead = leftLane.getNextCar();
	    				   
	    				   if (carLeftAhead.getPositionOfFront() < currentCar.getPositionOfFront()) //no cars in front of currentCar. carLeftAhead is definitely in front of currentCar
	    				   {
	    					   carLeftBehind = carLeftAhead;
	    					   carLeftAhead = null;
	    					   break;
	    				   }
	    				   else if(leftLane.hasNextCar() == false) //if there are no other cars behind carLeftAhead, then carLeftBehind doesn't exist.
	    				   {
	    					   carLeftBehind = null;
	    					   break;
	    				   }
	    				   else if (leftLane.lookAtNextCar().getPositionOfFront() < currentCar.getPositionOfFront()) //if the NEXT car has a position behind currentCar,and carLeftAhead is definitely in front of currentCar(otherwise the if condition above would trigger) it is the carLeftBehind. Middle Case
	    				   {
	    					   carLeftBehind = leftLane.lookAtNextCar();
	    					   break;    					   //break out of the while loop. We found our leftCarAhead and rightCarAhead.
	    				   }
	    			   }
	 
    			   }
    			//Now we should have carAhead, carBehind, CarLeftAhead, carLeftBehind, carRightAhead, carRightBehind and currentCar. 
    			   
    			   //Calculations of velocities and distances of all these cars
    				   
    			   //carAhead
    			   if (carAhead != null) //if there is a car ahead
    			   {
    				   velocityOfCarAhead = carAhead.getVelocity();
    				   distanceFromCarAhead = ( ( carAhead.getPositionOfFront() - carAhead.LENGTH) ) - currentCar.getPositionOfFront();
    			   }
    			   else //if there is no car ahead
    			   {
    				   velocityOfCarAhead= -1;
    				   distanceFromCarAhead = -1;
    			   }
    			   
    			   //car Behind
    			   if (carBehind != null) //if there is a car behind
    			   {
    				   velocityOfCarBehind = carBehind.getVelocity();
    				   distanceFromCarBehind = ( ( currentCar.getPositionOfFront() - currentCar.LENGTH) ) - carBehind.getPositionOfFront();
    			   }
    			   else //if there is no car behind
    			   {
    				   velocityOfCarBehind = -1;
    				   distanceFromCarBehind = -1;
    			   }
    			   
    			   //leftAhead
    			   if (carLeftAhead != null && leftLane != null) //there is a lane to the side, and there is a car to the side
    			   {
    				   velocityOfCarAheadLeft = carLeftAhead.getVelocity();
    				   distanceFromCarAheadLeft = ( ( carLeftAhead.getPositionOfFront() - carLeftAhead.LENGTH) ) - currentCar.getPositionOfFront();
    			   }
    			   else if (carLeftAhead == null && leftLane != null ) //there is a lane to the side, but there is no car to the side
    			   {
    				   velocityOfCarAheadLeft = -1;
    				   distanceFromCarAheadLeft = -1;
    			   }
    			   else if (carLeftAhead == null && leftLane == null) //there is no lane to the side
    			   {
    				   velocityOfCarAheadLeft = 50;
    				   distanceFromCarAheadLeft = -1;
    			   }
    			   else
    			   {
    				   velocityOfCarAheadLeft = 50;
    				   distanceFromCarAheadLeft = -1;
    			   }
    			   
    			   //leftBehind
    			   if (carLeftBehind != null && leftLane != null) //there is a lane to the left, and there is a car to the left
    			   {
    				   velocityOfCarBehindLeft = carLeftBehind.getVelocity();
    				   distanceFromCarBehindLeft = ( ( currentCar.getPositionOfFront() - currentCar.LENGTH) ) - carLeftBehind.getPositionOfFront();
    			   }
    			   else if (carLeftBehind == null && leftLane != null) //there is a lane to the left, but no car to the left
    			   {
    				   velocityOfCarBehindLeft = -1;
    				   distanceFromCarBehindLeft = -1;
    			   }
    			   else if (carLeftBehind == null && leftLane == null) //if there is no lane and no car to the left
    			   {
    				   velocityOfCarBehindLeft = 50;
    				   distanceFromCarBehindLeft = -1;
    			   }
    			   else //if there is a car but no lane to the left(shouldn't exist)
    			   {
    				   velocityOfCarBehindLeft = 50;
    				   distanceFromCarBehindLeft = -1; 
    			   }
    			   
    			   //rightAhead
    			   if (carRightAhead != null && rightLane != null) //there is a lane to the side, and there is a car to the side
    			   {
    				   velocityOfCarAheadRight = carRightAhead.getVelocity();
    				   distanceFromCarAheadRight = ( ( carRightAhead.getPositionOfFront() - carRightAhead.LENGTH) ) - currentCar.getPositionOfFront();
    			   }
    			   else if (carRightAhead == null && rightLane != null ) //there is a lane to the side, but there is no car to the side
    			   {
    				   velocityOfCarAheadRight = -1;
    				   distanceFromCarAheadRight = -1;
    			   }
    			   else if (carRightAhead == null && rightLane == null) //there is no lane to the side
    			   {
    				   velocityOfCarAheadRight = 50;
    				   distanceFromCarAheadRight = -1;
    			   }
    			   else 
    			   {
    				   velocityOfCarAheadRight = 50;
    				   distanceFromCarAheadRight = -1; 
    			   }
    			   
    			   //rightBehind
    			   if (carRightBehind != null && rightLane != null) //there is a lane to the right, and there is a car to the right
    			   {
    				   velocityOfCarBehindRight = carRightBehind.getVelocity();
    				   distanceFromCarBehindRight = ( ( currentCar.getPositionOfFront() - currentCar.LENGTH) ) - carRightBehind.getPositionOfFront();
    			   }
    			   else if (carRightBehind == null && rightLane != null)
    			   {
    				   velocityOfCarBehindRight = -1;
    				   distanceFromCarBehindRight = -1;
    			   }
    			   else if (carRightAhead == null && rightLane == null)
    			   {
    				   velocityOfCarBehindRight = 50;
    				   distanceFromCarBehindRight = -1;
    			   }
    			   else
    			   {
    				   velocityOfCarBehindRight = 50;
    				   distanceFromCarBehindRight = -1;
    			   }
    			   
    			   
    			   //making the decision
    			   currentCar.getDriver().setCanAct(false);
    			   int decision = currentCar.getDriver().makeDecision(velocityOfCarAhead, distanceFromCarAhead, 
    				         velocityOfCarBehind, distanceFromCarBehind, 
    				         velocityOfCarAheadLeft, distanceFromCarAheadLeft, 
    				         velocityOfCarBehindLeft, distanceFromCarBehindLeft,
    				         velocityOfCarAheadRight, distanceFromCarAheadRight, 
    				         velocityOfCarBehindRight, distanceFromCarBehindRight,
    				         tickLength);
    			   
    			   

    			   
    			   if (decision == Driver.GO_LEFT)
    			   {
    				  this.highway.moveCar(currentCar, i, i-1);
    				  carLeftAhead = currentCar;
    				  currentCar = carAhead;
    				  
    			   }
    			   else if (decision == Driver.GO_RIGHT)
    			   {
    				   this.highway.moveCar(currentCar, i, i+1);
    				   carRightAhead = currentCar;
    				   currentCar = carAhead;
    			   }
    			   
    			   carAhead = currentCar;
    			   currentCar = carBehind;
    			   //progressing the iterator
    			  
    			   
    		   }
    	   }
	   
       currentLane.rewind();
    	}	   
   }

   

      
   /**
    * For each coin on the highway, check if it overlaps
    * with the player's car. If it does, update the player's score and
    * time left and remove that coin from the highway. 
    */
   private void pickUpCoins() {
	   Coin currentCoin;
	   Lane currentLane;
	   
	   
	   for (int i = 0; i < this.highway.getNumLanes(); i++)
	   {
		   currentLane = this.highway.lanes.get(i);
		   currentLane.rewind();
		   
		   while(currentLane.hasNextCoin())
		   {
			   currentCoin = currentLane.getNextCoin();
			   if (this.car.getPositionOfFront() - this.car.LENGTH < currentCoin.getPositionOfFront() && this.car.getPositionOfFront() > currentCoin.getPositionOfFront())
			   {
				   currentCoin.setIsCollected(true);
				   this.timeLeft += currentCoin.getTimeBonus();
				   this.score += currentCoin.getScoreBonus();
				   currentCoin.ticksAlive = currentCoin.lifetimeInTicks - (2 * CarGame.TICKS_PER_SECOND);
				   currentLane.removeCoin(currentCoin);
		   }
	   }
		   
	   }
	
	  
   }

   
   /**
    * This method processes key presses made before this tick. They key
    * presses are located in the instance variable keysPressed which we
    * provided in the code skeleton. Make use of that array to change the
    * velocity of the player's car and the lane that car is in. Refer to
    * constants defined in CarGameController to find out what
    * the individual array elements mean. For example,
    * this.keysPressed[CarGameController.KEY_UP] tells us whether
    * the up key was pressed.
    * <br /> 
    * The following should happen:
    * <ul>
    *   <li>If the up key is pressed, the player's car should move one
    *     lane to the left, even if this causes an accident. Nothing should
    *     happen if the player's car is in the leftmost lane already.</li>
    *   <li>If the right key is pressed, the player's car velocity should
    *     increase by 1.</li>
    *   <li>If the down key is pressed, the player's car should move one
    *     lane to the right, even if this causes an accident. Nothing should
    *     happen if the player's car is in the rightmost lane already.</li>
    *   <li>If the left key is pressed, the player's car velocity should
    *     decrease by 1.</li>
    * </ul>
    * Once this method returns, the keysPressed array should indicate
    * that no keys were pressed.
    */
   private void processKeysPressed() {
	   if (this.keysPressed[CarGameController.KEY_UP] == true && this.playerCarLane != 0)
	   {
		   this.highway.moveCar(this.car, playerCarLane, playerCarLane - 1);
		   this.playerCarLane -= 1;
		   
	   }
	   if (this.keysPressed[CarGameController.KEY_DOWN] == true && this.playerCarLane != this.highway.getNumLanes()-1)
	   {
		   this.highway.moveCar(this.car, playerCarLane, playerCarLane + 1);
		   this.playerCarLane += 1;
	   }
	   if (this.keysPressed[CarGameController.KEY_RIGHT] == true)
	   {
		   this.car.setVelocity(this.car.getVelocity() + 1);
	   }
	   if (this.keysPressed[CarGameController.KEY_LEFT] == true)
	   {
		   this.car.setVelocity(this.car.getVelocity() -1);
	   }

	
   }
   
   
   /**
    * Every car and every coin should now move (tick) for
    * the amount of time specified by the game's tick length.
    */
   private void moveEverything() {
	   for (int i = 0; i < this.highway.getNumLanes(); i++)
	   {
		   Lane currentLane = this.highway.lanes.get(i);
		   currentLane.rewind();

		   
		   while (currentLane.hasNextCoin() == true)
		   {
			   Coin currentCoin = currentLane.getNextCoin(); 
			   currentCoin.tick(tickLength);
		   }
		   
		   
		   while (currentLane.hasNextCar() == true)
		   {
			   Car currentCar = currentLane.getNextCar();
			   currentCar.tick(tickLength);
		   }
		   
		  currentLane.rewind();
	   }
	   
//tick?
   }
   
   
   /**
    * Remove all expired coins from the highway so that
    * the player cannot pick the up and get points/time for them. 
    */
   private void removeExpiredCoins() {
	  
	   for (int i = 0; i < this.highway.getNumLanes(); i++)
	   {
		   Lane currentLane = this.highway.lanes.get(i);
		   currentLane.rewind();

		   while (currentLane.hasNextCoin() == true)
		   {
			   Coin currentCoin = currentLane.getNextCoin(); 

			   if(currentCoin.isExpired())
			   {
				   currentLane.removeCoin(currentCoin);
			   }
		   }
	   }
   }
   
   
   /**
    * To speed up the game, all cars whose distance from the player's car is
    *   more than DROP_CARS_DISTANCE should be removed from the highway.
    */
   private void removeDistantCars() {
	   for (int i = 0; i < this.highway.getNumLanes(); i++)
	   {
		   Lane currentLane = this.highway.lanes.get(i);
		   currentLane.rewind();
		   
		   while (currentLane.hasNextCar() == true)
		   {
			   Car currentCar = currentLane.getNextCar();
			   if((currentCar.getPositionOfFront() - currentCar.LENGTH) - this.car.getPositionOfFront() >= DROP_CARS_DISTANCE)
			   {
				   currentLane.removeCar(currentCar);
			   }
			   
		   }
		    currentLane.rewind();//rewind the iterator
	   }

   }
   
   
   /**
    * Add one random car to the highway. Place it in a random lane at a random
    *   distance away from the player's car and give it a new driver
    *   who should have a random preferred velocity.
    *   Placing this car on the highway should not cause an
    *   accident, so make sure to check your random location and change it if
    *   necessary.
    */
   
   private void addRandomCar() {
	   
      Car randomCar = new Car(new Color(r.nextInt(256), r.nextInt(256), 0), (double) 2 + r.nextInt(4)); //fix to double later on
      Driver randomDriver = new Driver("dude", (double) (20 + r.nextInt(80)), false);
      randomDriver.enterCar(randomCar);
      randomCar.setDriver(randomDriver);
      
      if (this.tickNumber < 0)
      {
    	  randomCar.setPositionOfFront(this.car.getPositionOfFront() + 50 + r.nextInt(200)); 
      }
      else 
      {
    	  randomCar.setPositionOfFront(this.car.getPositionOfFront() + 250 + r.nextInt(200)); 
      }
      
      ArrayList<Integer> lanesThatCanFitCar = new ArrayList<>();
      
      
      
      
      while (lanesThatCanFitCar.isEmpty())
      {
    	  if (lanesThatCanFitCar.isEmpty() == false)
    	  {
    		  randomCar.setPositionOfFront(randomCar.getPositionOfFront()+5);
    	  }
    	  else
    	  {
    		  for (int i = 0; i < this.highway.getNumLanes(); i++) // check lanes in which this car can fit
    	      {
    	    	  if (this.highway.lanes.get(i).canFitCar(randomCar) == true)
    	    	  {
    	    		  lanesThatCanFitCar.add(i);
    	    	  }
    	      }
    	  }
      }
      
      int randomLaneToFit =  r.nextInt(lanesThatCanFitCar.size());
      this.highway.addCar(randomCar, randomLaneToFit);
      

      


      

      
	   // Here are some things you may want to consider...
      // 1) How to choose a random lane (DONE)
      // 2) What is the range for the random distance from the player's car. (DONE)
      // 3) How long should the car be? 
      // 4) What should its driver's preferred velocity be? (DONE)
      // 5) How to change where to put the car if its current
      //      position would immediately cause an accident?
      //
      // Also, to give the new car a color, use new Color(red, green, blue) 
      //      where red, green and blue should be between 0 and 255. 
      //      To distinguish computer cars from the player's car, we suggest
      //      you set the blue component of the car's color to zero. (DONE)

   }
   
   
   /**
    * Add one random coin to the highway. Place it in a random
    *   lane and pick some interesting properties for it. Again, we leave
    *   the details up to your creativity. 
    */
   private void addRandomCoin() { 
	   
	   Coin randomCoin = new Coin(8, 0.1, 1000, 12, 400, this.car.getPositionOfFront() + 100, 20 + r.nextInt(51), 5);
	   int randomLaneIndex = r.nextInt(this.highway.getNumLanes());
	   this.highway.addCoin(randomCoin, randomLaneIndex);

   }
   
   
   /**
    * If there are any cars that crashed into each other (that is, they overlap),
    * this method should remove them from the highway.
    */
   private void checkForCrashes() {
	   Car currentCar = null;
	   Car otherCar = null;
	   
	   for (int i = 0; i < this.highway.getNumLanes(); i++) // rewinding all lanes
	   {
		   
		   Lane currentLane = this.highway.lanes.get(i);
		   currentLane.rewind();
		   
		   if (currentLane.hasNextCar() == true) //if currentLane has any cars
		   {
			   currentCar = currentLane.getNextCar(); //0 set first car in the lane to the currentCar
		   }
		   else 
		   {
			   continue; //otherwise, check next lane because there definitely isn't gonna be a crash if there are no cars in a lane
		   }
		   
		   while (currentLane.hasNextCar() == true)
		   {
			   otherCar = currentLane.getNextCar(); //1 
			   if ((otherCar.getPositionOfFront() - otherCar.LENGTH) <= currentCar.getPositionOfFront() && currentCar.getPositionOfFront() <= otherCar.getPositionOfFront())
			   {
				   currentCar.setCrashed(true);
				   otherCar.setCrashed(true);
				   this.highway.lanes.get(i).removeCar(currentCar);
				   this.highway.lanes.get(i).removeCar(otherCar);
				   
			   }
			   
			   currentCar = otherCar;
		   }
		   
		   currentLane.rewind();
		   while(currentLane.hasNextCar() == true)
		   {
			   currentCar = currentLane.getNextCar();
			   if (currentCar.isCrashed())
			   {
				   currentLane.removeCar(currentCar);
			   }
		   }
		   
		 
	   }

   }   
}
