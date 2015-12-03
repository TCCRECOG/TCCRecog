/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author u14298
 */
public class DBOAluno {
    protected int RA;
    protected String Nome;
    protected String URL;
    
    protected void setRa(int ra){
        this.RA = ra;
    }
    
    protected void setNome(String nome){
        this.Nome = nome;
    }
    
    protected void setURL(String url){
        this.URL = url;
    } 
    
    public int getRa(){
        return this.RA;
    }
    
    public String getNome(){
        return this.Nome;
    }
    
    public String getURL(){
        return this.URL;
    }
    
    public DBOAluno(){
    }
    
    public DBOAluno(int ra,String nome,String url){
        this.setRa(ra);
        this.setNome(nome);
        this.setURL(url);
    }
    
}
