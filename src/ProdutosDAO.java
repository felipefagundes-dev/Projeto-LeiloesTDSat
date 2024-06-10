/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Adm
 */
import java.sql.PreparedStatement;
import java.sql.Connection;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.sql.SQLException;
import java.util.List;

public class ProdutosDAO {

    Connection conn;
    PreparedStatement prep;
    ResultSet resultset;
    ArrayList<ProdutosDTO> listagem = new ArrayList<>();

    public void cadastrarProduto(ProdutosDTO produto) {
        conn = new conectaDAO().connectDB();
        String sql = "INSERT INTO produtos (nome, valor, status) VALUES (?, ?, ?)";

        try {
            prep = conn.prepareStatement(sql);
            prep.setString(1, produto.getNome());
            prep.setInt(2, produto.getValor());
            prep.setString(3, produto.getStatus());

            prep.executeUpdate();
            JOptionPane.showMessageDialog(null, "Produto cadastrado com sucesso!");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao cadastrar produto: " + e.getMessage());
        } finally {
            try {
                if (prep != null) {
                    prep.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Erro ao fechar conexão: " + e.getMessage());
            }
        }
    }

    public List<ProdutosDTO> listarTodos() {

        List<ProdutosDTO> listaProdutos = new ArrayList<>();

        try {
            conn = new conectaDAO().connectDB();

            String sql = "SELECT * FROM produtos";
            PreparedStatement consulta = conn.prepareStatement(sql);

            ResultSet resposta = consulta.executeQuery();

            while (resposta.next()) {
                ProdutosDTO produto = new ProdutosDTO();

                produto.setId(resposta.getInt("id"));
                produto.setNome(resposta.getString("nome"));
                produto.setValor(resposta.getInt("valor"));
                produto.setStatus(resposta.getString("status"));

                listaProdutos.add(produto);
            }

        } catch (SQLException e) {

        }
        return listaProdutos;
    }

    public void venderProduto(int idProduto) {

        conn = new conectaDAO().connectDB();
        String sql = "UPDATE produtos SET status = 'Vendido' WHERE id = ?";

        try {
            prep = conn.prepareStatement(sql);
            prep.setInt(1, idProduto);

            prep.executeUpdate();
            JOptionPane.showMessageDialog(null, "Produto vendido com sucesso!");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao vender produto: " + e.getMessage());
        } finally {
            try {
                if (prep != null) {
                    prep.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Erro ao fechar conexão: " + e.getMessage());
            }
        }
    }

    public List<ProdutosDTO> listarProdutosVendidos() {
        List<ProdutosDTO> lista = new ArrayList<>();

        try {
            conn = new conectaDAO().connectDB();
            String sql = "SELECT * FROM produtos WHERE status = 'Vendido'";
            prep = conn.prepareStatement(sql);
            ResultSet resposta = prep.executeQuery();

            while (resposta.next()) {
                ProdutosDTO produto = new ProdutosDTO();
                produto.setId(resposta.getInt("id"));
                produto.setNome(resposta.getString("nome"));
                produto.setValor(resposta.getInt("valor"));
                produto.setStatus(resposta.getString("status"));

                lista.add(produto);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao buscar produtos vendidos: " + e.getMessage());
        } finally {
            try {
                if (prep != null) {
                    prep.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Erro ao fechar conexão: " + e.getMessage());
            }
        }

        return lista;
    }

}
