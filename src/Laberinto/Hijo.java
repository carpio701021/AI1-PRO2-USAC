/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Laberinto;

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

}
