package nl.hu.iac.soap.imp;

import com.sun.xml.ws.developer.SchemaValidation;

import javax.jws.WebService;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nl.hu.iac.soap.wsinterface.Fault;
import nl.hu.iac.soap.wsinterface.ObjectFactory;
import nl.hu.iac.soap.wsinterface.CarFault;
import nl.hu.iac.soap.wsinterface.AddcarRequest;
import nl.hu.iac.soap.wsinterface.AddcarResponse;
import nl.hu.iac.soap.wsinterface.DeletecarRequest;
import nl.hu.iac.soap.wsinterface.DeletecarResponse;
import nl.hu.iac.soap.wsinterface.ReadcarRequest;
import nl.hu.iac.soap.wsinterface.ReadcarResponse;
import nl.hu.iac.soap.wsinterface.UpdatecarRequest;
import nl.hu.iac.soap.wsinterface.UpdatecarResponse;
import nl.hu.iac.soap.wsinterface.CarServiceInterface;


/**
 * Created by bobby on 6/20/2017.
 */
@WebService(endpointInterface = "nl.hu.iac.soap.wsinterface.CarServiceInterface")
@SchemaValidation(handler = SchemaValidationErrorHandler.class)
public class CarServiceImpl implements CarServiceInterface {

    List<Map> cars = new ArrayList();
    Map car = new HashMap();

    @Override
    public AddcarResponse addCar(AddcarRequest request) throws Fault {
        System.out.println("Request object "+request.getMerk()+ " " +request.getPrijs()+ " " +request.getBeschrijving());
        ObjectFactory factory = new ObjectFactory();
        AddcarResponse response = factory.createAddcarResponse();
        try {
            String merk = request.getMerk();
            Double prijs = request.getPrijs();
            String beschrijving = request.getBeschrijving();
            car.put("Merk", merk);
            car.put("prijs", prijs);
            car.put("beschrijving", beschrijving);
            cars.add(car);
            String result = cars.toString();
            response.setResult(result);
        } catch (RuntimeException e) {
            CarFault x = factory.createCarFault();
            x.setErrorCode((short) 1);
            x.setMessage("Eletric car cannot be registred.");
            Fault fault = new Fault(
                    "Something went wrong while trying to register the car", x);
            throw fault;
        }
        return response;
    }

    public DeletecarResponse deleteCar(DeletecarRequest request) throws Fault {
        System.out.println("Request object "+request.getAutoID());
        ObjectFactory factory = new ObjectFactory();
        DeletecarResponse response = factory.createDeletecarResponse();
        try {
            BigInteger index = request.getAutoID();
            cars.remove(index);
            String result = cars.toString();
            response.setResult(result);
        } catch (RuntimeException e) {
            CarFault x = factory.createCarFault();
            x.setErrorCode((short) 1);
            x.setMessage("Car cannot be deleted");
            Fault fault = new Fault(
                    "Something went wrong while trying to delete the car", x);
            throw fault;
        }
        return response;
    }

    public ReadcarResponse readCar(ReadcarRequest request) throws Fault {
        System.out.println("Request object "+request.getCommand());
        ObjectFactory factory = new ObjectFactory();
        ReadcarResponse response = factory.createReadcarResponse();
        try {
            String command = request.getCommand();
            String result = null;
            if (command.equals("Read")){
                result = cars.toString();
            }
            else
            {result = "Something went wrong.";}
            response.setResult(result);
        } catch (RuntimeException e) {
            CarFault x = factory.createCarFault();
            x.setErrorCode((short) 1);
            x.setMessage("Cannot get the car data.");
            Fault fault = new Fault(
                    "Something went wrong while trying to get car data", x);
            throw fault;
        }
        return response;
    }

    public UpdatecarResponse updateCar(UpdatecarRequest request) throws Fault {
        ObjectFactory factory = new ObjectFactory();
        UpdatecarResponse response = factory.createUpdatecarResponse();
        try {
            BigInteger autoid = request.getAutoId();
            String merk = request.getMerk();
            Double prijs = request.getPrijs();
            String beschrijving = request.getBeschrijving();
            car.put("Merk",merk);
            car.put("Prijs", prijs);
            car.put("Beschrijving", beschrijving);
            cars.set(autoid.intValue(), car);
            String result = cars.toString();
            response.setResult(result);
        } catch (RuntimeException e) {
            CarFault x = factory.createCarFault();
            x.setErrorCode((short) 1);
            x.setMessage("Cannot update electric car info.");
            Fault fault = new Fault(
                    "Something went wrong while trying to update the car", x);
            throw fault;
        }
        return response;
    }

}
