package subclasses;

import java.util.Formatter;

public class ElectricCar extends Car implements Drivable{

	private double battery;
	private final int TOP_SPEED = getTopSpeed();
	private final int MAX_POWER;		//units in kW
	private double powerUsage;			//power consumption in %, per kW and per second
	private double energy;			//energy in kJ
	private double partialEnergy;		//energy needed to accelerate to next threshold
	
	{
		battery = 100;
		MAX_POWER = 150;
		powerUsage = 0.015;
	}
	@Override
	public int getTopSpeed(){
		return 220;					//CHANGE EFFICIENCY GRAPH LINE 49 IF NECESSARY (minimum efficiency is set to 0.01)
	}
	public void recharge(int hours){
		if(getSpeed()>0){
			System.out.println("you can't recharge while driving!");
		}
		else {
			System.out.println("charging battery...");
			this.battery += 10*hours;
			this.battery = (battery>100?100:battery);
		}
	}
	@Override
	public void applyPower(double power){
		power = (power<0?0:power);
		power = (power>100?100:power);		//limits the applicable power
		int kW = (int)(MAX_POWER*(power/100));
		System.out.println("applying power of "+kW+"kW.");
		energy = kW;
		
	if(battery>0){				//if there is power
		if(getSpeed()!=TOP_SPEED){
		}
		for(int i=1;i<=TOP_SPEED;i++){						//looks up the current speed and calculates from there
			if(getSpeed()>=i-1&&getSpeed()<i){				//accelerates to next speed, subtracts battery & energy, uses graph for efficiency
				acceleration((2/Math.log10(0.5*i+50)-0.87)<0.01?0.01:(2/Math.log10(0.5*i+50)-0.87),i);
				if(energy==0||battery==0) return;			//stop calculating if energy or battery used up
			}
			if(i==TOP_SPEED){
				System.out.println("applying power but already reached top speed, cutting power...");
				}
			}
		}
		else System.out.println("no more juice!");
			
		}
	//helping applyPower method
	private void acceleration(double efficiency,int upperThreshold){
		partialEnergy = (upperThreshold-getSpeed())/efficiency;
		if(energy>=partialEnergy){		//if there is enough energy to accelerate to next threshold
			if(battery>=partialEnergy*powerUsage){		//if there is enough battery to reach next threshold
				energy -= partialEnergy;
				battery -= partialEnergy*powerUsage;
				setSpeed(upperThreshold);
			}
			else{								//if there is not enough battery to reach next threshold
				setSpeed(getSpeed()+((battery/powerUsage)*efficiency));
				battery = 0;
				energy = 0;
			}
		}
		else if(battery>=energy*powerUsage){			//if there is not enough energy but enough battery to accelerate to next threshold
			battery -= energy*powerUsage;
			setSpeed(getSpeed()+(int)(energy*efficiency));
			energy = 0;
		}
		else{											//if there is not enough battery and not enough energy to accelerate to next threshold
			if(battery*powerUsage<energy){
				setSpeed(getSpeed()+(int)((battery/powerUsage)*efficiency));		//if battery is less than energy
				battery = 0;
			}
			else{
				setSpeed(getSpeed()+(int)(energy*efficiency));				//if energy is less than battery
				battery -= energy*powerUsage;
			}
			energy = 0;
		}
	}
	@Override
	public void applyBrakes(int brakingPower){
		brakingPower = (brakingPower<0?0:brakingPower);
		brakingPower = (brakingPower>100?100:brakingPower);
		double dec = brakingPower*0.3;
		dec = (getSpeed()-dec<0?getSpeed():dec);
		
		if(getSpeed()==0){
			System.out.println("trying to slow down but standing still already...");
		}
		else{								// if you slow down to speed above or equal to 0
			System.out.println("applying brakes at "+brakingPower+"%.");
			powerGeneration(dec);
			setSpeed(getSpeed()-dec);
			}
		if(this.battery>100){
			this.battery = 100;
		}
	}
	//helping applyBrakes method
	private void powerGeneration(double speed){
		for(int i=TOP_SPEED;i>0;i--){
			if(i<=getSpeed()&&i>(getSpeed()-speed)){
				battery += (2/Math.log10(-0.4*i+180)-0.85);
			}
		}
	}
	@SuppressWarnings("resource")
	public String batteryStatus(){
		Formatter f = new Formatter();
		f.format("%.2f", this.battery);
		return f+"%";
	}
	public void setCharge(int charge){
		this.battery = charge;
	}
	public ElectricCar(){
		super();
	}
	public int getMaxPower(){
		return MAX_POWER;
	}
	public void slowing(){
		if(getSpeed()<=0) setSpeed(0);
		else setSpeed(getSpeed()*0.996-0.4);
	}
}
