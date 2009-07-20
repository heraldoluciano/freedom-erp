/*
 * Created on 18/04/2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package org.freedom.telas;

import java.awt.Component;
import java.awt.Container;
import org.freedom.infra.model.jdbc.DbConnection;

import org.freedom.componentes.JPanelPad;

/**
 * @author alexandre
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public interface IFilho {
    public abstract void setTitulo(String tit, String name);

    public abstract void setAtribos(int Esq, int Topo, int Larg, int Alt);

    public abstract void setTela(Container c);

    public abstract Container getTela();
    
    public abstract JPanelPad adicBotaoSair();

    public abstract void setFirstFocus(Component firstFocus);

    public abstract void firstFocus();

    public abstract void setConexao(DbConnection cn);

    public abstract void execShow();

    public abstract boolean getInitFirstFocus();

    public abstract void setInitFirstFocus(boolean initFirstFocus);

    public abstract void setTelaPrim(FPrincipal fP);
}