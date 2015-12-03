
import java.sql.SQLException;

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
            throw new Exception ("Erro ao recuperar livros");
        }

        return resultado;
    }
}
