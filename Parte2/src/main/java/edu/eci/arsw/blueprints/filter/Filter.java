package edu.eci.arsw.blueprints.filter;

import edu.eci.arsw.blueprints.model.Blueprint;


public interface Filter {
    /**
     * Filter of the blueprints
     * @param blueprint that you want to filter
     * @return blueprint filtered
     */
    public abstract Blueprint filterBlueprint(Blueprint blueprint);
}
