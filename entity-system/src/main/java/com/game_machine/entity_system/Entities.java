package com.game_machine.entity_system;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.game_machine.entity_system.generated.Entity;

public class Entities {

	public static String encoding = "protobuf";
	
	private static final Logger log = LoggerFactory.getLogger(Entities.class);

	private HashMap<Integer, Entity> entities = new HashMap<Integer, Entity>();

	public Entities() {
	}

	public void addEntity(Entity entity) {
		entities.put(entity.getId(), entity);
	}

	public Entity getEntity(Integer id) {
		return entities.get(id);
	}

	public Boolean hasEntity(Integer id) {
		if (entities.containsKey(id)) {
			return true;
		} else {
			return false;
		}
	}
	
	public HashMap<Integer, Entity> getEntities() {
		return this.entities;
	}

	public static Class<? extends Component> findClassByName(String name) {
		String qualifiedClassName = null;
		Class<? extends Component> klass = null;

		qualifiedClassName = "com.game_machine.proto." + name;
		try {
			klass = (Class<? extends Component>) Class.forName(Class.forName(qualifiedClassName).getName());

		} catch (ClassNotFoundException e) {
		}
		if (klass == null) {
			throw new RuntimeException("Unable to find class (" + qualifiedClassName + ") from @class=" + name);
		}
		return klass;

	}

}
