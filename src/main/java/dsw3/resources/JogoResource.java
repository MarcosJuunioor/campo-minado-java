/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dsw3.resources;

import Models.DimensoesTabuleiro;
import Models.Jogo;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author marco
 */
@Path("jogo")
public class JogoResource {
    protected static Jogo jogo;
     
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Jogo novoJogo(@Context HttpServletRequest request, DimensoesTabuleiro dt){
        jogo = new Jogo(dt); 
        return jogo; 
    }
    
    @PUT
    @Path("{celulaJogada}")
    @Produces(MediaType.APPLICATION_JSON)
    public Jogo jogar(@Context HttpServletRequest request, @PathParam("celulaJogada") String celulaJogada){
        jogo.revelarCelula(celulaJogada);
        return jogo; 
    }
    
}
