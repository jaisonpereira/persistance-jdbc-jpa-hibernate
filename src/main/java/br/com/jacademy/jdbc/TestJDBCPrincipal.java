package br.com.jacademy.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Test com CRUD BASICO ,transacao,statement e preparedStatement
 *
 * @author jpereira
 * 
 * 
 * 
 * 
 *         link:
 *         https://pt.stackoverflow.com/questions/99620/qual-a-diferen%C3%A7a-entre-o-statement-e-o-preparedstatement
 * 
 * 
 * 
 *         A diferença vai além da simples adição de parâmetros.
 * 
 *         A maioria dos bancos de dados relacionais lida com uma consulta
 *         (query) JDBC / SQL em quatro passos:
 * 
 *         1- Interpretar (parse) a consulta SQL;
 * 
 * 
 *         2 -Compilar a consulta SQL;
 * 
 * 
 *         3 - Planejar e otimizar o caminho de busca dos dados;
 * 
 *         4 - Executar a consulta otimizada, buscando e retornando os dados.
 * 
 * 
 *         Um Statement irá sempre passar pelos quatro passos acima para cada
 *         consulta SQL enviada para o banco.
 * 
 *         Já um Prepared Statement pré-executa os passos (1) a (3). Então, ao
 *         criar um Prepared Statement alguma pré-otimização é feita de
 *         imediato. O efeito disso é que, se você pretende executar a mesma
 *         consulta repetidas vezes mudando apenas os parâmetros de cada uma, a
 *         execução usando Prepared Statements será mais rápida e com menos
 *         carga sobre o banco.
 * 
 *         Outra vantagem dos Prepared Statements é que, se utilizados
 *         corretamente, ajudam a evitar ataques de Injeção de SQL. Note que
 *         para isso é preciso que os parâmetros da consulta sejam atribuídos
 *         através dos métodos setInt(), setString(), etc. presentes na
 *         interface PreparedStatement e não por concatenação de strings.
 * 
 *         Para uma consulta que vai ser executada poucas vezes e não requer
 *         nenhum parâmetro, Statement basta. Para os demais casos, prefira
 *         PreparedStatement.
 *
 */
public class TestJDBCPrincipal {

	private static void atualizaProduto(Connection connection, Integer id, String descricao) throws SQLException {
		PreparedStatement preparedStatement = connection
				.prepareStatement("update   Produto set descricao=? where id=?");
		preparedStatement.setString(1, "Descricao :" + descricao);
		preparedStatement.setInt(2, id);
		preparedStatement.execute();
		preparedStatement.close();
	}

	/**
	 * @param preparedStatement
	 * @throws SQLException
	 */
	private static void buscaProduto(Connection connection) throws SQLException {
		PreparedStatement preparedStatement = connection.prepareStatement("select * from Produto");
		/**
		 * Obj result set
		 */
		ResultSet rs = preparedStatement.executeQuery();
		while (rs.next()) {
			System.out.println("id prod: " + rs.getInt("id"));
			System.out.println("id prod: " + rs.getString("nome"));
			System.out.println("id prod: " + rs.getString("descricao"));
		}
		preparedStatement.close();
		rs.close();
	}

	private static void criaTabelaProduto(Connection connection) {
		try {
			/**
			 * Statement e Prepared Statement
			 *
			 * Interpretar (parse) a consulta SQL; Compilar a consulta SQL; Planejar e
			 * otimizar o caminho de busca dos dados; Executar a consulta otimizada,
			 * buscando e retornando os dados.
			 *
			 *
			 * Já um Prepared Statement pré-executa os passos (1) a (3). Então, ao criar um
			 * Prepared Statement alguma pré-otimização é feita de imediato.
			 *
			 * Outra vantagem dos Prepared Statements é que, se utilizados corretamente,
			 * ajudam a evitar ataques de Injeção de SQL
			 *
			 *
			 */
			// Statement statement = connection.createStatement();
			// statement.create.....
			PreparedStatement preparedStatement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS "
					+ " Produto(id INT IDENTITY PRIMARY KEY, nome VARCHAR(255),descricao VARCHAR(255))");
			/**
			 * notea diferenca estamos executando um update ao inves de query
			 */
			preparedStatement.executeUpdate();
			preparedStatement.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	private static void deleteProduto(Connection connection) throws SQLException {
		/**
		 * Aqui eu estou executando para devolver que o id que foi gerado
		 */
		PreparedStatement preparedStatement = connection.prepareStatement("delete from produto where id>2");
		preparedStatement.execute();
		int linhas = preparedStatement.getUpdateCount();
		System.out.println(linhas + " linha(s) removida(s)");
		preparedStatement.close();
	}

	private static void executaTransacao() {
		/**
		 * aqui estamos utilizando try with resources , classes implementan
		 * autocloseable
		 */
		try (java.sql.Connection connection = getConnection()) {
			connection.setAutoCommit(false);
			try {
				System.out.println("transacao iniciada");
				insereProduto(connection, "Televisor", "Tv de plasma");
				insereProduto(connection, "Antena", "Antena Digital");
				connection.commit();
				System.out.println("transacao finalizada");
			} catch (Exception ex) {
				connection.rollback();
				System.out.println("rollback efetuado");
				throw ex;
			} finally {
				connection.setAutoCommit(true);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	private static Connection getConnection() throws SQLException {
		// poderia pasar user e senha aqui caso fosse necessario
		return DriverManager.getConnection("jdbc:h2:file:./bd-data", "sa", "");
	}

	private static void insereProduto(Connection connection, String nome, String descricao) throws SQLException {
		/**
		 * Aqui eu estou executando para devolver que o id que foi gerado
		 *
		 * exemplificar sql injection
		 *
		 */
		PreparedStatement preparedStatement = connection
				.prepareStatement("insert into Produto (nome,descricao) values (?,?)", Statement.RETURN_GENERATED_KEYS);
		preparedStatement.setString(1, nome);
		preparedStatement.setString(2, descricao);
		preparedStatement.execute();
		ResultSet rs = preparedStatement.getGeneratedKeys();
		/*
		 * retorna todos os ids gerados
		 */
		while (rs.next()) {
			Integer id = rs.getInt("id");
			System.out.println("id gerado " + id);
			atualizaProduto(connection, id, descricao);
		}
		rs.close();
		preparedStatement.close();
	}

	public static void main(String[] args) {
		Connection connection = null;
		try {
			connection = getConnection();
			System.out.println("open connection");
			criaTabelaProduto(connection);
			// inserindo item
			for (int i = 0; i < 5; i++) {
				insereProduto(connection, "Geladeira " + i, "Frost free " + i);
			}
			buscaProduto(connection);
			deleteProduto(connection);

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		/**
		 * Testando transacao
		 */
		executaTransacao();
	}

}
