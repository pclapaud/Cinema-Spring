package fr.laerce.cinema.web;

import fr.laerce.cinema.dao.FilmsDao;
import fr.laerce.cinema.model.Film;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.apache.commons.io.IOUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

@Controller
public class MainController {
    @Autowired
    FilmsDao filmsDao;
    @Value("${monUrl}")
    String monUrl;

    @GetMapping("/")
    public String main(Model model){

        model.addAttribute("nom","Karl");
        model.addAttribute("films", filmsDao.films());
        return "index";
    }
    //@GetMapping("/detail")
//    public String detail(Model model, @RequestParam("id")String id){
    @GetMapping("/detail/{id}")
    public String detail(Model model, @PathVariable("id")String id){
        Integer idfilm = Integer.parseInt(id);

        model.addAttribute("film", filmsDao.getById(idfilm));

        return "details";
    }


    @GetMapping(value = "/image/{id}"
    )
//public void getImage(HttpServletRequest request,HttpServletResponse response, @PathVariable("id")String id) throws IOException {
//        ServletContext cntx= request.getServletContext();
//        // Chemin absolu de l'image
//        String url = "C:\\Users\\patou\\Desktop\\CDA\\Frederick\\ressources\\affiches";
//        String filename = url+"/"+id;
//        String mime = cntx.getMimeType(filename);
//        if (mime == null) {
//            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
//            return;
//        }
//        response.setContentType(mime);
//        File file = new File(filename);
//        response.setContentLength((int)file.length());
//        FileInputStream in = new FileInputStream(file);
//        OutputStream out = response.getOutputStream();
//
//        byte[] buf = new byte[1024];
//        int count = 0;
//        while ((count = in.read(buf)) >= 0) {
//            out.write(buf, 0, count);
//        }
//        out.close();
//        in.close();
//}
    @RequestMapping(value = "/image/{id}")
    public ResponseEntity<byte[]> affiche(@PathVariable("id")String id) {
        try {
            File file = new File(monUrl+id);
            FileInputStream in = new FileInputStream(file);
            byte[] media = IOUtils.toByteArray(in);
            ResponseEntity<byte[]> responseEntity = new ResponseEntity<>(media, HttpStatus.OK);
            return responseEntity;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }




}

