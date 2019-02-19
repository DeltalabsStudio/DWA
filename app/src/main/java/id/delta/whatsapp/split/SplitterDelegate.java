package id.delta.whatsapp.split;

public interface SplitterDelegate {
    void completed();

    void progress(int i, String str);

    void started();
}
