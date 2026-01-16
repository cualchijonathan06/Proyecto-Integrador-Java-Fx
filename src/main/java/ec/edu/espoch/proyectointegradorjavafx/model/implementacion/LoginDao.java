package ec.edu.espoch.proyectointegradorjavafx.model.implementacion;

import ec.edu.espoch.proyectointegradorjavafx.model.interfaces.ILoginDao;

public class LoginDao implements ILoginDao
{
    @Override
    public boolean validar(String usuario, String clave)
    {
        if(usuario == null || clave == null)
        {
            return false;
        }

        usuario = usuario.trim();
        clave = clave.trim();

        if(usuario.equals("admin") && clave.equals("1234"))
        {
            return true;
        }

        return false;
    }
}
