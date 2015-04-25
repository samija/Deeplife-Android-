package Database;

/**
 * Created by Ab on 4/13/2015.
 */
public class UserMeasurment {
    public int id;
    public String method;
    public String numberof;
   // public String time;
    public int idcheat;

    public int getIdcheat(){
        return idcheat;
    }
    public void setIdcheat(int idcheat){
        this.idcheat=idcheat;
    }


    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id=id;
    }

    public String getMethod(){
        return method;}

    public  void setMethod(String method){
        this.method=method; }

    public String getNumberof(){ return numberof;}
    public  void setNumberof(String numberof){this.numberof=numberof; }

   // public String getTime(){
    //    return time;
   // }
    //public void setTime(String time){
   //     this.time=time;
 //   }



    public String toString(){
        //NumberFormat nf = NumberFormat.getCurrencyInstance();

        return numberof+" " + method;

    }

}
