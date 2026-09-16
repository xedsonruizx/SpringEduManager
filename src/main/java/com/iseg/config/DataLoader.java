package com.iseg.config;

import java.time.LocalDate;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.iseg.model.Estudiante;
import com.iseg.repository.EstudianteRepository;

@Component
public class DataLoader implements CommandLineRunner {

    private final EstudianteRepository estudianteRepository;

    public DataLoader(EstudianteRepository estudianteRepository) {
        this.estudianteRepository = estudianteRepository;
    }

    @Override
    public void run(String... args) {
        if (estudianteRepository.count() > 0) {
            return;
        }

        estudianteRepository.save(crear("12345678-5", "Juan", "Perez", "Gonzalez",
                "juan.perez@mail.com", "912345678", LocalDate.of(2000, 5, 14),
                "Av. Siempre Viva 123", "Santiago", 95.5, 6.2, true));

        estudianteRepository.save(crear("23456789-6", "Maria", "Soto", "Reyes",
                "maria.soto@mail.com", "923456789", LocalDate.of(1999, 11, 2),
                "Calle Los Aromos 45", "Valparaiso", 88.0, 5.8, true));

        estudianteRepository.save(crear("34567890-5", "Pedro", "Munoz", "Vidal",
                "pedro.munoz@mail.com", "934567890", LocalDate.of(2001, 3, 22),
                "Pasaje Las Rosas 78", "Concepcion", 92.3, 6.5, true));

        estudianteRepository.save(crear("45678901-3", "Camila", "Rojas", "Fuentes",
                "camila.rojas@mail.com", "945678901", LocalDate.of(1998, 7, 9),
                "Av. Central 210", "La Serena", 78.4, 4.9, true));

        estudianteRepository.save(crear("56789012-0", "Diego", "Fernandez", "Silva",
                "diego.fernandez@mail.com", "956789012", LocalDate.of(2000, 12, 30),
                "Los Alerces 33", "Temuco", 85.0, 5.5, true));

        estudianteRepository.save(crear("67890123-7", "Valentina", "Torres", "Contreras",
                "valentina.torres@mail.com", "967890123", LocalDate.of(1997, 9, 17),
                "Av. Norte 555", "Antofagasta", 99.0, 6.9, true));

        estudianteRepository.save(crear("78901234-2", "Matias", "Herrera", "Gomez",
                "matias.herrera@mail.com", "978901234", LocalDate.of(2002, 1, 5),
                "Calle Sur 12", "Rancagua", 60.5, 4.2, false));

        estudianteRepository.save(crear("89012345-7", "Fernanda", "Castro", "Molina",
                "fernanda.castro@mail.com", "989012345", LocalDate.of(1999, 4, 25),
                "Pje. El Roble 89", "Talca", 91.2, 6.0, true));

        estudianteRepository.save(crear("90123456-6", "Sebastian", "Lopez", "Araya",
                "sebastian.lopez@mail.com", "990123456", LocalDate.of(2000, 8, 11),
                "Av. Libertad 300", "Puerto Montt", 70.0, 5.0, true));

        estudianteRepository.save(crear("01234567-4", "Antonia", "Vargas", "Paredes",
                "antonia.vargas@mail.com", "901234567", LocalDate.of(2001, 6, 18),
                "Los Pinos 47", "Iquique", 96.8, 6.7, true));
    }

    private Estudiante crear(String rut, String nombre, String apellidoPaterno, String apellidoMaterno,
                             String email, String telefono, LocalDate fechaNacimiento,
                             String direccion, String ciudad, Double asistencia, Double promedio,
                             boolean activo) {
        Estudiante e = new Estudiante();
        e.setRut(rut);
        e.setNombre(nombre);
        e.setApellidoPaterno(apellidoPaterno);
        e.setApellidoMaterno(apellidoMaterno);
        e.setEmail(email);
        e.setTelefono(telefono);
        e.setFechaNacimiento(fechaNacimiento);
        e.setDireccion(direccion);
        e.setCiudad(ciudad);
        e.setAsistencia(asistencia);
        e.setPromedio(promedio);
        e.setActivo(activo);
        return e;
    }
}
