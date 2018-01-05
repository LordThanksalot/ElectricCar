package subclasses;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Driver implements Runnable{
	
	static BufferedReader in;
    static int quit=0;
    
    @Override
    public void run(){
        String msg = null;
        while(true){
            try{
            msg=in.readLine();
            }catch(Exception e){}
             
            if(msg.endsWith("")) {quit=1;break;}
        }
    }

	@SuppressWarnings("deprecation")
	public static void main(String[] args) throws Exception{
		Scanner sc = new Scanner(System.in);
		in=new BufferedReader(new InputStreamReader(System.in));
		Drivable tesla = new ElectricCar();
		
		if(tesla instanceof Car){
		((Car)tesla).setColour("black");
		System.out.println("Our Tesla has a "+((Car)tesla).getColour()+" colour.");
		}
		if(tesla instanceof ElectricCar){
		System.out.println("The Tesla has a maximum power of "+((ElectricCar)tesla).getMaxPower()+"kW.\n");
		System.out.println("We start the Tesla.");
		System.out.println("Speed: "+(int)tesla.getSpeed()+"km/h");
		System.out.println("Battery: "+((ElectricCar)tesla).batteryStatus());
		}
		
		Thread thread = new Thread(new Runnable(){
			@Override
			public void run(){
				while(true){
					try {
						TimeUnit.SECONDS.sleep(1);
					} catch (Exception e) {
						e.printStackTrace();
					}
					((ElectricCar)tesla).slowing();
				}
			}
		});
		thread.start();
		
		System.out.println("Do you want to accelerate or slow down? Or nothing at all??"
				+"\nPress 1 for accelerating, 2 for slowing down, and 0 for nothing. Other buttons will stop the simulation.");
		int choice1 = sc.nextInt();
		
		while(choice1==0||choice1==1||choice1==2){
		if(choice1 == 1){
			System.out.println("How much power? It's 0 to 100%.");
			int power = sc.nextInt();
			Thread t1=new Thread(new Driver());
	        t1.start();
	        System.out.println("press ENTER to stop accelerating");
	        while(true){
	        	TimeUnit.SECONDS.sleep(1);
	            if(quit==1){
	            	quit = 0;
	            	break;
	            }
	            tesla.applyPower(power);
	        }
			System.out.println("Speed: "+(int)tesla.getSpeed()+"km/h");
			System.out.println("Battery: "+((ElectricCar)tesla).batteryStatus());
		}
		if(choice1 == 2){
			System.out.println("How hard do you want to press the brake pedal? It's 0 to 100%.");
			int pedal = sc.nextInt();
			Thread t1=new Thread(new Driver());
	        t1.start();
	        System.out.println("press ENTER to stop breaking");
	        while(true){
	        	TimeUnit.SECONDS.sleep(1);
	            if(quit==1){
	            	quit = 0;
	            	break;
	            }
	            tesla.applyBrakes(pedal);
	        }
			System.out.println("Speed: "+(int)tesla.getSpeed()+"km/h");
			System.out.println("Battery: "+((ElectricCar)tesla).batteryStatus());
		}
		if(choice1 == 0){
			System.out.println("Speed: "+(int)tesla.getSpeed()+"km/h");
			System.out.println("Battery: "+((ElectricCar)tesla).batteryStatus());
		}
		System.out.println("Do you want to accelerate or slow down? Or nothing at all??"
				+"\nPress 1 for accelerating, 2 for slowing down, and 0 for nothing. Other buttons will stop the simulation.");
		choice1 = sc.nextInt();
		}
		thread.stop();
		sc.close();
	}
}
