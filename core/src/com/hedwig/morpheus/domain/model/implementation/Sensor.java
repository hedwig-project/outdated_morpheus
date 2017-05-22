package com.hedwig.morpheus.domain.model.implementation;

import com.hedwig.morpheus.domain.model.abstracts.Component;

/**
 * Created by hugo on 22/05/17. All rights reserved.
 */
public class Sensor extends Component{

    public Sensor(String name, String type, String requiredPermissions, String location, String topic) {
        super(name, type, requiredPermissions, location, topic);
    }
}
