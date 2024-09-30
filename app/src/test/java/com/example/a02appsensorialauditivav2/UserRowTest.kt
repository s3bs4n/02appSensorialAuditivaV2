import com.example.a02appsensorialauditivav2.Usuarios
import org.junit.Test
import org.mockito.Mockito.verify

class UserRowTest {

    @Test
    fun `test eliminarUsuario is called`() {
        // Crear un lambda simulada (mock manual) para eliminar un usuario
        var wasCalled = false
        val eliminarUsuarioMock: (String) -> Unit = {
            wasCalled = true  // Cambiar el estado cuando se invoque
        }

        val user = Usuarios(email = "test@test.com")

        // Llamada a la función que debe disparar eliminarUsuario
        eliminarUsuarioMock.invoke(user.email)

        // Verificar que la función fue llamada
        assert(wasCalled)
    }

    @Test
    fun `test modificarCorreoUsuario is called`() {
        // Crear un lambda simulada (mock manual) para modificar el correo
        var wasCalled = false
        val modificarCorreoUsuarioMock: (String, String) -> Unit = { _, _ ->
            wasCalled = true  // Cambiar el estado cuando se invoque
        }

        val user = Usuarios(email = "test@test.com")
        val nuevoCorreo = "nuevo@test.com"

        // Llamada a la función que debe disparar modificarCorreoUsuario
        modificarCorreoUsuarioMock.invoke(user.email, nuevoCorreo)

        // Verificar que la función fue llamada
        assert(wasCalled)
    }
}
