/*
 * Copyright 2015 Len Payne <len.payne@lambtoncollege.ca>.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package services;

import entities.Product;
import java.util.List;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

/**
 *
 * @author Len Payne <len.payne@lambtoncollege.ca>
 */
@Path("/product")
public class ProductREST {

    @PersistenceContext(unitName = "sampleproductPU")
    EntityManager em;

    List<Product> productList;

    @GET
    public Response getAll() {
        JsonArrayBuilder json = Json.createArrayBuilder();
        Query q = em.createNamedQuery("Product.findAll");
        productList = q.getResultList();
        for (Product p : productList) {
            json.add(p.toJSON());
        }
        return Response.ok(json.build().toString()).build();
    }

    @GET
    @Path("{id}")
    public Response getById(@PathParam("id") int id) {
        Query q = em.createQuery("SELECT p FROM Product p WHERE p.productId = :productId");
        q.setParameter("productId", id);
        Product p = (Product) q.getSingleResult();
        return Response.ok(p.toJSON().toString()).build();
    }

    @POST
    @Consumes("application/json")
    public Response add(JsonObject json) {
        Response result;
        try {
            em.getTransaction().begin();
            Product p = new Product(json);
            em.persist(p);
            em.getTransaction().commit();
            result = Response.ok().build();
        } catch (Exception ex) {
            result = Response.status(500).entity(ex.getMessage()).build();
        }
        return result;
    }
}
