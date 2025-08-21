import java.io.Serializable;

public class FindCars extends CommandImplemented implements Serializable {
    public FindCars(){}

    public void execute(){
        clearResult();
        String type = (String) getParameter("rodzaj");

        if(type == null){
            return;
        }
        addResult(type);
    }
}
