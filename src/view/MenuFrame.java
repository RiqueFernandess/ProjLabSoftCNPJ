package view;

import service.EmpresaService;
import service.ConsultaService;
import service.UsuarioService;

import javax.swing.*;
import java.awt.*;

public class MenuFrame extends JFrame {
    private final UsuarioService usuarioService;
    private final EmpresaService empresaService;
    private final ConsultaService consultaService;

    public MenuFrame(
        UsuarioService usuarioService,
        EmpresaService empresaService,
        ConsultaService consultaService
    ) {
        this.usuarioService = usuarioService;
        this.empresaService = empresaService;
        this.consultaService = consultaService;
        configurarJanela();
        criarComponentes();
        setVisible(true);
    }

    private void configurarJanela() {
        setTitle("Menu Principal");
        setSize(500,500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void criarComponentes() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JLabel lblUsuario = new JLabel("Usuário: " + usuarioService.getUsuarioLogado().getNome());
        lblUsuario.setBorder(
            BorderFactory.createEmptyBorder(10,10,10,10)
        );
        panel.add(lblUsuario, BorderLayout.NORTH);

        /*JTextArea area = new JTextArea();
        area.setEditable(false);
        area.setText("""
                Sistema iniciado com sucesso.
            
                Próximas telas:
                
                - Consultar CNPJ
                - Listar empresas
                - Estatísticas
                """
        );
        panel.add(area, BorderLayout.CENTER);*/

        JButton btnConsultar = new JButton("Consultar CNPJ");
        btnConsultar.addActionListener(e -> {
            new ConsultaCnpjFrame(
                empresaService,
                consultaService,
                usuarioService
            );
        });
        panel.add(btnConsultar);
        add(panel);

        JButton btnLogout = new JButton("Logout");
        btnLogout.addActionListener(e -> logout());
        panel.add(btnLogout, BorderLayout.SOUTH);
        add(panel);
    }


    private void logout() {
        usuarioService.logout();
        dispose();
        new LoginFrame();
    }
}