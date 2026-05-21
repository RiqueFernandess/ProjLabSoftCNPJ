package view;

import service.UsuarioService;

import javax.swing.*;
import java.awt.*;

public class CadastroUsuarioFrame extends JFrame {

    private final UsuarioService usuarioService;

    private JTextField txtNome;
    private JTextField txtCpf;
    private JTextField txtEmail;

    private JPasswordField txtSenha;
    private JPasswordField txtConfirmarSenha;

    private JButton btnCadastrar;
    private JButton btnCancelar;

    public CadastroUsuarioFrame(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
        configurarJanela();
        criarComponentes();
        setVisible(true);
    }

    private void configurarJanela() {
        setTitle("Cadastro de Usuário");
        setSize(400, 350);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private void criarComponentes() {
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setBorder(
            BorderFactory.createEmptyBorder(20,20,20,20)
        );
        panelPrincipal.setLayout(new BorderLayout(10,10));

        JLabel lblTitulo = new JLabel("NOVO USUÁRIO");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        panelPrincipal.add(lblTitulo, BorderLayout.NORTH);

        JPanel panelFormulario = new JPanel();
        panelFormulario.setLayout(new GridLayout(5,2,10,10));
        panelFormulario.add(new JLabel("Nome:"));
        txtNome = new JTextField();
        panelFormulario.add(txtNome);
        panelFormulario.add(new JLabel("CPF:"));
        txtCpf = new JTextField();
        panelFormulario.add(txtCpf);
        panelFormulario.add(new JLabel("Email:"));
        txtEmail = new JTextField();
        panelFormulario.add(txtEmail);
        panelFormulario.add(new JLabel("Senha:"));
        txtSenha = new JPasswordField();
        panelFormulario.add(txtSenha);
        panelFormulario.add(new JLabel("Confirmar senha:"));
        txtConfirmarSenha = new JPasswordField();
        panelFormulario.add(txtConfirmarSenha);
        panelPrincipal.add(panelFormulario, BorderLayout.CENTER);

        JPanel panelBotoes = new JPanel();
        btnCadastrar = new JButton("Cadastrar");
        btnCancelar = new JButton("Cancelar");
        panelBotoes.add(btnCadastrar);
        panelBotoes.add(btnCancelar);
        panelPrincipal.add(panelBotoes, BorderLayout.SOUTH);

        adicionarEventos();
        add(panelPrincipal);
    }

    private void adicionarEventos() {
        btnCadastrar.addActionListener(e -> cadastrar());
        btnCancelar.addActionListener(e -> dispose());
    }

    private void cadastrar() {
        String nome = txtNome.getText().trim();
        String cpf = txtCpf.getText().trim();
        String email = txtEmail.getText().trim();
        String senha = new String(txtSenha.getPassword());
        String confirmarSenha = new String(txtConfirmarSenha.getPassword());
        if(nome.isBlank()
                || cpf.isBlank()
                || email.isBlank()
                || senha.isBlank()
        ) {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos");
            return;
        }

        if(!senha.equals(confirmarSenha)) {
            JOptionPane.showMessageDialog(this, "As senhas não coincidem");
            return;
        }

        try {
            usuarioService.cadastrar(nome, cpf, email, senha);
            JOptionPane.showMessageDialog(this, "Usuário cadastrado com sucesso");
            dispose();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }

    }

}