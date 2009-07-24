package core;

public class GTerrorJava extends Exception
{
  private static final long serialVersionUID = -4436530374845710694L;

  public GTerrorJava() {
    
  }
 
  public GTerrorJava(String err_mess) {
    super(err_mess);
  }
  
  public GTerrorJava(String err_mess, Throwable cause) {
    super(err_mess, cause);
  }
  
}
