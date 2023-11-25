package org.kudladev.utils;

import java.util.ArrayList;
import java.util.List;

public class SpriteSheetList {

    private List<SpriteSheetValue> spriteSheets;

    public SpriteSheetList(){
        this.spriteSheets = new ArrayList<SpriteSheetValue>();
    }

    public void add(SpriteSheetValue spriteSheetValue){
        this.spriteSheets.add(spriteSheetValue);
    }

    public List<SpriteSheetValue> getSprites(){
        return spriteSheets;
    }




}
