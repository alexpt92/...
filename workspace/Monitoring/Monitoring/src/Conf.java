
import java.util.Date;

public class Conf {

	double year;
	Date date;
	Date customdate=new Date(1,1,2000);
	
	
	Conf(Date date, double year){
		this.date=date;
		this.year=year;
	}
	
	public Date getCustomdate() {
		return this.customdate;
	}
	
	
	public double getYear(){
		return this.year;
	
	}
	public Date getDate(){
		return this.date;
	}
	
	public void setYear(double y){
		this.year=y;
	}
	
	public void setDate(Date d){
		this.date=d;
	}
	public void setCustomdate(int d, int m,int y) {
		this.customdate=new Date(d,m,y+1900);
	}
}

