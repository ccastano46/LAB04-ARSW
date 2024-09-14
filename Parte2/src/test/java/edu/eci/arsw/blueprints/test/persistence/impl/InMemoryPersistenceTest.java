/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.test.persistence.impl;


import edu.eci.arsw.blueprints.filter.impl.RedundancyFilter;
import edu.eci.arsw.blueprints.filter.impl.SubFilter;
import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.persistence.BlueprintPersistenceException;
import edu.eci.arsw.blueprints.persistence.BlueprintsPersistence;
import edu.eci.arsw.blueprints.persistence.impl.InMemoryBlueprintPersistence;
import edu.eci.arsw.blueprints.services.BlueprintsServices;
import org.junit.Test;

import java.util.List;

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
            BlueprintsServices blueprintsServices = new BlueprintsServices(ibpp,
                    new SubFilter());
            Point[] pts0=new Point[]{new Point(40, 40),new Point(15, 15)};
            Blueprint bp0=new Blueprint("mack", "maypaint",pts0);
            ibpp.saveBlueprint(bp0);
            Point[] pts=new Point[]{new Point(0, 0),new Point(10, 10)};
            Blueprint bp=new Blueprint("john", "thepaint",pts);
            ibpp.saveBlueprint(bp);
            assertEquals(blueprintsServices.getBlueprint("mack","maypaint"),bp0);
            assertEquals(blueprintsServices.getBlueprint("john","thepaint"),bp);
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
            BlueprintsServices blueprintsServices = new BlueprintsServices(ibpp,
                    new SubFilter());
            Point[] pts0=new Point[]{new Point(40, 40),new Point(15, 15)};
            Blueprint bp0=new Blueprint("john", "maypaint",pts0);
            ibpp.saveBlueprint(bp0);
            Point[] pts=new Point[]{new Point(0, 0),new Point(10, 10)};
            Blueprint bp=new Blueprint("john", "ht",pts);
            ibpp.saveBlueprint(bp);
            assertEquals(2,blueprintsServices.getBlueprintsByAuthor("john").size());
            assertTrue(blueprintsServices.getBlueprintsByAuthor("john").contains(new Blueprint("john", "maypaint",pts0)));
            assertTrue(blueprintsServices.getBlueprintsByAuthor("john").contains(new Blueprint("john", "ht",pts)));

        }catch (BlueprintPersistenceException e){
            fail("Blueprint persistence failed inserting the a blueprint.");
        }catch (BlueprintNotFoundException exception){
            fail(exception.getMessage());
        }

    }

    @Test
    public void filterSubBlueprintTest(){
        BlueprintsServices blueprintsServices = new BlueprintsServices(new InMemoryBlueprintPersistence(),
                new SubFilter());
        Point[] pts0=new Point[]{new Point(40, 40),new Point(15, 15)};
        Blueprint bp0=new Blueprint("john", "maypaint",pts0);
        bp0.addPoint(new Point(16,10));
        bp0.addPoint(new Point(16,20));
        bp0.addPoint(new Point(10,30));
        bp0.addPoint(new Point(50,60));
        bp0 = blueprintsServices.filterBlueprint(bp0);
        assertEquals(3,bp0.getPoints().size());
        assertTrue(bp0.getPoints().contains(new Point(40,40)));
        assertTrue(bp0.getPoints().contains(new Point(16,10)));
        assertTrue(bp0.getPoints().contains(new Point(10,30)));
        assertFalse(bp0.getPoints().contains(new Point(15,15)));
        assertFalse(bp0.getPoints().contains(new Point(16,20)));
        assertFalse(bp0.getPoints().contains(new Point(50,60)));
    }

    private int countPoints(Point point, List<Point> points){
        int count = 0;
        for(Point p : points){
            if(point.equals(p)) count++;
        }
        return count;
    }

    @Test
    public void filterRedudancyTest(){
        BlueprintsServices blueprintsServices = new BlueprintsServices(new InMemoryBlueprintPersistence(),
                new RedundancyFilter());
        Point[] pts0=new Point[]{new Point(40, 40),new Point(40, 40)};
        Point[] pts1=new Point[]{new Point(40, 40),new Point(40, 40),new Point(40, 40),new Point(40, 40)};
        Blueprint bp0=new Blueprint("john", "maypaint",pts0);
        Blueprint bp1=new Blueprint("john", "maypaint",pts1);
        bp0.addPoint(new Point(40,40));
        bp0.addPoint(new Point(1,1));
        bp0.addPoint(new Point(16,20));
        bp0.addPoint(new Point(16,20));
        bp0.addPoint(new Point(50,60));
        bp0.addPoint(new Point(1,1));
        bp0 = blueprintsServices.filterBlueprint(bp0);
        bp1 = blueprintsServices.filterBlueprint(bp1);
        assertEquals(5,bp0.getPoints().size());
        assertEquals(1,countPoints(new Point(40, 40),bp0.getPoints()));
        assertEquals(1,countPoints(new Point(16, 20),bp0.getPoints()));
        assertEquals(2,countPoints(new Point(1, 1),bp0.getPoints()));
        assertEquals(1,bp1.getPoints().size());


    }





    
}
