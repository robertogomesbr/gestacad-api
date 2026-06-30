package br.com.ifpe.gestacad.modelo.mensagens;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import br.com.ifpe.gestacad.modelo.acesso.Usuario;
import br.com.ifpe.gestacad.modelo.professor.Professor;
import br.com.ifpe.gestacad.modelo.reposicao.Reposicao;
import jakarta.annotation.PostConstruct;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

@Component
public class EmailService {

    @Value("${spring.mail.username}")
    private String username;

    @Value("${spring.mail.password}")
    private String password;

    @Value("${spring.mail.host}")
    private String host;

    @Value("${spring.mail.port}")
    private int port;

    @Value("${spring.mail.properties.mail.smtp.auth}")
    private String smtpAuth;

    @Value("${spring.mail.properties.mail.smtp.starttls.enable}")
    private String starttls;

    private JavaMailSender emailSender;
    private TemplateEngine templateEngine;

    @PostConstruct
    public void init() {
        this.emailSender = createJavaMailSender();
        this.templateEngine = createTemplateEngine();
    }

    @Async("emailExecutor")
    public void enviarEmailSolicitacaoCadastroProfessor(Professor professor) {
        String assuntoEmail = "Solicitacao de Cadastro Enviada com Sucesso";
        Context params = new Context();
        params.setVariable("professor", professor);
        this.processAndSend("solicitacao_professor", professor.getUsuario().getUsername(), assuntoEmail, params);
    }

    @Async("emailExecutor")
    public void enviarEmailLoginProfessor(Professor professor){
        String assuntoEmail = "Acesso na Plataforma!";
        Context params = new Context();
        params.setVariable("professor", professor);
        this.processAndSend("bem_vindo_professor_login", professor.getUsuario().getUsername(), assuntoEmail, params);
    }

    @Async("emailExecutor")
    public void enviarEmailLoginAdmin(Usuario usuario){
        String assuntoEmail = "Acesso na Plataforma Admin!";
        Context params = new Context();
        params.setVariable("usuario", usuario);
        this.processAndSend("bem_vindo_admin_login", usuario.getUsername(), assuntoEmail, params);
    }

    @Async("emailExecutor")
    public void enviarEmailReposicao(Reposicao reposicao) {
        String assuntoEmail = "Reposição Concluída!";
        Context params = new Context();
        params.setVariable("reposicao", reposicao);
        this.processAndSend("reposicao_aula", reposicao.getProfessor().getEmail(), assuntoEmail, params);
    }

    private void processAndSend(String template, String to, String subject, Context params) {
        try {
            String content = templateEngine.process(template, params);
            this.sendMail(to, subject, content);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendMail(String to, String subject, String content) throws UnsupportedEncodingException {
        MimeMessage message = emailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, StandardCharsets.UTF_8.name());

            helper.setFrom(new InternetAddress(username, "GestAcad-no-reply"));
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true); // true = processa HTML corretamente
            helper.setEncodeFilenames(true);

            emailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    private TemplateEngine createTemplateEngine() {
        TemplateEngine engine = new TemplateEngine();
        ClassLoaderTemplateResolver resolver = new ClassLoaderTemplateResolver();
        
        resolver.setPrefix("templates/");
        resolver.setSuffix(".html");
        resolver.setTemplateMode(TemplateMode.HTML);
        resolver.setCharacterEncoding(StandardCharsets.UTF_8.name());
        resolver.setOrder(0);
        
        resolver.setCacheable(true);
        resolver.setCacheTTLMs(null); 

        engine.setTemplateResolver(resolver);
        return engine;
    }

    private JavaMailSender createJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(host);
        mailSender.setPort(port);
        mailSender.setUsername(username);
        mailSender.setPassword(password);

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", smtpAuth);
        props.put("mail.smtp.starttls.enable", starttls);
        props.put("mail.debug", "false");

        return mailSender;
    }
}
