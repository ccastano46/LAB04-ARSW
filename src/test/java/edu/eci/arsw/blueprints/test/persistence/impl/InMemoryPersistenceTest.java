/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.test.persistence.impl;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.persistence.BlueprintPersistenceException;
import edu.eci.arsw.blueprints.persistence.BlueprintsPersistence;
import edu.eci.arsw.blueprints.persistence.impl.InMemoryBlueprintPersistence;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author hcadavid
 */
public class InMemoryPersistenceTest {
    
    @Test
    public void saveNewAndLoadTest() throws BlueprintPersistenceException, BlueprintNotFoundException{
        InMemoryBlueprintPersistence ibpp=new InMemoryBlueprintPersistence();

        Point[] pts0=new Point[]{new Point(40, 40),new Point(15, 15)};
        Blueprint bp0=new Blueprint("mack", "mypaint",pts0);
        
        ibpp.saveBlueprint(bp0);
        
        Point[] pts=new Point[]{new Point(0, 0),new Point(10, 10)};
        Blueprint bp=new Blueprint("john", "thepaint",pts);
        
        ibpp.saveBlueprint(bp);
        
        assertNotNull("Loading a previously stored blueprint returned null.",ibpp.getBlueprint(bp.getAuthor(), bp.getName()));
        
        assertEquals("Loading a previously stored blueprint returned a different blueprint.",ibpp.getBlueprint(bp.getAuthor(), bp.getName()), bp);
        
    }


    @Test
    public void saveExistingBpTest() {
        InMemoryBlueprintPersistence ibpp=new InMemoryBlueprintPersistence();
        
        Point[] pts=new Point[]{new Point(0, 0),new Point(10, 10)};
        Blueprint bp=new Blueprint("john", "thepaint",pts);
        
        try {
            ibpp.saveBlueprint(bp);
        } catch (BlueprintPersistenceException ex) {
            fail("Blueprint persistence failed inserting the first blueprint.");
        }
        
        Point[] pts2=new Point[]{new Point(10, 10),new Point(20, 20)};
        Blueprint bp2=new Blueprint("john", "thepaint",pts2);

        try{
            ibpp.saveBlueprint(bp2);
            fail("An exception was expected after saving a second blueprint with the same name and autor");
        }
        catch (BlueprintPersistenceException ex){
            
        }
    }


    @Test
    public void getBlueprintTest() {
        try{
            BlueprintsPersistence ibpp= new InMemoryBlueprintPersistence();
            Point[] pts0=new Point[]{new Point(40, 40),new Point(15, 15)};
            Blueprint bp0=new Blueprint("mack", "maypaint",pts0);
            ibpp.saveBlueprint(bp0);
            Point[] pts=new Point[]{new Point(0, 0),new Point(10, 10)};
            Blueprint bp=new Blueprint("john", "thepaint",pts);
            ibpp.saveBlueprint(bp);
            assertEquals(ibpp.getBlueprint("mack","maypaint"),bp0);
            assertEquals(ibpp.getBlueprint("john","thepaint"),bp);
        }catch (BlueprintPersistenceException e){
            fail("Blueprint persistence failed inserting the a blueprint.");
        }catch (BlueprintNotFoundException exception){
            fail(exception.getMessage());
        }

    }

    @Test
    public void getBlueprintsByAuthorTest() {
        try{
            BlueprintsPersistence ibpp= new InMemoryBlueprintPersistence();
            Point[] pts0=new Point[]{new Point(40, 40),new Point(15, 15)};
            Blueprint bp0=new Blueprint("john", "maypaint",pts0);
            ibpp.saveBlueprint(bp0);
            Point[] pts=new Point[]{new Point(0, 0),new Point(10, 10)};
            Blueprint bp=new Blueprint("john", "ht",pts);
            ibpp.saveBlueprint(bp);
            assertEquals(ibpp.getBlueprintsByAuthor("john").size(),2);
            assertTrue(ibpp.getBlueprintsByAuthor("john").contains(new Blueprint("john", "maypaint",pts0)));
            assertTrue(ibpp.getBlueprintsByAuthor("john").contains(new Blueprint("john", "ht",pts)));

        }catch (BlueprintPersistenceException e){
            fail("Blueprint persistence failed inserting the a blueprint.");
        }catch (BlueprintNotFoundException exception){
            fail(exception.getMessage());
        }

    }



    
}
