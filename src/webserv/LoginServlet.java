package webserv;

import service.UsuarioService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private UsuarioService usuarioService;

    @Override
    public void init() {
        this.usuarioService = new UsuarioService();
    }

    @Override
    protected void doGet(
            HttpServletRequest req,
            HttpServletResponse resp
    ) throws ServletException, IOException {

        // abre login.jsp
        req.getRequestDispatcher("login.jsp")
                .forward(req, resp);
    }

    @Override
    protected void doPost(
            HttpServletRequest req,
            HttpServletResponse resp
    ) throws ServletException, IOException {

        String email = req.getParameter("email");
        String senha = req.getParameter("senha");

        boolean sucesso =
                usuarioService.login(email, senha);

        if (!sucesso) {

            req.setAttribute(
                    "erro",
                    "Email ou senha inválidos"
            );

            req.getRequestDispatcher("login.jsp")
                    .forward(req, resp);

            return;
        }

        HttpSession session =
                req.getSession();

        session.setAttribute(
                "usuario",
                usuarioService.getUsuarioLogado()
        );

        resp.sendRedirect("dashboard.jsp");
    }
}