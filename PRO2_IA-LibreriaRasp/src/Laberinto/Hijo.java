/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Laberinto;

import java.io.Serializable;

/**
 *
 * @author javier
 */
public class Hijo {

    private Hijo norte = null;
    private Hijo sur = null;
    private Hijo este = null;
    private Hijo oeste = null;
    private boolean entradaSalida = false;
    private boolean caminoErroneo = false;
    private boolean explorado = false;
    private boolean victima = false;

    public boolean isExplorado() {
        return explorado;
    }

    public boolean isEntradaSalida() {
        return entradaSalida;
    }

    public boolean isCaminoErroneo() {
        return caminoErroneo;
    }

    public void setExplorado() {
        this.explorado = true;
    }

    public void setAsVictima() {
        this.explorado = true;
    }

    public boolean isVictiva() {
        return this.victima;
    }

    public void setAsCaminoErroneo() {
        this.caminoErroneo = true;
    }

    public void setAsInicio() {
        this.setAsEntradaSalida();
        this.setExplorado();
    }

    public Hijo getNorte() {
        return norte;
    }

    public Hijo getSur() {
        return sur;
    }

    public Hijo getEste() {
        return este;
    }

    public Hijo getOeste() {
        return oeste;
    }

    public Hijo() {
        this.entradaSalida = false;
        this.caminoErroneo = false;
        this.explorado = false;
    }

    public Hijo setAsEntradaSalida() {
        this.entradaSalida = true;
        return this;
    }

    public Hijo addHijos(boolean norte, boolean sur, boolean este, boolean oeste) {
        if (norte && this.norte == null) {
            this.norte = new Hijo();
            this.norte.sur = this;
        }
        if (sur && this.sur == null) {
            this.sur = new Hijo();
            this.sur.norte = this;
        }
        if (este && this.este == null) {
            this.este = new Hijo();
            this.este.oeste = this;
        }
        if (oeste && this.oeste == null) {
            this.oeste = new Hijo();
            this.oeste.este = this;
        }
        System.out.println();
        return this;
    }

    public int addRescatando(int[] direcciones) {
        Hijo h = new Hijo();
        h.setAsVictima();

        int dir = ((direcciones[0]==2)?10:((direcciones[1]==2)?11:((direcciones[2]==2)?12:(13))));
        
        switch (dir) {
            case 10:
                this.norte = h;
                this.norte.sur = this;
                break;
            case 11:
                this.sur = h;
                this.sur.norte = this;
                break;
            case 12:
                this.este = h;
                this.este.oeste = this;
                break;
            case 13:
                this.oeste = h;
                this.oeste.este = this;
                break;
            default:
                return -1;
        }
        return dir;
    }

}
