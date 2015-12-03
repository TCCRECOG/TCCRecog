
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.awt.image.ImageProducer;
import java.awt.image.RescaleOp;
import java.sql.SQLException;
import java.util.Calendar;
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
    
    public static BufferedImage matToBufferedImage(Mat matrix) {  
     int cols = matrix.cols();  
     int rows = matrix.rows();  
     int elemSize = (int)matrix.elemSize();  
     byte[] data = new byte[cols * rows * elemSize];  
     int type;  
     matrix.get(0, 0, data);  
     switch (matrix.channels()) {  
       case 1:  
         type = BufferedImage.TYPE_BYTE_GRAY;  
         break;  
       case 3:  
         type = BufferedImage.TYPE_3BYTE_BGR;  
         // bgr to rgb  
         byte b;  
         for(int i=0; i<data.length; i=i+3) {  
           b = data[i];  
           data[i] = data[i+2];  
           data[i+2] = b;  
         }  
         break;  
       default:  
         return null;  
     }  
     BufferedImage image = new BufferedImage(cols, rows, type);  
     image.getRaster().setDataElements(0, 0, cols, rows, data);  
     return image;  
   }
    
    public static void main(String[] args) throws ClassNotFoundException, SQLException, Exception{
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);//biblioteca
        Arduino arduino = new Arduino("COM5",9600);
        BufferedImage picture = new BufferedImage(250, 270, BufferedImage.TYPE_INT_ARGB);//HOSPEDEIRO DA FOTO FINAL
        Image mage = null;
        CodigoDeRosseta recog  = new CodigoDeRosseta();//CRUCIAL (SERIO)!!!
        Tela tela = new Tela();//tela
        tela.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        tela.setVisible(true);
        CascadeClassifier faceDetection = new CascadeClassifier("C:\\openCV\\sources\\data\\lbpcascades\\lbpcascade_frontalface.xml");
        VideoCapture cap = new VideoCapture(0);//camera
        cap.open(0);//inicia a camera
        Mat frame = new Mat();
        Timer t = new Timer();
        while(tela.running){
        cap.read(frame);//TIRA A FOTO
        BufferedImage image = Main.matToBufferedImage(frame);//SALVA A FOTO
        tela.setFeed(image);
        MatOfRect faces = new MatOfRect();//CRUCIAL
        faceDetection.detectMultiScale(frame, faces);//CRUCIAL
        for(Rect rect : faces.toArray()){
            if(rect.width>=165 && rect.height>=165 && rect.width<=180 && rect.height<=180){
                if(t.running != true){
                    t.start();
                    tela.setStatus("Esperando posicionamento...");
                }
                if(t.ticks == 5){
                picture = image.getSubimage(rect.x+20,rect.y+30,130,130);//FOTO SÓ DO ROSTINHO
                RescaleOp rescaleOp = new RescaleOp(4.0f, -80.0f, null);//CLAREAR E MELHORAR CONTRASTE.1
                rescaleOp.filter(picture, picture);//.2
                ImageFilter filter = new GrayFilter(true, 50);  //.3
                ImageProducer producer = new FilteredImageSource(picture.getSource(), filter);//.4
                Image mage2 = Toolkit.getDefaultToolkit().createImage(producer);//.5
                mage = mage2;//.6
                Graphics2D cv = (Graphics2D)picture.createGraphics();//.7
                cv.drawImage(mage, 0,0,null);//.8
                cv.dispose();//.9
                String id = recog.getPrediction(picture);
                tela.setNome(id);
                tela.setStatus("Pronto");
                t.reset();
                Calendar cal = Calendar.getInstance();
                tela.setLog(cal.getTime()+" "+id+"\n");
                if(id.equals("nao reconhecido"))
                    arduino.enviaDados(1);
                else
                    arduino.enviaDados(3);
                Thread.sleep(5000);
                arduino.enviaDados(2);
                arduino.enviaDados(4);
                //Thread.sleep(100);
                }
            }else{
                t.reset();
                tela.setStatus("standby");
            }
        }
        }
    }
}
