package id.delta.whatsapp.split.presenter;

import java.util.Observable;

public class PublicObservable extends Observable {
    public void publicSetChanged() {
        setChanged();
    }
}
