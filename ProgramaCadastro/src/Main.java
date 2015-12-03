
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.awt.image.ImageProducer;
import java.awt.image.RescaleOp;
import java.sql.SQLException;
import javax.swing.GrayFilter;
import javax.swing.JFrame;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Rect;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.videoio.VideoCapture;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author u14298
 */
public class Main {

   public static void main(String[] args) throws ClassNotFoundException, SQLException, Exception{
       System.loadLibrary(Core.NATIVE_LIBRARY_NAME);//biblioteca
       BufferedImage picture = new BufferedImage(250, 270, BufferedImage.TYPE_INT_ARGB);//HOSPEDEIRO DA FOTO FINAL
       Image mage = null;
       Tela tela = new Tela();
       tela.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
       tela.setVisible(true);
       CascadeClassifier faceDetection = new CascadeClassifier("C:\\openCV\\sources\\data\\lbpcascades\\lbpcascade_frontalface.xml");
       VideoCapture cap = new VideoCapture(0);//camera
       cap.open(0);//inicia a camera
       Mat frame = new Mat();
       while(tela.running){
       cap.read(frame);//TIRA A FOTO
       BufferedImage image = tela.matToBufferedImage(frame);//SALVA A FOTO
       tela.setFeed(image);
       MatOfRect faces = new MatOfRect();//CRUCIAL
       faceDetection.detectMultiScale(frame, faces);//CRUCIAL
       for(Rect rect : faces.toArray()){
            if(rect.width>=165 && rect.height>=165 && rect.width<=180 && rect.height<=180){
                picture = image.getSubimage(rect.x+20,rect.y+30,130,130);//FOTO SÓ DO ROSTINHO
                tela.fotinha = picture;
                if(!tela.txtNome.getText().equals(""))
                    tela.btnTiraFoto.setEnabled(true);
                if(!tela.txtNome1.getText().equals(""))
                    tela.btnTiraFoto1.setEnabled(true);
            }else{
                tela.btnTiraFoto.setEnabled(false);
                tela.btnTiraFoto1.setEnabled(false);
            }
        }
       }
   }
}
