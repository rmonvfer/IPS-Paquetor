package es.uniovi.paquetor.services;

import es.uniovi.paquetor.entities.Paquete;
import es.uniovi.paquetor.entities.RutaExterna;
import es.uniovi.paquetor.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpSession;

public class PaquetesService {

    @Autowired
    private HttpSession httpSession;

    @Autowired
    private UsuarioRepository usersRepository;

    @Autowired
    private PaqueteRepository paquetesRepository;

    @Autowired
    private AlmacenRepository almacenesRepository;

    @Autowired
    private ParadaRepository paradasRepository;

    @Autowired
    private RutaExternaRepository rutasExternasRepository;

    @Autowired
    private RutaInternaRepository rutasInternasRepository;

    @Autowired
    private TransporteRepository transporteRepository;

    @Autowired
    private UbicacionRepository ubicacionRepository;

    @PostConstruct
    public void init() { }
}
