package edu.eci.arsw.blueprints.filter.impl;

import edu.eci.arsw.blueprints.filter.Filter;
import edu.eci.arsw.blueprints.model.Blueprint;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@Qualifier("sub")
public class SubFilter implements Filter {
    @Override
    public Blueprint filterBlueprint(Blueprint blueprint) {
        for(int i=1;i < blueprint.getPoints().size();i++) {
            blueprint.removePoint(blueprint.getPoints().get(i));
        }
        return blueprint;
    }
}
