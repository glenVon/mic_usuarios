@ExtendWith(MockitoExtension.class)
public class ServicioUsuarioTest {
    @InjectMocks
    private ServicioUsuario servicioUsuario;

    @Mock
    private UserRepository userRepository;

    @Test
    void getAllUsers_deberiaRetornarListaDeUsuarios() {
        
        List<User> usuarios = Arrays.asList(new User("1", "Egon"));//, new User("2", "Maria"));
        when(userRepository.findAll()).thenReturn(usuarios);
        
        ResponseEntity<List<User>> response = servicioUsuario.getAllUsers();
        
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(usuarios, response.getBody());
    }
}

