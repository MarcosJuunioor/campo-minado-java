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
import javax.ws.rs.DELETE;
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
@Path("bandeira")
public class BandeiraResource {

    @PUT
    @Path("{enderecoCelula}")
    @Produces(MediaType.APPLICATION_JSON)
    public Jogo colocarBandeira(@Context HttpServletRequest request, @PathParam("enderecoCelula") String enderecoCelula) {
        JogoResource.jogo.colocarBandeira(enderecoCelula);
        return JogoResource.jogo;
    }

    @DELETE
    @Path("{enderecoCelula}")
    @Produces(MediaType.APPLICATION_JSON)
    public Jogo retirarBandeira(@Context HttpServletRequest request, @PathParam("enderecoCelula") String enderecoCelula) {
        JogoResource.jogo.retirarBandeira(enderecoCelula);
        return JogoResource.jogo;
    }

}
