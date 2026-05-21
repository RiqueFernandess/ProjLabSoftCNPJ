package view;

import model.EmpresaModel;
import model.UsuarioModel;
import service.ConsultaService;
import service.EmpresaService;
import service.UsuarioService;

import javax.swing.*;
import java.awt.*;

public class ConsultaCnpjFrame extends JFrame {
    private final EmpresaService empresaService;
    private final ConsultaService consultaService;
    private final UsuarioService usuarioService;
    private JTextField txtCnpj;
    private JButton btnConsultar;
    private JTextArea areaResultado;

    public ConsultaCnpjFrame(
        EmpresaService empresaService,
        ConsultaService consultaService,
        UsuarioService usuarioService
    ) {
        this.empresaService = empresaService;
        this.consultaService = consultaService;
        this.usuarioService = usuarioService;
        configurarJanela();
        criarComponentes();
        setVisible(true);

    }

    private void configurarJanela() {
        setTitle("Consulta CNPJ");
        setSize(700, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private void criarComponentes() {
        JPanel panelPrincipal = new JPanel(new BorderLayout(10,10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        JLabel lblTitulo = new JLabel("CONSULTA DE EMPRESA");
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 20));
        panelPrincipal.add(lblTitulo, BorderLayout.NORTH);

        JPanel panelConsulta = new JPanel(new FlowLayout());
        panelConsulta.add(new JLabel("CNPJ:"));
        txtCnpj = new JTextField(20);
        panelConsulta.add(txtCnpj);
        btnConsultar = new JButton("Consultar");
        panelConsulta.add(btnConsultar);
        panelPrincipal.add(panelConsulta, BorderLayout.CENTER);
        areaResultado = new JTextArea();
        areaResultado.setEditable(false);
        areaResultado.setFont(new Font("Monospaced", Font.PLAIN, 14));

        JScrollPane scroll = new JScrollPane(areaResultado);
        scroll.setPreferredSize(new Dimension(650,300));
        panelPrincipal.add(scroll, BorderLayout.SOUTH);
        
        adicionarEventos();
        add(panelPrincipal);
    }

    private void adicionarEventos() {
        btnConsultar.addActionListener(e -> consultar());
    }

    private void consultar() {
        String cnpj = txtCnpj.getText().replaceAll("\\D","");
        if(cnpj.isBlank()) {
            JOptionPane.showMessageDialog(this, "Digite um CNPJ");
            return;
        }

        if(consultaService.atingiuLimite()) {
            JOptionPane.showMessageDialog(this, "Limite de consultas atingido");
            return;
        }

        if(consultaService.consultaRepetida(cnpj)) {
            JOptionPane.showMessageDialog(this, "Consulta repetida");
            return;
        }

        UsuarioModel usuario = usuarioService.getUsuarioLogado();
        long inicio = System.currentTimeMillis();
        areaResultado.setText("Consultando empresa...");

        SwingUtilities.invokeLater(() -> {
            EmpresaModel empresa = empresaService.consultarPorCnpj(cnpj);
            if(empresa == null) {
                areaResultado.setText("Empresa não encontrada.");
                consultaService.registrarConsultaCompleta(
                        usuario.getIdUsuario(),
                        null,
                        false,
                        "Empresa não encontrada",
                        inicio
                );

                return;
            }

            consultaService.registrarConsulta(cnpj);
            consultaService.registrarConsultaCompleta(
                usuario.getIdUsuario(),
                empresa.getIdEmpresa(),
                true,
                null,
                inicio
            );

            exibirEmpresa(empresa);
        });

    }

    private void exibirEmpresa(EmpresaModel e) {
        String texto = """
                ==============================    
                ID: %d
                CNPJ: %s
                Nome: %s
                Tipo: %s               
                Município: %s                
                Data abertura: %s                
                Data encerramento: %s                
                Situação: %s                
                ==============================
                """.formatted(
                e.getIdEmpresa(),
                e.getCnpj(),
                e.getNome(),
                e.getTipoEmpresa(),
                e.getMunicipio(),
                e.getDataAbertura(),
                e.getDataEncerramento(),
                e.getSituacao()
        );

        areaResultado.setText(texto);

    }

}