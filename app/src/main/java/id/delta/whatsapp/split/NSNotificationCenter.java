package id.delta.whatsapp.split;

import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

public class NSNotificationCenter {
    private static final NSNotificationCenter ourInstance = new NSNotificationCenter();
    HashMap<NSNotificationName, PObservable> a = new HashMap();

    private NSNotificationCenter() {
    }

    public static NSNotificationCenter getInstance() {
        return ourInstance;
    }

    public void addObserver(NSNotificationName nSNotificationName, Observer observer) {
        PObservable pObservable = (PObservable) this.a.get(nSNotificationName);
        if (pObservable == null) {
            pObservable = new PObservable();
            this.a.put(nSNotificationName, pObservable);
        }
        pObservable.addObserver(observer);
    }

    public void post(NSNotificationName nSNotificationName, Object obj) {
        PObservable pObservable = (PObservable) this.a.get(nSNotificationName);
        if (pObservable != null) {
            pObservable.publicSetChanged();
            pObservable.notifyObservers(obj);
        }
    }

    public void removeObserver(NSNotificationName nSNotificationName, Observer observer) {
        Observable observable = (Observable) this.a.get(nSNotificationName);
        if (observable != null) {
            observable.deleteObserver(observer);
        }
    }
}
