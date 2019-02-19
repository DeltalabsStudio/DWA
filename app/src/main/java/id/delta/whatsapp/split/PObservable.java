package id.delta.whatsapp.split;

import java.util.Observable;

public class PObservable extends Observable {
    public void publicSetChanged() {
        setChanged();
    }
}
