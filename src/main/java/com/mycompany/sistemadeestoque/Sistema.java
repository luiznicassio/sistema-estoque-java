package com.mycompany.sistemadeestoque;

import com.mycompany.sistemadeestoque.views.UsuarioLoginView;

public class Sistema {

    public Sistema() {
        iniciar();
    }
    
    private void iniciar() {
          new UsuarioLoginView().setVisible(true);
    }
}