
package Ejer2;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 *
 * @author javierballesteroscarcamo
 */
public class NewClass {

    
    
    public static void PanelInPanel(JPanel contentPanel,JPanel instancePanel){
        
        instancePanel.setSize(contentPanel.getSize());
        instancePanel.setLocation(0, 0);
        contentPanel.removeAll();
        contentPanel.add(instancePanel);
        contentPanel.revalidate();
        contentPanel.repaint();
    }
    
    public static void borrarContenidoPanel(JPanel panel){
        panel.removeAll();
        panel.revalidate();
        panel.repaint();
        
    }
    
    public static void escalarImagen(JLabel labelName, boolean mantenerProporciones, String rutaImagen){
    ImageIcon imagenEscalada = new ImageIcon(rutaImagen);
    
    if (mantenerProporciones) {
        int ancho = labelName.getWidth();
        int alto = labelName.getHeight();
        
        // Calcula la escala manteniendo las proporciones originales
        double escalaAncho = (double) ancho / imagenEscalada.getIconWidth();
        double escalaAlto = (double) alto / imagenEscalada.getIconHeight();
        double escala = Math.min(escalaAncho, escalaAlto);
        
        // Aplica la escala manteniendo las proporciones originales
        int nuevoAncho = (int) (imagenEscalada.getIconWidth() * escala);
        int nuevoAlto = (int) (imagenEscalada.getIconHeight() * escala);
        
        imagenEscalada = new ImageIcon(imagenEscalada.getImage().getScaledInstance(nuevoAncho, nuevoAlto, Image.SCALE_SMOOTH));
    } 
    else {
        // Reescala la imagen sin mantener las proporciones originales
        imagenEscalada = new ImageIcon(imagenEscalada.getImage().getScaledInstance(labelName.getWidth(), labelName.getHeight(), Image.SCALE_SMOOTH));
    }
    
    labelName.setIcon(imagenEscalada);
    labelName.repaint();   
    }
    
    public static void escalarImagen (JLabel labelDimension, boolean mantenerProporciones, String root, Dimension dimension){
        ImageIcon imagenEscalada = new ImageIcon(root);
    
    if (mantenerProporciones) {
        int ancho = dimension.width;
        int alto = dimension.height;
        
        // Calcula la escala manteniendo las proporciones originales
        double escalaAncho = (double) ancho / imagenEscalada.getIconWidth();
        double escalaAlto = (double) alto / imagenEscalada.getIconHeight();
        double escala = Math.min(escalaAncho, escalaAlto);
        
        // Aplica la escala manteniendo las proporciones originales
        int nuevoAncho = (int) (imagenEscalada.getIconWidth() * escala);
        int nuevoAlto = (int) (imagenEscalada.getIconHeight() * escala);
        
        imagenEscalada = new ImageIcon(imagenEscalada.getImage().getScaledInstance(nuevoAncho, nuevoAlto, Image.SCALE_SMOOTH));
    } 
    else {
        // Reescala la imagen sin mantener las proporciones originales
        imagenEscalada = new ImageIcon(imagenEscalada.getImage().getScaledInstance(labelDimension.getWidth(), labelDimension.getHeight(), Image.SCALE_SMOOTH));
    }
                                                    
    labelDimension.setIcon(imagenEscalada);
    labelDimension.repaint();  
    
    }
    
    public static void escalarImagenesSecuencialmente(JLabel label, boolean mantenerproporciones, String [] rutas, int delay, int period) {
        Timer timer = new Timer(delay, null);
        int[] currentIndex = {0}; // Usar un array para poder modificar el valor dentro del ActionListener

        timer.addActionListener((ActionEvent e) -> {
            escalarImagen(label, mantenerproporciones, rutas[currentIndex[0]]);
            currentIndex[0]++;
            if (currentIndex[0] >= rutas.length) {
                currentIndex[0] = 0; // Reiniciar el índice si hemos alcanzado el final de la lista
            }
        });

        timer.setInitialDelay(delay);
        timer.setDelay(period);
        timer.start();
    }
    
    public static void escalarImagenesSecuencialmenteMayorMenor(JLabel label, boolean mantenerproporciones, String [] rutas, int delay, int period) {
        Timer timer = new Timer(delay, null);
        int[] currentIndex = {rutas.length -1}; // Usar un array para poder modificar el valor dentro del ActionListener

        timer.addActionListener((ActionEvent e) -> {
            escalarImagen(label, mantenerproporciones, rutas[currentIndex[0]]);
            currentIndex[0]--;
            if (currentIndex[0] <=0) {
                currentIndex[0] = 0; // Reiniciar el índice si hemos alcanzado el final de la lista
            }
        });

        timer.setInitialDelay(delay);
        timer.setDelay(period);
        timer.start();
    }
    
    public static ArrayList<String> CreateStringList(String root, String name, String filetype, int size){
        ArrayList<String> ListaTemporal = new ArrayList<>();
        for(int i=0; i<size; i++){
            
            String route = root + name + i + "." + filetype;
            ListaTemporal.add(route);
            System.out.println(route);
        }
        return  ListaTemporal;
    }
    
    public static String ReadTextFile (String root) throws FileNotFoundException, IOException { 
          try(BufferedReader br = new BufferedReader(new FileReader(root))){
            StringBuilder sb = new StringBuilder();
            String line;
            while((line = br.readLine())!=null){
                sb.append(line);
                
                
            }
          return sb.toString(); 
    
        } 
          
    }
    
    public static void Async(Runnable asyncFunction, long waitTime) {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.schedule(asyncFunction, waitTime, TimeUnit.SECONDS);
    }
    
    public static void AnimacionPredeterminada (JLabel label, String [] rutas, int delay) {
            int[] contador = {0};
            Timer timer = new Timer(delay, event -> {
            if (contador[0] < rutas.length) {
                try {
                    BufferedImage img = ImageIO.read(new File(rutas[contador[0]]));
                    label.setIcon(new ImageIcon(img));
                    contador[0]++;
                } catch (IOException ex) {
                }
            } else {
                ((Timer) event.getSource()).stop();
            }
        });

        timer.start();
    }
    
    public static void AnimacionPredeterminadaMayorMenor (JLabel label, String [] rutas, int delay) {
            int[] contador = new int[rutas.length -1];
            Timer timer = new Timer(delay, event -> {
            if (contador[0] >=0) {
                try {
                    BufferedImage img = ImageIO.read(new File(rutas[contador[0]]));
                    label.setIcon(new ImageIcon(img));
                    contador[0]--;
                } catch (IOException ex) {
                }
            } else {
                ((Timer) event.getSource()).stop();
            }
        });

        timer.start();
    }

   
    

    
          
}
