/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.StringTokenizer;
import java.util.Vector;
import javax.imageio.ImageIO;

/**
 *
 * @author u14298
 */
public class CodigoDeRosseta {
    Vector<DBOAluno> alunos = new Vector<DBOAluno>();
    MeuPreparedStatement bd;
    MeuResultSet set;
    DAOAluno tAlunos;

    public CodigoDeRosseta() throws ClassNotFoundException, SQLException, Exception{
        bd = new MeuPreparedStatement(
              //"com.microsoft.sqlserver.jdbc.SQLServerDriver",
                   //"jdbc:sqlserver://localhost:1433;databasename=TCC",
                   //"TCC", "123456");
            "com.microsoft.sqlserver.jdbc.SQLServerDriver",
            "jdbc:sqlserver://regulus:1433;databasename=BDu14298",
            "BDu14298", "BDu14298");
        tAlunos = new DAOAluno(bd);
        set = tAlunos.getAlunos();
        while(set.resultado.next()){
            int ra = set.resultado.getInt(1);
            String nome = set.resultado.getString(2);
            String url = set.resultado.getString(3);
            alunos.add(new DBOAluno(ra,nome,url));
        }
    }
    
    public String getPrediction(BufferedImage cameraFeed) throws IOException, Exception{
        int index =0;
        double small = 100.0;
        boolean changed = false;
        for(int ct = 0;ct < alunos.size();ct++){
            String str = "";
            StringTokenizer breaker = new StringTokenizer(alunos.elementAt(ct).URL,"*");
            str = breaker.nextToken();
            BufferedImage img1 = ImageIO.read(new File(str));
            str = breaker.nextToken();
            BufferedImage img2 = ImageIO.read(new File(str));
            str = breaker.nextToken();
            BufferedImage img3 = ImageIO.read(new File(str));
            str = breaker.nextToken();
            BufferedImage img4 = ImageIO.read(new File(str));
            str = breaker.nextToken();
            BufferedImage img5 = ImageIO.read(new File(str));
            if(Reconhece(cameraFeed,img1)<small){
                changed = true;
                small = Reconhece(cameraFeed,img1);
            }
            if(Reconhece(cameraFeed,img2)<small){
                changed = true;
                small = Reconhece(cameraFeed,img2);
            }
            if(Reconhece(cameraFeed,img3)<small){
                changed = true;
                small = Reconhece(cameraFeed,img3);
            }
            if(Reconhece(cameraFeed,img4)<small){
                changed = true;
                small = Reconhece(cameraFeed,img4);
            }
            if(Reconhece(cameraFeed,img5)<small){
                changed = true;
                small = Reconhece(cameraFeed,img5);
            }
            if(changed){
                index = ct;
            }
            changed = false;
            
        }
        if(small < 0.025){
        return alunos.elementAt(index).Nome;
        }
        return "nao reconhecido";
    }
    
    public double Reconhece(BufferedImage img1,BufferedImage img2) throws Exception{
        int width1 = img1.getWidth();
        int width2 = img2.getWidth();
        int height1 = img1.getHeight();
        int height2 = img2.getHeight();
        if ((width1 != width2) || (height1 != height2)) {
            throw new Exception("Imagems Invalidas!");
        }
        long diff = 0;
        for (int y = 0; y < height1; y++) {
          for (int x = 0; x < width1; x++) {
            int rgb1 = img1.getRGB(x, y);
            int rgb2 = img2.getRGB(x, y);
            int r1 = (rgb1 >> 16) & 0xff;
            int g1 = (rgb1 >>  8) & 0xff;
            int b1 = (rgb1      ) & 0xff;
            int r2 = (rgb2 >> 16) & 0xff;
            int g2 = (rgb2 >>  8) & 0xff;
            int b2 = (rgb2      ) & 0xff;
            diff += Math.abs(r1 - r2);
            diff += Math.abs(g1 - g2);
            diff += Math.abs(b1 - b2);
          }
        }
        double n = width1 * height1 * 3;
        return  diff / n / 255.0;
    }
}
