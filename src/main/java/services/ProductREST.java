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
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

/**
 *
 * @author Len Payne <len.payne@lambtoncollege.ca>
 */
@Path("/product")
public class ProductREST {

    @PersistenceContext(unitName="sampleproductPU")
    EntityManager em;

    List<Product> productList;

    @GET
    public Response getAll() {
        JsonArrayBuilder json = Json.createArrayBuilder();
        productList = em.createQuery("SELECT p FROM Product p").getResultList();
        for (Product p : productList) {
            json.add(p.toJSON());
        }
        return Response.ok(json.build()).build();
    }

}
