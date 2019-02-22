package id.delta.whatsapp.split.presenter;

import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

public class NotificationCenter {
    private static final NotificationCenter ourInstance = new NotificationCenter();
    HashMap<NotificationName, PublicObservable> a = new HashMap();

    private NotificationCenter() {
    }

    public static NotificationCenter getInstance() {
        return ourInstance;
    }

    public void addObserver(NotificationName nSNotificationName, Observer observer) {
        PublicObservable pObservable = (PublicObservable) this.a.get(nSNotificationName);
        if (pObservable == null) {
            pObservable = new PublicObservable();
            this.a.put(nSNotificationName, pObservable);
        }
        pObservable.addObserver(observer);
    }

    public void post(NotificationName nSNotificationName, Object obj) {
        PublicObservable pObservable = (PublicObservable) this.a.get(nSNotificationName);
        if (pObservable != null) {
            pObservable.publicSetChanged();
            pObservable.notifyObservers(obj);
        }
    }

    public void removeObserver(NotificationName nSNotificationName, Observer observer) {
        Observable observable = (Observable) this.a.get(nSNotificationName);
        if (observable != null) {
            observable.deleteObserver(observer);
        }
    }
}
