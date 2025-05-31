@ExtendWith(MockitoExtension.class)
public class ServicioUsuarioTest {
    @InjectMocks
    private ServicioUsuario servicioUsuario;

    @Mock
    //private UserRepository userRepository;

    @Test
    void getAllUsers_deberiaRetornarListaDeUsuarios() {       
        List<User> usuarios =User("1", "Egon");//, new User("2", "Maria"));
        when(userRepository.findAll()).thenReturn(usuarios);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(usuarios, response.getBody());
    }
}

