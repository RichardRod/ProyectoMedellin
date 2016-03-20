package cucei.mx.udg.proyectomedellin;

/**
 * Created by MISAEL on 05/02/2016.
 */
public class News {

    private String urlImage;
    private String contenido;

    public News(String urlImage, String contenido){
        this.urlImage = urlImage;
        this.contenido = contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public String getContenido() {
        return contenido;
    }

    public String getUrlImage() {
        return urlImage;
    }

    @Override
    public String toString() {
        return "URL:" + urlImage + "|" + contenido;
    }
}
