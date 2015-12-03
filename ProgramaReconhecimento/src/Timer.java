
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author u14298
 */
public class Timer implements Runnable{
    protected boolean running;
    protected int ticks;
    
    public Timer(){
        running = false;
    }
    
    public void reset(){
        running = false;
    }
    
    public void start(){
        Thread t = new Thread(this);
        running = true;
        t.start();
    }
    
    public void run(){
        for(ticks = 0;ticks<5;ticks++){
            if(running){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                }
            }else{
                ticks = 0;
                break;
            }
        }
    }
}
