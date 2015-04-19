package Database;

/**
 * Created by Ab on 4/17/2015.
 */
public class Schedule {
User user;
    public int sid;
    public String lable;
    public String year;
    public String month;
    public String day;
    public String hour;
    public String minute;
    public int cheatsid;
public int id;
    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id=id;
    }

    public int getCheatsid(){
        return cheatsid;
    }
    public void setCheatsid(int cheatsid){
        this.cheatsid=cheatsid;
    }

    public String getLable(){
        return lable;}
    public  void setLable(String lable){
        this.lable=lable; }



    public int getSid(){
        return sid;
    }
    public void setSid(int sid){
        this.sid=sid;
    }


    public String getYear(){
        return year;
    }
    public void setYear(String year){
        this.year=year;
    }

    public String getMonth(){
        return month;
    }
    public void setMonth(String month){
        this.month=month;
    }

    public String getDay(){
        return day;
    }
    public void setDay(String day){
        this.day=day;
    }

    public String getHour(){
        return hour;
    }
    public void setHour(String hour){
        this.hour=hour;
    }

    public String getMinute(){
        return minute;
    }
    public void setMinute(String minute){
        this.minute=minute;
    }


    public String toString(){
        //NumberFormat nf = NumberFormat.getCurrencyInstance();

        return lable+"\n" + day+"/" + month +"/" + year+"\n"+hour+":"+minute;

    }


}

