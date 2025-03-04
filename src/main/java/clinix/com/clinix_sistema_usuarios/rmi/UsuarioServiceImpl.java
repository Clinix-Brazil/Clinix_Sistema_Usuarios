package clinix.com.clinix_sistema_usuarios.rmi;

import clinix.com.clinix_sistema_usuarios.repository.MedicoRepository;
import clinix.com.clinix_sistema_usuarios.repository.PacienteRepository;
import com.clinix.api.dto.MedicoRmiDTO;
import com.clinix.api.dto.PacienteRmiDTO;
import com.clinix.api.interfaces.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

@Service
public class UsuarioServiceImpl extends UnicastRemoteObject implements UsuarioService {

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    public UsuarioServiceImpl() throws RemoteException {
        super();
    }

    @Override
    public MedicoRmiDTO getMedicoPorId(Long id) throws RemoteException {
        return medicoRepository.findById(id)
                .map(medico -> new MedicoRmiDTO(medico.getId(), medico.getNome()))
                .orElse(null);
    }

    @Override
    public PacienteRmiDTO getPacientePorId(Long id) throws RemoteException {
        return pacienteRepository.findById(id)
                .map(paciente -> new PacienteRmiDTO(paciente.getId(), paciente.getNome()))
                .orElse(null);
    }
}