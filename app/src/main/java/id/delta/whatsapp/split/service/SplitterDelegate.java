package id.delta.whatsapp.split.service;

public interface SplitterDelegate {
    void completed();

    void progress(int i, String str);

    void started();
}
