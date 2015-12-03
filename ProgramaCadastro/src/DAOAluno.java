
import java.sql.SQLException;
import java.sql.Statement;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author u14298
 */
public class DAOAluno {
    
    private MeuPreparedStatement bd;

    public DAOAluno (MeuPreparedStatement bd) throws Exception
    {
        if (bd==null)
            throw new Exception ("Acesso a BD nao fornecido");

        this.bd = bd;
    }
    
    public MeuResultSet getAlunos () throws Exception
    {
        MeuResultSet resultado = null;

        try
        {
            String sql;

            sql = "SELECT * FROM Aluno";

            bd.prepareStatement (sql);

            resultado = bd.executeQuery();
        }
        catch (SQLException erro)
        {
            throw new Exception ("Erro ao recuperar alunos");
        }

        return resultado;
    }
    
    public void insertAlunos (String aluno) throws Exception
    {   
        String sql;
        sql = "INSERT INTO Aluno VALUES("+aluno+")";
        bd.prepareStatement(sql);
        bd.executeUpdate();
        bd.commit();
        
    }
    
    public void deletaAluno(String ra) throws SQLException{
        String sql;
        sql = "delete Aluno from Aluno where RA="+ra;
        bd.prepareStatement(sql);
        bd.executeUpdate();
        bd.commit();
    }
    
    public MeuResultSet getAlunosBusca (String dados, boolean tipoDeBusca) throws Exception
    {
        MeuResultSet resultado = null;
        
        if(tipoDeBusca){

        try
        {
            String sql;

            sql = "SELECT * FROM Aluno where Nome like '%"+dados+"%'";

            bd.prepareStatement (sql);

            resultado = bd.executeQuery();
        }
        catch (SQLException erro)
        {
            throw new Exception ("Erro ao recuperar alunos");
        }
        }else{
            String sql;

            sql = "SELECT * FROM Aluno where RA ="+dados+"";

            bd.prepareStatement (sql);

            resultado = bd.executeQuery();
        }

        return resultado;
    }
}
