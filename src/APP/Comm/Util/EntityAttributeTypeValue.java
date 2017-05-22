package APP.Comm.Util;

/**
 * entity属性类型和值 编写者：施建龙 时间：2013年1月11日 10:40:29
 */
public class EntityAttributeTypeValue {
	public EntityAttributeTypeValue(Class type, Object value) {

		setAttributeType(type);
		setValue(value);
	}

	private Class privateAttributeType;

	public final Class getAttributeType() {
		return privateAttributeType;
	}

	public final void setAttributeType(Class value) {
		privateAttributeType = value;
	}

	private Object privateValue;

	public final Object getValue() {
		return privateValue;
	}

	public final void setValue(Object value) {
		privateValue = value;
	}

}