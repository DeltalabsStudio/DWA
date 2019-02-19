package id.delta.whatsapp.split;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.database.Cursor;
import android.media.MediaCodec.BufferInfo;
import android.media.MediaExtractor;
import android.media.MediaFormat;
import android.media.MediaMetadataRetriever;
import android.media.MediaMuxer;
import android.net.Uri;
import android.os.Build;
import android.os.Build.VERSION;
import android.provider.DocumentsContract;
import android.provider.MediaStore.Video.Media;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.HashMap;

import id.delta.whatsapp.BuildConfig;

public class Splitter {
    private Context context;
    private SplitterDelegate delegate;

    public Splitter(Context context) {
        this.context = context;
    }

    public Splitter(Context context, SplitterDelegate splitterDelegate) {
        this.context = context;
        this.delegate = splitterDelegate;
    }

    @TargetApi(19)
    private String generateFromKitkat(Uri uri, Context context) {
        String str = null;
        if (DocumentsContract.isDocumentUri(context, uri)) {
            String str2 = DocumentsContract.getDocumentId(uri).split(":")[1];
            String[] strArr = new String[]{"_data"};
            Cursor query = context.getContentResolver().query(Media.EXTERNAL_CONTENT_URI, strArr, "_id=?", new String[]{str2}, null);
            int columnIndex = query.getColumnIndex(strArr[0]);
            if (query.moveToFirst()) {
                str = query.getString(columnIndex);
            }
            query.close();
        }
        return str;
    }

    public String generatePath(Uri uri) {
        String generateFromKitkat = (VERSION.SDK_INT >= 19 ? 1 : 0) != 0 ? generateFromKitkat(uri, this.context) : null;
        if (generateFromKitkat != null) {
            return generateFromKitkat;
        }
        String string;
        Cursor query = this.context.getContentResolver().query(uri, new String[]{"_data"}, null, null, null);
        if (query != null) {
            string = query.moveToFirst() ? query.getString(query.getColumnIndexOrThrow("_data")) : generateFromKitkat;
            query.close();
        } else {
            string = generateFromKitkat;
        }
        if (string == null) {
            string = uri.getPath();
        }
        return string;
    }

    public void split(String str, double d, boolean z) {
        int i = 0;
        File file = new File(str);
        if (!z && file.isDirectory()) {
            String[] list = file.list();
            for (String file2 : list) {
                new File(file, file2).delete();
            }
        }
        int splitDuration = DataStorage.getInstance().getSplitDuration();
        String str2 = BuildConfig.FLAVOR;
        String selectedVideoPath = z ? DataStorage.getInstance().getSelectedVideoPath() : generatePath(DataStorage.getInstance().getVideoPath());
        MediaExtractor mediaExtractor = new MediaExtractor();
        try {
            mediaExtractor.setDataSource(selectedVideoPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        int trackCount = mediaExtractor.getTrackCount();
        if (this.delegate != null) {
            this.delegate.started();
        }
        int i2 = 1;
        while (((double) i) < d) {
            String str3 = str + "Part_" + i2 + ".mp4";
            try {
                trim(i, i + (splitDuration * 1000), str3, trackCount, mediaExtractor, selectedVideoPath);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (this.delegate != null) {
                this.delegate.progress(i2, str3);
            }
            i += splitDuration * 1000;
            i2++;
        }
        if (this.delegate != null) {
            this.delegate.completed();
        }
        if (z) {
            new File(selectedVideoPath).delete();
        }
    }

    @SuppressLint("WrongConstant")
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    public void trim(int i, int i2, String str, int i3, MediaExtractor mediaExtractor, String str2) throws IOException {
        new File(str).getParentFile().mkdirs();
        MediaMuxer mediaMuxer = new MediaMuxer(str, 0);
        HashMap hashMap = new HashMap(i3);
        int i4 = -1;
        int i5 = 0;
        while (i5 < i3) {
            int integer;
            MediaFormat trackFormat = mediaExtractor.getTrackFormat(i5);
            trackFormat.getString("mime");
            mediaExtractor.selectTrack(i5);
            hashMap.put(Integer.valueOf(i5), Integer.valueOf(mediaMuxer.addTrack(trackFormat)));
            if (trackFormat.containsKey("max-input-size")) {
                integer = trackFormat.getInteger("max-input-size");
                if (integer <= i4) {
                    integer = i4;
                }
            } else {
                integer = i4;
            }
            i5++;
            i4 = integer;
        }
        if (i4 < 0) {
            i4 = 4096;
        }
        MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
        mediaMetadataRetriever.setDataSource(str2);
        String extractMetadata = mediaMetadataRetriever.extractMetadata(24);
        if (extractMetadata != null) {
            int integer = Integer.parseInt(extractMetadata);
            if (integer >= 0) {
                mediaMuxer.setOrientationHint(integer);
            }
        }
        if (i > 0) {
            mediaExtractor.seekTo((long) (i * 1000), 2);
        }
        ByteBuffer allocate = ByteBuffer.allocate(i4);
        BufferInfo bufferInfo = new BufferInfo();
        try {
            mediaMuxer.start();
            while (true) {
                bufferInfo.offset = 0;
                bufferInfo.size = mediaExtractor.readSampleData(allocate, 0);
                if (bufferInfo.size >= 0) {
                    bufferInfo.presentationTimeUs = mediaExtractor.getSampleTime();
                    if (i2 > 0 && bufferInfo.presentationTimeUs > ((long) (i2 * 1000))) {
                        break;
                    }
                    bufferInfo.flags = 1;
                    mediaMuxer.writeSampleData(((Integer) hashMap.get(Integer.valueOf(mediaExtractor.getSampleTrackIndex()))).intValue(), allocate, bufferInfo);
                    mediaExtractor.advance();
                } else {
                    break;
                }
            }
            bufferInfo.size = 0;
            mediaMuxer.stop();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } finally {
            mediaMuxer.release();
        }
    }

    public void trim(String str, String str2, int i, int i2) {
        File file = new File(str);
        if (file.isDirectory()) {
            String[] list = file.list();
            for (String file2 : list) {
                new File(file, file2).delete();
            }
        }
        String generatePath = generatePath(DataStorage.getInstance().getVideoPath());
        MediaExtractor mediaExtractor = new MediaExtractor();
        try {
            mediaExtractor.setDataSource(generatePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            trim(i, i2, str2, mediaExtractor.getTrackCount(), mediaExtractor, generatePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.delegate.completed();
    }
}
