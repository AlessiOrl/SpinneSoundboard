/*
 * Copyright (C) 2014 Caleb Sabatini
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.alessiorl.spinnesoundboard.Helpers;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;

import com.alessiorl.spinnesoundboard.R;

import java.util.ArrayList;

public abstract class SoundStore {

    public static ArrayList<Sound> getSounds(Context context) {
        Resources res = context.getApplicationContext().getResources();

        TypedArray labels = res.obtainTypedArray(R.array.labels);
        TypedArray ids = res.obtainTypedArray(R.array.ids);

        ArrayList<Sound> sounds = new ArrayList<>();

        for (int i = 0; i < labels.length(); i++) {
            int reso = ids.getResourceId(i, -1);
            sounds.add(new Sound(reso, labels.getString(i), Integer.toString(reso), "0"));
        }

        labels.recycle();
        ids.recycle();

        return sounds;
    }

}
