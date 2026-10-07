package resourcesrest;

import entities.Etudiant;
import entities.EtudiantList;
import entities.Option;
import metiers.EtudiantBusiness;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path("etudiants")
public class RestEtudiant {
    public static EtudiantBusiness etuB = new EtudiantBusiness();

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addEtudiant(Etudiant e) {
        if (e == null || e.getOption() == null) {
            return Response.status(404).build();
        }
        if (etuB.addEtudiant(e)) {
            return Response.status(200).build();
        }
        return Response.status(404).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllEtudiants() {
        return Response.status(200).entity(etuB.getAllEtudiants()).build();
    }

    @GET
    @Path("{identifiant}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEtudiantByIdentifiant(@PathParam("identifiant") String identifiant) {
        Etudiant e = etuB.getEtudiantByIdentifiant(identifiant);
        if (e != null) {
            return Response.status(200).entity(e).build();
        }
        return Response.status(404).build();
    }

    @DELETE
    @Path("{identifiant}")
    public Response deleteEtudiant(@PathParam("identifiant") String identifiant) {
        if (etuB.deleteEtudiant(identifiant)) {
            return Response.status(204).build();
        }
        return Response.status(404).build();
    }

    @PUT
    @Path("{identifiant}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateEtudiant(@PathParam("identifiant") String identifiant, Etudiant e) {
        if (etuB.updateEtudiant(identifiant, e)) {
            return Response.status(200).entity(e).build();
        }
        return Response.status(404).build();
    }

    @GET
    @Path("option")
    @Produces(MediaType.APPLICATION_XML)
    public Response getEtudiantsByOption(@QueryParam("codeOption") int codeOption) {
        Option option = RestOption.optB.getOptionByCode(codeOption);
        if (option == null) {
            return Response.status(404).build();
        }
        List<Etudiant> copies = new ArrayList<Etudiant>();
        for (Etudiant e : etuB.getEtudiantsByOption(option)) {
            copies.add(new Etudiant(e.getIdentifiant(), e.getNom(), e.getPrenom(),
                    null, e.getAnneeEtude(), e.getEmail()));
        }
        return Response.status(200).entity(new EtudiantList(copies)).build();
    }
}