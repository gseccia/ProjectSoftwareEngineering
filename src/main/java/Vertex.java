package main.java;

public class Vertex{
    private int el;
    private boolean token;

    public Vertex(int el, boolean token) {
        this.el = el;
        this.token = token;
    }

    public int getEl() {
        return el;
    }

    public void setEl(int el) {
        this.el = el;
    }

    public boolean isToken() {
        return token;
    }

    public void setToken(boolean token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "Vertex{" +
                "el=" + el +
                ", token=" + token +
                '}';
    }
}