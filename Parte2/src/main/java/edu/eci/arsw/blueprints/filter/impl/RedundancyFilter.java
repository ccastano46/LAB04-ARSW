package edu.eci.arsw.blueprints.filter.impl;

import edu.eci.arsw.blueprints.filter.Filter;
import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@Qualifier("redundancy")
public class RedundancyFilter implements Filter {

    @Override
    public Blueprint filterBlueprint(Blueprint blueprint) {
        try{
            Point currentPoint;
            Point nextPoint;
            for(int i = 0; i < blueprint.getPoints().size()-1; i++) {
                currentPoint = blueprint.getPoints().get(i);
                nextPoint = blueprint.getPoints().get(i+1);
                while (currentPoint.equals(nextPoint)) {
                    blueprint.getPoints().remove(nextPoint);
                    nextPoint = blueprint.getPoints().get(i+1);
                }
            }
        }catch (IndexOutOfBoundsException e){
            return blueprint;
        }
        return blueprint;
    }
}
