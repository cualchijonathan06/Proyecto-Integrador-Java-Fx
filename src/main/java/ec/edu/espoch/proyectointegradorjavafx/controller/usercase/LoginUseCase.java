
package ec.edu.espoch.proyectointegradorjavafx.controller.usercase;
import ec.edu.espoch.proyectointegradorjavafx.model.implementacion.LoginDao;
import ec.edu.espoch.proyectointegradorjavafx.model.interfaces.ILoginDao;

public class LoginUseCase
{
   private ILoginDao dao = new LoginDao();

public boolean ingresar(String usuario, String clave)
{
    return dao.validar(usuario, clave);
}

}