
import java.util.Date;

public class Conf {

	double year;
	Date date;
	Date customdate;
	
	
	Conf(Date date, double year){
		this.date=date;
		this.year=year;
	}
	
	public Date getCustomdate() {
		return this.customdate;
	}
	
	public void setCustomdate(Date d) {
		this.customdate=d;
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
}
