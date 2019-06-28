package uid.worker;

/**
 * WorkerNodeType
 * <li>CONTAINER: Such as Docker
 * <li>ACTUAL: Actual machine
 * Created by Oak on 2018/7/19.
 * Description:
 */
public enum WorkerNodeType  {

    CONTAINER(1), ACTUAL(2);

    /**
     * Lock type
     */
    private final Integer type;

    /**
     * Constructor with field of type
     */
     WorkerNodeType(Integer type) {
        this.type = type;
    }

    public Integer value() {
        return type;
    }

}
