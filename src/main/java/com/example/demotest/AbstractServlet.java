package com.example.demotest;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public abstract class AbstractServlet extends HttpServlet {

    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        xmlContent(response);

        responseGet(request, response);
    }

    protected abstract void responseGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;

    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        xmlContent(response);
        responsePost(request, response);
    }

    protected abstract void responsePost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;

    protected void xmlContent(HttpServletResponse response) {
        if (Objects.isNull(response)) {
            return;
        }
        response.setContentType("application/xml;charset=UTF-8");
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.setCharacterEncoding("UTF-8");

    }

    /**
     * Vide la valeur dans la session
     *
     * @param name Le nom de la clé en session
     * @param session La session
     */
    protected void viderSessionValue(String name, HttpSession session) {
        if (Objects.nonNull(session) && Objects.nonNull(name)) {
            session.setAttribute(name, null);
        }
    }

    /**
     * Redirige la requête vers une page
     *
     * @param path Le chemin vers la vue
     * @param request L'objet requête
     * @param response L'objet de réponse
     * @throws ServletException Dans le cas où une erreur est remontée
     * @throws IOException Dans le cas où des erreurs de lecture sont remontées
     */
    protected void view(String path, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (Objects.nonNull(path)) {

            forward("/WEB-INF/content/" + path + ".jsp"
                    , request
                    , response);
        }
    }

    /**
     * Chaînage de la requête vers la route
     *
     * @param path La route de redirection
     * @param request L'objet requête
     * @param response L'objet de réponse
     * @throws ServletException Dans le cas où une erreur est remontée
     * @throws IOException Dans le cas où des erreurs de lecture sont remontées
     */
    protected void forward(String path, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (Objects.nonNull(path)) {

            this.getServletContext()
                    .getRequestDispatcher(path)
                    .forward(request, response);
        }
    }

    /**
     * Vide la variable contenant les erreurs
     *
     * @param request L'objet de requête
     */
    protected void viderErreurs(HttpServletRequest request) {
        if (Objects.nonNull(request)) {
            request.setAttribute("errors", Collections.emptyList());
        }
    }

    /**
     * Détermine si aucune erreur n'est présent
     *
     * @param request L'objet requête
     * @return true si aucune erreur, false autrement
     */
    @SuppressWarnings("unchecked")
    protected boolean isValid(HttpServletRequest request) {
        return Objects.nonNull(request)
                && Objects.nonNull(request.getAttribute("errors"))
                && ((List<String>) request.getAttribute("errors")).isEmpty();
    }

    /**
     * Ajoute une erreur à la liste
     *
     * @param error L'erreur
     * @param request L'objet de requête
     */
    @SuppressWarnings("unchecked")
    protected void ajouterErreur(String error,  HttpServletRequest request) {
        if (Objects.nonNull(error) && Objects.nonNull(request)) {
            List<String> erreurList = (List<String>) request.getAttribute("errors");

            List<String> nouvelleErreurList = Objects.isNull(erreurList)
                    ? Collections.emptyList()
                    : erreurList;

            nouvelleErreurList.add(error);

            request.setAttribute("errors", nouvelleErreurList);
        }
    }
}
