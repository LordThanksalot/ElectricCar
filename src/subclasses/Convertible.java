package subclasses;

public class Convertible extends Car{

	private int sunRoof;
	{
		sunRoof = 0;
	}
	
	public void openRoof(){
		this.sunRoof+=25;
	}
	public void closeRoof(){
		this.sunRoof-=25;
	}
	public void setRoof(int sunRoof){
		this.sunRoof = sunRoof;
	}
	public Convertible(){
		super();
	}
	public int getSunRoof(){
		return sunRoof;
	}
}
