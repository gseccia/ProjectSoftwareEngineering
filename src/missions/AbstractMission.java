package missions;

public abstract class AbstractMission implements Mission {

    private String targetId;

    public String getTargetId() {
        return targetId;
    }

    public void setTargetId(String targetId) {
        this.targetId = targetId;
    }

    @Override
    public boolean equals(Object obj) {
        return this.getClass() == obj.getClass() && targetId == ((AbstractMission) obj).getTargetId();
    }
}
