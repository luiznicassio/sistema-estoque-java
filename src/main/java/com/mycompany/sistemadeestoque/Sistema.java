/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sistemadeestoque;

import com.mycompany.sistemadeestoque.views.UsuarioCadastroView;
import com.mycompany.sistemadeestoque.views.UsuarioLoginView;


 
public class Sistema {


    public Sistema() {
        iniciar();
    }

   
    private void iniciar() {
        //new UsuarioCadastroView().setVisible(true);
          new UsuarioLoginView().setVisible(true);
       
    }
}