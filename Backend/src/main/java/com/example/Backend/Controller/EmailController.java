package com.example.Backend.Controller;

import com.example.Backend.Model.DataTransferObject.EmailDTO;
import com.example.Backend.Service.Implementation.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/email")
@CrossOrigin(origins = "*")
public class EmailController {

    private EmailService emailService;

    @Autowired
    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/enviar-correo")
    public String enviarCorreo(@RequestBody EmailDTO emailDTO) {
        String destinatario = emailDTO.getDestinatario();
        String asunto = emailDTO.getAsunto();
        String contenido = emailDTO.getContenido();

        emailService.enviarCorreo(destinatario, asunto, contenido,true);

        return "Correo enviado correctamente";
    }
}
