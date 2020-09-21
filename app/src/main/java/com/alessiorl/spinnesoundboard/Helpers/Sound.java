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

public class Sound {

    private String mName;
    private String key_id;
    private int mResourceId;
    private String favStatus;


    public Sound(int resourceId, String title, String key_id, String favStatus) {
        this.mName = title;
        this.key_id = key_id;
        this.mResourceId = resourceId;
        this.favStatus = favStatus;
    }

    public int getResourceId() {
        return mResourceId;
    }

    public void setResourceId(int resourceId) {
        this.mResourceId = resourceId;
    }

    public String getTitle() {
        return mName;
    }

    public void setTitle(String name) {
        this.mName = name;
    }

    public String getKey_id() {
        return this.key_id;
    }

    public String getFavStatus() {
        return favStatus;
    }

    public void setFavStatus(String favStatus) {
        this.favStatus = favStatus;
    }

    @Override
    public String toString() {
        return mName;
    }
}
