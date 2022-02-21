import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "ServletSkier")
public class ServletSkier extends HttpServlet {
    private static final int DAY_MIN = 1;
    private static final int DAY_MAX = 366;
    private static final String SKIERS = "skiers";
    private static final String SEASONS = "seasons";
    private static final String DAYS = "days";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        String urlPath = request.getPathInfo();

        // check we have a URL!
        if (urlPath == null || urlPath.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.getWriter().write("invalid url");
            return;
        }

        String[] urlParts = urlPath.split("/");
        // and now validate url path and return the response status code
        // (and maybe also some value if input is valid)

        if (!isUrlValid(urlParts)) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        } else {
            response.setStatus(HttpServletResponse.SC_OK);
        }
    }

        protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
        res.setContentType("application/json");
        String urlPath = req.getPathInfo();

        // check we have a URL!
        if (urlPath == null || urlPath.isEmpty()) {
            res.setStatus(HttpServletResponse.SC_NOT_FOUND);
            res.getWriter().write("invalid url");
            return;
        }

        String[] urlParts = urlPath.split("/");
        // and now validate url path and return the response status code
        // (and maybe also some value if input is valid)

        if (!isUrlValid(urlParts)) {
            res.setStatus(HttpServletResponse.SC_NOT_FOUND);
        } else {
            res.setStatus(HttpServletResponse.SC_OK);
            // do any sophisticated processing with urlParts which contains all the url params
            res.getWriter().write("{\n" +
                            "  \"skiers\": " + urlParts[7] +
                    "}");
        }
    }

    private boolean isUrlValid(String[] urlPath) {
//        return true;
//        http://localhost:8080/ServletSkier_war_exploded/skiers/1997/seasons/9977/days/114/skiers/1114
        if(urlPath.length == 8) {
            return isNumeric(urlPath[1]) &&
                    urlPath[2].equals(SEASONS) &&
                    isNumeric(urlPath[3]) &&
                    urlPath[3].length() == 4 &&
                    urlPath[4].equals(DAYS) &&
                    isNumeric(urlPath[5]) &&
                    Integer.parseInt(urlPath[5]) >= DAY_MIN &&
                    Integer.parseInt(urlPath[5]) <= DAY_MAX &&
                    urlPath[6].equals(SKIERS) &&
                    isNumeric(urlPath[7]);
        }
        return false;
    }

    private boolean isNumeric(String s) {
        if(s == null || s.equals("")) return false;
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException ignored) { }
        return false;
    }
}
