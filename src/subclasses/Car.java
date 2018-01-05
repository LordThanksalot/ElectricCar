package subclasses;

public abstract class Car {

	private String colour;
	private double speed;
	private final int TOP_SPEED = getTopSpeed();
	{
		speed = 0;
	}

	
	public void accelerate(int inc){
		if(this.speed == TOP_SPEED){
			System.out.println("trying to accelerate by "+inc+"km/h but at top speed already");
			return;
		}
		this.speed += inc;
		if(this.speed > TOP_SPEED){
			this.speed = TOP_SPEED;
			System.out.println("VRRROOOOOO...+trying to accelerate by "+inc+"km/h");
		}
		else System.out.println("VRRROOOOOO...+accelerating by "+inc+"km/h");
		}
	
	public void slow(int dec){
		this.speed -= dec;
		if(this.speed < 0){
			this.speed = 0;
		}
		else {
			System.out.println("iiiiii....");
		}
	}
	public double getSpeed(){
		return this.speed;
	}
	public void setSpeed(double speed){
		speed = (speed>TOP_SPEED?TOP_SPEED:speed);
		this.speed = speed;
	}
	public void setColour(String colour){
		this.colour = colour;
	}
	public String getColour(){
		return this.colour;
	}
	public Car(){
		
	}
	public int getTopSpeed(){
		return 200;
	}
}
