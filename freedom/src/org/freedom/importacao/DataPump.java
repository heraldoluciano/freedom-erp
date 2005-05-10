/*
 * Created on 03/05/2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package org.freedom.importacao;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.freedom.componentes.JTextFieldPad;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * @author alexandre
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class DataPump {
    private Banco banco = null;

    private String url = "jdbc:firebirdsql:10.1.1.20/3050:/opt/firebird/dados/clientes/pomiagro/freedom.fdb";

    private String driver = "org.firebirdsql.jdbc.FBDriver";

    private String arquivo = "";

    private String dados = "";

    private Document document = null;

    private Vector size = new Vector();

    private Vector values = new Vector();

    private Vector fields = new Vector();

    protected String extractParenthesis(String field) {
        String result = field.substring(field.indexOf('(') + 1, field
                .indexOf(')'));

        return new Integer(Integer.parseInt(result)).toString();
    }

    protected String extractType(String field) {
        String result = field.substring(0, field.indexOf('('));

        return result;
    }

    protected String createSQLColumns(Node node, String sql, String tableName) {
        if (node.hasChildNodes()) {
            NodeList children = node.getChildNodes();

            for (int i = 0; i < children.getLength(); i++) {
                Node child = children.item(i);
                if (child.hasAttributes()) {
                    NamedNodeMap attr = child.getAttributes();

                    if (attr.getNamedItem("picture") == null) {
                        if (child.hasChildNodes())
                            sql = createSQLColumns(child, sql, tableName);
                        continue;
                    }

                    String name = attr.getNamedItem("name").getNodeValue()
                            .replace('-', '_');
                    JTextFieldPad field = null;
                    sql += name + " ";

                    Integer length = new Integer(extractParenthesis(attr
                            .getNamedItem("picture").getNodeValue()));

                    size.addElement(length);

                    if (attr.getNamedItem("numeric") != null
                            && attr.getNamedItem("numeric").getNodeValue()
                                    .equalsIgnoreCase("true")) {
                        sql += "NUMERIC(" + length + ",0)";
                        field = new JTextFieldPad(JTextFieldPad.TP_NUMERIC,
                                length.intValue(), 0);
                    } else {
                        sql += "CHAR(" + length + ")";
                        field = new JTextFieldPad(JTextFieldPad.TP_STRING,
                                length.intValue(), 0);
                    }

                    field.setNomeCampo(name);
                    field.setName(name);
                    fields.addElement(field);
                    if (child.getNextSibling() != null)
                        sql += ", ";
                }
            }
        } else {
            Node child = node;
            if (child.hasAttributes()) {
                NamedNodeMap attr = child.getAttributes();

                if (attr.getNamedItem("picture") == null) {
                    if (child.hasChildNodes())
                        sql = createSQLColumns(child, sql, tableName);
                    return sql;
                }

                String name = attr.getNamedItem("name").getNodeValue().replace(
                        '-', '_');

                sql += name + " ";

                Integer length = new Integer(extractParenthesis(attr
                        .getNamedItem("picture").getNodeValue()));

                size.addElement(length);
                JTextFieldPad field = null;

                if (attr.getNamedItem("numeric") != null
                        && attr.getNamedItem("numeric").getNodeValue()
                                .equalsIgnoreCase("true")) {
                    sql += "NUMERIC(" + length + ",0)";
                    field = new JTextFieldPad(JTextFieldPad.TP_NUMERIC, length
                            .intValue(), 0);
                } else {
                    sql += "CHAR(" + length + ")";
                    field = new JTextFieldPad(JTextFieldPad.TP_STRING, length
                            .intValue(), 0);
                }

                field.setNomeCampo(name);
                field.setName(name);
                fields.addElement(field);
                if (child.getNextSibling() != null)
                    sql += ", ";
            }
        }
        return sql;
    }

    protected String leDados(FileReader leDados, String tableName) {
        String result = "INSERT INTO " + tableName + " ";
        String columns = "(";
        String values = "(";

        for (int i = 0; i < fields.size(); i++) {
            int origSize = ((Integer) size.get(i)).intValue();
            char cbuf[] = new char[origSize];
            boolean parar = false;
            try {
                int j = 0;
                for (j = 0 ; j < origSize; j++) {                
                    cbuf[j] = (char) leDados.read(); 
                    if (cbuf[j] == '\r' || cbuf[j] == -1 ) {
                        if (cbuf[j] == '\r') {
                            cbuf[j] = 20;
                        }
                        parar = true;
                        break;
                    }
                }
                
                String buf = new String(cbuf).trim();

                JTextFieldPad field = (JTextFieldPad) fields.get(i);
                field.setVlrString(buf);

                columns += field.getNomeCampo();
                switch (field.getTipo()) {
                case JTextFieldPad.TP_STRING:
                    values += "'" + field.getVlrString() + "'";
                    break;
                case JTextFieldPad.TP_NUMERIC:
                    values += field.getVlrBigDecimal();
                default:
                }

                if (parar)
                    break;
                
                if (i < size.size() - 1) {
                    columns += ", ";
                    values += ", ";
                }

            } catch (IOException e) {
                break;
            }
        }

        columns += ")";
        values += ")";

        result += columns + " VALUES " + values + ";";
        return result;
    }

    public DataPump(String user, String pass, String arquivo, String dados) {
        super();

        banco = new Banco(url, driver, user, pass);

        if (banco.getConnection() == null) {
            System.err.println("Não conectou no banco de dados");
            System.exit(2);
        }

        this.arquivo = arquivo;

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            document = builder.parse(new File(arquivo));

            String filename = document.getDocumentElement().getAttribute(
                    "filename").replace('.', '_');
            String sql = "";

            if (document.getDocumentElement().hasChildNodes()) {

                sql = "CREATE TABLE " + filename + " (";
                sql = createSQLColumns(document.getDocumentElement(), sql,
                        filename)
                        + ");\n";
            }

            try {
                System.out.println("Tentando dar DROP na tabela");
                Statement stmt = banco.getStatement();
                stmt.executeUpdate("DROP TABLE " + filename + ";\n");
            } catch (SQLException e) {
                System.err.println("Tabela não encontrada");
            }

            try {
                System.err.println("Criando a tabela");
                System.out.println(sql);
                Statement stmt = banco.getStatement();
                stmt.executeUpdate(sql);
            } catch (SQLException e) {
                System.err.println("ERRO NA CONEXÃO\n");
                e.printStackTrace();
                System.exit(-1);
            }

            File arquivoDados = new File(dados);
            FileReader leDados = new FileReader(arquivoDados);

            String insertSQL = leDados(leDados, filename);
            if (insertSQL != null) {
                try {
                    System.out.println(insertSQL);
                    Statement stmt = banco.getStatement();
                    stmt.executeUpdate(insertSQL);
                } catch (SQLException e) {
                    System.err.println("ERRO NA INSERÇÃO\n");
                    e.printStackTrace();
                    System.exit(-1);
                }

            }

        } catch (SAXException sxe) {
            Exception x = sxe;
            if (sxe.getException() != null)
                x = sxe.getException();
            x.printStackTrace();

        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public static void main(String[] args) {
        if (args.length != 4) {
            System.err
                    .println("Uso: java DataPump arquivo_de_dados arquivo_de_definição usuario senha");
            System.exit(1);
        }

        DataPump dataPump = new DataPump(args[2], args[3], args[1], args[0]);

    }
}