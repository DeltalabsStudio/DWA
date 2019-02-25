package com.whatsapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

public class NewGroup extends DialogToastActivity {

    public static void a(Activity activity, int n2, Collection<String> collection) {
        Intent intent = new Intent((Context)activity, GroupMembersSelector.class);
        intent.putExtra("entry_point", n2);
        if (collection != null && !collection.isEmpty()) {
            intent.putExtra("selected", (Serializable)new ArrayList(collection));
        }
        activity.startActivity(intent);
    }
}
