/*
 * Created on 03/05/2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package org.freedom.importacao;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

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

    protected String extractParenthesis(String field) {
        String result = field.substring(field.indexOf('(') + 1, field
                .indexOf(')'));

        return new Integer(Integer.parseInt(result)).toString();
    }

    protected String extractType(String field) {
        String result = field.substring(0, field.indexOf('('));

        return result;
    }

    protected String createSQLColumns(Node node, String sql) {
        if (node.hasChildNodes()) {
            NodeList children = node.getChildNodes();

            for (int i = 0; i < children.getLength(); i++) {
                Node child = children.item(i);
                if (child.hasAttributes()) {
                    NamedNodeMap attr = child.getAttributes();

                    if (attr.getNamedItem("picture") == null) {
                        if (child.hasChildNodes())
                            sql = createSQLColumns(child, sql);
                        continue;
                    }

                    sql += attr.getNamedItem("name").getNodeValue().replace(
                            '-', '_')
                            + " ";

                    if (attr.getNamedItem("numeric") != null
                            && attr.getNamedItem("numeric").getNodeValue()
                                    .equalsIgnoreCase("true")) {
                        sql += "INTEGER";
                    } else {
                        if (attr.getNamedItem("display-length").getNodeValue()
                                .equals("0")) {
                            sql += "CHAR("
                                    + extractParenthesis(attr.getNamedItem(
                                            "picture").getNodeValue()) + ")";
                        } else {
                            sql += "CHAR("
                                    + attr.getNamedItem("display-length")
                                            .getNodeValue() + ")";

                        }
                    }

                    if (i < children.getLength() - 1)
                        sql += ", ";
                }
            }
        } else {
            Node child = node;
            if (child.hasAttributes()) {
                NamedNodeMap attr = child.getAttributes();

                if (attr.getNamedItem("picture") == null) {
                    if (child.hasChildNodes())
                        sql = createSQLColumns(child, sql);
                    return sql;
                }

                sql += attr.getNamedItem("name").getNodeValue().replace('-',
                        '_')
                        + " ";

                if (attr.getNamedItem("numeric") != null
                        && attr.getNamedItem("numeric").getNodeValue()
                                .equalsIgnoreCase("true")) {
                    sql += "INTEGER";
                } else {
                    if (attr.getNamedItem("display-length").getNodeValue()
                            .equals("0")) {
                        sql += "CHAR("
                                + extractParenthesis(attr.getNamedItem(
                                        "picture").getNodeValue()) + ")";
                    } else {
                        sql += "CHAR("
                                + attr.getNamedItem("display-length")
                                        .getNodeValue() + ")";
                    }
                }
            }
        }
        return sql;
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
                sql = createSQLColumns(document.getDocumentElement(), sql)
                        + ")";
            }

            System.out.println(sql);

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