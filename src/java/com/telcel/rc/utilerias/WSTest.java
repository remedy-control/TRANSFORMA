/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.telcel.rc.utilerias;

import com.telcel.rc.actions.WSTransformaBES;

/**
 *
 * @author GCaballero
 */
public class WSTest {
    public static void main(String arg[]){
        WSTransformaBES wsTest = new WSTransformaBES();
        String vRes;
        vRes = wsTest.updateTT("INC000009054007", "", "CANCELADO DE PRUEBA", "Cancelled");
        System.out.println("RESULTADO DE LA OPERACION: " +vRes);
    }
}

