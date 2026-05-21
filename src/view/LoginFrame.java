package view;

import service.ConsultaService;
import service.EmpresaService;
import service.UsuarioService;

import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {

    private final UsuarioService usuarioService;
    private final EmpresaService empresaService;
    private final ConsultaService consultaService;

    private JTextField txtEmail;
    private JPasswordField txtSenha;

    private JButton btnLogin;
    private JButton btnCadastrar;

    public LoginFrame() {
        usuarioService = new UsuarioService();
        this.empresaService = new EmpresaService();
        this.consultaService = new ConsultaService();
        configurarJanela();
        criarComponentes();
        setVisible(true);
    }

    private void configurarJanela() {
        setTitle("Sistema Empresa");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
    }

    private void criarComponentes() {
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setBorder(
                BorderFactory.createEmptyBorder(20,20,20,20)
        );
        panelPrincipal.setLayout(new BorderLayout());
        
        JLabel lblTitulo = new JLabel("LOGIN DO SISTEMA");
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        panelPrincipal.add(lblTitulo, BorderLayout.NORTH);
        
        JPanel panelFormulario = new JPanel();
        panelFormulario.setLayout(new GridLayout(2,2,10,10));
        panelFormulario.add(new JLabel("Email:"));
        
        txtEmail = new JTextField();
        panelFormulario.add(txtEmail);
        panelFormulario.add(new JLabel("Senha:"));

        txtSenha = new JPasswordField();
        panelFormulario.add(txtSenha);
        panelPrincipal.add(panelFormulario, BorderLayout.CENTER);

        JPanel panelBotoes = new JPanel();
        btnLogin = new JButton("Login");
        btnCadastrar = new JButton("Cadastrar");
        panelBotoes.add(btnLogin);
        panelBotoes.add(btnCadastrar);
        panelPrincipal.add(panelBotoes, BorderLayout.SOUTH);

        adicionarEventos();
        add(panelPrincipal);
    }

    private void adicionarEventos() {
        btnLogin.addActionListener(e -> fazerLogin());
        btnCadastrar.addActionListener(e -> abrirCadastro());
    }

    private void fazerLogin() {
        String email = txtEmail.getText().trim();
        String senha = new String(txtSenha.getPassword());
        if(email.isBlank() || senha.isBlank()) {
            JOptionPane.showMessageDialog(this, "Preencha email e senha");
            return;
        }

        boolean sucesso = usuarioService.login(email, senha);
        if(!sucesso) {
            JOptionPane.showMessageDialog(this,"Email ou senha inválidos");
            return;
        }

        JOptionPane.showMessageDialog(this,"Bem-vindo " + usuarioService.getUsuarioLogado().getNome());
        dispose();
        new MenuFrame(usuarioService, empresaService, consultaService);
    }

    private void abrirCadastro() {
        new CadastroUsuarioFrame(usuarioService);
    }

}  