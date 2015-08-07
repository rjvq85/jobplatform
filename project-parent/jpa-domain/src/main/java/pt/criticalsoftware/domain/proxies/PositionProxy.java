package pt.criticalsoftware.domain.proxies;

import pt.criticalsoftware.domain.entities.PositionEntity;
import pt.criticalsoftware.service.model.IPosition;

public class PositionProxy implements IEntityAware<PositionEntity>, IPosition {
	
	private PositionEntity position;
	
	public PositionProxy() {
		this(null);
	}
	
	public PositionProxy(PositionEntity entity) {
		position = entity != null ? entity : new PositionEntity();
	}
	
	@Override
	public PositionEntity getEntity() {
		return this.position;
	}

}
